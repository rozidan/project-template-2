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
package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

/**
 * @author Idan Rozenfeld
 * <p>
 * It is recommended to replace the messages with those
 * that do not reveal details about the code.
 */
@ApiModel("Error")
@Getter
@Setter
@Builder
public class ErrorDto {
    private final String errorCode;

    private final String message;

    @Builder.Default
    private final Date timestamp = Date.from(Instant.now());

    @Singular
    private final Set<Object> errors;

    public Date getTimestamp() {
        return new Date(timestamp.getTime());
    }
}