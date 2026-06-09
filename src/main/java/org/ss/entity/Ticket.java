package org.ss.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Ticket {
    private int id;
    private String userId;
    private int scheduleId;
    private int selectedSeat;
}
