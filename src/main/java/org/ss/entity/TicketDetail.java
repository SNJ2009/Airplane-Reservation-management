package org.ss.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TicketDetail extends Ticket{
    private Ticket ticket;
    private Schedule schedule;
//    private int id;
//    private int planeId;
//    private String departure; // 출발지 (IATA 코드 (ICN, NRT, , , ))
//    private String arrival; // 도착지
//    private LocalDateTime departureTime;
//    private int flightTime;
//
//    private String planeInfo;


    @Override
    public String toString() {
        return String.format("[Ticket #%d] %s | [%s번 항공편] %s ➔ %s, %d분 | %s | 좌석번호: %s",
                ticket.getId(), schedule.getDepartureTime(),
                schedule.getId(), schedule.getDeparture(), schedule.getArrival(), schedule.getFlightTime(),
                schedule.getPlaneInfo(), ticket.getSelectedSeat());
    }
}
