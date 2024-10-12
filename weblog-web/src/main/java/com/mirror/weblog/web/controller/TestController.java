package com.mirror.weblog.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mirror.weblog.common.aspect.ApiOperationLog;
import com.mirror.weblog.common.exception.BizException;
import com.mirror.weblog.common.exception.ResponseCodeEnum;
import com.mirror.weblog.common.utils.JsonUtil;
import com.mirror.weblog.common.utils.Response;
import com.mirror.weblog.web.model.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 * @author mirror
 */
@RestController
@Slf4j
public class TestController {

    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
//    //测试简单参数
//    public User test(@RequestBody User user) {
//        // 返参
//        return user;
//    }
    //测试参数验证
//    //@Validated是告诉spring 需要对User对象执行校验
//    //BindingResult 验证的结果对象，其中包含所有验证错误信息
//    public ResponseEntity<String> test(@RequestBody @Validated User user, BindingResult bindingResult) {
//        // 是否存在校验错误
//        if (bindingResult.hasErrors()) {
//            // 获取校验不通过字段的提示信息
//            String errorMsg = bindingResult.getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .collect(Collectors.joining(", "));
//
//            return ResponseEntity.badRequest().body(errorMsg);
//        }
//
//        // 返参
//        return ResponseEntity.ok("参数没有任何问题");
//    }


    //测试自定义响应类型
//    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
//        // 是否存在校验错误
//        if (bindingResult.hasErrors()) {
//            // 获取校验不通过字段的提示信息
//            String errorMsg = bindingResult.getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .collect(Collectors.joining(", "));
//
//            return Response.fail(errorMsg);
//        }
//
//        // 返参
//        return Response.success();
//    }

    //测试全局异常之自定义异常
//    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
//        // 手动抛异常，入参是前面定义好的异常码枚举，返参统一交给全局异常处理器搞定
//        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
//    }

    //处理运行异常
//    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
//        // 主动定义一个运行时异常，分母不能为零
//        int i = 1 / 0;
//        return Response.success();
//    }

    //参数校验
//    public Response test(@RequestBody @Validated User user) {
//        return Response.success();
//    }

    //测试新加的user 信息
    public Response test(@RequestBody @Validated User user) {
        // 打印入参
        log.info(JsonUtil.toJsonString(user));

        // 设置三种日期字段值
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());

        return Response.success(user);
    }

    @ApiOperationLog(description = "测试接口")
    //没有全局异常管理的时候
    @GetMapping("/product/{id}")
    public ResponseEntity<String> getProduct(@PathVariable String id) {
        try {
            int productId = Integer.parseInt(id);

            // 判断产品 ID 是否合规
            if (productId <= 0) {
                throw new IllegalArgumentException("Product ID must be greater than 0");
            }

            // 假设这里是一个查找产品的逻辑
            String productInfo = "Product Info for ID: " + id;

            return ResponseEntity.ok(productInfo);
        } catch (NumberFormatException e) {
            // 捕获 ID 不是数字的情况
            return ResponseEntity.badRequest().body("Invalid Product ID format");
        } catch (IllegalArgumentException e) {
            // 捕获产品 ID 小于等于0的情况
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // 捕获所有其他类型的异常
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }



}