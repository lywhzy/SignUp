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
    <meta charset="utf-8" />
    <!--<meta http-equiv="X-UA-Compatible" content="IE=edge">-->
    <title>注册</title>
    <!--<link rel="stylesheet" href="css/bootstrap/3.3.6/bootstrap.css">-->
    <script src="Js/jquery.min.js"></script>
    <script src="Js/jquery-ui.min.js"></script>
    <script src="Js/jquery.countdown-2.2.0/jquery.countdown.js"></script>
    <link rel="stylesheet" href="Css/register.css" type="text/css">
    <script src="Js/register.js"></script>
    <script>

        // 验证用户名
        function checkName() {
            var username = $("#username").val();
            var divName = $("#divName");
            divName.html("");
            if ("" == username){
                divName.html("用户名不能为空");
                return false;
            }
            for (var i = 0; i < username.length; ++i){
                var j = username.substring(i, i + 1);
                if (isNaN(j) == false){
                    divName.html("用户名中不能包含数字");
                    return false;
                }
            }
            return true;
        }

        // 验证手机号
        function checkPhone(){
            var phone = $("#phone").val();
            var divPhone = $("#divPhone");
            divPhone.html("");

            var regMobile = /^1\d{10}$/;
            if (regMobile.test(phone) == false){
                divPhone.html("手机号码不正确，请重新输入");
                return false;
            }
            return true;
        }

        // 验证电子邮箱
        function checkEmail(){
            var email = $("#email").val();
            var divEmail = $("#divEmail");
            divEmail.html("");
            if ("" == email){
                divEmail.html("Email不能为空");
                return false;
            }
            // 使用正则表达式验证邮箱
            var reg = /^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;
            if (reg.test(email) == false){
                divEmail.html("电子邮件格式不正确，请重新输入");
                return false;
            }
            return true;
        }

        // 验证密码
        function checkPassword(){
            var password = $("#password").val();
            var divPassword = $("#divPassword");
            divPassword.html("");
            if ("" == password){
                divPassword.html("密码不能为空");
                return false;
            }
            if (password.length < 6){
                divPassword.html("密码必须大于或等于6个字符");
                return false;
            }
            /*var flag1 = false;
            var flag2 = false;
            var flag3 = false;
            for (var i = 0; i < password.length; ++i){
                var j = password.substring(i, i + 1);
                if (isNaN(j) == false){
                    flag1 = true;
                }

            }*/
            return true;
        }

        // 验证确认密码
        function checkRePassword(){
            var password = $("#password").val();
            var rePassword = $("rePassword").val();
            var divRePassword = $("#divRePassword");
            divRePassword.html("");
            if (password != rePassword){
                divRePassword.html("两次输入密码不一致，请重新输入");
                return false;
            }
            return true;
        }

        $(document).ready(function () {
            // 绑定失去焦点事件
            $("#username").blur(checkName);
            $("#phone").blur(checkPhone);
            $("#email").blur(checkEmail);
            $("#password").blur(checkPassword);

            $("#getCode").click(function () {
                var email = $("#email").val();
                $.ajax({
                    url:"mail",
                    type: "post",
                    data: {email:email},
                    async: false,
                    success:function (data) {

                    }
                })
            })

            $("#submit").click(function () {
                var username = $("#username").val();
                var phone = $("#phone").val();
                var email = $("#email").val();
                var password = $("#password").val();
                var code = $("#code").val();
                $.ajax({
                    url:"insert",
                    type:"post",
                    data:{username:username, phone:phone, email:email, password:password, code:code},
                    async:false,
                    success:function (data) {
                        if (data == "SUCCESS"){
                            alert("注册成功");
                        }else if (data == "FAIL"){
                            alert("注册失败");
                        }else if (data == "ERROR"){
                            alert("验证码错误");
                        }
                    }
                })
            })
        })

    </script>

</head>
<body>
<div id="large-header">
    <canvas id="demo-canvas">
    </canvas>
</div>
<!-- 網狀特效1 -->
<script src="Js/EasePack.min.js"></script>
<script src="Js/TweenLite.min.js"></script>
<script src="Js/helloweb.js"></script>
<!-- 網狀特效2 -->
<!--内容-->
<div id="container">

    <div id="msform">

        <!-- progressbar -->

        <ul id="progressbar">

            <li class="active">Account Setup</li>

            <li>Social Profiles</li>

            <li>Personal Details</li>

        </ul>

        <!-- fieldsets -->

        <fieldset class="active">

            <h2>Create your account</h2>

            <h3>This is step 1</h3>

            <input id="username" type="text" name="name" placeholder="用户名" />
            <div class="" id="divName"></div>
            <input id="phone" type="text" name="phone" placeholder="手机号" />
            <div class="" id="divPhone"></div>
            <input id="email" type="text" name="email" placeholder="邮箱" />
            <div class="" id="divEmail"></div>
            <input id="password" type="password" name="pass" placeholder="密码" />
            <div class="" id="divPassword"></div>
            <!--<button class="next" onclick="function f() {-->
            <!--alert('hello');-->
            <!--}">Next</button>-->
            <input type="button" class="next" value="下一步" />

        </fieldset>

        <fieldset>

            <h2>Social Profiles</h2>

            <h3>Your presence on the social network</h3>

            <input id="code" class="code" type="text" name="code" /><button id="getCode">获取验证码</button>

            <input type="button" class="previous" value="上一步" />

            <input id="submit" type="button" class="next" value="下一步" />

        </fieldset>

        <fieldset>

            <h2>注册成功</h2>
            <input id="return" type="button" class="next" value="返回首页" />

        </fieldset>
    </div>
</div>
</body>
<footer>
</footer>
</html>