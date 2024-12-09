package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.CustomerDto;
import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.response.LoginResponse;

public interface CustomerService {

    String addCustomer(CustomerDto customerDto);

    LoginResponse loginCustomer(LoginDto loginDto);
}
