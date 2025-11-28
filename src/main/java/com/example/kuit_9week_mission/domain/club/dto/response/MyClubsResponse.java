package com.example.kuit_9week_mission.domain.club.dto.response;

import java.util.List;

public record MyClubsResponse(
        String studentName,
        List<String> clubNames
) {
    public static MyClubsResponse of(String studentName, List<String> clubNames) {
        return new MyClubsResponse(studentName, clubNames);
    }
}