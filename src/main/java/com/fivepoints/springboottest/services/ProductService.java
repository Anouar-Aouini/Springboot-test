package com.fivepoints.springboottest.services;

import com.fivepoints.springboottest.entities.Product;
import com.fivepoints.springboottest.exceptions.ResourceNotFoundException;
import com.fivepoints.springboottest.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductsRepository productsRepository;

    public Product saveNewProduct(Product product)
    {
        // Create new user account
        Product product1 = new Product();
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setQuantity(product.getQuantity());
        product1.setPrice(product.getPrice());
        product1.setUser(product.getUser());
        return this.productsRepository.save(product);
    }

    public List<Product> getAllProducts()
    {
        return this.productsRepository.findAll();
    }

    public Product findProductByID(long id)
    {
        Optional<Product> productData = this.productsRepository.findById(id);
        // Return statement if product exist or null
        return productData.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public String updateProductByID(long id, Product product)
    {
        Optional<Product> productData = this.productsRepository.findById(id);
        if (productData.isPresent()) {
            Product existingProduct = productData.orElse(null);
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setPrice(product.getPrice());
            // save existingProduct in the database
            this.productsRepository.save(existingProduct);
            // return statement
            return "Product updated successfully!";
        } else {
            throw new ResourceNotFoundException("Product not found");
        }
    }

    public String deleteProductById(long id)
    {
        Optional<Product> productData = this.productsRepository.findById(id);
        if (productData.isPresent()) {
            this.productsRepository.deleteById(id);
            return "Product deleted successfully!";
        } else {
            throw new ResourceNotFoundException("Product not found");
        }
    }
}

