package com.technophiles.Diaryapppro.controllers.response;

import lombok.*;
import org.hibernate.internal.build.AllowPrintStacktrace;

@Getter
@Setter@Builder @AllArgsConstructor
public class ApiResponse {

    private  Object payload;
    private boolean isSuccessful;
    private int statusCode;
    private String message;
}
