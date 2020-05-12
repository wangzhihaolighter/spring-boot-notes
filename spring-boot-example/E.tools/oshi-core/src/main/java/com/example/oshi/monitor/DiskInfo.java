package com.example.oshi.monitor;

/**
 * 磁盘信息
 */
public class DiskInfo {
    /**
     * 文件系统名
     */
    private String name;
    /**
     * 总空间大小
     */
    private String total;
    /**
     * 已用空间大小
     */
    private String used;
    /**
     * 剩余空间大小
     */
    private String available;
    /**
     * 空间使用率
     */
    private String usageRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(String usageRate) {
        this.usageRate = usageRate;
    }
}
