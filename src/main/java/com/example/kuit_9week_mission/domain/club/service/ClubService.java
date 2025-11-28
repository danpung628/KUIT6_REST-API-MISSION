package com.example.kuit_9week_mission.domain.club.service;

import com.example.kuit_9week_mission.domain.club.dto.request.UpdateClubRequest;
import com.example.kuit_9week_mission.domain.club.dto.response.ClubListResponse;
import com.example.kuit_9week_mission.domain.club.dto.response.ClubSimpleResponse;
import com.example.kuit_9week_mission.domain.club.model.Club;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import com.example.kuit_9week_mission.domain.club.repository.ClubRepository;
import com.example.kuit_9week_mission.global.common.exception.CustomException;
import com.example.kuit_9week_mission.global.common.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;

    // TODO 1: 동아리 목록 조회 기능 구현(토큰 불필요) - GET (무한 스크롤 - 각 페이지당 5개의 데이터를 보여줄 것 & status 기반 필터링)
    private static final int PAGE_SIZE = 5;

    public ClubListResponse getClubList(Long lastId, ClubStatus status) {
        // 실제로는 PAGE_SIZE + 1개를 조회해서 hasNext를 판단
        List<Club> clubs = clubRepository.findClubsWithCursor(lastId, status, PAGE_SIZE + 1);

        boolean hasNext = clubs.size() > PAGE_SIZE;

        // 실제 반환할 데이터는 PAGE_SIZE만큼만
        List<ClubSimpleResponse> data = clubs.stream()
                .limit(PAGE_SIZE)
                .map(ClubSimpleResponse::from)
                .toList();

        // lastId는 현재 페이지의 마지막 데이터의 clubId
        Long newLastId = data.isEmpty() ? null : data.get(data.size() - 1).clubId();

        return ClubListResponse.of(data, newLastId, hasNext);
    }

    // TODO 2: 동아리 정보 수정 기능 구현(토큰 불필요) - PUT
    public void updateClub(Long clubId, UpdateClubRequest request) {
        int updated = clubRepository.updateClub(clubId, request.name(), request.description());

        if (updated == 0) {
            throw new CustomException(ErrorCode.NOT_FOUND, new IllegalArgumentException("해당 동아리가 존재하지 않습니다."));
        }
    }

    // TODO 3: 동아리 삭제 기능 구현(토큰 불필요) - DELETE
    public void deleteClub(Long clubId) {
        int deleted = clubRepository.deleteClub(clubId);

        if (deleted == 0) {
            throw new CustomException(ErrorCode.NOT_FOUND, new IllegalArgumentException("해당 동아리가 존재하지 않습니다."));
        }
    }
}
