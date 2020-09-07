package com.example.testing.junit4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * junit打包测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Junit4Tests.class,
        TestingApplicationTests.class,
        MockMvcTests.class,
        MockMvcExampleTests.class,
        MockMvcExampleTests2.class
})
public class SuiteTests {
}
