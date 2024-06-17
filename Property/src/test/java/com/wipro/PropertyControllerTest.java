package com.wipro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.Service.PropertyService;
import com.wipro.controller.PropertyController;
import com.wipro.entity.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PropertyController.class)
@AutoConfigureMockMvc
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProperties() throws Exception {
        // Get all properties will return empty array even without any properties in database
        mockMvc.perform(MockMvcRequestBuilders.get("/property")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetPropertyById() throws Exception {
        // Assuming property with id 1 does not exist
        mockMvc.perform(MockMvcRequestBuilders.get("/property/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testCreateProperty() throws Exception {
        Property property = new Property();
        property.setTitle("Sample Property");
        property.setLocation("Sample Location");
        property.setType("House");
        property.setPrice(100.0);
        property.setAvailableRooms(3);
        property.setLockForBook(false);

        String jsonProperty = objectMapper.writeValueAsString(property);

        mockMvc.perform(MockMvcRequestBuilders.post("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProperty))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProperty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/property/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProperty() throws Exception {
        Property property = new Property();
        property.setId(1);
        property.setTitle("Updated Property Title");
        property.setLocation("Updated Location");
        property.setType("Villa");
        property.setPrice(500.0);
        property.setAvailableRooms(4);
        property.setLockForBook(false);

        String jsonProperty = objectMapper.writeValueAsString(property);

        mockMvc.perform(MockMvcRequestBuilders.put("/property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProperty))
                .andExpect(status().isOk());
    }

}
