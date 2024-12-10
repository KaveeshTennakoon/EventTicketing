package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.dto.VendorDto;
import lk.iit.eventticketing.response.LoginResponse;

public interface VendorService {

    String addVendor(VendorDto vendorDto);

    LoginResponse loginVendor(LoginDto loginDto);

}
