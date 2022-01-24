package com.example.easyexcel.config.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/** Excel数据监听器 */
@Slf4j
public class EasyExcelDataListener<T> extends AnalysisEventListener<T> {
  private final List<T> list = new ArrayList<>();

  @Override
  public void invoke(T data, AnalysisContext context) {
    list.add(data);
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {}

  public List<T> getData() {
    return list;
  }
}
