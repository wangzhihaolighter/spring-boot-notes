package com.example.oshi.service;

import com.example.oshi.monitor.*;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 系统监控服务类
 */
@Component
public class MonitorService {

    public Map<String, Object> getServerMonitorInfo() {
        Map<String, Object> resMap = new HashMap<>(8);
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        HardwareAbstractionLayer hal = si.getHardware();
        //系统信息
        resMap.put("sys", getSystemInfo(os));
        //cpu信息
        resMap.put("cpu", getCpuInfo(hal.getProcessor()));
        //内存信息
        resMap.put("memory", getMemoryInfo(hal.getMemory()));
        //交换区信息
        resMap.put("swap", getSwapInfo(hal.getMemory()));
        //磁盘
        resMap.put("disk", getDiskInfo(os));
        //jvm信息
        resMap.put("jvm", getJvmInfo());
        return resMap;
    }

    private SysInfo getSystemInfo(OperatingSystem os) {
        Properties props = System.getProperties();
        //系统名称
        String osName = props.getProperty("os.name");
        //架构名称
        String osArch = props.getProperty("os.arch");

        SysInfo sysInfo = new SysInfo();
        sysInfo.setOs(os.toString());
        sysInfo.setOsName(osName);
        sysInfo.setOsArch(osArch);
        sysInfo.setLocalIp(getLocalIp());
        return sysInfo;
    }

    private CpuInfo getCpuInfo(CentralProcessor processor) {
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.setName(processor.getProcessorIdentifier().getName());
        cpuInfo.setPhysicalPackageCount(String.valueOf(processor.getPhysicalPackageCount()));
        cpuInfo.setPhysicalProcessorCount(String.valueOf(processor.getPhysicalProcessorCount()));
        cpuInfo.setLogicalProcessorCount(String.valueOf(processor.getLogicalProcessorCount()));
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 等待1秒...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
        cpuInfo.setSysUtilization(RATE_DECIMAL_FORMAT.format(sys * 1.0 / totalCpu));
        cpuInfo.setUserUtilization(RATE_DECIMAL_FORMAT.format(user * 1.0 / totalCpu));
        cpuInfo.setIoWaitUtilization(RATE_DECIMAL_FORMAT.format(iowait * 1.0 / totalCpu));
        cpuInfo.setUsageUtilization(RATE_DECIMAL_FORMAT.format(1.0 - (idle * 1.0 / totalCpu)));
        return cpuInfo;
    }

    private MemoryInfo getMemoryInfo(GlobalMemory memory) {
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long availableByte = memory.getAvailable();

        MemoryInfo memoryInfo = new MemoryInfo();
        memoryInfo.setTotal(FormatUtil.formatBytes(totalByte));
        memoryInfo.setAvailable(FormatUtil.formatBytes(availableByte));
        memoryInfo.setUsed(FormatUtil.formatBytes(totalByte - availableByte));
        memoryInfo.setUsageRate(RATE_DECIMAL_FORMAT.format((totalByte - availableByte) * 1.0 / totalByte));
        return memoryInfo;
    }

    private SwapInfo getSwapInfo(GlobalMemory memory) {
        SwapInfo swapInfo = new SwapInfo();
        swapInfo.setTotal(FormatUtil.formatBytes(memory.getVirtualMemory().getSwapTotal()));
        swapInfo.setUsed(FormatUtil.formatBytes(memory.getVirtualMemory().getSwapUsed()));
        swapInfo.setAvailable(FormatUtil.formatBytes(memory.getVirtualMemory().getSwapTotal() - memory.getVirtualMemory().getSwapUsed()));
        swapInfo.setUsageRate(RATE_DECIMAL_FORMAT.format(memory.getVirtualMemory().getSwapUsed() * 1.0 / memory.getVirtualMemory().getSwapTotal()));
        return swapInfo;
    }

    private List<DiskInfo> getDiskInfo(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        List<DiskInfo> diskInfoList = new ArrayList<>();
        for (OSFileStore fs : fileStores) {
            DiskInfo diskInfo = new DiskInfo();
            diskInfo.setName(fs.getName());
            diskInfo.setTotal(fs.getTotalSpace() > 0 ? formatByte(fs.getTotalSpace()) : "?");
            diskInfo.setAvailable(formatByte(fs.getUsableSpace()));
            long used = fs.getTotalSpace() - fs.getUsableSpace();
            diskInfo.setUsed(formatByte(used));
            diskInfo.setUsageRate(RATE_DECIMAL_FORMAT.format(used * 1.0 / fs.getTotalSpace()));
            diskInfoList.add(diskInfo);
        }
        return diskInfoList;
    }

    private JvmInfo getJvmInfo() {
        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        //jdk路径
        String jdkHome = props.getProperty("java.home");
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //jvm最大可申请
        long jvmJvmMaxMemoryByte = runtime.maxMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        // jvm 运行时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        LocalDateTime jvmStartTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        Duration duration = Duration.between(jvmStartTime, LocalDateTime.now());

        JvmInfo jvmInfo = new JvmInfo();
        jvmInfo.setJdkVersion(jdkVersion);
        jvmInfo.setJdkHome(jdkHome);
        jvmInfo.setJvmTotalMemory(FormatUtil.formatBytes(jvmTotalMemoryByte));
        jvmInfo.setJvmMaxMemory(FormatUtil.formatBytes(jvmJvmMaxMemoryByte));
        jvmInfo.setJvmUsedMemory(FormatUtil.formatBytes(jvmTotalMemoryByte - freeMemoryByte));
        jvmInfo.setJvmFreeMemory(FormatUtil.formatBytes(freeMemoryByte));
        jvmInfo.setJvmMemoryUtilization(RATE_DECIMAL_FORMAT.format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        jvmInfo.setElapsedTime(formatBetweenTime(duration));
        return jvmInfo;
    }

    // =============== 分割线 ===============

    public static final DecimalFormat RATE_DECIMAL_FORMAT = new DecimalFormat("#.##%");

    /**
     * 获取当前机器的IP
     *
     * @return /
     */
    public static String getLocalIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return "unknown";
        }
        byte[] ipAddr = addr.getAddress();
        StringBuilder ipAddrStr = new StringBuilder();
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr.append(".");
            }
            ipAddrStr.append(ipAddr[i] & 0xFF);
        }
        return ipAddrStr.toString();
    }

    public static String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    public static String formatBetweenTime(Duration duration) {
        StringBuilder sb = new StringBuilder();
        long seconds = duration.getSeconds();
        long day = seconds / (60L * 60L * 60L);
        long hour = seconds / (60L * 60L) - day * 24L;
        long minute = seconds / 60L - day * 24L * 60L - hour * 60L;
        long betweenOfSecond = ((day * 24L + hour) * 60L + minute) * 60L;
        long second = seconds - betweenOfSecond;
        return sb
                .append(day).append("天")
                .append(hour).append("小时")
                .append(minute).append("分钟")
                .append(second).append("秒")
                .toString();
    }

}
