package com.leetjourney.device_service.dto;

import com.leetjourney.device_service.model.DeviceType;
import lombok.Builder;


@Builder
public record DeviceDto (
     Long id,
     String name,
     DeviceType type,
     String location,
     Long userId) {}