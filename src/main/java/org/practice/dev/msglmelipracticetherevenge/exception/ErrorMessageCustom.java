package org.practice.dev.msglmelipracticetherevenge.exception;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ErrorMessageCustom {
    private Timestamp timestamp;
    private Integer code;
    private String detail;
}
