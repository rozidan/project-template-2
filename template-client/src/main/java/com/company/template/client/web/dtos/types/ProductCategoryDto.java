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
package com.company.template.client.web.dtos.types;

import com.company.template.client.EnumUtils;
import com.company.template.client.IdentifierType;
import java.util.Objects;

/**
 * @author Idan Rozenfeld
 */
public enum ProductCategoryDto implements IdentifierType<String> {
    GAME("GAME"), CLOTHING("CLOTHING");

    private final String id;

    ProductCategoryDto(String id) {
        this.id = id;
    }

    public static ProductCategoryDto byValue(String value) {
        if (Objects.nonNull(value)) {
            return EnumUtils.getByValueIgnoreCase(ProductCategoryDto.class, value);
        }

        return null;
    }

    @Override
    public String getValue() {
        return id;
    }
}
