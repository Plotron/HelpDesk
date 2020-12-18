package com.pg.helpdesk.service;

import com.pg.helpdesk.entity.Message;
import com.pg.helpdesk.entity.Ticket;
import com.pg.helpdesk.entity.User;
import com.pg.helpdesk.form.MessageForm;
import com.pg.helpdesk.form.TicketForm;
import com.pg.helpdesk.repository.MessageRepository;
import com.pg.helpdesk.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MessageRepository messageRepository;

    public Ticket saveNewTicket(TicketForm formData, User creator) {
        LocalDateTime now = LocalDateTime.now();

        Ticket ticket = new Ticket();
        ticket.setTitle(formData.getTitle());
        ticket.setUser(creator);
        ticket.setLastStatusChangeBy(creator);
        ticket.setLastStatusChangeDate(now);
        ticket.setCreationDate(now);
        ticket.setStatus(Ticket.Status.NEW);
        ticketRepository.saveAndFlush(ticket);

        Message message = new Message();
        message.setContent(formData.getMessage());
        message.setCreationDate(now);
        message.setUser(creator);
        message.setTicket(ticket);
        messageRepository.saveAndFlush(message);

        return ticket;
    }

    public Ticket closeTicket(Ticket ticket, User who) {
        ticket.setLastStatusChangeDate(LocalDateTime.now());
        ticket.setLastStatusChangeBy(who);
        ticket.setStatus(Ticket.Status.CLOSED);
        ticketRepository.save(ticket);

        return ticket;
    }

    public void addStaffMessage(Ticket ticket, MessageForm formData, User sender) {
        if (ticket.getStatus() == Ticket.Status.NEW) {
            ticket.setStatus(Ticket.Status.OPEN);
            ticket.setLastStatusChangeDate(LocalDateTime.now());
            ticket.setLastStatusChangeBy(sender);
        }
        addMessage(ticket, formData, sender);
    }

    public void addMessage(Ticket ticket, MessageForm formData, User sender) throws IllegalArgumentException {
        LocalDateTime now = LocalDateTime.now();
        if (ticket.getStatus() == Ticket.Status.CLOSED) {
            throw new IllegalArgumentException("This ticket is closed and new messages cannot be added to it.");
        }

        Message message = new Message();
        message.setTicket(ticket);
        message.setUser(sender);
        message.setCreationDate(now);
        message.setContent(formData.getContent());

        messageRepository.save(message);
    }

    public Page<Ticket> findAllByUser(User user, Pageable pageable) {
        return ticketRepository.findAllByUserOrderByLastStatusChangeDateDesc(user, pageable);
    }

    public boolean ticketBelongsToUser(Ticket ticket, User user) {
        return (ticket.getUser() == user);
    }

    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAllByOrderByCreationDateDesc(pageable);
    }
}
