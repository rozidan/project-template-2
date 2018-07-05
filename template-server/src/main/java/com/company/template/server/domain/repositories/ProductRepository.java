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
package com.company.template.server.domain.repositories;

import com.company.template.server.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Idan Rozenfeld
 */
@RepositoryDefinition(domainClass = Product.class, idClass = Long.class)
public interface ProductRepository {
    List<Product> findAll();

    List<Product> findAll(Specification<Product> spec);

    List<Product> findAll(Specification<Product> spec, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    Optional<Product> getOne(long id);

    Product save(Product product);

    void delete(Product product);

    @Query("SELECT AVG(p.unitPrice) from Product p")
    float getAverage();

    @Query("SELECT count(p) > 0 FROM Product p WHERE p.name = :name AND (:id is null or p.id = :id)")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("id") Long id);
}