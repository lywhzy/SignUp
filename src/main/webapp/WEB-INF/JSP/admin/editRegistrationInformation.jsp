<%--
  Created by IntelliJ IDEA.
  User: L
  Date: 2019/5/9
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>

<%@include file="../include/adminHead.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/Css/editRegistrationInformation_style.css" type="text/css">

<div id="large-header">
    <canvas id="demo-canvas">
    </canvas>
</div>
<!-- 網狀特效1 -->
<script src="${pageContext.request.contextPath}/Js/EasePack.min.js"></script>
<script src="${pageContext.request.contextPath}/Js/TweenLite.min.js"></script>
<script src="${pageContext.request.contextPath}/Js/helloweb.js"></script>
<script src="${pageContext.request.contextPath}/Js/editRegistrationInformation.js"></script>
<!-- 網狀特效2 -->
<!--内容-->
<div id="container">
    <div id="logo">
        <img id="logoimg" src="${pageContext.request.contextPath}/img/logo1.png">
    </div>
    <div id="content">
        <!--左边的导航栏-->
        <div id="navigationdiv">
            <p align="center">
                <img id="headPortrait" src="${pageContext.request.contextPath}/img/girl.png"><br />
                <span>ID:</span>
                <!--此处添加id号-->
                <span>270625</span>
            </p>
            <ul id="navigation_bar">
                <li class="li">
                    <!--<div>-->
                    <a id="competitionList_a" class="a" href="listMyContest">比赛列表</a>
                    <!--</div>-->

                </li>
                <li class="li">
                    <a id="edit_a" class="a" href="${pageContext.request.contextPath}/editRegistrationInformation.html">编辑报名信息</a>
                </li>
            </ul>
        </div>
        <!--右边的详细内容-->
        <div id="detail">
            <table class="table table-striped table-bordered table-hover  table-condensed">
                <tbody>
                <c:forEach items="${list}" var="ci">
                    <tr>
                        <td><span class="span">${ci.name}</span></td>
                        <td class="addIcon" type="${ci.icontype}" name="${ci.name}"></td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>






<%@include file="../include/adminFoot.jsp"%>
