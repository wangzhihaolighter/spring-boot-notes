package com.example.springframework.boot.utils.pojo.orika;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDateTime;

public class LocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, LocalDateTime> {
    @Override
    public LocalDateTime convertTo(LocalDateTime localDateTime, Type<LocalDateTime> type, MappingContext mappingContext) {
        return LocalDateTime.from(localDateTime);
    }

    @Override
    public LocalDateTime convertFrom(LocalDateTime localDateTime, Type<LocalDateTime> type, MappingContext mappingContext) {
        return LocalDateTime.from(localDateTime);
    }
}
