package com.oklimenko.dockerspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthStatus {
    private String status;
}
