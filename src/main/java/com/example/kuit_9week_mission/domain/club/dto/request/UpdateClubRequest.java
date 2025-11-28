package com.example.kuit_9week_mission.domain.club.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateClubRequest(
        @NotBlank(message = "동아리 이름은 필수입니다.")
        @Size(max = 50, message = "동아리 이름은 50자 이하여야 합니다.")
        String name,

        @Size(max = 200, message = "동아리 설명은 200자 이하여야 합니다.")
        String description
) {
}