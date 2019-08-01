package com.example.testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * junit打包测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        Junit4Test.class,
        TestingApplicationTests.class,
        MockMvcTests.class
})
public class AllTests {
}
