package ru.job4j.accident.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Controller
public class AccidentControl {
    @Autowired
    private AccidentService accidentService;

    @GetMapping("/create")
    public String create(Model model) {
        Collection<AccidentType> types = accidentService.findAllTypes();
        Collection<Rule> rules = accidentService.findAllRules();
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidentService.save(accident, ids);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findAccidentById(id));
        Collection<AccidentType> types = accidentService.findAllTypes();
        Collection<Rule> rules = accidentService.findAllRules();
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "accident/update";
    }
}
