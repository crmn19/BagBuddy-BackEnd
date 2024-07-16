package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.Product;
import carmenromano.capstone_project.exceptions.BadRequestException;
import carmenromano.capstone_project.payload.ProductPayload;
import carmenromano.capstone_project.services.CartService;
import carmenromano.capstone_project.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

public class CartController {

}
