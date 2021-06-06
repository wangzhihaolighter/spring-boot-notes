package com.example.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JasyptTest extends JasyptApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void encrypt() {
        System.out.println(stringEncryptor.encrypt("123456"));
    }

    @Test
    public void decrypt() {
        System.out.println(stringEncryptor.decrypt("EDKs7jXf2ukmiwQl59P4DS+7vVKNjBReJLSfIizuYV+8haE6tZyEp/zLZiRTKWiD"));
    }

}
