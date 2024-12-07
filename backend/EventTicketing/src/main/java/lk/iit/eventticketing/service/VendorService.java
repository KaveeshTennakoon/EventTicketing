package lk.iit.eventticketing.service;

import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.dto.VendorDto;
import lk.iit.eventticketing.response.LoginRespose;

public interface VendorService {

    String addVendor(VendorDto vendorDto);

    LoginRespose loginVendor(LoginDto loginDto);

}
