package com.example.oshi.monitor;

/**
 * 内存信息
 */
public class MemoryInfo {
    /**
     * 总内存
     */
    private String total;
    /**
     * 已用内存
     */
    private String used;
    /**
     * 剩余内存
     */
    private String available;
    /**
     * 内存使用率
     */
    private String usageRate;

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
