package com.example.kuit_9week_mission.domain.student.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateStudentNameRequest(
        @NotBlank(message = "학생 이름은 필수입니다.")
        @Size(max = 30, message = "학생 이름은 30자 이하여야 합니다.")
        String name
) {
}