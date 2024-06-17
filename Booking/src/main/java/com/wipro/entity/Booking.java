package com.wipro.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "booking")
@ToString
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String bookingDescription;
    @Column
    private String orderNumber;
    @Column
    private Integer propertyId;
    @Column
    private Integer numRooms;
    @Column
    private String bookingStatus;

}
