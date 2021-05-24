package ru.job4j.accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentJdbcTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.stream.Stream;

@Controller
public class AccidentControl {
    private final AccidentJdbcTemplate accidents;

    public AccidentControl(AccidentJdbcTemplate accidents) {
        this.accidents = accidents;
    }

    @GetMapping("/create")
    public String create(Model model) {
        Collection<AccidentType> types = accidents.findAllTypes();
        Collection<Rule> rules = accidents.findAllRules();
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "accident/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        int[] ruleIds = Stream.of(req.getParameterValues("rIds"))
                .mapToInt(Integer::parseInt)
                .toArray();
        if (accident.getId() == 0) {
            accidents.save(accident, ruleIds);
        } else {
            accidents.update(accident, ruleIds);
        }
        return "redirect:/";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidents.findAccidentById(id));
        Collection<AccidentType> types = accidents.findAllTypes();
        Collection<Rule> rules = accidents.findAllRules();
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "accident/update";
    }
}
