/**
 * Copyright (C) 2018 user name (user@email.com) the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.company.template.server.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.errors.ErrorCodes;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.config.JsonConfiguration;
import com.company.template.server.services.ProductService;
import com.company.template.server.web.controllers.CatalogueController;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JsonConfiguration.class)
@WebMvcTest(secure = false, controllers = CatalogueController.class)
public class CatalogueControllerTest {

    @Autowired
    private ObjectWriter writer;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    @Test
    public void whenCatalogue_thenReturnProductDto() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        when(service.catalogue(any(ProductDto.class))).thenReturn(1L);

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId", is(equalTo(1))));

        verify(service).catalogue(any(ProductDto.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenDeleteNotExists_thenReturnNotFound() throws Exception {
        doThrow(new EmptyResultDataAccessException(1)).when(service).remove(1);

        mvc.perform(delete("/catalogue/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).remove(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenCatalogUniqueName_thenReturnErrorDataValidationResponse() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        when(service.catalogue(request)).thenThrow(new UniqueFieldException("bla", "name", "1"));

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode", is(equalTo(ErrorCodes.DATA_VALIDATION.toString()))))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName", is(equalTo("name"))))
                .andExpect(jsonPath("$.errors[0].errorCode", is(equalTo("UNIQUE"))))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is(equalTo("1"))));

        verify(service).catalogue(any(ProductDto.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenAvgPrice_thenReturnAvgResonse() throws Exception {
        when(service.getProductPriceAvg()).thenReturn(10.0F);

        mvc.perform(get("/catalogue/avgPrice")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.avg", is(equalTo(10.0))));

        verify(service).getProductPriceAvg();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenCatalogueEmptyName_thenReturnErrorDataValidationResponse() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode", is(equalTo(ErrorCodes.DATA_VALIDATION.toString()))))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName", is(equalTo("name"))))
                .andExpect(jsonPath("$.errors[0].errorCode", is(equalTo("NotEmpty"))))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is(equalTo(""))))
                .andExpect(jsonPath("$.errors[0].params").isArray())
                .andExpect(jsonPath("$.errors[0].params").isEmpty());

        verifyNoMoreInteractions(service);
    }

    @Test
    public void whenCatalogueUnitPriceLower10_thenReturnErrorDataValidationResponse() throws Exception {
        ProductDto request = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(8.5F)
                .tag(TagDto.of("clo", 2))
                .build();

        mvc.perform(post("/catalogue")
                .content(writer.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorCode", is(equalTo(ErrorCodes.DATA_VALIDATION.toString()))))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0].fieldName", is(equalTo("unitPrice"))))
                .andExpect(jsonPath("$.errors[0].errorCode", is(equalTo("Min"))))
                .andExpect(jsonPath("$.errors[0].rejectedValue", is(equalTo(8.5))))
                .andExpect(jsonPath("$.errors[0].params").isArray())
                .andExpect(jsonPath("$.errors[0].params[0]", is(equalTo("10"))));

        verifyNoMoreInteractions(service);
    }


}
