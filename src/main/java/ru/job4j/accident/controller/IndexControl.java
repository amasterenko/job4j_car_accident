package ru.job4j.accident.controller;

import org.hibernate.Hibernate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accident.config.DataConfig;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.repository.AccidentHibernate;
import ru.job4j.accident.repository.AccidentJdbcTemplate;
import ru.job4j.accident.repository.AccidentRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexControl {
    private final AccidentRepository accidents;

    public IndexControl(AccidentRepository accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/")
    @Transactional
    public String index(Model model) {
        List<Accident> res = new ArrayList<>();
        accidents.findAll().forEach(a -> {
            Hibernate.initialize(a.getRules());
            Hibernate.initialize(a.getType());
            res.add(a);
        });
        model.addAttribute("accidents", res);
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        return "index";
    }
}