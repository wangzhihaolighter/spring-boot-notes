package com.example.springframework.boot.utils.pojo;

import com.example.springframework.boot.utils.pojo.orika.InstantConverter;
import com.example.springframework.boot.utils.pojo.orika.LocalDateConverter;
import com.example.springframework.boot.utils.pojo.orika.LocalDateTimeConverter;
import com.example.springframework.boot.utils.pojo.orika.LocalTimeConverter;
import com.example.springframework.boot.utils.pojo.orika.OffsetDateTimeConverter;
import com.example.springframework.boot.utils.pojo.orika.ZonedDateTimeConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * orika简单封装，用于对象拷贝
 * 实现：深度转换Bean<->Bean的Mapper.
 * 注意：仅适用于普通的bean, 如果遇到性能瓶颈请使用传统的setter
 * 来源：https://github.com/prontera/spring-cloud-rest-tcc
 * 作者：Zhao Junjian
 */
public final class OrikaMapper {

    private static final MapperFacade FACADE;

    static {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        final ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new InstantConverter());
        converterFactory.registerConverter(new LocalDateConverter());
        converterFactory.registerConverter(new LocalDateTimeConverter());
        converterFactory.registerConverter(new LocalTimeConverter());
        converterFactory.registerConverter(new OffsetDateTimeConverter());
        converterFactory.registerConverter(new ZonedDateTimeConverter());
        //自定义其他拓展转换
        FACADE = mapperFactory.getMapperFacade();
    }

    private OrikaMapper() {
    }

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <S, D> D map(S source, Class<D> destinationClass) {
        return FACADE.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <S, D> List<D> mapList(Iterable<S> sourceList, Class<D> destinationClass) {
        return FACADE.mapAsList(sourceList, destinationClass);
    }

}