package com.mtv.data.collection.select.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDto {

    private Integer id;
    private String title;
    private String description;

    @JsonProperty(value = "cover_photo_id")
    private String coverPhotoId;

}
