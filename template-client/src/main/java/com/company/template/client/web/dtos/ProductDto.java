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
package com.company.template.client.web.dtos;

import com.company.template.client.web.dtos.types.ProductCategoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 5762617605382814204L;

    @ApiModelProperty(readOnly = true)
    @Null
    private Long id;

    @NotEmpty
    private String name;

    @JsonProperty("desc")
    private String description;

    @ApiModelProperty(required = true, allowableValues = "range[10,infinity]")
    @Min(10)
    @NotNull
    private Float unitPrice;

    @NotNull
    private ProductCategoryDto category;

    @NotEmpty
    @Singular
    private Set<TagDto> tags;


}