# dynamic-quartz-scheduler

## 项目说明
通过RESTful接口，动态管理定时任务。如，查询，新增，更新，删除，暂停等。
部分接口示例如下：

* 查询任务列表
``` http
GET /job/query/all HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

* 添加或覆盖(更新 - 根据任务组名和任务名确定是否重复)任务
``` http
POST /job/add HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
  "jobDO": {
    "description": "测试get something",
    "group": "TEST_HTTP_JOB",
    "name": "test_get_something",
    "extInfo": {
      "type": "http_job",
      "method": "GET",
      "url": "http://localhost:8080/something/get",
      "jsonParams": ""
    }
  },
  "triggerDOs": [
    {
      "cronExpression": "0/30 * * * * ?",
      "description": "get something每30秒调用一次",
      "group": "TEST_HTTP_TRIGGER",
      "name": "test_get_something_trigger"
    }
  ]
}
```

* 查询指定jobKey任务信息
``` http
GET /job/query/TEST_HTTP_JOB/test_get_something HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
```

* 删除任务
``` http
DELETE /job/remove HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
	"TEST_HTTP_JOB":["test_get_something"]
}
```

* 立即触发任务
``` http
PUT /job/trigger/now/TEST_HTTP_JOB/test_get_something HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache

{
  "type": "http_job",
  "method": "POST",
  "url": "http://localhost:8080/something/do",
  "jsonParams": ""
}
```

* 暂停触发器
``` http
PUT /job/trigger/pause/TEST_HTTP_TRIGGER/test_get_something_trigger HTTP/1.1
Host: localhost:8080
Cache-Control: no-cache
Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
```