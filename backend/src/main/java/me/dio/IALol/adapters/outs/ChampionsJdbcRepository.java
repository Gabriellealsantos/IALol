package me.dio.IALol.adapters.outs;

import me.dio.IALol.domain.models.Champion;
import me.dio.IALol.domain.ports.ChampionsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChampionsJdbcRepository implements ChampionsRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Champion> championsRowMapper;

    public ChampionsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.championsRowMapper = (rs, rowNum) -> new Champion(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("lore"),
                rs.getString("image_url")
        );
    }

    @Override
    public List<Champion> findAll() {
        return jdbcTemplate.query("SELECT * FROM CHAMPIONS", championsRowMapper);
    }

    @Override
    public Optional<Champion> findById(Long id) {
        String sql = "SELECT * FROM CHAMPIONS WHERE ID = ?";
        List<Champion> champions = jdbcTemplate.query(sql, championsRowMapper, id);
        return champions.stream().findFirst();
    }
}