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
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.company.template.client.web.dtos.errors.ErrorCodes;
import com.company.template.client.web.dtos.errors.ErrorDto;
import com.company.template.client.web.dtos.errors.ValidationErrorCodes;
import com.company.template.client.web.dtos.errors.ValidationErrorDto;
import com.company.template.server.web.handlers.exceptions.UniqueFieldException;
import com.github.rozidan.springboot.logger.Loggable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Idan Rozenfeld
 * <p>
 * It is recommended to replace the messages with those
 * that do not reveal details about the code.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ValidationErrorHandlers {

    @Loggable
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDto handleNotReadableError(HttpMessageNotReadableException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.REQUEST_NOT_READABLE.toString())
                .message(ex.getLocalizedMessage())
                .build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(UniqueFieldException.class)
    public ErrorDto handleUniqueFieldExceptionError(UniqueFieldException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.DATA_VALIDATION.toString())
                .error(ValidationErrorDto.builder()
                        .errorCode(ValidationErrorCodes.UNIQUE.toString())
                        .fieldName(ex.getField())
                        .rejectedValue(ex.getRejectedValue())
                        .message(ex.getLocalizedMessage())
                        .build())
                .message(ex.getLocalizedMessage())
                .build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto handleValidationError(MethodArgumentNotValidException ex) {
        Set<ValidationErrorDto> errors = Stream.concat(
                ex.getBindingResult().getFieldErrors().stream()
                        .map(err -> ValidationErrorDto.builder()
                                .errorCode(err.getCode())
                                .fieldName(err.getField())
                                .rejectedValue(err.getRejectedValue())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()),
                ex.getBindingResult().getGlobalErrors().stream()
                        .map(err -> ValidationErrorDto.builder()
                                .errorCode(err.getCode())
                                .params(collectArguments(err.getArguments()))
                                .message(err.getDefaultMessage())
                                .build()))
                .collect(Collectors.toSet());

        return ErrorDto.builder()
                .errorCode(ErrorCodes.DATA_VALIDATION.toString())
                .errors(Collections.unmodifiableSet(errors))
                .message(ex.getLocalizedMessage())
                .build();
    }

    private List<String> collectArguments(Object[] arguments) {
        return arguments == null ?
                Collections.emptyList() :
                Stream.of(arguments)
                        .skip(1)
                        .map(Object::toString)
                        .collect(Collectors.toList());
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorDto handleMissingServletRequestParameterError(MissingServletRequestParameterException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.MISSING_REQUEST_PARAM.toString())
                .errors(Collections.singleton(ex.getParameterName()))
                .message(ex.getLocalizedMessage())
                .build();
    }
}