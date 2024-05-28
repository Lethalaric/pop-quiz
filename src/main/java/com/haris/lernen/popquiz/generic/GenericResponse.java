package com.haris.lernen.popquiz.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class GenericResponse {
    private GenericStatus genericStatus;
    private Object data;
}
