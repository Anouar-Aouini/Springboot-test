package com.fivepoints.springboottest.repositories;

import com.fivepoints.springboottest.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
}
