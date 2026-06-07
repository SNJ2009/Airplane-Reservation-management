-- 항공편 관리 시스템 초기화 스크립트
-- DB에 복붙

CREATE DATABASE airplane;
USE airplane;

CREATE TABLE plane ( -- 항공사 항공기 별 최대 좌석 수
    id INT PRIMARY KEY AUTO_INCREMENT,
    airline VARCHAR(20) NOT NULL, -- 항공사 (같은 기종이어도 항공사마다 최대 좌석 수 다름)
    model VARCHAR(50), NOT NULL-- B747, A380 등
    max_seat INT NOT NULL, -- 최대 좌석 수
);

CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    plane_id INT,
    departure CHAR(3) NOT NULL, -- 출발지
    destination CHAR(3) NOT NULL, -- 도착지
    run_time DATETIME NOT NULL, -- 출발시간
    flight_time INT NOT NULL, -- 소요시간/분

    FOREIGN KEY (plane_id) REFERENCES plane(id)
);

CREATE TABLE flight_occupancies ( -- 좌석 상태
    id INT PRIMARY KEY AUTO_INCREMENT,
    schedule_id INT,
    seat VARCHAR(10) NOT NULL, -- A1, B2 . . .
    is_take BOOLEAN DEFAULT FALSE, -- 좌석 예약 여부

    FOREIGN KEY (schedule_id) REFERENCES schedule(id),
    UNIQUE(schedule_id, seat) -- Composite Unique
);

CREATE TABLE user (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(64) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    isManager BOOLEAN DEFAULT FALSE, -- 항공편 관리 가능한 사람인지 (프로그램에서 직접 주는거 X, 회원가입 후 DB 직접 수정해서 true로 바꿔줘야 함)
    salt VARCHAR(16) NOT NULL
);

CREATE TABLE reservation ( -- 누가 어떤 좌석 예약했는지
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(50), -- 예약한 유저
    schedule_id INT, -- 어떤 항공편 좌석인지 ( 항공편 id )
    selected_seat INT, -- 좌석번호 FK (좌석 번호 id)(A1, A2 , , , )

    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id),
    FOREIGN KEY (selected_seat) REFERENCES schedule_seat(id)
);
