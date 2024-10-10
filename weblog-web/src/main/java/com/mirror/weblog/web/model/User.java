package com.mirror.weblog.web.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * @author mirror
 */
@Data
public class User {

    // 用户名
    //注解确保用户名不为空
    @NotBlank(message = "用户名不能为空")
    private String username;
    // 性别
    @NotNull(message = "性别不能为空")
    private Integer sex;

    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄必须大于或者等于18")
    @Max(value = 100, message = "年龄必须小于或等于100")
    private Integer age;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
}
