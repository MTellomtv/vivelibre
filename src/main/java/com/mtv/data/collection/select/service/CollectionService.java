package com.mtv.data.collection.select.service;

import com.github.dozermapper.core.Mapper;
import com.mtv.data.collection.select.repository.CollectionApiUnsplashRepository;
import com.mtv.data.collection.select.service.model.Filter;
import com.mtv.data.collection.select.service.model.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionApiUnsplashRepository collectionUnsplashRepository;

    private final Mapper dozerMapper;

    //    @Retryable(value = {FeignException.Unauthorized.class}, maxAttempts = 2)
    public List<Item> getAllItems(Filter filter) {
        List<com.mtv.data.collection.select.repository.entity.Item> itemsFromUnsplash = collectionUnsplashRepository.getAllItemFromUnsplash(filter.getPageNumber(), filter.getPageSize());
        List<Item> unfilteredItems = mapListEntityToModel(itemsFromUnsplash);
        return filterItems(unfilteredItems, filter.getFilter());
    }

//    @Recover
//    public List<Item>  retryUnsplashRecovery(FeignException.Unauthorized feu, Filter filter) throws IllegalAccessException {
//        log.info("Retry Recovery - {}", feu.getMessage());
//        unsplashPropertiesLoader.resetAuthToken();
//        return  getAllItems(filter);
//    }

    private List<Item> mapListEntityToModel(List<com.mtv.data.collection.select.repository.entity.Item> itemsFromUnsplash) {
        return itemsFromUnsplash.stream().map(itemUnsplash -> dozerMapper.map(itemUnsplash, Item.class)).collect(Collectors.toList());
    }

    private List<Item> filterItems(List<Item> unfilteredItems, String filter) {
        if (filter != null) {
            return unfilteredItems.stream().filter(item ->
                    checkIfStringEqualInteger(filter, item.getId())
                            || checkIfContains(filter, item.getDescription())
                            || checkIfContains(filter, item.getTitle())
                            || checkIfContains(filter, item.getCoverPhotoId())
            ).collect(Collectors.toList());
        } else {
            return unfilteredItems;
        }
    }

    private Boolean checkIfStringEqualInteger(String query, Integer item) {
        return StringUtils.isNumeric(query) && item.toString().equals(query);
    }

    private Boolean checkIfContains(String query, String description) {
        return ofNullable(description).map(desc -> desc.toLowerCase().contains(query.toLowerCase())).orElse(false);
    }

}
