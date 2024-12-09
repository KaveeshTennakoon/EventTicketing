package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.CustomerDto;
import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.response.LoginRespose;

public interface CustomerService {

    String addCustomer(CustomerDto customerDto);

    LoginRespose loginCustomer(LoginDto loginDto);
}
