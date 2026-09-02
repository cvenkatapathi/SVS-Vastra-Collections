package com.svs.svscollections.repository;

import com.svs.svscollections.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}