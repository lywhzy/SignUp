<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: L
  Date: 2019/6/5
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>




<%@include file="../include/adminHead.jsp"%>
<title>我的大赛</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Competitions.css" type="text/css">
<script src="${pageContext.request.contextPath}/Js/Competitions.js"></script>

<div id="large-header">
    <canvas id="demo-canvas">
    </canvas>
</div>
<!-- 網狀特效1 -->
<script src="${pageContext.request.contextPath}/Js/EasePack.min.js"></script>
<script src="${pageContext.request.contextPath}/Js/TweenLite.min.js"></script>
<script src="${pageContext.request.contextPath}/Js/helloweb.js"></script>
<!-- 網狀特效2 -->
<!--内容-->
<div id="container">
    <div id="logo">
        <img id="logoimg" src="${pageContext.request.contextPath}/img/logo1.png">
        

        <p>${user.name}，<button id="exit">退出</button></p>

    </div>
    <%@include file="../include/adminNavigator.jsp"%>
    <div id="content">
        <c:forEach items="${list}" var="c">
            <a style="cursor: pointer;" class="a1" value="${c.status}" href="editMyContest?cid=${c.id}&status=0">
                <div class="item">
                    <p class="name">${c.name}<span>${c.status}</span></p>
                    <p class="intro"><span>&emsp;&emsp;</span>${c.characterization}</p>
                </div>
            </a>
            <div class="item"></div>
        </c:forEach>
        <div class="pager">
            <%@include file="../include/adminPage.jsp"%>
        </div>
    </div>

</div>
</body>
<footer>

</footer>
</html>

