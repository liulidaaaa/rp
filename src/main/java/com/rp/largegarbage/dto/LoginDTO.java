package com.rp.largegarbage.dto;

import lombok.Data;
/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/23 17:45
 */
@Data
public class LoginDTO implements RequestDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{\"LoginDTO\":{"
                + "\"username\":\"" + username + "\""
                + ", \"password\":\"" + password + "\""
                + "}}";
    }
}
