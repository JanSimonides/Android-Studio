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
    private String ppropertyOutDate;
    private State propertyState;
    private Type propertyType;


    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
