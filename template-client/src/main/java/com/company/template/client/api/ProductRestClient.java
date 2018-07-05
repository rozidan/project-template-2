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
package com.company.template.client.api;

import com.company.template.client.config.RestClientProperties;
import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.RestPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Idan Rozenfeld
 */
@Component
public class ProductRestClient {

    private final String productPath;

    private final RestTemplate rest;

    @Autowired
    public ProductRestClient(RestTemplate rest, RestClientProperties prop) {
        this.rest = rest;
        productPath = prop.getPath() + "/products";
    }

    public ProductDto catalogue(ProductDto productDto) {
        return rest.exchange(productPath, HttpMethod.POST,
                new HttpEntity<>(productDto), ProductDto.class).getBody();
    }

    public Page<ProductDto> list(Pageable pageable) {
        ParameterizedTypeReference<RestPageImpl<ProductDto>> ptr = new RestPageTypeReference<>();

        URI targetUrl = UriComponentsBuilder.fromUriString(productPath)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("sort", pageable.getSort())
                .build()
                .toUri();

        return rest.exchange(targetUrl, HttpMethod.GET, null, ptr).getBody();
    }

    public void remove(long id) {
        Map<String, Object> uriVars = new HashMap<>();
        uriVars.put("id", id);
        rest.exchange(productPath + "/{id}", HttpMethod.DELETE,
                null, Void.class, uriVars);
    }

    public ProductDto retrieve(long id) {
        Map<String, Object> uriVars = new HashMap<>();
        uriVars.put("id", id);
        return rest.exchange(productPath + "/{id}", HttpMethod.GET,
                null, ProductDto.class, uriVars).getBody();
    }
}
