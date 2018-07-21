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
package com.company.template.server.web.handlers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.template.client.web.dtos.errors.ErrorCodes;
import com.company.template.client.web.dtos.errors.ErrorDto;
import com.github.rozidan.springboot.logger.Loggable;

/**
 * @author Idan Rozenfeld
 * <p>
 * It is recommended to replace the messages with those
 * that do not reveal details about the code.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class DataExceptionHandlers {

    @Loggable
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorDto handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.NOT_FOUND.toString())
                .message(ex.getLocalizedMessage())
                .build();
    }

}
