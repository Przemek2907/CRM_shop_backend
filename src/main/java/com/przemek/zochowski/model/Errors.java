package com.przemek.zochowski.model;


import com.przemek.zochowski.exceptions.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Errors {

    @Id
    @GeneratedValue
    private Long id;
    private ErrorCode errorCode;
    private LocalDateTime dateTime;
    private String message;
}
