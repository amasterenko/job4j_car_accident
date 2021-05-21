package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents;
    private final Map<Integer, AccidentType> accidentTypes;
    private final AtomicInteger index = new AtomicInteger(5);

    public AccidentMem() {
        this.accidentTypes = new HashMap<>() {{
            put(1, AccidentType.of(1, "Two cars"));
            put(2, AccidentType.of(2, "Car and person"));
            put(3, AccidentType.of(3, "Car and bike"));
        }};
        this.accidents = new ConcurrentHashMap<>() {{
            put(1, new Accident(1, "accident1", "accident1 text", "address1", findTypeById(1)));
            put(2, new Accident(2, "accident2", "accident2 text", "address2", findTypeById(1)));
            put(3, new Accident(3, "accident3", "accident3 text", "address3", findTypeById(1)));
            put(4, new Accident(4, "accident4", "accident4 text", "address4", findTypeById(1)));
            put(5, new Accident(5, "accident5", "accident5 text", "address5", findTypeById(1)));
        }};
    }

    public Collection<Accident> findAllAccidents() {
        return accidents.values();
    }

    public Collection<AccidentType> findAllTypes() {
        return accidentTypes.values();
    }

    public Accident save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(index.getAndIncrement());
        }
        accident.setType(findTypeById(accident.getType().getId()));
        this.accidents.put(accident.getId(), accident);
        return accident;
    }

    public Accident findAccidentById(int id) {
        return accidents.get(id);
    }

    public AccidentType findTypeById(int id) {
        return accidentTypes.get(id);
    }
}
