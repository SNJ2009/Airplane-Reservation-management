package org.ss.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Seat {
    private int id;
    private int scheduleId;
    private int seatNumber;
    private boolean isBooked;
}
