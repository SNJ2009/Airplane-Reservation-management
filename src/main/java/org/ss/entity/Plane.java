package org.ss.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Plane {
    private int id;
    private String airline;
    private String model;
    private int maxSeat;

    @Override
    public String toString() {
        return "[" +id + "] " + airline + " | " + model + " | " + maxSeat;
    }
}
