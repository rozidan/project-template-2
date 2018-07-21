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
package com.company.template.server.domain.model.types;

import com.company.template.client.EnumUtils;
import com.company.template.client.IdentifierType;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Idan Rozenfeld
 */
public enum ProductCategory implements IdentifierType<String> {
    GAME("GM"), CLOTHING("CL");

    private final String id;

    ProductCategory(String id) {
        this.id = id;
    }

    public static ProductCategory byValue(String value) {
        return EnumUtils.getByValue(ProductCategory.class, value);
    }

    @Override
    public String getValue() {
        return id;
    }

    @Converter(autoApply = true)
    public static class ProductCategoryConverter implements AttributeConverter<ProductCategory, String> {

        @Override
        public String convertToDatabaseColumn(ProductCategory attribute) {
            if (Objects.nonNull(attribute)) {
                return attribute.getValue();
            }
            return null;
        }

        @Override
        public ProductCategory convertToEntityAttribute(String dbData) {
            if (Objects.nonNull(dbData)) {
                return ProductCategory.byValue(dbData);
            }
            return null;
        }

    }
}
