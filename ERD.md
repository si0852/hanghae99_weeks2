# hanghae99_week2

<img width="1049" alt="image" src="https://github.com/si0852/hanghae99_week2/assets/64186698/796ba70d-8109-4066-aa7d-32656cddc2d6">


1. Users -> 유저정보 테이블
   - userId: 유저를 식별하는 Id, 기본키 설정
   - userName: 유저의 이름
3. Lectures -> 강의정보 테이블
   - lcId: 강의 식별하는 Id, 기본키
   - lectureName: 강의 이름
5. Apply -> 신청정보 테이블
   - applyId: 신청정보를 구별할수 있는 Id, 기본키
   - scheduleId: Schedule의 Id
   - userId: Users의 Id
   - attenDate: 신청일자
   - attendanceYn: 신청여부
7. Schedule -> 특강내역 테이블
   - scheduleId: 특강을 식별하는 Id, 기본키
   - lcId: Lectures의 Id
   - lectureName: Lectures의 lectureName
   - openDate: 특강 오픈 일자
   - maxAttendees: 참석자 수
   - attendees: 현재 참석자
9. LectureHistory : 강의 이력 테이블
    - historyId: 강의 이력을 구별할 수 있는 Id\
    - scheduleId: Schedule의 id\
    - userId: Users의 id
    - lectureType: 신청 or 취소 타입
    - attendDate: 신청일자
