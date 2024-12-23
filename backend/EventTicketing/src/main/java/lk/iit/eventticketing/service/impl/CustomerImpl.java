package lk.iit.eventticketing.service.impl;

import lk.iit.eventticketing.dto.CustomerDto;
import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.model.Customer;
import lk.iit.eventticketing.repo.CustomerRepo;
import lk.iit.eventticketing.response.LoginResponse;
import lk.iit.eventticketing.service.CustomerService;
import lk.iit.eventticketing.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String addCustomer(CustomerDto customerDto) {

        Customer customer = new Customer(
                customerDto.getCustomerID(),
                customerDto.getCustomerName(),
                customerDto.getCustomerEmail(),
                this.passwordEncoder.encode(customerDto.getCustomerPassword())
        );

        customerRepo.save(customer);

        return customer.getCustomerName();
    }

    @Override
    public LoginResponse loginCustomer(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        // Find vendor by email
        Customer customer = customerRepo.findByCustomerEmail(email);

        if (customer != null) {
            // Compare the provided password with the stored (hashed) password
            boolean isPasswordCorrect = passwordEncoder.matches(password, customer.getCustomerPassword());

            if (isPasswordCorrect) {
                String token = jwtUtil.generateToken(email, "CUSTOMER");
                return new LoginResponse("Login successful", true, token, customer.getCustomerName(), customer.getCustomerID());
            } else {
                return new LoginResponse("Password is incorrect", false, null, null, -1);
            }
        } else {
            return new LoginResponse("Email is incorrect", false, null, null, -1);
        }
    }
}
