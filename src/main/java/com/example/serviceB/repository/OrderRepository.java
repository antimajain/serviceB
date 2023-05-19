package com.example.serviceB.repository;

import com.example.serviceB.module.Order;
import com.example.serviceB.module.OrderPerCountry;
import com.example.serviceB.module.WeightPerCountry;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM orders o WHERE (:country is null OR " +
            "o.country= :country) " +
            "AND (:weightLimit is null OR o.parcel_weight >= :weightLimit)")
    List<Order> findALLByCountryOrWeightLimit(String country, Double weightLimit, Pageable page);

    @Query(value = "SELECT new com.example.serviceB.module.OrderPerCountry(o.country as country,COUNT(o)" +
            " as countNo) FROM orders o GROUP BY o.country")
   // @Query(nativeQuery = true, value = "SELECT o.country as country,COUNT(*) as countNo FROM orders as o GROUP BY o.country")
    List<OrderPerCountry> findOrdersPerCountry();

    @Query(value = "SELECT new com.example.serviceB.module.WeightPerCountry(o.country as country," +
            "SUM(o.parcel_weight) as totalWeight) FROM orders o GROUP BY o.country")
    List<WeightPerCountry> findTotalWeightPerCountry();
}
