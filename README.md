# Airplane

# 실행 전 필수 설정 (DB 연결)
DB 비밀번호 포함하고 있지 않음 (직접 세팅 필요)
1. **설정 파일 변경**
   - `src/main/resources/db.properties.example` 파일의 이름을 **`db.properties`**로 변경 (뒤 `.example` 제거)
2. **접속 정보 수정**
   - `db.properties` 파일을 열어 본인의 MySQL 비밀번호, 정보 입력
   ```properties
    db.url=DB URL 입력 (예 : jdbc:mysql://localhost:3306/airline_db)
    db.user=이름 입력 (예 : root)
    db.password=비밀번호 입력
   ```
3. **초기 SQL문 실행**
   - `src/main/resources/schema.sql` 코드 복사 후, MySQL에 실행 (테이블 생성)
