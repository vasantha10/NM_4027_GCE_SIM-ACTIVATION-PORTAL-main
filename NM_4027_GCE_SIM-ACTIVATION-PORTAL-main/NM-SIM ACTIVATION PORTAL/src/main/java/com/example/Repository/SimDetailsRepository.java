package com.example.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.SimDetails;

public interface SimDetailsRepository extends JpaRepository<SimDetails, Long> {
    Optional<SimDetails> findBySimNumberAndServiceNumber(String simNumber, String serviceNumber);
}