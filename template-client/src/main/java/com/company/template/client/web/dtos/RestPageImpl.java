package com.company.template.client.web.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * @author Idan Rozenfeld
 */
public class RestPageImpl<T> extends PageImpl<T> {
    private static final long serialVersionUID = -8657428674401242979L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageImpl(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int page,
                        @JsonProperty("size") int size,
                        @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }
}