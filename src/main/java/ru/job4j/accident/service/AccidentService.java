package ru.job4j.accident.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.repository.AccidentMem;

import java.util.Collection;

@Service
public class AccidentService {
    private AccidentMem rep;

    @Autowired
    public AccidentService(AccidentMem rep) {
        this.rep = rep;
    }

    public Collection<Accident> findAllAccidents() {
        return rep.findAllAccidents();
    }

    public Accident save(Accident accident) {
        return rep.save(accident);
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
}
