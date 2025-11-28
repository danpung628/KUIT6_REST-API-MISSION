package com.example.kuit_9week_mission.domain.student.repository;

import com.example.kuit_9week_mission.domain.student.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepository {

    private final JdbcTemplate jdbc;

    private static final RowMapper<Student> MAPPER = (ResultSet rs, int rowNum) -> new Student(
            rs.getLong("student_id"),
            rs.getInt("student_number"),
            rs.getString("name")
    );

    // 로그인용
    public Optional<Student> findByStudentNumber(Integer studentNumber) {
        String sql = "SELECT student_id, student_number, name FROM Students WHERE student_number = ?";
        return jdbc.query(sql, MAPPER, studentNumber).stream().findFirst();
    }

    // 내 정보 조회
    public Optional<Student> findById(Long studentId) {
        String sql = "SELECT student_id, student_number, name FROM Students WHERE student_id = ?";
        return jdbc.query(sql, MAPPER, studentId).stream().findFirst();
    }

    // 학생 이름 수정
    public int updateStudentName(Long studentId, String name) {
        String sql = "UPDATE Students SET name = ? WHERE student_id = ?";
        return jdbc.update(sql, name, studentId);
    }


}
