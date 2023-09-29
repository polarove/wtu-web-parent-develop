package cn.neorae.wtu.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@RestControllerAdvice
public class ControllerAdvisor extends RuntimeException{

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseVO<String> ParamExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        return ResponseVO.argsNotSatisfied(exceptions.getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage).orElse(ResponseEnum.BAD_PARAM.getMessage()));
    }

    @ExceptionHandler(NotLoginException.class)
    public ResponseVO<String> NotLoginExceptionHandler() {
        return ResponseVO.failed(ResponseEnum.SERVER_RESTART);
    }
}
