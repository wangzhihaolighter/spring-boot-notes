package com.example.mongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfoDTO<T> {
    private Integer pageNum;
    private Integer pageSize;
    private List<T> data;
    private Integer total;
    //other
}
