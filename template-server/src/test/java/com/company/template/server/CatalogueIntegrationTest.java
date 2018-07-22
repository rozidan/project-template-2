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
package com.company.template.server;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.catalogue.CatalogueResponse;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;


//https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CatalogueIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void when1Catalogue_thenIdAnd201IsReceived() {
        ProductDto product = ProductDto.builder()
                .name("T")
                .category(ProductCategoryDto.CLOTHING)
                .tag(TagDto.of("A", 3))
                .unitPrice(10.3F)
                .build();

        ResponseEntity<CatalogueResponse> response =
                this.restTemplate.postForEntity("/catalogue", product, CatalogueResponse.class);
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getProductId(), is(greaterThanOrEqualTo(0L)));
    }

}