package com.company.template.server;

import com.company.template.client.api.RestPageTypeReference;
import com.company.template.client.web.dtos.ProductDto;
import com.company.template.client.web.dtos.RestPageImpl;
import com.company.template.client.web.dtos.TagDto;
import com.company.template.client.web.dtos.catalogue.CatalogueResponse;
import com.company.template.client.web.dtos.types.ProductCategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


//https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CatalogueIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void when1Catalogue_thenIdAnd201IsReceived() {
        ProductDto product = ProductDto.builder()
                .name("T")
                .category(ProductCategoryDto.CLOTHING)
                .tag(TagDto.of("A", 3))
                .unitPrice(10.3F)
                .build();

        ResponseEntity<CatalogueResponse> response =
                this.restTemplate.postForEntity("/catalogue", product, CatalogueResponse.class);
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
        assertThat(response.getBody().getProductId(), is(greaterThanOrEqualTo(0L)));
    }

}