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

import com.company.template.client.api.ProductRestClient;
import com.company.template.client.config.RestClientProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Idan Rozenfeld
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = "template.client.api.path=someServer")
public class AutoConfigurationTest {

    @Autowired(required = false)
    private RestTemplate restTemplate;

    @Autowired(required = false)
    private RestClientProperties properties;

    @Autowired(required = false)
    private ProductRestClient client;

    @Test
    public void whenClientIsDependency_thenRestClientCreated() {
        assertThat(client, is(notNullValue()));
    }

    @Test
    public void whenRestTemplateNotExists_thenCreateDefault() {
        assertThat(restTemplate, is(notNullValue()));
        assertThat(restTemplate.getInterceptors(), hasSize(0));
    }

    @Test
    public void whenClientIsDependency_thenRestPropertiesCreated() {
        assertThat(properties, is(notNullValue()));
    }

    @Test
    public void whenPropertyExists_thenOverrideDefaults() {
        assertThat(properties.getPath(), is(equalTo("someServer")));
    }

    @Configuration
    @EnableAutoConfiguration
    public static class Application {

    }

}

