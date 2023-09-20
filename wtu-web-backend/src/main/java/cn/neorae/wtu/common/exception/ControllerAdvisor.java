package cn.neorae.wtu.common.exception;

import cn.neorae.common.response.ResponseVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseVO<String> paramExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult exceptions = e.getBindingResult();
        return ResponseVO.argsNotSatisfied(exceptions.getAllErrors().stream().findFirst().map(ObjectError::getDefaultMessage).orElse("参数校验失败"));
    }
}
