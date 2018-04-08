<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
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