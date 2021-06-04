package com.github.xhrg.bee.admin.config.advice;

import com.github.xhrg.bee.admin.exp.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice("com.github.xhrg.bee.admin.controller")
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception ex) {
        Response resp = new Response();
        if (ex instanceof BizException) {
            //如果是业务异常，设置全局业务异常代码。
            resp.setCode(Response.bizError);
            //如果业务设置了自定义业务异常，则使用自定义业务异常。
            BizException bizException = (BizException) ex;
            if (bizException.getCode() > 0) {
                resp.setCode(bizException.getCode());
            }
            return new ResponseEntity<Object>(resp, HttpStatus.OK);

        }
        //打印异常日志
        log.error("handlerException, exception is ", ex);
        ex.printStackTrace();
        //设置返回信息
        resp.setCode(Response.systemError);
        resp.setMsg(ex.getMessage());
        //返回500
        return new ResponseEntity<Object>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}