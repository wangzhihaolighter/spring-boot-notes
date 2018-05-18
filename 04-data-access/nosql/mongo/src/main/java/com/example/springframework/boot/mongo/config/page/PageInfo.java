package com.example.springframework.boot.mongo.config.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo<T> {
    private Integer pageNum;
    private Integer pageSize;
    private List<T> data;
    private Integer total;
    //other
}
