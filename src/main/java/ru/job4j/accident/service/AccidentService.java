package ru.job4j.accident.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentRepository;
import ru.job4j.accident.repository.RuleRepository;
import ru.job4j.accident.repository.TypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccidentService {
    private final AccidentRepository accidents;
    private final TypeRepository types;
    private final RuleRepository rules;

    public AccidentService(
            AccidentRepository accidents,
            TypeRepository types,
            RuleRepository rules
    ) {
        this.accidents = accidents;
        this.types = types;
        this.rules = rules;
    }

    public List<Accident> findAllAccidents() {
        List<Accident> res = new ArrayList<>();
        accidents.findAll().forEach(a -> {
            Hibernate.initialize(a.getRules());
            Hibernate.initialize(a.getType());
            res.add(a);
        });
        return res;
    }

    public Accident findAccidentById(int id) {
        Accident accident = accidents.findById(id).orElse(
                new Accident(0, "", "", "", AccidentType.of(1, "")));
        Hibernate.initialize(accident.getRules());
        Hibernate.initialize(accident.getType());
        return accident;
    }

    public List<AccidentType> findAllTypes() {
        List<AccidentType> res = new ArrayList<>();
        types.findAll().forEach(res::add);
        return res;
    }

    public List<Rule> findAllRules() {
        List<Rule> res = new ArrayList<>();
        rules.findAll().forEach(res::add);
        return res;
    }

    public Accident saveAccident(Accident accident) {
        accidents.save(accident);
        return accident;
    }
}
