package com.company.template.client.web.dtos.errors;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

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
public class ErrorDto implements Serializable {
    private static final long serialVersionUID = -4708936233513887899L;

    private String errorCode;

    private String message;

    @Builder.Default
    private Date timestamp = Date.from(Instant.now());

    @Singular
    private Set<Object> errors;
}