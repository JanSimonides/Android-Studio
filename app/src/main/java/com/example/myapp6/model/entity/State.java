package com.example.myapp6.model.entity;

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

    public State(char charState, String description) {
        this.charState = charState;
        this.description = description;
    }

    public State(char charState) {
        this.charState = charState;
    }
}
