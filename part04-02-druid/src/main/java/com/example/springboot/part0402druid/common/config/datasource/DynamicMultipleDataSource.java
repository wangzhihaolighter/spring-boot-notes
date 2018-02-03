package com.example.springboot.part0402druid.common.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicMultipleDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal< String > DATA_SOURCE_KEY = new ThreadLocal<>();

    static void setDataSourceKey ( String dataSource ) {
        DATA_SOURCE_KEY.set( dataSource );
    }

    private static void clear () {
        DATA_SOURCE_KEY.remove();
    }

    @Override
    protected Object determineCurrentLookupKey () {
        final String lookupKey = DATA_SOURCE_KEY.get();
        clear();
        System.out.println("*****动态数据源获取数据源*****"+lookupKey);
        return lookupKey;
    }


}
