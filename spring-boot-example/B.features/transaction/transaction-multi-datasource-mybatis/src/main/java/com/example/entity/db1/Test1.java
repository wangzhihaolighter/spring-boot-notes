package com.example.entity.db1;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_test1")
public class Test1 {
  @TableId(value = "ID", type = IdType.AUTO)
  private Long id;

  private String name;
}
