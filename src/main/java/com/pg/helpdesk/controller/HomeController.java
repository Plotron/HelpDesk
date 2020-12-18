package com.pg.helpdesk.controller;

import com.pg.helpdesk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ArticleService articleService;

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model) {
        model.addAttribute("entities", articleService.findTop5ByOrderByCreationDateDesc());
        return "index";
    }

    @GetMapping({"/admin"})
    public String home() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

}
