package com.example.kuit_9week_mission.domain.club.repository;

import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubRepository {

    private final JdbcTemplate jdbc;

    private static final RowMapper<Club> MAPPER = (ResultSet rs, int rowNum) -> new Club(
            rs.getLong("club_id"),
            rs.getString("name"),
            rs.getString("description"),
            ClubStatus.valueOf(rs.getString("status"))
    );

    // 무한 스크롤
    public List<Club> findClubsWithCursor(Long lastId, ClubStatus status, int limit) {
        String sql;

        if (lastId == null) {
            // 첫 페이지
            if (status != null) {
                sql = "SELECT club_id, name, description, status FROM Clubs WHERE status = ? ORDER BY club_id DESC LIMIT ?";
                return jdbc.query(sql, MAPPER, status.name(), limit);
            } else {
                sql = "SELECT club_id, name, description, status FROM Clubs ORDER BY club_id DESC LIMIT ?";
                return jdbc.query(sql, MAPPER, limit);
            }
        } else {
            // 다음 페이지 (lastId 이후)
            if (status != null) {
                sql = "SELECT club_id, name, description, status FROM Clubs WHERE club_id < ? AND status = ? ORDER BY club_id DESC LIMIT ?";
                return jdbc.query(sql, MAPPER, lastId, status.name(), limit);
            } else {
                sql = "SELECT club_id, name, description, status FROM Clubs WHERE club_id < ? ORDER BY club_id DESC LIMIT ?";
                return jdbc.query(sql, MAPPER, lastId, limit);
            }
        }
    }

}
