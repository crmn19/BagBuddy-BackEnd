package carmenromano.capstone_project.security;

import carmenromano.capstone_project.entities.Customer;
import carmenromano.capstone_project.exceptions.UnauthorizedException;
import carmenromano.capstone_project.services.CustomerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JWTAuthFilter {
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private CustomerService customerService;


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Il token inserito non è corretto!");
        String accessToken = authorizationHeader.substring(7);
        jwtTools.verifyToken(accessToken);

        String customerId = jwtTools.extractIdFromToken(accessToken);
        Customer customer = customerService.findById(UUID.fromString(customerId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(customer, null, customer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }



    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }

}