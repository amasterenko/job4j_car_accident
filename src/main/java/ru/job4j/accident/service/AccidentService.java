package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;
import java.util.stream.Stream;

@Service
public class AccidentService {
    private AccidentMem rep;

    public AccidentService(AccidentMem rep) {
        this.rep = rep;
    }

    public Collection<Accident> findAllAccidents() {
        return rep.findAllAccidents();
    }

    public Accident save(Accident accident, String[] ruleIds) {
        return rep.save(accident, Stream.of(ruleIds).mapToInt(Integer::parseInt).toArray());
    }

    public Accident findAccidentById(int id) {
        return rep.findAccidentById(id);
    }

    public AccidentType findAccidentTypeById(int id) {
        return rep.findTypeById(id);
    }

    public Collection<AccidentType> findAllTypes() {
        return rep.findAllTypes();
    }

    public Collection<Rule> findAllRules() {
        return rep.findAllRules();
    }
}
