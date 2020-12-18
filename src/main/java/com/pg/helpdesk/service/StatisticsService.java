package com.pg.helpdesk.service;

import com.pg.helpdesk.domain.Statistics;
import com.pg.helpdesk.entity.Ticket;
import com.pg.helpdesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    TicketRepository ticketRepository;

    public Statistics getTicketStatistics(Statistics statistics) {
        statistics.setNewTickets(ticketRepository.countAllByStatusEquals(Ticket.Status.NEW));
        statistics.setOpenTickets(ticketRepository.countAllByStatusEquals(Ticket.Status.OPEN));
        statistics.setClosedTickets(ticketRepository.countAllByStatusEquals(Ticket.Status.CLOSED));

        return statistics;
    }
}
