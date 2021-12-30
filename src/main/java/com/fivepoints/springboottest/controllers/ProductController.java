package com.fivepoints.springboottest.controllers;

import com.fivepoints.spring.payload.responses.MessageResponse;
import com.fivepoints.springboottest.entities.Product;
import com.fivepoints.springboottest.entities.User;
import com.fivepoints.springboottest.payload.responses.MessageResponseProduct;
import com.fivepoints.springboottest.repositories.ProductsRepository;
import com.fivepoints.springboottest.repositories.UserRepository;
import com.fivepoints.springboottest.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> saveNewProduct(@RequestBody Product product, @CurrentSecurityContext(expression="authentication") Authentication authentication)
    {
        User user = this.userRepository.findByEmail(authentication.getName());
        product.setUser(user);
        this.productsRepository.save(product);
        Product savedProduct =  this.productService.saveNewProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    //get all products
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> listProducts = this.productService.getAllProducts();
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }
    //get product by id
    @GetMapping("/{id}")
    public ResponseEntity<?> findProductByID(@PathVariable("id") long id)
    {
        Product product = this.productService.findProductByID(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //update product by id
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseProduct> updateUserByID(@PathVariable("id") long id, @RequestBody Product product
            , @CurrentSecurityContext(expression="authentication") Authentication authentication)
    {
        String connectedEmail = this.userRepository.findByEmail(authentication.getName()).getEmail();
        String emailUserProduct = this.productService.findProductByID(id).getUser().getEmail();
        if(connectedEmail == emailUserProduct){
            String message = this.productService.updateProductByID(id, product);

            Optional<Product> updatedProduct = this.productsRepository.findById(id);
            return new ResponseEntity<>(new MessageResponseProduct(message,updatedProduct), HttpStatus.OK);
        }else{
            Optional<Product> updatedProduct = this.productsRepository.findById(id);
            return new ResponseEntity<>(new MessageResponseProduct("Not authorized",
                    this.productsRepository.findById(id)), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteProductById(@PathVariable("id") long id)
    {
        String message = this.productService.deleteProductById(id);
        return new ResponseEntity<>(new MessageResponse(message), HttpStatus.OK);
    }

}
