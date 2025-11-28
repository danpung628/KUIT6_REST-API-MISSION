package com.example.kuit_9week_mission.domain.club.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JoinClubRequest(
        @NotNull(message = "동아리 ID는 필수입니다.")
        @Positive(message = "동아리 ID는 양수여야 합니다.")
        Long clubId
) {
}