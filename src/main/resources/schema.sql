-- 항공편 관리 시스템 초기화 스크립트
-- DB에 복붙

CREATE DATABASE airplane;
USE DATABASE airplane;

CREATE TABLE model (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

CREATE TABLE plane (
    id INT PRIMARY KEY AUTO_INCREMENT,
    airline VARCHAR(20) NOT NULL,
    model_id INT, -- 기종명
    max_seat INT NOT NULL, -- 최대 좌석 수

    FOREIGN KEY (model_id) REFERENCES model(id)
);

CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    plane_id INT,
    departure CHAR(3) NOT NULL, -- 출발지
    destination CHAR(3) NOT NULL, -- 도착지

    FOREIGN KEY (plane_id) REFERENCES plane(id)
);

CREATE TABLE schedule_seat ( -- 남은 좌석 위치
    id INT PRIMARY KEY AUTO_INCREMENT,
    schedule_id INT,
    seat VARCHAR(10) NOT NULL, -- A1, B2 . . .
    is_take BOOLEAN DEFAULT NULL, -- 좌석 예약 여부

    FOREIGN KEY (schedule_id) REFERENCES schedule(id)
    UNIQUE(schedule_id, seat) -- Composite Unique
);

CREATE TABLE user (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(64) NOT NULL, -- SHA-256 기준 64글자
    phone VARCHAR(20) NOT NULL,
    salt VARCHAR(5)
);

CREATE TABLE reservation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(50),
    schedule_id INT,
    selected_seat INT,

    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id),
    FOREIGN KEY (selected_seat) REFERENCES schedule_seat(id)
);
