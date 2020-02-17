package com.example.myapp6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    private int propertyId;
    private  String propertyName;
    private  String propertyRoom;
    private float propertyPrice;
    private String propertyInDate;
    private String propertyOutDate;
    private State propertyState;
    private Type propertyType;


    @Override
    public String toString() {
        return
                      propertyId +
                " " + propertyName +
                " " + propertyRoom +
                " " + propertyPrice +
                " " + propertyInDate +
                " " + propertyOutDate +
                " " + propertyState.getDescription() +
                " " + propertyType.getDescription()
                ;
    }
}
