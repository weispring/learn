package com.lxc.learn.doc.jdbc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

public class UserLoginService {
    /**
     * 在验证码为4个字符则表示验证通过，用户loginId只有zhangsan才能登录。
     * @param user 登录用户，User类具备loginId属性。
     * @param verifyCode 验证码
     * @return 登录成功返回：true，登录失败返回：false。
     */
    boolean login(User user, String verifyCode){
        if (user == null || !"zhangsan".equals(user.getLoginId())){
            return false;
        }
        if (StringUtils.isEmpty(verifyCode) || verifyCode.length() != 4){
            return false;
        }
        return true;
    }

    @Setter
    @Getter
    public static class User {
        private String loginId;
    }
}
