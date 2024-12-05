package com.example.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.Customer;
import com.example.Entity.CustomerAddress;
import com.example.Repository.CustomerRepository;
import com.example.Repository.CustomerAddressRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    
    @PostMapping("/basic-validation")
    public ResponseEntity<?> validateCustomerBasicDetails(@RequestParam String email, @RequestParam String dob) {
        if (email.isBlank() || dob.isBlank()) {
            return ResponseEntity.badRequest().body("Email/dob value is required");
        }
        
        try {
            LocalDate dateOfBirth = LocalDate.parse(dob);
            if (!email.matches(".+@.+\\.[a-z]{2,3}")) {
                return ResponseEntity.badRequest().body("Invalid email");
            }

            Optional<Customer> customerOpt = customerRepository.findByEmailAddressAndDateOfBirth(email, dateOfBirth);
            if (customerOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("No request placed for you.");
            }

            return ResponseEntity.ok("Validation successful");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Date of birth format should be yyyy-mm-dd");
        }
    }

    @PutMapping("/update-address")
    public ResponseEntity<?> updateCustomerAddress(@RequestBody CustomerAddress updatedAddress) {
        if (updatedAddress.getAddress().length() > 25) {
            return ResponseEntity.badRequest().body("Address should be maximum of 25 characters");
        }
        
        if (!updatedAddress.getPincode().matches("\\d{6}")) {
            return ResponseEntity.badRequest().body("Pin should be 6 digit number");
        }
        
        if (!updatedAddress.getCity().matches("[A-Za-z ]+") || !updatedAddress.getState().matches("[A-Za-z ]+")) {
            return ResponseEntity.badRequest().body("City/State should not contain any special characters except space");
        }

        customerAddressRepository.save(updatedAddress);
        return ResponseEntity.ok("Address updated successfully");
    }
}