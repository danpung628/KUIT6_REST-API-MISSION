package com.example.kuit_9week_mission.domain.club.controller;

import com.example.kuit_9week_mission.domain.club.dto.request.UpdateClubRequest;
import com.example.kuit_9week_mission.domain.club.dto.response.ClubListResponse;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import com.example.kuit_9week_mission.domain.club.service.ClubMemberService;
import com.example.kuit_9week_mission.domain.club.service.ClubService;
import com.example.kuit_9week_mission.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;
    private final ClubMemberService clubMemberService;

    // 동아리 목록 조회 (무한 스크롤 + status 필터링)
    @GetMapping
    public ApiResponse<ClubListResponse> getClubs(
            @RequestParam(required = false) Long lastId,
            @RequestParam(required = false) ClubStatus status
    ) {
        return ApiResponse.ok(clubService.getClubList(lastId, status));
    }

    // 동아리 정보 수정
    @PutMapping("/{clubId}")
    public ApiResponse<Void> updateClub(
            @PathVariable Long clubId,
            @Valid @RequestBody UpdateClubRequest request
    ) {
        clubService.updateClub(clubId, request);
        return ApiResponse.ok(null);
    }

    // 동아리 삭제
    @DeleteMapping("/{clubId}")
    public ApiResponse<Void> deleteClub(@PathVariable Long clubId) {
        clubService.deleteClub(clubId);
        return ApiResponse.ok(null);
    }
}
