package cn.neorae.wtu.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.common.response.ResponseVO;
import com.sun.mail.util.MailConnectException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;


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

    @ExceptionHandler(TeamException.class)
    public ResponseVO<String> TeamExceptionHandler(TeamException e) {
        return ResponseVO.failed(e.getResponseEnum());
    }

    @ExceptionHandler(MailConnectException.class)
    public ResponseVO<String> MailConnectExceptionHandler() {
        return ResponseVO.failed(ResponseEnum.MAIL_CONNECT_FAILED);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseVO<String> NoSuchElementExceptionHandler() {
        return ResponseVO.failed(ResponseEnum.BAD_NETTY_PARAM);
    }
}
