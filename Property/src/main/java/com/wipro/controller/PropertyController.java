package com.wipro.controller;

import com.wipro.Service.PropertyService;
import com.wipro.entity.Property;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property")
@Tag(name = "Property", description = "Property management APIs")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @PostMapping
    @Operation(summary = "Save a Property", description = "Saves the property object into the database.")
    public Property addProperty(@RequestBody Property property) {
        return propertyService.saveProperty(property);
    }

    @GetMapping
    @Operation(summary = "Get all Properties", description = "Gets all properties present in the database.")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Property ", description = "Gets a property present in the database for the given Id.")
    public ResponseEntity<Property> getPropertyById(@PathVariable int id) {
        return propertyService.getPropertyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Get all Properties based on title ",
            description = "Gets a property present in the database for the given title.")
    public List<Property> getPropertiesByTitle(@PathVariable String title) {
        return propertyService.getPropertiesByTitle(title);
    }

    @GetMapping("/location/{location}")
    @Operation(summary = "Get all Properties based on location ",
            description = "Gets a property present in the database for the given location.")
    public List<Property> getPropertiesByLocation(@PathVariable String location) {
        return propertyService.getPropertiesByLocation(location);
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get all Properties based on type ",
            description = "Gets a property present in the database for the given type.")
    public List<Property> getPropertiesByType(@PathVariable String type) {
        return propertyService.getPropertiesByType(type);
    }

    @GetMapping("/price/{price}")
    @Operation(summary = "Get all Properties based on price ",
            description = "Gets a property present in the database for the given price.")
    public List<Property> getPropertiesByPrice(@PathVariable double price) {
        return propertyService.getPropertiesByPrice(price);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Property ", description = "Deletes a property present in the database for the given Id.")
    public void deleteProperty(@PathVariable int id) {
        propertyService.deleteProperty(id);
    }

    @PutMapping
    @Operation(summary = "Update a Property ", description = "Updates a property present in the database for the given Id, " +
            "else throw exception.")
    public Property updateProperty(@RequestBody Property property) {
        return propertyService.updateProperty(property);
    }

}
