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
package com.company.template.server.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.Tag;
import com.company.template.server.domain.model.types.ProductCategory;
import com.company.template.server.domain.repositories.ProductRepository;
import java.util.List;
import java.util.Optional;
import javax.validation.ConstraintViolationException;

import static com.company.template.server.domain.model.specifications.ProductSpecifications.productCostMoreThan;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @Test
    public void whenGetOne_thenReturnProduct() {
        Product persist = entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build());

        Optional<Product> product = repository.getOne(persist.getId());
        assertTrue(product.isPresent());
        assertThat(product.get().getName(), is(equalTo("John")));
        assertThat(product.get().getCategory(), is(equalTo(ProductCategory.GAME)));
        assertThat(product.get().getUnitPrice(), is(equalTo(100F)));
        assertThat(product.get().getDesc(), is(equalTo("desc")));
    }

    @Test
    public void whenGetAverage_thenReturnAvgFloat() {
        entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build());
        entityManager.persist(Product.builder()
                .name("Mario")
                .category(ProductCategory.GAME)
                .unitPrice(51F)
                .desc("desc")
                .build());

        float avg = repository.getAverage();
        assertThat(avg, is(equalTo(75.5F)));
    }

    @Test
    public void whenExistsByNameAndIdNot_thenReturnTrue() {
        Product product = entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build());

        assertTrue(repository.existsByNameAndIdNot("John", product.getId()));
        assertTrue(repository.existsByNameAndIdNot("John", null));
    }

    @Test(expected = ConstraintViolationException.class)
    public void whenSaveProductWithoutName_thenThrowConstraintViolationException() {
        Product product = Product.builder()
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .tag(Tag.of("gam", 1))
                .build();

        repository.save(product);
        entityManager.flush();
    }

    @Test
    public void whenFindAllSpecUnitPriceGreaterThen15_thenReturnOne() {
        entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(24F)
                .desc("desc")
                .build());
        entityManager.persist(Product.builder()
                .name("Mario")
                .category(ProductCategory.GAME)
                .unitPrice(12F)
                .desc("desc")
                .build());

        List<Product> greaterThen15 = repository.findAll(productCostMoreThan(15));
        assertThat(greaterThen15, hasSize(1));
    }

}
