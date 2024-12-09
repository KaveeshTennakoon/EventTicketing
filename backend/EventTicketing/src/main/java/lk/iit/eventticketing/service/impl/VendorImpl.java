package lk.iit.eventticketing.service.impl;

import lk.iit.eventticketing.dto.LoginDto;
import lk.iit.eventticketing.response.LoginResponse;
import lk.iit.eventticketing.service.VendorService;
import lk.iit.eventticketing.dto.VendorDto;
import lk.iit.eventticketing.model.Vendor;
import lk.iit.eventticketing.repo.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lk.iit.eventticketing.util.JwtUtil;


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
    public LoginResponse loginVendor(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();

        // Find vendor by email
        Vendor vendor = vendorRepo.findByVendorEmail(email);

        if (vendor != null) {
            // Compare the provided password with the stored (hashed) password
            boolean isPasswordCorrect = passwordEncoder.matches(password, vendor.getVendorPassword());

            if (isPasswordCorrect) {
                String token = jwtUtil.generateToken(email, "VENDOR");
                return new LoginResponse("Login successful", true, token, vendor.getVendorName(), vendor.getVendorID());
            } else {
                return new LoginResponse("Password is incorrect", false, null, null, -1);
            }
        } else {
            return new LoginResponse("Email is incorrect", false, null, null, -1);
        }
    }

}
