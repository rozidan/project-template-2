package com.company.template.server.domain.model.specifications;

import com.company.template.server.domain.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecifications {

    public static Specification<Product> priceIsGreaterThen(float price) {
        return (root, query, cb) -> cb.greaterThan(root.get("unitPrice"), price);
    }
}
