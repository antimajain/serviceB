package com.example.serviceB.module;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeightPerCountry {
    private String country;
    private double totalWeight;

    public WeightPerCountry(String country, double totalWeight) {
        this.country = country;
        this.totalWeight = totalWeight;
    }
}
