package com.mtv.data.collection.select.service.model;

import lombok.Data;

@Data
public class Filter {

    private Integer pageNumber;
    private Integer pageSize;
    private String filter;

}
