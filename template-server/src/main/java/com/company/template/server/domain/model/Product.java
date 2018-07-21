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
package com.company.template.server.domain.model;

import com.company.template.server.domain.model.types.ProductCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Idan Rozenfeld
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(of = "id", callSuper = true)
@Entity
@SequenceGenerator(name = "productSeq", sequenceName = "product_seq")
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "productSeq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private String desc;

    @Min(10)
    @NotNull
    @Column(nullable = false)
    private Float unitPrice;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Version
    private long version;

    private ProductCategory category;

    @Singular
    @ElementCollection
    @Embedded
    private Set<Tag> tags;

}