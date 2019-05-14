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
<script src="${pageContext.request.contextPath}/Js/EasePack.min.js"></script>
<script src="${pageContext.request.contextPath}/Js/TweenLite.min.js"></script>
<script src="${pageContext.request.contextPath}/Js/helloweb.js"></script>
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
                    <a id="edit_a" class="a" href="editMyContest">编辑报名信息</a>
                </li>
            </ul>
        </div>
        <!--右边的详细内容-->
        <div id="detail">
            <br /><br /><br /><br />
            <%--<p align="center" style="font-size: 30px">暂无比赛！快去报名吧</p>--%>
            <table class="table table-striped table-bordered table-hover  table-condensed">
                <thead>
                    <tr class="warning">
                        <th>比赛名称</th>
                        <th>比赛状态</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="con">
                        <tr id="${con.id}">
                            <td>${con.name}
                                <a href="editMyContest?uid=${user.id}&cid=${con.id}" class="a1" value="${con.status}">
                                    <span class="glyphicon glyphicon-edit"></span>
                                </a>
                            </td>
                            <td>
                                <span>${con.status}</span>
                            </td>
                            <td>
                                <a href="downLoad?attachment=${con.attachment}&name=${con.name}" class="a2" value="${con.attachment}">下载</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<%@include file="../include/adminFoot.jsp"%>