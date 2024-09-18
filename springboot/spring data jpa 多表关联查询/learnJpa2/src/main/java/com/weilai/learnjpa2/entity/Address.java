package com.weilai.learnjpa2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;


    private String areaCode;

    private String country;

    private String province;

    private String city;

    private String area;

    private String detailAddress;

    public Address(String areaCode, String country, String province, String city, String area,
                   String detailAddress) {
        super();
        this.areaCode = areaCode;
        this.country = country;
        this.province = province;
        this.city = city;
        this.area = area;
        this.detailAddress = detailAddress;
    }
}