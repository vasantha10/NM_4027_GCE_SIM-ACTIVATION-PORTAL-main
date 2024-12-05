package com.example.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.SimDetails;
import com.example.Entity.SimOffers;
import com.example.Repository.SimDetailsRepository;
import com.example.Repository.SimOffersRepository;

@RestController
@RequestMapping("/api/sim")
public class SimController {

    @Autowired
    private SimDetailsRepository simDetailsRepository;

    @Autowired
    private SimOffersRepository simOffersRepository;

    @PostMapping("/validate")
    public ResponseEntity<?> validateSim(@RequestParam String simNumber, @RequestParam String serviceNumber) {
        Optional<SimDetails> simDetailsOpt = simDetailsRepository.findBySimNumberAndServiceNumber(simNumber, serviceNumber);

        if (simDetailsOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid details, please check again SIM number/Service number!");
        }

        SimDetails simDetails = simDetailsOpt.get();
        if ("active".equalsIgnoreCase(simDetails.getSimStatus())) {
            return ResponseEntity.badRequest().body("SIM already active");
        }

        List<SimOffers> offers = simOffersRepository.findBySimDetails(simDetails);
        return ResponseEntity.ok(offers);
    }
}