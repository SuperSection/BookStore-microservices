package com.supersection.bookstore.catalog.web.controllers;

import com.supersection.bookstore.catalog.common.dto.PagedResult;
import com.supersection.bookstore.catalog.domain.Product;
import com.supersection.bookstore.catalog.domain.ProductService;
import com.supersection.bookstore.catalog.domain.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }

/*
    void sleep() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
*/
}
