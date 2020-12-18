package com.pg.helpdesk.controller;

import com.pg.helpdesk.entity.Ticket;
import com.pg.helpdesk.entity.User;
import com.pg.helpdesk.exception.ResourceNotFoundException;
import com.pg.helpdesk.form.MessageForm;
import com.pg.helpdesk.form.TicketForm;
import com.pg.helpdesk.service.TicketService;
import com.pg.helpdesk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class TicketController {
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;

    @Secured("ROLE_USER")
    @GetMapping({"/user/tickets", "/user/ticket/list"})
    public String myTickets(
            Model model,
            Pageable pageable
    ) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("entities", ticketService.findAllByUser(currentUser, pageable));
        return "/user/ticket/list";
    }

    @Secured("ROLE_HELPDESK")
    @GetMapping({"/admin/tickets", "/admin/ticket/list"})
    public String tickets(
            Model model,
            Pageable pageable
    ) {
        model.addAttribute("entities", ticketService.findAll(pageable));
        return "/admin/ticket/list";
    }

    @Secured("ROLE_USER")
    @GetMapping("/user/ticket/create")
    public String add(
            Model model
    ) {
        model.addAttribute("formData", new TicketForm());
        return "/user/ticket/create";
    }

    @Secured("ROLE_USER")
    @PostMapping("/user/ticket/create")
    public String add(
            @ModelAttribute("formData") @Valid TicketForm formData,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/user/ticket/create";
        }
        Ticket ticket = ticketService.saveNewTicket(formData, userService.getCurrentUser());
        return "redirect:/user/ticket/view/" + ticket.getId();
    }

    @Secured("ROLE_USER")
    @GetMapping("user/ticket/view/{id}")
    public String viewMyTicket(
            @PathVariable("id") Ticket ticket,
            Model model
    ) {
        if (ticket == null || !ticketService.ticketBelongsToUser(ticket, userService.getCurrentUser())) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("ticket", ticket);
        return "/user/ticket/view";
    }

    @Secured("ROLE_HELPDESK")
    @GetMapping("admin/ticket/view/{id}")
    public String viewTicket(
            @PathVariable("id") Ticket ticket,
            Model model
    ) {
        if (ticket == null) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("ticket", ticket);
        return "/admin/ticket/view";
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "user/ticket/close/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String closeMyTicket(
            HttpServletRequest request,
            @PathVariable("id") Ticket ticket,
            Model model
    ) {
        User currentUser = userService.getCurrentUser();
        if (ticket == null || !ticketService.ticketBelongsToUser(ticket, currentUser)) {
            throw new ResourceNotFoundException();
        }

        boolean isPost = request.getMethod().equals("POST");
        if (isPost) {
            ticket = ticketService.closeTicket(ticket, currentUser);
            model.addAttribute("ticket", ticket);
            return "/user/ticket/view";
        }

        model.addAttribute("object", ticket);
        return "/user/ticket/close";
    }

    @Secured("ROLE_HELPDESK")
    @RequestMapping(value = "admin/ticket/close/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String closeTicket(
            HttpServletRequest request,
            @PathVariable("id") Ticket ticket,
            Model model
    ) {
        User currentUser = userService.getCurrentUser();
        if (ticket == null) {
            throw new ResourceNotFoundException();
        }

        boolean isPost = request.getMethod().equals("POST");
        if (isPost) {
            ticket = ticketService.closeTicket(ticket, currentUser);
            model.addAttribute("ticket", ticket);
            return "/admin/ticket/view";
        }

        model.addAttribute("object", ticket);
        return "/admin/ticket/close";
    }

    @Secured("ROLE_USER")
    @GetMapping("/user/ticket/add-message/{id}")
    public String addMessage(
            @PathVariable("id") Ticket ticket,
            Model model
    ) {
        if (ticket == null || !ticketService.ticketBelongsToUser(ticket, userService.getCurrentUser())) {
            throw new ResourceNotFoundException();
        }

        model.addAttribute("object", ticket);
        model.addAttribute("formData", new MessageForm());
        return "/user/ticket/add-message";
    }

    @Secured("ROLE_USER")
    @PostMapping("/user/ticket/add-message/{id}")
    public String addMessage(
            @PathVariable("id") Ticket ticket,
            @ModelAttribute("formData") @Valid MessageForm formData,
            BindingResult bindingResult
    ) {
        if (ticket == null || !ticketService.ticketBelongsToUser(ticket, userService.getCurrentUser())) {
            throw new ResourceNotFoundException();
        }

        if (bindingResult.hasErrors()) {
            return "/user/ticket/add-message";
        }
        ticketService.addMessage(ticket, formData, userService.getCurrentUser());
        return "redirect:/user/ticket/view/" + ticket.getId();
    }

    @Secured("ROLE_HELPDESK")
    @GetMapping("/admin/ticket/add-message/{id}")
    public String addStaffMessage(
            @PathVariable("id") Ticket ticket,
            Model model
    ) {
        if (ticket == null) {
            throw new ResourceNotFoundException();
        }
        model.addAttribute("object", ticket);
        model.addAttribute("formData", new MessageForm());
        return "/admin/ticket/add-message";
    }

    @Secured("ROLE_HELPDESK")
    @PostMapping("/admin/ticket/add-message/{id}")
    public String addStaffMessage(
            @PathVariable("id") Ticket ticket,
            @ModelAttribute("formData") @Valid MessageForm formData,
            BindingResult bindingResult
    ) {
        if (ticket == null) {
            throw new ResourceNotFoundException();
        }
        if (bindingResult.hasErrors()) {
            return "/admin/ticket/add-message";
        }
        ticketService.addStaffMessage(ticket, formData, userService.getCurrentUser());
        return "redirect:/admin/ticket/view/" + ticket.getId();
    }
}
