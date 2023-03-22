package com.Security_1.security_1.controller;

import com.Security_1.security_1.dto.AuthRequest;
import com.Security_1.security_1.dto.Product;
import com.Security_1.security_1.entity.UserInfo;
import com.Security_1.security_1.service.JwtService;
import com.Security_1.security_1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private JwtService jwtService;
@Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return service.addUser(userInfo);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllTheProducts() {
        return service.getProducts();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Product getProductById(@PathVariable int id) {
        return service.getProduct(id);
    }
//@PostMapping("/authenticate")
//    public String authenticationAndGetToken(@RequestBody  AuthRequest authRequest){
//    System.out.println("Hello...");
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
//if(authentication.isAuthenticated()) {
//    return jwtService.generateToken(authRequest.getUsername());
//}
//else {
//    throw new UsernameNotFoundException("Invalid user Request...");
//}
@PostMapping("/authenticate")
public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
    if (authentication.isAuthenticated()) {
        return jwtService.generateToken(authRequest.getUsername());
    } else {
        throw new UsernameNotFoundException("invalid user request !");
    }
}
}
