package com.company.template.client.web.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Tag")
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class TagDto implements Serializable {

    private static final long serialVersionUID = -876929898508450858L;

    @NonNull
    @NotBlank
    private String caption;

    @NonNull
    @NotNull
    private Integer level;
}
