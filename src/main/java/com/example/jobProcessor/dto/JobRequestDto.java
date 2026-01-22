package com.example.jobProcessor.dto;

import com.example.jobProcessor.entity.Types;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobRequestDto {

    @NotBlank(message = "Type should not be blank")
    private Types type;

    @NotBlank(message = "PayLoad should not be blank")
    private String payload;

}
