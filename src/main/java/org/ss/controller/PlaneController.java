package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.dao.MemberDAO;
import org.ss.dao.PlaneDAO;
import org.ss.entity.Plane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PlaneController {
    private final PlaneDAO planeDAO = new PlaneDAO();
//    private Map<String, Consumer<String[]>> commandMap = new HashMap<>();

//    public PlaneController(){}

    public void plane(String[] cmd){
        MemberController.isNotManager();
        Command.validLength(cmd, 2, 6);
        if(cmd.length == 2) { list(); return; }

        String action = cmd[2];
        if(action.equals("add")) {
            Command.validLength(cmd, 6);
            String airline = cmd[3];
            String model = cmd[4];
            int maxSeat = Integer.parseInt(cmd[5]);

            // 0은 파라미터 수 맞추기용
            addPlane(new Plane(0, airline, model, maxSeat));
        } else if (action.equals("remove")) {
            Command.validLength(cmd, 4);
            int id = Integer.parseInt(cmd[3]);

            removePlane(id);
        } else if (cmd.length == 4) {
            String airline = cmd[2];
            String model = cmd[3];

            list(airline, model);
        } else throw new IllegalArgumentException("Invalid command");
    }

    public void addPlane(Plane plane){
        planeDAO.save(plane);
    }
    public void removePlane(int id){
        planeDAO.remove(id);
    }

    public void list(){
        List<Plane> planeList = planeDAO.getList();
        ConsoleView.printPlaneList(planeList);
    }
    public void list(String airline, String model){
        List<Plane> planeList = planeDAO.getList(airline, model);
        ConsoleView.printPlaneList(planeList);
    }
}
