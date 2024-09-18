package com.weilai.learnjpa2.repository;

import com.weilai.learnjpa2.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    public Address findByAddressId(Long addressId);
}