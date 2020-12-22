package com.mtv.data.integration.collection.select;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.mtv.data.collection.select.controller.dto.ItemDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.mtv.data.util.FileUtil.getResourceAsString;
import static org.springframework.cloud.contract.spec.internal.HttpStatus.OK;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8090)
public class SelectCollectionTest {

    public static final String GET_ALL = "/collection/all";
    public static final String GET_COLLECTION_UNSPLASH = "/collections";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void withoutFilterReturnListItems() throws Exception {
        mockStandarUnsplashResponse("?page=0&per_page=30");

        ResultActions result = mockMvc.perform(get(GET_ALL))
                .andExpect(status().isOk());

        var itemsReturned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ItemDto>>() {
        });

        Assertions.assertThat(itemsReturned.size()).isEqualTo(1);

        Assertions.assertThat(itemsReturned.get(0).getId()).isEqualTo(296);
        Assertions.assertThat(itemsReturned.get(0).getTitle()).isEqualTo("I like a man with a beard.");
        Assertions.assertThat(itemsReturned.get(0).getDescription()).isEqualTo("Yeah even Santa...");
        Assertions.assertThat(itemsReturned.get(0).getCoverPhotoId()).isEqualTo("C-mxLOk6ANs");

    }

    @Test
    void withOnlyPageNumberFilterReturnListItems() throws Exception {
        mockStandarUnsplashResponse("?page=8&per_page=30");

        ResultActions result = mockMvc.perform(get(GET_ALL + "?pageNumber=8"))
                .andExpect(status().isOk());

        var itemsReturned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ItemDto>>() {
        });

        Assertions.assertThat(itemsReturned.size()).isEqualTo(1);

        Assertions.assertThat(itemsReturned.get(0).getId()).isEqualTo(296);
        Assertions.assertThat(itemsReturned.get(0).getTitle()).isEqualTo("I like a man with a beard.");
        Assertions.assertThat(itemsReturned.get(0).getDescription()).isEqualTo("Yeah even Santa...");
        Assertions.assertThat(itemsReturned.get(0).getCoverPhotoId()).isEqualTo("C-mxLOk6ANs");
    }

    @Test
    void withOnlyPageSizeFilterReturnListItems() throws Exception {
        mockStandarUnsplashResponse("?page=0&per_page=10");

        ResultActions result = mockMvc.perform(get(GET_ALL + "?pageSize=10"))
                .andExpect(status().isOk());

        var itemsReturned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ItemDto>>() {
        });

        Assertions.assertThat(itemsReturned.size()).isEqualTo(1);

        Assertions.assertThat(itemsReturned.get(0).getId()).isEqualTo(296);
        Assertions.assertThat(itemsReturned.get(0).getTitle()).isEqualTo("I like a man with a beard.");
        Assertions.assertThat(itemsReturned.get(0).getDescription()).isEqualTo("Yeah even Santa...");
        Assertions.assertThat(itemsReturned.get(0).getCoverPhotoId()).isEqualTo("C-mxLOk6ANs");
    }

    @ParameterizedTest
    @ValueSource(strings = {"?pageNumber=8&pageSize=0", "?pageNumber=8&pageSize=-1", "?pageNumber=-1&pageSize=0"})
    void withBadFilterReturnBadRequest(String parameters) throws Exception {
        ResultActions result = mockMvc.perform(get(GET_ALL + parameters))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withAllFilterReturnListItems() throws Exception {
        mockStandarUnsplashResponse("?page=8&per_page=10");

        ResultActions result = mockMvc.perform(get(GET_ALL + "?pageNumber=8&pageSize=10"))
                .andExpect(status().isOk());

        var itemsReturned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ItemDto>>() {
        });

        Assertions.assertThat(itemsReturned.size()).isEqualTo(1);

        Assertions.assertThat(itemsReturned.get(0).getId()).isEqualTo(296);
        Assertions.assertThat(itemsReturned.get(0).getTitle()).isEqualTo("I like a man with a beard.");
        Assertions.assertThat(itemsReturned.get(0).getDescription()).isEqualTo("Yeah even Santa...");
        Assertions.assertThat(itemsReturned.get(0).getCoverPhotoId()).isEqualTo("C-mxLOk6ANs");
    }

    private void mockStandarUnsplashResponse(String parameters) throws IOException {
        stubFor(WireMock.get(urlEqualTo(GET_COLLECTION_UNSPLASH + parameters))
                .willReturn(aResponse().withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(OK)
                        .withBody(getResourceAsString("com.mtv.data.integration.collection.select/standardUnsplashResponse.json"))));
    }

    @Test
    void withStringQueryReturnListItems() throws Exception {
        stubFor(WireMock.get(urlEqualTo(GET_COLLECTION_UNSPLASH + "?page=0&per_page=30"))
                .willReturn(aResponse().withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(OK)
                        .withBody(getResourceAsString("com.mtv.data.integration.collection.select/multipleResulForSimpleQuerytUnsplashResponse.json"))));

        ResultActions result = mockMvc.perform(get(GET_ALL + "?filter=hello"))
                .andExpect(status().isOk());

        var itemsReturned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ItemDto>>() {
        });

        Assertions.assertThat(itemsReturned.size()).isEqualTo(3);

        Assertions.assertThat(itemsReturned.get(0).getId()).isEqualTo(296);
        Assertions.assertThat(itemsReturned.get(1).getId()).isEqualTo(297);
        Assertions.assertThat(itemsReturned.get(2).getId()).isEqualTo(298);

    }

    @Test
    void withNumberQueryReturnListItems() throws Exception {
        stubFor(WireMock.get(urlEqualTo(GET_COLLECTION_UNSPLASH + "?page=0&per_page=30"))
                .willReturn(aResponse().withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withStatus(OK)
                        .withBody(getResourceAsString("com.mtv.data.integration.collection.select/multipleResulForSimpleQuerytUnsplashResponse.json"))));

        ResultActions result = mockMvc.perform(get(GET_ALL + "?filter=299"))
                .andExpect(status().isOk());

        var itemsReturned = objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), new TypeReference<List<ItemDto>>() {
        });

        Assertions.assertThat(itemsReturned.size()).isEqualTo(1);

        Assertions.assertThat(itemsReturned.get(0).getId()).isEqualTo(299);

    }

}
