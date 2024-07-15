package carmenromano.capstone_project.services;


import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.exceptions.UnauthorizedException;
import carmenromano.capstone_project.payload.CustomerLoginPayload;
import carmenromano.capstone_project.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(CustomerLoginPayload payload){

        Customer user = this.customerService.findByEmail(payload.email());

        if(bcrypt.matches(payload.password(), user.getPassword())){

            return jwtTools.createToken(user);
        } else {

            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}