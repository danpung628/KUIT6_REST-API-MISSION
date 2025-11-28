package com.example.kuit_9week_mission.domain.club.service;

import com.example.kuit_9week_mission.domain.club.dto.response.MyClubsResponse;
import com.example.kuit_9week_mission.domain.club.repository.ClubMemberRepository;
import com.example.kuit_9week_mission.domain.club.repository.ClubRepository;
import com.example.kuit_9week_mission.domain.student.model.Student;
import com.example.kuit_9week_mission.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.kuit_9week_mission.domain.club.dto.request.JoinClubRequest;
import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import com.example.kuit_9week_mission.global.common.exception.CustomException;
import com.example.kuit_9week_mission.global.common.response.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubMemberService {

    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    // TODO 5: 현재 로그인한 학생의 동아리 가입 기능 구현(토큰 필요) - POST

    public void joinClub(Long studentId, JoinClubRequest request) {
        Long clubId = request.clubId();

        // 1. 동아리 존재 여부 확인
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,
                        new IllegalArgumentException("해당 동아리가 존재하지 않습니다.")));

        // 2. 동아리 상태 확인 (ACTIVE만 가입 가능)
        if (club.status() != ClubStatus.ACTIVE) {
            throw new CustomException(ErrorCode.BAD_REQUEST,
                    new IllegalArgumentException("ACTIVE 상태의 동아리만 가입할 수 있습니다."));
        }

        // 3. 이미 가입했는지 확인
        if (clubMemberRepository.existsByStudentIdAndClubId(studentId, clubId)) {
            throw new CustomException(ErrorCode.BAD_REQUEST,
                    new IllegalArgumentException("이미 가입한 동아리입니다."));
        }

        // 4. 가입 처리
        clubMemberRepository.joinClub(studentId, clubId);
    }

    // TODO 6: 현재 로그인한 학생이 속해있는 동아리 목록 조회(토큰 필요) - (학생의 이름 & 동아리 이름 모두 반환 => JOIN 잘 활용하기) - GET
    public MyClubsResponse getMyClubs(Long studentId) {
        // 1. 학생 정보 조회
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND,
                        new IllegalArgumentException("해당 학생이 존재하지 않습니다.")));

        // 2. 가입한 동아리 이름 목록 조회 (JOIN 사용)
        List<String> clubNames = clubMemberRepository.findClubNamesByStudentId(studentId);

        return MyClubsResponse.of(student.name(), clubNames);
    }



}
