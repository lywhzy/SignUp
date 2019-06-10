<%--
  Created by IntelliJ IDEA.
  User: L
  Date: 2019/5/9
  Time: 8:09
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8"%>

<%@include file="../include/adminHead.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/Css/myCompetition_style.css" type="text/css">
<script src="${pageContext.request.contextPath}/Js/myCompitition.js"></script>

<script>
    $(function () {
        $(".a1").click(function () {
            var value = $(this).attr("value");
            if(value=="报名截止"){
                alert("报名已截止 无法修改信息");
                return false;
            }
        });
        $(".a2").click(function () {
            var value = $(this).attr("value");
            if(value.length<1){
                alert("比赛无附件");
                return false;
            }
        })
    })

</script>

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
        <img id="logoimg" src="${pageContext.request.contextPath}/img/logo1.png">
        <p>${user.name}，<button id="exit">退出</button></p>
    </div>
    <%@include file="../include/adminNavigator.jsp"%>
    <h2 align="center" id="h4">您的比赛信息如下</h2>
    <div id="content">
        <table class="table table-hover table-bordered">
            <thead>
                <tr>
                    <th>比赛名称</th>
                    <th>比赛状态</th>
                    <th>比赛附件</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="con">
                <tr id="${con.id}">
                    <td>${con.name}
                        <a href="editMyContest?cid=${con.id}&status=1" class="a1" value="${con.status}">
                            <span class="glyphicon glyphicon-edit"></span>
                        </a>
                    </td>
                    <td>
                        <span>${con.status}</span>
                    </td>
                    <td>
                        <a href="downLoad?attachment=${con.attachment}&name=${con.name}" class="a2" value="${con.attachment}">
                            <span class="glyphicon glyphicon-download-alt"></span>${con.name}
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="pager">
            <%@ include file="../include/adminPage.jsp"%>
        </div>
    </div>
</div>


<%@include file="../include/adminFoot.jsp"%>