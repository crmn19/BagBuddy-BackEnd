package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.exceptions.BadRequestException;
import carmenromano.capstone_project.payload.ProductPayload;
import carmenromano.capstone_project.services.CustomerService;
import carmenromano.capstone_project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.productService.getProduct(page, size, sortBy);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody @Validated ProductPayload productPayload, BindingResult validation) throws IOException {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return productService.save(productPayload);
    }
    @GetMapping("/{productId}")
    public Product findById(@PathVariable UUID productId) {
        return this.productService.findById(productId);
    }

    @PutMapping("/{productId}")
    public Product findByIdAndUpdate(@PathVariable UUID productId, @RequestBody Product body) {
        return productService.findByIdAndUpdate(productId, body);
    }


    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID productId) {
        productService.findByIdAndDelete(productId);
    }

    @PostMapping("/{productId}/avatar")
    public Product uploadImage(@PathVariable UUID productId, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.productService.uploadImage(productId, image);
    }

}
