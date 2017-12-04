package com.john.density_info.handler;

import com.john.density_info.utils.ResultUtil;
import com.john.density_info.exceptions.MainException;
import com.john.density_info.mode.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获处理累
 */
@ControllerAdvice
class ExceptionHandle {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result ExceptionHandle(Exception e){

        // 模板主类处理返回
        if (e instanceof MainException) {
            logger.error("Exception-模板主类异常:{}", e.toString());
            MainException mainExceptionException = (MainException) e;
            return ResultUtil.fail(mainExceptionException.getCode(), mainExceptionException.getMessage());
        }

        logger.error("Exception-内部异常:{}", e.toString());
        return ResultUtil.fail(-1, e.getMessage());
    }

}
