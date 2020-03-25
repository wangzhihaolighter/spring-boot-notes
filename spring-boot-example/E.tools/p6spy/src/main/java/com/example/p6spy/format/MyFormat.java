package com.example.p6spy.format;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return !"".equals(sql.trim()) ?
                String.format(
                        "[ 当前时间：%s ] --- | SQL耗时：%s ms | 连接信息：%s - %s | 执行语句 : %s"
                        , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        , elapsed
                        , category
                        , connectionId
                        , sql
                )
                :
                String.format(
                        "[ 当前时间：%s ] --- | 耗时：%s ms | 连接信息：%s - %s"
                        , LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                        , elapsed
                        , category
                        , connectionId
                );
    }
}