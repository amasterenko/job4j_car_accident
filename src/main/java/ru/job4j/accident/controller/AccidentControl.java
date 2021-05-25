package ru.job4j.accident.controller;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class AccidentControl {
    private final AccidentRepository accidents;
    private final TypeRepository types;
    private final RuleRepository rules;

    public AccidentControl(
            AccidentRepository accidents,
            TypeRepository types,
            RuleRepository rules
    ) {
        this.accidents = accidents;
        this.types = types;
        this.rules = rules;
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<AccidentType> typesList = new ArrayList<>();
        types.findAll().forEach(typesList::add);
        List<Rule> rulesList = new ArrayList<>();
        rules.findAll().forEach(rulesList::add);
        model.addAttribute("types", typesList);
        model.addAttribute("rules", rulesList);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        Set<Rule> rules = Stream.of(req.getParameterValues("rIds"))
                .map(id -> Rule.of(Integer.parseInt(id), id))
                .collect(Collectors.toSet());
        accident.setRules(rules);
        accidents.save(accident);
        return "redirect:/";
    }

    @GetMapping("/update")
    @Transactional
    public String update(@RequestParam("id") int id, Model model) {
        Accident accident = accidents.findById(id).orElse(
                new Accident(0, "", "", "", AccidentType.of(1, "")));
        Hibernate.initialize(accident.getRules());
        Hibernate.initialize(accident.getType());
        model.addAttribute("accident", accident);
        List<AccidentType> typesList = new ArrayList<>();
        types.findAll().forEach(typesList::add);
        List<Rule> rulesList = new ArrayList<>();
        rules.findAll().forEach(rulesList::add);
        model.addAttribute("types", typesList);
        model.addAttribute("rules", rulesList);
        return "accident/update";
    }
}
