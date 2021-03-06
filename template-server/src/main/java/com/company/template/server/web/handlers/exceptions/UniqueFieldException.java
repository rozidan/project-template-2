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
package com.company.template.server.web.handlers.exceptions;

import lombok.Getter;

@Getter
public class UniqueFieldException extends RuntimeException {

    private static final long serialVersionUID = -2308752260602001806L;

    public final String field;
    public final String rejectedValue;

    public UniqueFieldException(String message, String field, String rejectedValue) {
        super(message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }
}
