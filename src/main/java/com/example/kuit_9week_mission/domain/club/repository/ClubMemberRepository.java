package com.example.kuit_9week_mission.domain.club.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClubMemberRepository {

    private final JdbcTemplate jdbc;


    // 이미 가입했는지 확인
    public boolean existsByStudentIdAndClubId(Long studentId, Long clubId) {
        String sql = "SELECT COUNT(*) FROM Club_Members WHERE student_id = ? AND club_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, studentId, clubId);
        return count != null && count > 0;
    }

    // 동아리 가입
    public void joinClub(Long studentId, Long clubId) {
        String sql = "INSERT INTO Club_Members (join_date, student_id, club_id) VALUES (CURDATE(), ?, ?)";
        jdbc.update(sql, studentId, clubId);
    }

    // 학생이 가입한 동아리 이름 목록 조회 (JOIN 사용)
    public List<String> findClubNamesByStudentId(Long studentId) {
        String sql = """
            SELECT c.name 
            FROM Club_Members cm
            JOIN Clubs c ON cm.club_id = c.club_id
            WHERE cm.student_id = ?
            ORDER BY cm.join_date
            """;
        return jdbc.queryForList(sql, String.class, studentId);
    }

}
