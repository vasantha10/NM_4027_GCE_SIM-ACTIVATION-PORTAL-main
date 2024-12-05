package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Entity.CustomerAddress;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {}
