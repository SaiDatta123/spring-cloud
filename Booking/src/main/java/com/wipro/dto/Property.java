package com.wipro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    private int id;
    private String title;
    private String location;
    private String type;
    private Double price;
    private Integer availableRooms;
    private boolean lockForBook;

}
