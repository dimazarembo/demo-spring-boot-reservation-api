package com.example.reservation_system.web;

import java.time.LocalDateTime;

public record ErrorResponseDTO (String message, String detailedMessage, LocalDateTime errorTime){
}
