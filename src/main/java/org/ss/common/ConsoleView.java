package org.ss.common;

import org.ss.entity.Plane;
import org.ss.entity.Schedule;
import org.ss.entity.Ticket;
import org.ss.entity.TicketDetail;

import java.util.List;

public class ConsoleView {
    public static void commands(){ // HELP 명령 입력 시
        System.out.println("USER ------------------------------------------------------------------------------------------");
        System.out.println("[SignUp]            air user signup \"ID\" \"이름\" \"비밀번호\" \"전화번호\"");
        System.out.println("[Login]             air user login \"ID\" \"비밀번호\"");
        System.out.println("MANAGEMENT (관리자 권한 필수) --------------------------------------------------------------------");
        System.out.println("[Plane Add]         air plane add \"항공사\" \"항공기 모델\" 좌석수");
        System.out.println("[Plane Remove]      air plane remove 항공기ID");
        System.out.println("[Planes]            air plane");
        System.out.println("[Planes(Filter)]    air plane \"항공사\" \"항공기 모델\"");
        System.out.println("[Schedule Add]      air schedule add 항공기ID 출발지 도착지 \"출발시간(YYYY-MM-DD HH:MM:SS)\" 소요시간(분)");
        System.out.println("[Schedule Remove]   air schedule remove 스케줄ID");
        System.out.println("[Schedule Delay]    air schedule delay 스케줄ID 지연시간(분 단위, +/- 둘 다 가능)");
        System.out.println("FLIGHTS ---------------------------------------------------------------------------------------");
        System.out.println("[Schedules]         air schedule");
        System.out.println("[Schedules(Filter)] air schedule 출발지 도착지");
        System.out.println("[Booked]            air book");
        System.out.println("[Booked]            air book 출발지 도착지");
        System.out.println("[Booked]            air book 도착지");
        System.out.println("[Booked]            air book 티켓ID");
        System.out.println("[Book Add]          air book add 스케줄ID 선택할_좌석_번호");
        System.out.println("[Book Cancel]       air book remove 티켓ID");
        System.out.println("[Seat Change]       air book sc 티켓ID 좌석번호");
        System.out.println("-----------------------------------------------------------------------------------------------");
        System.out.println(Color.YELLOW+ "출발지/도착지 모두 IATA 공항 코드 사용 (ICN, NRT, NYC. . . )\n" +
                "띄어쓰기 없는 경우 \"\" 생략 가능, 띄어쓰기 포함 시 \"\" 필수\n" +
                "예약 정보 조회/추가/삭제 시 로그인 필수" +Color.RESET);
    }

    public static void printScheduleList(List<Schedule> list){
        printList(list, "조회된 스케줄 없음");
    }
    public static void printPlaneList(List<Plane> list){
        printList(list, "조회된 항공기 없음");
    }
    public static void printTicketList(List<TicketDetail> list){
        printList(list, "조회된 예약 없음");
    }
    private static <T> void printList(List<T> list, String message){
        if(list.isEmpty() || list == null){
            message(message);
            return;
        }

        for(T item : list){
            message(item.toString());
        }
    }

    /**
     * null이 아닌지 검사 <br>
     *
     * @param fields 입력받은 필드
     * @return 하나라도 {@code null}이라면 {@code true} 반환
     */
    public static boolean checkEmpty(String...fields){
        for(String field : fields) {
            if (field.isEmpty() || field.equals("") || field == null) {
                error("입력된 내용 중 일부 누락됨");
                return true;
            }
        }
        return false;
    }

    public static void asciArt(){
        System.out.println(Color.CYAN);
        System.out.println("     ___   _   ____   ____   _        ___   _   _   _____");
        System.out.println("    / _ \\ | | |  _ \\ |  _ \\ | |      / _ \\ | \\ | | | ____|");
        System.out.println("   / /_\\ \\| | | |_) || |_) || |     / /_\\ \\|  \\| | |  _|  ");
        System.out.println("  / ____ \\| | |  _ < |  __/ | |___ / ____ \\| |\\  | | |___ ");
        System.out.println(" /_/    \\_\\_|_|_| \\_\\|_|    |_____/_/    \\_\\_| \\_| |_____|");
        System.out.println(Color.RESET);
    }


    public static void successful(){
        info(Color.GREEN+ "Successful" +Color.RESET);
    }
    public static void info(String message){
        System.out.println("[INFO] : " +message+ "\n");
    }
    public static void message(String message) { System.out.println(message+ "\n"); }
    public static void debugger(String message) {
        System.out.println("[DEBUGGER] : " + message + "\n");
    } // 사실 그냥 sout 하면 그만이긴 한데 구분용이랄까?

    public static void failedBook(String message){ error("예약 실패 : " +message+ " 또는 잘못된 입력"); }
    public static void error(String message){
        System.out.println(Color.RED+ "[ERROR] : " +message+ Color.RESET+ "\n");
    }
    public static void line(int level){
        for(int i = 0; i < level; i++){
            System.out.println();
        }
    }
}
