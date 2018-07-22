package com.company.template.server.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = {"spring.jpa.hibernate.ddl-auto=validate"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, FlywayTestExecutionListener.class})
@FlywayTest
public class FlywayMigrationTest {

    @Autowired
    private Flyway flyway;

    @Test
    @FlywayTest(locationsForMigrate = "db/migration")
    public void flywayValidateSuccess() {

    }

    @Configuration
    @EnableAutoConfiguration
    @Import(FlywayConfig.class)
    public static class Application {

    }
}
