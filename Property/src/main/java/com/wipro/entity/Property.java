package com.wipro.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "property")
@ToString
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String location;
    @Column
    private String type;
    @Column
    private Double price;
    @Column
    private Integer availableRooms;
    @Column
    private boolean lockForBook;
}
