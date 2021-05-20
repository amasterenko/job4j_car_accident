package ru.job4j.accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents;
    private final AtomicInteger index = new AtomicInteger(5);

    public AccidentMem() {
        this.accidents = new ConcurrentHashMap<>() {{
            put(1, new Accident(1, "accident1", "accident1 text", "address1"));
            put(2, new Accident(2, "accident2", "accident2 text", "address2"));
            put(3, new Accident(3, "accident3", "accident3 text", "address3"));
            put(4, new Accident(4, "accident4", "accident4 text", "address4"));
            put(5, new Accident(5, "accident5", "accident5 text", "address5"));
        }};
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Accident save(Accident accident) {
        if (accident.getId() == 0) {
            accident.setId(index.getAndIncrement());
        }
        this.accidents.put(accident.getId(), accident);
        return accident;
    }

    public Accident findById(int id) {
        return accidents.get(id);
    }
}
