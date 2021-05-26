package ru.job4j.accident.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.service.AccidentService;

@Controller
public class IndexControl {
    private final AccidentService accidentService;

    public IndexControl(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/")
    @Transactional
    public String index(Model model) {
        model.addAttribute("accidents", accidentService.findAllAccidents());
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        return "index";
    }
}