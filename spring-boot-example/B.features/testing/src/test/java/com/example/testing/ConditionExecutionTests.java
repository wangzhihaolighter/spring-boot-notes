package com.example.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@DisplayName("条件执行的单元测试示例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConditionExecutionTests {

  /*
  按操作系统设置条件
      @EnabledOnOs 指定多个操作系统，只有当前操作系统是其中的一个，测试方法才会执行
      @DisabledOnOs 指定多个操作系统，只要当前操作系统是其中的一个，测试方法就不会执行

  按JAVA环境设置条件
      @EnabledOnJre 指定多个JRE版本，只有当前JRE是其中的一个，测试方法才会执行；
      @DisabledOnJre 指定多个JRE版本，只要当前JRE是其中的一个，测试方法就不会执行；
      @EnabledForJreRange 指定JRE版本的范围，只有当前JRE在此范围内，测试方法才会执行；
      @DisabledForJreRange 指定JRE版本的范围，只要当前JRE在此范围内，测试方法就不会执行；
  按系统属性设置条件
      @EnabledIfSystemProperty 指定系统属性的key和期望值(模糊匹配)，只有当前系统有此属性并且值也匹配，测试方法才会执行；
      @DisabledIfSystemProperty 指定系统属性的key和期望值(模糊匹配)，只要当前系统有此属性并且值也匹配，测试方法就不会执行；

  按环境变量设置条件
      @EnabledIfEnvironmentVariable 指定环境变量的key和期望值(模糊匹配)，只有当前系统有此环境变量并且值也匹配，测试方法才会执行；
      @DisabledIfEnvironmentVariable 指定环境变量的key和期望值(模糊匹配)，只要当前系统有此环境变量并且值也匹配，测试方法就不会执行；

  自定义条件(从junit5.7版本开始)
      @EnabledIf 条件成立则执行
      @DisabledIf 条件成立则不执行
   */

  @Test
  @Order(1)
  @EnabledOnOs(OS.WINDOWS)
  @DisplayName("操作系统：只有windows才会执行")
  void onlyWindowsTest() {}

  @Test
  @Order(2)
  @EnabledOnOs({OS.WINDOWS, OS.LINUX})
  @DisplayName("操作系统：windows和linux都会执行")
  void windowsOrLinuxTest() {}

  @Test
  @Order(3)
  @DisabledOnOs({OS.WINDOWS})
  @DisplayName("操作系统：只有windows才不会执行")
  void withoutWindowsTest() {}

  @Test
  @Order(4)
  @EnabledOnJre({JRE.JAVA_11, JRE.JAVA_17})
  @DisplayName("Java环境：只有JAVA11和17版本才会执行")
  void onlyJava11And17Test() {}

  @Test
  @Order(5)
  @DisabledOnJre({JRE.JAVA_11})
  @DisplayName("Java环境：JAVA11不执行")
  void withoutJava11Test() {}

  @Test
  @Order(6)
  @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_17)
  @DisplayName("Java环境：从JAVA8到11之间的版本都会执行")
  void fromJava8To11Test() {}

  @Test
  @Order(7)
  @EnabledIfSystemProperty(named = "os.arch", matches = ".*64.*")
  @DisplayName("系统属性：64位操作系统才会执行")
  void only64BitArch() {}

  @Test
  @Order(8)
  @DisabledIfSystemProperty(named = "java.vm.name", matches = ".*HotSpot.*")
  @DisplayName("系统属性：HotSpot不会执行")
  void withOutHotSpotTest() {}

  @Test
  @Order(9)
  @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*")
  @DisplayName("环境变量：JAVA_HOME才会执行")
  void onlyJavaHomeExistsInEnvTest() {}

  @Test
  @Order(10)
  @DisabledIfEnvironmentVariable(named = "GOPATH", matches = ".*")
  @DisplayName("环境变量：有GOPATH就不执行")
  void withoutGoPathTest() {}

  boolean customCondition() {
    return true;
  }

  @Test
  @Order(11)
  @EnabledIf("customCondition")
  @DisplayName("自定义：customCondition返回true就执行")
  void onlyCustomConditionTest() {}

  @Test
  @Order(12)
  @DisabledIf("customCondition")
  @DisplayName("自定义：customCondition返回true就不执行")
  void withoutCustomConditionTest() {}
}
