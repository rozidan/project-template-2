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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Set;

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