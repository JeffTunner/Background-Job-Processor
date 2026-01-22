package com.example.jobProcessor.dto;

import com.example.jobProcessor.entity.Status;
import com.example.jobProcessor.entity.Types;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobResponseDto {

    private Long id;
    private Types type;
    private Status status;
    private int retryCount;
    private String payload;

}
