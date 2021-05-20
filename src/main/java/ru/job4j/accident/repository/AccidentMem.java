package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents;

    public AccidentMem() {
        this.accidents = new HashMap<>() {{
            put(0, new Accident(0, "accident1", "accident1 text", "address1"));
            put(1, new Accident(1, "accident2", "accident2 text", "address2"));
            put(2, new Accident(2, "accident3", "accident3 text", "address3"));
            put(3, new Accident(3, "accident4", "accident4 text", "address4"));
            put(4, new Accident(4, "accident5", "accident5 text", "address5"));
        }};
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
