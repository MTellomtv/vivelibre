package com.mtv.data.collection.select.repository.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Item {

    private Integer id;
    private String title;
    private String description;

    @JsonProperty(value = "cover_photo")
    private CoverPhoto coverPhoto;

    @Data
    class CoverPhoto {
        private String id;
    }

}
