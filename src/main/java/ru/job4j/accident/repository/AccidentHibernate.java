package ru.job4j.accident.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.util.List;

@Repository
public class AccidentHibernate {
    private final SessionFactory sf;

    public AccidentHibernate(SessionFactory sf) {
        this.sf = sf;
    }

    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.getTransaction().begin();
            session.save(accident);
            session.getTransaction().commit();
        }
        return accident;
    }

    public Accident update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.getTransaction().begin();
            session.update(accident);
            session.getTransaction().commit();
        }
        return accident;
    }

    public List<Accident> findAllAccidents() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery(
                            "select distinct a from Accident a join fetch a.type"
                                    + " left join fetch a.rules order by a.id",
                            Accident.class)
                    .list();
        }
    }

    public Accident findAccidentById(int id) {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery(
                            "select distinct a from Accident a join fetch a.type"
                            + " left join fetch a.rules where a.id =:paramId",
                            Accident.class)
                    .setParameter("paramId", id)
                    .getSingleResult();
        }
    }

    public List<Rule> findAllRules() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from Rule", Rule.class)
                    .list();
        }
    }

    public List<AccidentType> findAllTypes() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("from AccidentType", AccidentType.class)
                    .list();
        }
    }
}
