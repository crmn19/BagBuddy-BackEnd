package carmenromano.capstone_project.services;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.enums.CategoryProduct;
import carmenromano.capstone_project.exceptions.BadRequestException;
import carmenromano.capstone_project.exceptions.NotFoundException;
import carmenromano.capstone_project.payload.NewCustomerPayload;
import carmenromano.capstone_project.payload.ProductPayload;
import carmenromano.capstone_project.repositories.CustomerRepository;
import carmenromano.capstone_project.repositories.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Page<Product> getProduct(int pageNumber, int pageSize, String sortBy, String category, String search) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        CategoryProduct categoryProduct = null;
        try {
            if (category != null && !category.isEmpty()) {
                categoryProduct = CategoryProduct.valueOf(category);
            }
        } catch (IllegalArgumentException e) {
            categoryProduct = null;
        }


        if (search != null && !search.isEmpty() ) {
            return productRepository.findByNameContainingIgnoreCase(search, pageable);
        } else if (categoryProduct != null) {
            return productRepository.findByCategory(categoryProduct, pageable);
        } else {
            return productRepository.findAll(pageable);
        }


    }
    public Product save(ProductPayload body) {
        Product product = new Product();
        product.setName(body.nome());
        product.setDescription(body.description());
        product.setBrand(body.brand());
        product.setPrice(body.price());
        product.setCategory(body.categoryProduct());
        product.setInMagazzino(body.inMagazzino());
        product.setImageUrl("https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.brand());
        product.setCreatedAt(LocalDate.now());
        product.setDiscount(0);
        Product saved = productRepository.save(product);

        return saved;
    }
    public Product findById(UUID productId) {
        return this.productRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));
    }
    public Product findByIdAndUpdate(UUID productId, Product modifiedProduct) {
        Product found = this.findById(productId);
        found.setName(modifiedProduct.getName());
        found.setDescription(modifiedProduct.getDescription());
        found.setBrand(modifiedProduct.getBrand());
        found.setPrice(modifiedProduct.getPrice());
        found.setCategory(modifiedProduct.getCategory());
        found.setInMagazzino(modifiedProduct.getInMagazzino());
        found.setImageUrl("https://ui-avatars.com/api/?name=" + modifiedProduct.getName() + "+" + modifiedProduct.getBrand());
        found.setCreatedAt(LocalDate.now());
        found.setDiscount(0);
        return this.productRepository.save(found);
    }
    public void findByIdAndDelete(UUID productId) {
        Product found = this.findById(productId);
        this.productRepository.delete(found);
    }

        public Product uploadImage(UUID id, MultipartFile file) throws IOException {
        Product found = this.findById(id);
        String avatarURL = (String) cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setImageUrl(avatarURL);
        return productRepository.save(found);
    }
}
