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
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("HttpMediaTypeError")
@Getter
@Setter
@Builder
public class HttpMediaTypeErrorDto implements Serializable {
    private static final long serialVersionUID = 7301072886218818L;

    private String mediaType;

}
