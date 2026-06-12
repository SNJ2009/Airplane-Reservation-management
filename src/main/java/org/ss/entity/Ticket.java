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

    @Override
    public String toString() {
        return "[" +id+ "] 항공편 ID : " +scheduleId+ " | 좌석번호 : " +selectedSeat;
    }
}
