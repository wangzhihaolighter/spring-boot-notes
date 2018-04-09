<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<html lang="en">
<head>
    <title>welcome</title>
</head>
<link rel="stylesheet" type="text/css" href="/bootstrap/bootstrap.min.css">
<body>
<form class="form-inline" role="form">
    <button class="btn btn-primary">这是一个用户信息:</button>
    <div class="form-group">
        <label>id:</label> <input class="form-control" value="${sysUser.id}"/><br/>
    </div>
    <div class="form-group">
        <label>姓名:</label> <input class="form-control" value="${sysUser.username}"/><br/>
    </div>
    <div class="form-group">
        <label>密码:</label> <input class="form-control" value="${sysUser.password}"/><br/>
    </div>
</form>
</body>
</html>