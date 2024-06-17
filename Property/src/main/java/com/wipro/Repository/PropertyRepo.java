package com.wipro.Repository;

import com.wipro.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepo extends JpaRepository<Property,Integer> {

    List<Property> findByTitle(String title);
    List<Property> findByLocation(String location);
    List<Property> findByType(String type);
    List<Property> findByPrice(double price);

}
