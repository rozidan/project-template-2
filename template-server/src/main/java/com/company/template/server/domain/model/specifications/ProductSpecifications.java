package com.company.template.server.domain.model.specifications;

import com.company.template.server.domain.model.Product;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ProductSpecifications {

    public static Specification<Product> productCostMoreThan(final float price) {
        return (root, query, cb) -> cb.greaterThan(root.get("unitPrice"), price);
    }
}
