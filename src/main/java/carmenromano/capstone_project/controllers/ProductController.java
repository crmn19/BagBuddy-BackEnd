package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.exceptions.BadRequestException;
import carmenromano.capstone_project.payload.ProductPayload;
import carmenromano.capstone_project.services.CustomerService;
import carmenromano.capstone_project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Validated ProductPayload productPayload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return productService.save(productPayload);
    }
}
