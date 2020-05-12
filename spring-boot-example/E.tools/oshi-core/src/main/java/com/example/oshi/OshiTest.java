package com.example.oshi;

import com.example.oshi.service.MonitorService;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class OshiTest {

    public static void main(String[] args) {
        try {
            OshiTest.printlnCpuInfo();
            OshiTest.getMemoryInfo();
            OshiTest.getThread();
            OshiTest.getSysInfo();
            OshiTest.getJvmInfo();
            OshiTest.getDiskInfo();
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printlnCpuInfo() throws InterruptedException {
        System.out.println("----------------cpu信息----------------");
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);
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
        System.out.println("cpu核数:" + processor.getLogicalProcessorCount());
        System.out.println("cpu系统使用率:" + MonitorService.RATE_DECIMAL_FORMAT.format(sys * 1.0 / totalCpu));
        System.out.println("cpu用户使用率:" + MonitorService.RATE_DECIMAL_FORMAT.format(user * 1.0 / totalCpu));
        System.out.println("cpu当前等待率:" + MonitorService.RATE_DECIMAL_FORMAT.format(iowait * 1.0 / totalCpu));
        System.out.println("cpu当前使用率:" + MonitorService.RATE_DECIMAL_FORMAT.format(1.0 - (idle * 1.0 / totalCpu)));
    }

    public static void getMemoryInfo() {
        System.out.println("----------------主机内存信息----------------");
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long availableByte = memory.getAvailable();
        System.out.println("总内存 = " + MonitorService.formatByte(totalByte));
        System.out.println("已用内存 = " + MonitorService.formatByte(totalByte - availableByte));
        System.out.println("剩余内存 = " + MonitorService.formatByte(availableByte));
        System.out.println("内存使用率：" + MonitorService.RATE_DECIMAL_FORMAT.format((totalByte - availableByte) * 1.0 / totalByte));
    }

    public static void getSysInfo() {
        System.out.println("----------------操作系统信息----------------");
        Properties props = System.getProperties();
        //系统名称
        String osName = props.getProperty("os.name");
        //架构名称
        String osArch = props.getProperty("os.arch");
        System.out.println("操作系统名 = " + osName);
        System.out.println("系统架构 = " + osArch);
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        System.out.println("系统信息 = " + os.toString());
    }

    public static void getJvmInfo() {
        System.out.println("----------------jvm信息----------------");
        Properties props = System.getProperties();
        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //jvm最大可申请
        long jvmMaxMemoryByte = runtime.maxMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        //jdk路径
        String jdkHome = props.getProperty("java.home");
        // jvm 运行时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        LocalDateTime jvmStartTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        Duration duration = Duration.between(jvmStartTime, LocalDateTime.now());

        System.out.println("java版本 = " + jdkVersion);
        System.out.println("jdkHome = " + jdkHome);
        System.out.println("jvm内存总量 = " + MonitorService.formatByte(jvmTotalMemoryByte));
        System.out.println("jvm最大可用内存总数 = " + MonitorService.formatByte(jvmMaxMemoryByte));
        System.out.println("jvm已使用内存 = " + MonitorService.formatByte(jvmTotalMemoryByte - freeMemoryByte));
        System.out.println("jvm剩余内存 = " + MonitorService.formatByte(freeMemoryByte));
        System.out.println("jvm内存使用率 = " + MonitorService.RATE_DECIMAL_FORMAT.format((jvmTotalMemoryByte - freeMemoryByte) * 1.0 / jvmTotalMemoryByte));
        System.out.println(String.format("项目运行时间：%s天%s小时%s秒", duration.toDays(), duration.toHours(), duration.toMillis() / 1000));
    }

    public static void getThread() {
        System.out.println("----------------线程信息----------------");
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();

        while (currentGroup.getParent() != null) {
            // 返回此线程组的父线程组
            currentGroup = currentGroup.getParent();
        }
        //此线程组中活动线程的估计数
        int noThreads = currentGroup.activeCount();

        Thread[] lstThreads = new Thread[noThreads];
        //把对此线程组中的所有活动子组的引用复制到指定数组中。
        currentGroup.enumerate(lstThreads);
        for (Thread thread : lstThreads) {
            System.out.println("线程数量：" + noThreads + " 线程id：" + thread.getId() + " 线程名称：" + thread.getName() + " 线程状态：" + thread.getState());
        }
    }

    private static void getDiskInfo() {
        System.out.println("----------------磁盘信息----------------");
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fileStores = fileSystem.getFileStores();
        for (OSFileStore fs : fileStores) {
            Map<String, Object> diskInfo = new LinkedHashMap<>();
            diskInfo.put("name", fs.getName());
            diskInfo.put("total", fs.getTotalSpace() > 0 ? MonitorService.formatByte(fs.getTotalSpace()) : "?");
            long used = fs.getTotalSpace() - fs.getUsableSpace();
            diskInfo.put("available", MonitorService.formatByte(fs.getUsableSpace()));
            diskInfo.put("used", MonitorService.formatByte(used));
            diskInfo.put("usageRate", MonitorService.RATE_DECIMAL_FORMAT.format(used * 1.0 / fs.getTotalSpace()));
            System.out.println(diskInfo);
        }

    }

}
