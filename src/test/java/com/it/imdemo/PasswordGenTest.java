package com.it.imdemo;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenTest {

    @Test
    public void generatePassword() {
        // 1. 创建 BCrypt 编码器
        // 这里的 10 是强度参数 (strength)，范围 4-31，默认 10
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);

        // 2. 定义你要加密的明文密码
        String rawPassword = "123456";

        // 3. 加密 (这里会自动生成随机盐值并混入)
        String encodedPassword = encoder.encode(rawPassword);

        // 4. 打印出来 (复制这个字符串去数据库 insert)
        System.out.println("明文密码: " + rawPassword);
        System.out.println("加密后 (存入数据库): " + encodedPassword);

        // --- 验证演示 ---

        // 5. 再次加密同样的密码，你会发现结果完全不一样！(因为每次的盐值都是随机的)
        String encodedPassword2 = encoder.encode(rawPassword);
        System.out.println("第二次加密 (结果不同): " + encodedPassword2);

        // 6. 验证密码 (matches 方法会自动提取哈希串里的盐值进行比对)
        boolean isMatch = encoder.matches(rawPassword, encodedPassword);
        System.out.println("密码比对结果: " + isMatch); // true
    }
}
