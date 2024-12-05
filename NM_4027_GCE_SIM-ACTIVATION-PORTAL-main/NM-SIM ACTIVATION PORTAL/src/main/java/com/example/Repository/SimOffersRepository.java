package com.example.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.SimOffers;

public interface SimOffersRepository extends JpaRepository<SimOffers, Long> {
    List<SimOffers> findBySimDetails(com.example.Entity.SimDetails simDetails);
}