package com.example.springframework.boot.data.source.aop.annotation.config.ds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> DATA_SOURCE_KEY = new ThreadLocal<>();

    public static void setDataSourceKey(String dataSource) {
        DATA_SOURCE_KEY.set(dataSource);
    }

    private static void clear() {
        DATA_SOURCE_KEY.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // 使用ThreadLocal保证线程安全，并且得到当前线程中的数据源key
        String dataSourceKey = DATA_SOURCE_KEY.get() == null ? DataSourceKeyConstant.MASTER : DATA_SOURCE_KEY.get();
        log.info("-----数据源key为：" + dataSourceKey + "-----");
        //注意：要清除变量
        clear();
        return dataSourceKey;
    }

}