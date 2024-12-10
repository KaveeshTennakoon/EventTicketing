package lk.iit.eventticketing.controller;

import lk.iit.eventticketing.dto.CustomerDto;
import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.response.LoginResponse;
import lk.iit.eventticketing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping(path = "/save")
    public String saveCustomer(@RequestBody CustomerDto customerDto) {
        String id = customerService.addCustomer(customerDto);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginDto loginDto){
        LoginResponse loginResponse = customerService.loginCustomer(loginDto);
        return ResponseEntity.ok(loginResponse);
    }
}
