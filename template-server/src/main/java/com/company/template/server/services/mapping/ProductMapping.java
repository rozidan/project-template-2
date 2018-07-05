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
package com.company.template.server.services.mapping;

import com.company.template.client.web.dtos.ProductDto;
import com.company.template.server.domain.model.Product;
import com.github.rozidan.springboot.modelmapper.TypeMapConfigurer;
import lombok.experimental.UtilityClass;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

/**
 * @author Idan Rozenfeld
 */
@UtilityClass
public class ProductMapping {

    @Component
    public static class ProductToDtoMapping extends TypeMapConfigurer<Product, ProductDto> {

        @Override
        public void configure(TypeMap<Product, ProductDto> typeMap) {
            typeMap.addMapping(Product::getDesc, ProductDto::setDescription);
        }
    }

    @Component
    public static class ProductFromDtoMapping extends TypeMapConfigurer<ProductDto, Product> {

        @Override
        public void configure(TypeMap<ProductDto, Product> typeMap) {
            typeMap.addMappings(map -> map.skip(Product::setId));
            typeMap.addMapping(ProductDto::getDescription, Product::setDesc);
        }
    }

}