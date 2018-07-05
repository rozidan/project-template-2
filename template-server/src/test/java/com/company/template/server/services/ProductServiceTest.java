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
package com.company.template.server.services;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.domain.repositories.ProductRepository;
import com.company.template.server.services.impl.ProductServiceImpl;
import com.company.template.server.services.mapping.MappingBasePackage;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
@ContextConfiguration(classes = ProductServiceImpl.class)
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @MockBean
    private ProductRepository repository;

    @Test
    public void whenRemoveNotExists_thenThrowEmptyResultException() {
        Mockito.when(repository.getOne(1)).thenReturn(Optional.empty());

        try {
            service.remove(1);
        } catch (Exception ex) {
            assertTrue(ex instanceof EmptyResultDataAccessException);
        }

        verify(repository).getOne(1);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenCatalogUniqueName_thenThrowUniqueFieldException() {
        ProductDto productDto = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        Mockito.when(repository.existsByNameAndIdNot("John", null)).thenReturn(true);

        try {
            service.catalogue(productDto);
        } catch (Exception ex) {
            assertTrue(ex instanceof UniqueFieldException);
            UniqueFieldException ufe = (UniqueFieldException) ex;
            assertThat(ufe.getField(), is(equalTo("name")));
            assertThat(ufe.getRejectedValue(), is(equalTo("John")));
        }

        verify(repository).existsByNameAndIdNot("John", null);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenReplaceUniqueName_thenThrowUniqueFieldException() {
        ProductDto productDto = ProductDto.builder()
                .name("John")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100.0F)
                .tag(TagDto.of("clo", 2))
                .build();

        Mockito.when(repository.existsByNameAndIdNot("John", 1L)).thenReturn(true);

        try {
            service.replace(productDto, 1L);
        } catch (Exception ex) {
            assertTrue(ex instanceof UniqueFieldException);
            UniqueFieldException ufe = (UniqueFieldException) ex;
            assertThat(ufe.getField(), is(equalTo("name")));
            assertThat(ufe.getRejectedValue(), is(equalTo("John")));
        }

        verify(repository).existsByNameAndIdNot("John", 1L);
        verifyNoMoreInteractions(repository);
    }
}
