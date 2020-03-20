package com.example.myapp6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDTO {

    private  String propertyName;
    private  String propertyRoom;
    private float propertyPrice;
    private String propertyInDate;
    private String propertyOutDate;
    private char charState;
    private int intType;

}
