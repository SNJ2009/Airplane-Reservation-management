-- 항공편 관리 시스템 초기화 스크립트
-- DB에 복붙

CREATE DATABASE airplane;
USE airplane;

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
    runtime DATETIME NOT NULL, -- 출발시간
    endtime DATETIME NOT NULL, -- 도착시간

    FOREIGN KEY (plane_id) REFERENCES plane(id)
);

CREATE TABLE schedule_seat ( -- 좌석 번호랄까? 비행기 좌석 구조랄까? ( 이거 없으면 유저가 예약 취소 했을 떄 좌석까지 같이 사라짐 )
    id INT PRIMARY KEY AUTO_INCREMENT,
    schedule_id INT,
    seat VARCHAR(10) NOT NULL, -- A1, B2 . . .
    is_take BOOLEAN DEFAULT NULL, -- 좌석 예약 여부

    FOREIGN KEY (schedule_id) REFERENCES schedule(id),
    UNIQUE(schedule_id, seat) -- Composite Unique
);

CREATE TABLE user (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(64) NOT NULL, -- SHA-256 기준 64글자
    phone VARCHAR(20) NOT NULL,
    isManager BOOLEAN DEFAULT FALSE, -- 항공편 관리 가능한 사람인지
    salt VARCHAR(16) NOT NULL -- 레인보우 테이블 어택 무력화용 salt ( 사용자마다 솔트 다르게 들어가서 같은 비번이라도 해시값 다름 = 공격 지연 )
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
