package com.example.kuit_9week_mission.domain.club.dto.response;

import com.example.kuit_9week_mission.domain.club.model.Club;

public record ClubSimpleResponse(
        Long clubId,
        String name,
        String description
) {
    public static ClubSimpleResponse from(Club club) {
        return new ClubSimpleResponse(
                club.clubId(),
                club.name(),
                club.description()
        );
    }
}