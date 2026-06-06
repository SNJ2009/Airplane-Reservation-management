package org.ss.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString // System.out.println() 으로 확인용
public class Plane {
    private int id;
    private String airline;
    private String model;
    private int maxSeat;
}
