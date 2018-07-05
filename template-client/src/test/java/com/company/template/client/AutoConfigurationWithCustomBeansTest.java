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
package com.company.template.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "template.client.api.path=someServer")
public class AutoConfigurationWithCustomBeansTest {

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Test
    public void whenRestTemplateExists_thenDontCreateDefault() {
        assertThat(restTemplate.getInterceptors(), hasSize(1));
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {
        @Bean
        public RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.interceptors((request, body, execution) ->
                    new MockClientHttpResponse(new byte[]{}, HttpStatus.OK)).build();
        }
    }

}

