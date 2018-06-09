package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.List;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("HttpRequestMethodError")
@Getter
@Setter
@Builder
public class HttpRequestMethodErrorDto implements Serializable {
    private static final long serialVersionUID = 4115067500106084449L;

    private String actualMethod;

    @Singular
    private List<HttpMethod> supportedMethods;
}
