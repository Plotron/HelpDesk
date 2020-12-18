package com.pg.helpdesk.controller;

import com.pg.helpdesk.form.UserForm;
import com.pg.helpdesk.service.UserService;
import com.pg.helpdesk.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    UserValidator userValidator;

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "/login";
        }
        return "redirect:/";
    }

    @InitBinder("userForm")
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @GetMapping("/signup")
    public String signup(
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            model.addAttribute("formData", new UserForm());
            return "/signup";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/signup")
    public String signup(
            @ModelAttribute("formData") @Valid UserForm formData,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/signup";
        }
            userService.saveNewUser(formData);
        return "/signup-success";
    }

}



