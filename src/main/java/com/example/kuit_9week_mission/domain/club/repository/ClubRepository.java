package com.example.kuit_9week_mission.domain.club.repository;

import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

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

    // 동아리 정보 수정
    public int updateClub(Long clubId, String name, String description) {
        String sql = "UPDATE Clubs SET name = ?, description = ? WHERE club_id = ?";
        return jdbc.update(sql, name, description, clubId);
    }

    // 동아리 삭제
    public int deleteClub(Long clubId) {
        String sql = "DELETE FROM Clubs WHERE club_id = ?";
        return jdbc.update(sql, clubId);
    }

    // 동아리 ID로 조회
    public Optional<Club> findById(Long clubId) {
        String sql = "SELECT club_id, name, description, status FROM Clubs WHERE club_id = ?";
        return jdbc.query(sql, MAPPER, clubId).stream().findFirst();
    }

}
