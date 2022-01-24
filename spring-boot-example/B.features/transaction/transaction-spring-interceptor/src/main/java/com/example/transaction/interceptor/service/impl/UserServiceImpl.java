package com.example.transaction.interceptor.service.impl;

import com.example.transaction.interceptor.config.exception.DeleteRuntimeException;
import com.example.transaction.interceptor.config.exception.InsertException;
import com.example.transaction.interceptor.config.exception.OtherException;
import com.example.transaction.interceptor.config.exception.OtherRuntimeException;
import com.example.transaction.interceptor.config.exception.UpdateRuntimeException;
import com.example.transaction.interceptor.dao.UserRepository;
import com.example.transaction.interceptor.entity.User;
import com.example.transaction.interceptor.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> query() {
    return userRepository.findAll();
  }

  @Override
  public void queryReadOnlyTest() {
    // 测试只读设置是否生效：非只读，不执行
    // 之前是报错，不知道什么版本改的，这里就不细究了。。。
    List<User> all = userRepository.findAll();
    userRepository.deleteById(all.get(all.size() - 1).getId());
  }

  @Override
  public void saveTest() throws InsertException, OtherException {
    User user = new User();
    String randomStr = RandomString.make();
    user.setUsername(randomStr);
    user.setPassword(randomStr);
    userRepository.save(user);
    log.info(String.valueOf(user));

    // 测试：指定非运行时异常，其他非运行时异常
    if (LocalDateTime.now().getSecond() % 2 == 0) {
      throw new InsertException("指定非运行时异常，回滚：" + randomStr);
    } else {
      throw new OtherException("其他非运行时异常，不回滚：" + randomStr);
    }
  }

  @Override
  public void updateTest() {
    List<User> all = userRepository.findAll();
    User user = all.get(all.size() - 1);
    String randomStr = RandomString.make();
    user.setUsername(randomStr);
    user.setPassword(randomStr);
    user.setCreateTime(LocalDateTime.now());
    userRepository.save(user);

    // 测试：指定异常不回滚，其他异常回滚
    if (LocalDateTime.now().getSecond() % 2 == 0) {
      throw new UpdateRuntimeException("指定运行时异常，不回滚：" + randomStr);
    } else {
      throw new OtherRuntimeException("其他运行时异常，回滚：" + randomStr);
    }
  }

  @Override
  public void deleteTest() {
    List<User> all = userRepository.findAll();
    Long id = all.get(all.size() - 1).getId();
    userRepository.deleteById(id);
    log.info("delete id: {}", id);

    // 测试：指定异常不回滚，其他异常回滚
    if (LocalDateTime.now().getSecond() % 2 == 0) {
      throw new DeleteRuntimeException("指定运行时异常，不回滚");
    } else {
      throw new OtherRuntimeException("其他运行时异常，回滚");
    }
  }
}
