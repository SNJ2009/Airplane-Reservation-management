package org.ss.controller;

import org.ss.common.ConsoleView;
import org.ss.dao.ScheduleDAO;
import org.ss.dao.SeatDAO;
import org.ss.dao.TicketDAO;
import org.ss.entity.Member;
import org.ss.entity.Ticket;

import java.util.List;

public class BookController {
    private static final TicketDAO TICKET_DAO = new TicketDAO();
    private static final SeatDAO SEAT_DAO = new SeatDAO();
    private static final ScheduleDAO SCHEDULE_DAO = new ScheduleDAO();
    private static final Member MEMBER = Member.getInstance();

    public void book(String[] cmd) {
        Command.validLength(cmd, 0, 0);
    }

    public void ticketList(){
        List<Ticket> list = TICKET_DAO.ticketList(MEMBER.getId());
        ConsoleView.printTicketList(list);
    }
}
