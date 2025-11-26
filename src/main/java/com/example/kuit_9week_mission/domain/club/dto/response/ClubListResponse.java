package com.example.kuit_9week_mission.domain.club.dto.response;

import java.util.List;

public record ClubListResponse(
        List<ClubSimpleResponse> data,
        Long lastId,
        boolean hasNext
) {
    public static ClubListResponse of(List<ClubSimpleResponse> data, Long lastId, boolean hasNext) {
        return new ClubListResponse(data, lastId, hasNext);
    }
}