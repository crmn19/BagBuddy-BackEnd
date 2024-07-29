package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/paypal")
public class PaypalController {
    @Autowired
    PaypalService paypalService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home() {
        return "";
    }

    @PostMapping("/pay")
    public String payment(@ModelAttribute("order") OrderProduct orderProduct) {
        try {
            Payment payment = paypalService.createPayment(
                    orderProduct.getPrice(),
                    orderProduct.getCurrency(),
                    orderProduct.getMethod(),
                    orderProduct.getIntent(),
                    orderProduct.getDescription(),
                    "http://localhost:5173/pay/cancel",
                    "http://localhost:5173/pay/success");
            System.out.println(payment.getLinks());
            for (Links links:payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println("Error occurred during payment creation: " + e.getMessage());
            e.printStackTrace();
        }
        return "";

    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
}
