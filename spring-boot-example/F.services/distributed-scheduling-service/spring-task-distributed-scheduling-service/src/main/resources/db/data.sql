insert into t_scheduling_task_info
values (1, '测试', '简单的调度任务测试', '0/10 * * * * ?', 'TestTask', 'test', true, true)
     , (2, '测试', '简单的并发调度任务测试', '0/30 * * * * ?', 'TestTask', 'testLock', true, false)
;