package com.weilai.split.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_address")
public class Address {
    @Id
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "address_name")
    private String addressName;
}