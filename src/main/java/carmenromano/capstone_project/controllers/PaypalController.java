package carmenromano.capstone_project.controllers;

import carmenromano.capstone_project.entities.OrderProduct;
import carmenromano.capstone_project.enums.OrderStatus;
import carmenromano.capstone_project.payload.IndirizzoPayload;
import carmenromano.capstone_project.payload.OrderProductPaypalPayload;
import carmenromano.capstone_project.services.OrderProductService;
import carmenromano.capstone_project.services.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
//@RequestMapping("/paypal")
public class PaypalController {
    @Autowired
    PaypalService paypalService;
    @Autowired
    OrderProductService orderProductService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @GetMapping("/")
    public String home() {
        return "";
    }
    @PostMapping("/pay")
    public ResponseEntity<Map<String, String>> payment(@RequestBody OrderProductPaypalPayload orderProductPaypalPayload) {
        try {
            Payment payment = paypalService.createPayment(
                    orderProductPaypalPayload.amount(),
                    orderProductPaypalPayload.currency(),
                    orderProductPaypalPayload.method(),
                    orderProductPaypalPayload.intent(),
                    orderProductPaypalPayload.description(),
                    "http://localhost:5173/pay/cancel",
                    "http://localhost:5173/pay/success");

            Map<String, String> response = new HashMap<>();
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    response.put("approval_url", links.getHref());
                    return ResponseEntity.ok(response);
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println("Error occurred during payment creation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Errore durante la creazione del pagamento."));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Impossibile ottenere l'URL di approvazione."));
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
