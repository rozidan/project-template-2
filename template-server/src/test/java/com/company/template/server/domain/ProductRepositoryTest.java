package com.company.template.server.domain;

import com.company.template.server.domain.model.Product;
import com.company.template.server.domain.model.Tag;
import com.company.template.server.domain.model.types.ProductCategory;
import com.company.template.server.domain.repositories.ProductRepository;

import java.util.Optional;
import javax.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    @Test
    public void getOneShouldSuccess() {
        Product persist = entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build());

        Optional<Product> product = repository.getOne(persist.getId());
        assertTrue(product.isPresent());
        assertThat(product.get().getName(), is(equalTo("John")));
        assertThat(product.get().getCategory(), is(equalTo(ProductCategory.GAME)));
        assertThat(product.get().getUnitPrice(), is(equalTo(100F)));
        assertThat(product.get().getDesc(), is(equalTo("desc")));
    }

    @Test
    public void getAverageShouldSuccess() {
        entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build());
        entityManager.persist(Product.builder()
                .name("Mario")
                .category(ProductCategory.GAME)
                .unitPrice(51F)
                .desc("desc")
                .build());

        float avg = repository.getAverage();
        assertThat(avg, is(equalTo(75.5F)));
    }

    @Test
    public void existsByNameAndIdNotShouldSuccess() {
        Product product = entityManager.persist(Product.builder()
                .name("John")
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .build());

        assertTrue(repository.existsByNameAndIdNot("John", product.getId()));
        assertTrue(repository.existsByNameAndIdNot("John", null));
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveProductWithoutNameShouldFailed() {
        Product product = Product.builder()
                .category(ProductCategory.GAME)
                .unitPrice(100F)
                .desc("desc")
                .tag(Tag.of("gam", 1))
                .build();

        repository.save(product);
        entityManager.flush();
    }
}
