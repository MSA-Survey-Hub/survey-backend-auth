package com.cloud.auth.dto;

import lombok.Data;

@Data
public class FileDTO {
    private String originalName;
    private byte[] fileBytes;
}
