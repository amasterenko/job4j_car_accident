package ru.job4j.accident.repository;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.accident.model.Accident;
import ru.job4j.accident.model.AccidentType;
import ru.job4j.accident.model.Rule;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Transactional
    public void save(Accident accident, int[] ruleIds) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into accident (name, text, address, type_id) "
                                    + "values (?, ?, ?, ?)",
                            new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        int newAccidentId = (Integer) keyHolder.getKey();
        jdbc.batchUpdate("insert into accident_rule (accident_id, rule_id) values (?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                       ps.setInt(1, newAccidentId);
                       ps.setInt(2, ruleIds[i]);
                    }

                    @Override
                    public int getBatchSize() {
                        return ruleIds.length;
                    }
                });
    }

    @Transactional
    public void update(Accident accident, int[] ruleIds) {
        jdbc.update("update accident SET name =?, text=?, address=?, type_id=? where id=?;"
                        + "delete from accident_rule where accident_id = ?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId(),
                accident.getId()
        );
        jdbc.batchUpdate("insert into accident_rule (accident_id, rule_id) values (?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, accident.getId());
                        ps.setInt(2, ruleIds[i]);
                    }

                    @Override
                    public int getBatchSize() {
                        return ruleIds.length;
                    }
                });
    }

    public Collection<Accident> findAllAccidents() {
        return jdbc.query("select a.id as aId, a.name as aName, a.text as aTxt, "
                        + "a.address as aAddr, "
                        + "t.id as tId, t.name as tName, r.id as rId, r.name as rName "
                        + "from accident a join type t on a.type_id = t.id "
                        + "left join accident_rule ar on a.id = ar.accident_id "
                        + "left join rule r on ar.rule_id = r.id",
                resultSet -> {
            Map<Integer, Accident> accidents = new HashMap<>();
                    while (resultSet.next()) {
                        int id = resultSet.getInt("aId");
                        Rule rule = Rule.of(
                                resultSet.getInt("rId"),
                                resultSet.getString("rName"));
                        if (accidents.containsKey(id)) {
                            accidents.get(id).addRule(rule);
                            continue;
                        }
                        String name = resultSet.getString("aName");
                        String text = resultSet.getString("aTxt");
                        String address = resultSet.getString("aAddr");
                        int typeId = resultSet.getInt("tId");
                        String typeName = resultSet.getString("tName");
                        AccidentType aType = AccidentType.of(typeId, typeName);
                        Accident accident = new Accident(id, name, text, address, aType);
                        accident.addRule(rule);
                        accidents.put(id, accident);
                    }
                    return accidents.values();
                });
    }

    public Accident findAccidentById(int id) {
        return jdbc.query("select a.name as aName, a.text as aTxt, a.address as aAddr,"
                        + " t.id as tId, t.name as tName, r.id as rId, r.name as rName "
                        + "from accident a join type t on a.type_id = t.id "
                        + "left join accident_rule ar on a.id = ar.accident_id "
                        + "left join rule r on ar.rule_id = r.id "
                        + "where a.id = ?",
                preparedStatement -> preparedStatement.setInt(1, id),
                resultSet -> {
                    Accident accident = null;
                    while (resultSet.next()) {
                        Rule rule = Rule.of(
                                resultSet.getInt("rId"),
                                resultSet.getString("rName"));
                        if (accident != null) {
                            accident.addRule(rule);
                            continue;
                        }
                        String name = resultSet.getString("aName");
                        String text = resultSet.getString("aTxt");
                        String address = resultSet.getString("aAddr");
                        int typeId = resultSet.getInt("tId");
                        String typeName = resultSet.getString("tName");
                        AccidentType aType = AccidentType.of(typeId, typeName);
                        accident = new Accident(id, name, text, address, aType);
                    }
                    return accident;
                });
    }

    public AccidentType findTypeById(int id) {
        return jdbc.queryForObject("select id, name from type where id = ?",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                }, id);
    }

    public Collection<Rule> findRulesByIds(Integer[] ruleIds) {
        String inSql = String.join(",", Collections.nCopies(ruleIds.length, "?"));
        return jdbc.query(String.format("select id, name from rule where id in (%s)", inSql),
                ruleIds,
                (rs, row) -> Rule.of(rs.getInt("id"), rs.getString("name")));
    }

    public Collection<Rule> findAllRules() {
        return jdbc.query("select id, name from rule",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }

    public Collection<AccidentType> findAllTypes() {
        return jdbc.query("select id, name from type",
                (rs, row) -> {
                    AccidentType type = new AccidentType();
                    type.setId(rs.getInt("id"));
                    type.setName(rs.getString("name"));
                    return type;
                });
    }
}