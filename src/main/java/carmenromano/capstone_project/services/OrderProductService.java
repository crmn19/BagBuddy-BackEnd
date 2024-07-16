package carmenromano.capstone_project.services;


import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.entities.OrderItem;
import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.payload.OrderProductPayload;
import carmenromano.capstone_project.repositories.CustomerRepository;
import carmenromano.capstone_project.repositories.OrderItemRepository;
import carmenromano.capstone_project.repositories.OrderProductRepository;
import carmenromano.capstone_project.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderProductService {
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

}
