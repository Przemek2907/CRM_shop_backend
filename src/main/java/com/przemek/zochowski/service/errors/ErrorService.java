package com.przemek.zochowski.service.errors;

import com.przemek.zochowski.exceptions.ErrorCode;
import com.przemek.zochowski.exceptions.MyException;
import com.przemek.zochowski.model.Errors;
import com.przemek.zochowski.repository.ErrorsRepository;
import com.przemek.zochowski.repository.ErrorsRepositoryImpl;

public class ErrorService {
    private ErrorsRepository errorsRepository = new ErrorsRepositoryImpl();

    public void addError(MyException e) {
        errorsRepository.addOrUpdate(Errors.builder()
                .errorCode(e.getErrorCode())
                .message(e.getErrorMessage())
                .dateTime(e.getErrorDateTime())
                .build());
    }
}
