package com.pg.helpdesk.controller;

import com.pg.helpdesk.entity.Article;
import com.pg.helpdesk.form.ArticleForm;
import com.pg.helpdesk.service.ArticleService;
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
@RequestMapping("/article/")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public String list(Model model, Pageable pageable) {
        model.addAttribute("entities", articleService.findAllByOrderByCreationDateDesc(pageable));
        return "/article/list";
    }

    @GetMapping("/view/{id}")
    public String view(
            @PathVariable("id") Article article,
            Model model
    ) {
        model.addAttribute("object", article);
        return "/article/view";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam("query") String query,
            Pageable pageable,
            Model model
    ) {
        model.addAttribute("entities", articleService.searchByTitle(query, pageable));
        return "/article/search";
    }

    @Secured("ROLE_HELPDESK")
    @GetMapping("/create")
    public String add(
            Model model
    ) {
        model.addAttribute("formData", new ArticleForm());
        return "/article/create";
    }

    @Secured("ROLE_HELPDESK")
    @PostMapping("/create")
    public String add(
            @ModelAttribute("formData") @Valid ArticleForm formData,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/article/create";
        }
        Article article = articleService.saveNewArticle(formData, userService.getCurrentUser());
        return "redirect:/article/view/" + article.getId();
    }

    @Secured("ROLE_HELPDESK")
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Article entity,
            Model model
    ) {
        model.addAttribute("formData", new ArticleForm());
        model.addAttribute("object", entity);
        return "/article/edit";
    }

    @Secured("ROLE_HELPDESK")
    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable("id") Article entity,
            @ModelAttribute("formData") @Valid ArticleForm formData,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/article/edit/" + entity.getId();
        }
        Article article = articleService.saveModifiedArticle(formData, entity);
        return "redirect:/article/view/" + article.getId();
    }

    @Secured("ROLE_HELPDESK")
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(
            HttpServletRequest request,
            @PathVariable("id") Article entity,
            Model model
    ) {
        boolean isPost = request.getMethod().equals("POST");
        if (isPost) {
            articleService.delete(entity);
            return "redirect:/article/list/";
        }
        model.addAttribute("object", entity);
        return "/article/delete";
    }
}
