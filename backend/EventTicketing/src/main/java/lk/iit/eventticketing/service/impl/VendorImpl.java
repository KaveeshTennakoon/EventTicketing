package lk.iit.eventticketing.service.impl;

import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.response.LoginRespose;
import lk.iit.eventticketing.service.VendorService;
import lk.iit.eventticketing.dto.VendorDto;
import lk.iit.eventticketing.model.Vendor;
import lk.iit.eventticketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lk.iit.eventticketing.util.JwtUtil;

import java.util.Optional;


@Service
public class VendorImpl implements VendorService {

    @Autowired
    private VendorRepo vendorRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String addVendor(VendorDto vendorDto) {

        Vendor vendor = new Vendor(
                vendorDto.getVendorID(),
                vendorDto.getVendorName(),
                vendorDto.getVendorEmail(),
                this.passwordEncoder.encode(vendorDto.getVendorPassword())
        );

        vendorRepo.save(vendor);

        return vendor.getVendorName();
    }

    @Override
    public LoginRespose loginVendor(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        // Find vendor by email
        Vendor vendor = vendorRepo.findByVendorEmail(email);

        if (vendor != null) {
            // Compare the provided password with the stored (hashed) password
            boolean isPasswordCorrect = passwordEncoder.matches(password, vendor.getVendorPassword());

            if (isPasswordCorrect) {
                String token = jwtUtil.generateToken(email);
                return new LoginRespose("Login successful", true, token);
            } else {
                return new LoginRespose("Password is incorrect", false, null);
            }
        } else {
            return new LoginRespose("Email is incorrect", false, null);
        }
    }



}
