package com.example.myapp6.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class State {

    private  long idState;
    private Character charState;
    private String description;
}
