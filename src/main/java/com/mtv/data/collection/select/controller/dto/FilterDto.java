package com.mtv.data.collection.select.controller.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class FilterDto {

    @Min(0)
    private Integer pageNumber = 0;

    @Min(1)
    private Integer pageSize = 30;

    private String filter;

}
