package lk.iit.eventticketing.controller;

import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.dto.VendorDto;
import lk.iit.eventticketing.response.LoginResponse;
import lk.iit.eventticketing.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping(path = "/save")
    public String saveVendor(@RequestBody VendorDto vendorDto) {
        String id = vendorService.addVendor(vendorDto);
        return id;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginVendor(@RequestBody LoginDto loginDto) {
        LoginResponse loginResponse = vendorService.loginVendor(loginDto);
        return ResponseEntity.ok(loginResponse);
    }
}

