package com.example.myapp6.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private  long idType;
    private int intType;
    private String description;

    public Type(int number, String description) {
        this.intType = number;
        this.description = description;
    }

    public Type(int intType) {
        this.intType = intType;
    }
}
