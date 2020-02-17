package com.example.myapp6.model;

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
}
