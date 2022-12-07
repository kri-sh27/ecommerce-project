package com.nttdata.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.ecommerce.entity.Product;

//@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
