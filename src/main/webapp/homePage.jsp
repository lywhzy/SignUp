<%--
  Created by IntelliJ IDEA.
  User: wym
  Date: 2019/5/29
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8" />
    <!--<meta http-equiv="X-UA-Compatible" content="IE=edge">-->
    <title>首页</title>
    <!--<meta name="viewport" content="width=device-width, initial-scale=1">-->
    <script src="Js/jquery.min.js"></script>
    <link rel="stylesheet" href="Css/bootstrap/3.3.6/bootstrap.css">
    <link rel="stylesheet" href="Css/jquery-ui.min.css" type="text/css">
    <script src="Js/bootstrap.min.js"></script>
    <script src="Js/jquery-ui.min.js"></script>
    <script src="Js/homePage.js"></script>
    <link rel="stylesheet" href="Css/homePage_style.css" type="text/css">

    <script>
        $(function () {

            $.ajax({
                url:"isLogin",
                type:"post",
                async:false,
                success:function(data){
                    if (data != "FAIL"){
                        //$("div > p").html(data);
                        $("#user").html(data);
                    }else{
                        $("#user").html("未登录");
                    }
                }
            })

            $("#login").click(function () {
                $.ajax({
                    url:"isLogin",
                    //dataType:"json",
                    type:"post",
                    async:false,

                    success:function (data) {
                        if (data == "FAIL"){
                            /*$("div > p").html(data);
                            setTimeout(function () { test(); }, 2000);
                            window.location.href = "login.html";*/

                            $("#ok").click(function () {
                                var username = $("#username").val();
                                var password = $("#password").val();
                                $.ajax({
                                    url:"Login",
                                    data:{username : username, password : password},
                                    type:"post",
                                    async:false,
                                    success:function (data) {
                                        window.location.href = "homePage.jsp";
                                    }
                                })
                            })

                        }else {
                            //$("div > p").html(data);
                            $("#user").html(data);
                        }
                    }
                })
            })

            $("#logout").click(function () {
                $.ajax({
                    url:"logout",
                    type:"post",
                    async:false,
                    success:function (data) {
                        if (data == "SUCCESS"){
                            alert("退出成功");
                            window.location.href = "homePage.jsp";
                        }
                    }
                })
            })

        })
    </script>
    <style>
        input{
            margin: 1px 5px;
            border: 1px solid gainsboro;
            font-size: 20px;
            border-radius: 3px;
            box-shadow: 0px 1px 1px gray;
            width: 200px;
        }
        input[type="text"]:focus,input[type="password"]:focus{
            font-size: 20px;
            margin: 1px 5px;
            border-radius: 3px;
            box-shadow: 1px 1px 3px #2b669a;
            border: 1px solid cadetblue;
        }
    </style>
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

    <div id="logo">
        <img id="logoimg" src="img/logo1.png">
        <a href="#" id="logout" style="float: left;margin: 5px 4px 3px auto; font-size: 15px">退出</a>
        <a href="#" id="user" style="float: left;margin: 5px 4px 3px auto; font-size: 15px">未登录</a>
        <button type="button" class="btn btn-default">报名大赛</button>
        <button type="button" class="btn btn-default">我的大赛</button>
        <button type="button" class="btn btn-default" id="login" data-toggle="modal" data-target="#myModal">登    录</button>

    </div>
    <!--轮播-->
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            <li data-target="#carousel-example-generic" data-slide-to="3"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active">
                <img src="img/hujing_1.jpg" >
            </div>
            <div class="item">
                <img src="img/banner2.png" >
            </div>
            <div class="item">
                <img src="img/hujing_2.jpg" >
            </div>

            <div class="item">
                <img src="img/banner6.png" >
            </div>

        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>

        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>

        </a>

    </div>
    <!--三个大赛介绍-->
    <h3 align="center">近期精彩大赛介绍</h3>
    <table id="introduce_container">
        <tr>
            <td class="introduce">
                <div>
                    <h4 align="center">蓝桥杯</h4>
                    <p align="left" style="padding: 5px;">为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>
                </div>
            </td>
            <td class="introduce">
                <div>
                    <h4 align="center">蓝桥杯</h4>
                    <p align="left" style="padding: 5px;">为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>
                </div>
            </td>
            <td class="introduce">
                <div>
                    <h4 align="center">蓝桥杯</h4>
                    <p align="left" style="padding: 5px;">为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>
                </div>
            </td>
        </tr>
    </table>
    <!--<div id="introduce_container">-->
    <!--<h3 align="center">近期精彩大赛介绍</h3>-->
    <!--<div class="introduce">-->
    <!--<h4 align="center">蓝桥杯</h4>-->
    <!--<p align="center" style="padding: 5px;">为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>-->
    <!--</div>-->
    <!--<div class="introduce">-->
    <!--<h4 align="center">蓝桥杯</h4>-->
    <!--<p align="center" style="padding: 5px;">为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>-->
    <!--</div>-->
    <!--<div class="introduce">-->
    <!--<h4 align="center">蓝桥杯</h4>-->
    <!--<p align="center" style="padding: 5px;">为促进软件和信息领域专业技术人才培养，提升高校毕业生的就业竞争力，由教育部就业指导中心支持，工业和信息化部人才交流中心举办蓝桥杯大赛。九年来，包括北大、清华在内的超过 1200 余所院校，累计20万余名学子报名参赛，IBM、百度等知名企业全程参与，成为国内始终领跑的人才培养选拔模式并获得行业深度认可的IT类科技竞赛。</p>-->
    <!--</div>-->

    <!--</div>-->

    <!--模态窗口-->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" style="width: 312px;height: 350px;top: 20%">
            <div class="modal-content">
                <div class="modal-header">
                    <button data-dismiss="modal" class="close" type="button"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" align="center">登录</h4>
                </div>
                <div class="modal-body" style="height: 150px; margin-top: 32px">
                    <form>
                        <div align="center"><span class="glyphicon glyphicon-user"></span><input id="username" type="text"></div>
                        <br/>
                        <div align="center"><span class="glyphicon glyphicon-lock"></span><input id="password" type="password"></div>
                    </form>
                    <div style="display: none"><br/><span>验证码</span><input type="text"></div>
                    <a href="#" style="float: left;margin: 5px 4px 3px auto; font-size: 15px">忘记密码</a>
                    <a href="register.jsp" style="float: right;margin: 5px 4px 3px auto; font-size: 15px">注册</a>
                    <br/>
                </div>
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default" type="button" style="float: right;margin: 2px 20px">关闭</button>
                    <button id="ok" class="btn btn-primary" type="button" style="float: left;margin: 2px 20px">确定</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
    <!--模态窗口-->

</div>

</body>
<footer>
</footer>
</html>