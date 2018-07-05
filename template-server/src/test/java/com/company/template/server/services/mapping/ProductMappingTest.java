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
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.types.ProductCategory;
import com.github.rozidan.springboot.modelmapper.WithModelMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WithModelMapper(basePackageClasses = MappingBasePackage.class)
public class ProductMappingTest {

    @Autowired
    private ModelMapper mapper;

    @Test
    public void productDtoToEntityMapped_shouldSuccess() {
        ProductDto dto = ProductDto.builder()
                .name("John")
                .description("Desc")
                .category(ProductCategoryDto.CLOTHING)
                .unitPrice(100f)
                .tag(TagDto.of("clo", 2))
                .tag(TagDto.of("fit", 1))
                .build();

        Product result = mapper.map(dto, Product.class);

        assertThat(result.getName(), is(equalTo("John")));
        assertThat(result.getUnitPrice(), is(equalTo(100F)));
        //Enum mapped successfully cause modelmapper doing it by .toString() method
        assertThat(result.getCategory(), is(equalTo(ProductCategory.CLOTHING)));
        assertThat(result.getDesc(), is(equalTo("Desc")));
        assertThat(result.getTags(), hasSize(2));
        assertThat(result.getTags(), containsInAnyOrder(Arrays.asList(
                allOf(hasProperty("caption", is(equalTo("clo"))),
                        hasProperty("level", is(equalTo(2)))),
                allOf(hasProperty("caption", is(equalTo("fit"))),
                        hasProperty("level", is(equalTo(1)))))
        ));
    }
}
