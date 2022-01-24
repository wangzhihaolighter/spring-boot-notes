package com.example.entity.db2;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("T_TEST2")
public class Test2 {
  @TableId(value = "ID", type = IdType.AUTO)
  private Long id;

  private String name;
}
