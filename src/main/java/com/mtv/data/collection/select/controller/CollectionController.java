package com.mtv.data.collection.select.controller;

import com.github.dozermapper.core.Mapper;
import com.mtv.data.collection.select.controller.dto.FilterDto;
import com.mtv.data.collection.select.controller.dto.ItemDto;
import com.mtv.data.collection.select.service.CollectionService;
import com.mtv.data.collection.select.service.model.Filter;
import com.mtv.data.collection.select.service.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("collection")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    private final Mapper dozerMapper;

    @GetMapping(value = "all", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public List<ItemDto> getAllItems(@Validated FilterDto filterDto) {
        Filter filter = dozerMapper.map(filterDto, Filter.class);

        List<Item> items = collectionService.getAllItems(filter);

        return items.stream().map(item -> dozerMapper.map(item, ItemDto.class)).collect(Collectors.toList());
    }

}
