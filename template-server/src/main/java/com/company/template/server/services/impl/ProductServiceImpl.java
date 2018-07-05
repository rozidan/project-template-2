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
package com.company.template.server.services.impl;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.repositories.ProductRepository;
import com.company.template.server.services.ProductService;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Idan Rozenfeld
 */
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ProductServiceImpl implements ProductService {

    private final ModelMapper mapper;

    private final ProductRepository repository;

    @Override
    @Transactional
    public long catalogue(ProductDto productDto) {
        checkUniqueName(null, productDto.getName());
        Product product = mapper.map(productDto, Product.class);
        Product newProd = repository.save(product);
        return newProd.getId();
    }


    @Override
    @Transactional
    public void replace(ProductDto productDto, long id) {
        checkUniqueName(id, productDto.getName());
        Product product = repository.getOne(id).orElseThrow(this::createEmptyResultException);
        mapper.map(productDto, product);
    }

    @Override
    public Page<ProductDto> fetch(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(product -> mapper.map(product, ProductDto.class));
    }

    @Override
    @Transactional
    public void remove(long id) {
        Product product = repository.getOne(id).orElseThrow(this::createEmptyResultException);
        repository.delete(product);
    }

    @Override
    public ProductDto get(long id) {
        Product product = repository.getOne(id).orElseThrow(this::createEmptyResultException);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public float getProductPriceAvg() {
        return repository.getAverage();
    }

    private EmptyResultDataAccessException createEmptyResultException() {
        return new EmptyResultDataAccessException(1);
    }

    private void checkUniqueName(Long id, String name) {
        if (repository.existsByNameAndIdNot(name, id)) {
            throw new UniqueFieldException("Product name must be unique", "name", name);
        }
    }
}