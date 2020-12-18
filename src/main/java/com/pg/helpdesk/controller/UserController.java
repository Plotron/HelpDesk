package com.pg.helpdesk.controller;

import com.pg.helpdesk.entity.User;
import com.pg.helpdesk.form.UserForm;
import com.pg.helpdesk.service.TicketService;
import com.pg.helpdesk.service.UserService;
import com.pg.helpdesk.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserValidator userValidator;
    @Autowired
    UserService userService;
    @Autowired
    TicketService ticketService;

    @Secured("ROLE_USER")
    @GetMapping({"/account", "/"})
    public String account(
            Model model
    ) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "/user/account";
    }

    @Secured("ROLE_USER")
    @GetMapping("/account/edit")
    public String edit(
            Model model
    ) {
        User currentUser = userService.getCurrentUser();
        UserForm userForm = new UserForm();
        userForm.fromEntity(currentUser);
        model.addAttribute("formData", userForm);
        return "/user/edit";
    }

    @Secured("ROLE_USER")
    @PostMapping("/account/edit")
    public String edit(
            @ModelAttribute("formData") @Valid UserForm formData,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/user/edit";
        }
        User entity = userService.getCurrentUser();
        userService.saveModifiedUser(formData, entity);
        return "redirect:/user/account";
    }
}
