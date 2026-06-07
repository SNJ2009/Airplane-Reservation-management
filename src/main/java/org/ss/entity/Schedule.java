package org.ss.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Schedule {
    private int id;
    private int planeId;
    private String departure; // 출발지 (IATA 코드 (ICN, NRT, , , ))
    private String destination; // 도착지
    private LocalDateTime departureTime;
    private int flightTime;

    private String planeInfo;

    @Override
    public String toString() {
        return String.format("[%d] %s ➔ %s | 기종: %s | 출발: %s | 소요시간: %d분",
                id, departure, destination, planeInfo, departureTime, flightTime);
    }
}
