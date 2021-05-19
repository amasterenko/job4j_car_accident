package ru.job4j.accident.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class IndexControl {
    @GetMapping("/")
    public String index(Model model) {
        List<String> items = List.of(
                "description1",
                "description2",
                "description3",
                "description4",
                "description5"
        );
        model.addAttribute("items", items);
        return "index";
    }
}