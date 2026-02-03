package com.example.TickNet.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationCreateRequest(
        @NotNull Long visiteurId,
        @NotNull Long sessionId,
        @NotNull @Min(1) Integer nbPlaces
) {}
