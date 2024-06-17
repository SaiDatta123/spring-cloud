package com.wipro.Service;

import com.wipro.Repository.PropertyRepo;
import com.wipro.entity.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    PropertyRepo propertyRepo;

    public Property saveProperty(Property property) {
        return propertyRepo.save(property);
    }

    public List<Property> getAllProperties() {
        return propertyRepo.findAll();
    }

    public Optional<Property> getPropertyById(int id) {
        return propertyRepo.findById(id);
    }

    public List<Property> getPropertiesByTitle(String title) {
        return propertyRepo.findByTitle(title);
    }

    public List<Property> getPropertiesByLocation(String location) {
        return propertyRepo.findByLocation(location);
    }

    public List<Property> getPropertiesByType(String type) {
        return propertyRepo.findByType(type);
    }

    public List<Property> getPropertiesByPrice(double price) {
        return propertyRepo.findByPrice(price);
    }

    public void deleteProperty(int id) {
        propertyRepo.deleteById(id);
    }

    public Property updateProperty(Property property) {
        Optional<Property> existingProperty = propertyRepo.findById(property.getId());
        if (existingProperty.isPresent()) {
            if (property.getTitle() != null)
                existingProperty.get().setTitle(property.getTitle());
            if (property.getType() != null)
                existingProperty.get().setType(property.getType());
            if (property.getPrice() != null)
                existingProperty.get().setPrice(property.getPrice());
            if (property.getLocation() != null)
                existingProperty.get().setLocation(property.getLocation());
            if (property.getAvailableRooms() != null)
                existingProperty.get().setAvailableRooms(property.getAvailableRooms());
            existingProperty.get().setLockForBook(property.isLockForBook());
            System.out.println("Property to be updated : " + existingProperty.get());
            propertyRepo.save(existingProperty.get());
            return existingProperty.get();
        } else {
            throw new RuntimeException("Property not found for the given id : " + property.getId());
        }
    }


}
