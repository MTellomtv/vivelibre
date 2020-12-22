package com.mtv.data.collection.select.repository;

import com.mtv.data.collection.select.repository.entity.Item;
import com.mtv.data.config.UnsplashFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "unsplash", url = "${unsplash.api.url}", configuration = UnsplashFeignConfig.class)
public interface CollectionApiUnsplashRepository {

    @GetMapping(value = "collections", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<Item> getAllItemFromUnsplash(@RequestParam Integer page, @RequestParam Integer per_page);
}
