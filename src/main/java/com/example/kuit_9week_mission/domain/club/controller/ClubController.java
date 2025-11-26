package com.example.kuit_9week_mission.domain.club.controller;

import com.example.kuit_9week_mission.domain.club.dto.response.ClubListResponse;
import com.example.kuit_9week_mission.domain.club.model.ClubStatus;
import com.example.kuit_9week_mission.domain.club.service.ClubMemberService;
import com.example.kuit_9week_mission.domain.club.service.ClubService;
import com.example.kuit_9week_mission.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
