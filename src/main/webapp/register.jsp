<%--
  Created by IntelliJ IDEA.
  User: wym
  Date: 2019/5/10
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
    <title>register</title>
    <script src="Js/jquery.min.js"></script>
    <script>

        $(function () {
            $("#submit").click(function () {
                $.ajax({
                    url:"insert",
                    type:"post",
                    async:false,
                    success:function (data) {
                        alert("注册成功");
                    }
                })
            })
        })

    </script>
</head>
<body>

<form action="${pageContext.request.contextPath}/insert" method="post">
    <p>用户名<input type="text" name="userName"></p>
    <p>联系方式<input type="text" name="phoneNumber"></p>7

    <p>密码<input type="password" name="password"></p>
    <p>
        <label><input name="level" type="radio" value="">普通用户</label>
        <label><input name="level" type="radio" value="">管理员</label>
        <label><input name="level" type="radio" value="">超级管理员</label>
    </p>
    <form action="${pageContext.request.contextPath}/mail" method="post">
        <p>邮箱<input type="text" name="email"></p>
        邮箱验证<input id="idenCode" name="idenCode" value="${idenCode}" type="text" name="mail"/>
        <input type="submit" value="验证码"/>
    </form>

    <input id="submit" type="submit" value="注册">
</form>


</body>
</html>