package com.company.template.client.web.dtos.catalogue;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Idan Rozenfeld
 */
@ApiModel("Avg Price Response")
@Getter
@RequiredArgsConstructor(staticName = "of", onConstructor = @__({@JsonCreator}))
public class AvgPriceResponse {
    private final float avg;
}
