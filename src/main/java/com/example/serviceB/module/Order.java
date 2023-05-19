package com.example.serviceB.module;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    @Id
    private String id;
    private String email;
    private String phone_number;
    private Double parcel_weight;
    private String country;
}
