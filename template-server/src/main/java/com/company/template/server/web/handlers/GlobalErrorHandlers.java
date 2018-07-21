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

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.company.template.client.web.dtos.errors.ErrorCodes;
import com.company.template.client.web.dtos.errors.ErrorDto;
import com.company.template.client.web.dtos.errors.HttpMediaTypeErrorDto;
import com.company.template.client.web.dtos.errors.HttpRequestMethodErrorDto;
import com.github.rozidan.springboot.logger.Loggable;
import lombok.extern.slf4j.Slf4j;
import java.util.Collections;

/**
 * @author Idan Rozenfeld
 * <p>
 * It is recommended to replace the messages with those
 * that do not reveal details about the code.
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorHandlers {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.NOT_FOUND.toString())
                .message(ex.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorDto handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.NOT_FOUND.toString())
                .message(ex.getLocalizedMessage())
                .build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorDto handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.METHOD_NOT_ALLOWED.toString())
                .errors(Collections.singleton(HttpRequestMethodErrorDto.builder()
                        .actualMethod(ex.getMethod())
                        .supportedMethods(ex.getSupportedHttpMethods())
                        .build()))
                .message(ex.getLocalizedMessage())
                .build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorDto handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return ErrorDto.builder()
                .errorCode(ErrorCodes.HTTP_MEDIA_TYPE_NOT_SUPPORTED.toString())
                .errors(Collections.singleton(HttpMediaTypeErrorDto.builder()
                        .mediaType(ex.getContentType().toString())
                        .build()))
                .message(ex.getLocalizedMessage())
                .build();
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDto handleGlobalError(Exception ex) {
        log.error("Global error handler exception: ", ex);
        return ErrorDto.builder()
                .errorCode(ErrorCodes.UNKNOWN.toString())
                .message(ex.getLocalizedMessage())
                .build();
    }
}