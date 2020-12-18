package com.pg.helpdesk.init;

import com.pg.helpdesk.entity.Message;
import com.pg.helpdesk.entity.Ticket;
import com.pg.helpdesk.entity.User;
import com.pg.helpdesk.repository.MessageRepository;
import com.pg.helpdesk.repository.TicketRepository;
import com.pg.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TicketInitializer implements DataLoader {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void load() {
        if (ticketRepository.count() == 0) {
            User user = userRepository.findByUsername("user");
            LocalDateTime now = LocalDateTime.now();

            Ticket ticket = new Ticket();
            ticket.setCreationDate(now);
            ticket.setStatus(Ticket.Status.NEW);
            ticket.setUser(user);
            ticket.setLastStatusChangeBy(user);
            ticket.setLastStatusChangeDate(now);
            ticket.setTitle("Super Device 2.0 is a worthless DOA piece of shit");
            ticketRepository.save(ticket);

            Message message = new Message();
            message.setTicket(ticket);
            message.setUser(user);
            message.setCreationDate(now);
            message.setContent("Your stupid shit doesn't even turn on. I have tried different power cables and outlets, and yet this SHIT DEVICE (for lack of a better word) refuses to work at all. Symptoms? You plug in the power and the LED doesn't even turn off. Black screen, no fan noise. Nothing.");
            messageRepository.save(message);
        }
    }
}
