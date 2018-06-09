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
