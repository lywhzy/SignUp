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
        <p>黄紫移，<button id="exit">退出</button></p>
    </div>
    <div id="content">
        <form id="form">
            <table class="table table-hover table-bordered" width="500">
                <c:forEach items="${list}" var="ci" varStatus="st">
                    <tr>
                        <td align="right">${ci.name}</td>
                        <td id="${st.count}" align="center">
                            <c:if test="${ci.icontype.equals('文本框')}">
                                <script>
                                    addI(${st.count},${ci.id});
                                </script>
                            </c:if>
                            <c:if test="${ci.icontype.equals('下拉框')}">
                                <script>
                                    addO(${st.count},${ci.id});
                                </script>
                            </c:if>
                            <c:if test="${ci.icontype.equals('单选框')}">
                                <script>
                                    addR(${st.count},${ci.id});
                                </script>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
        <div align="center">
            <button type="button" class="btn btn-primary"  id = "update">保存</button>
            <button type="button" class="btn btn-primary" id = "ex">返回</button>
        </div>
    </div>




</div>

<%@include file="../include/adminFoot.jsp"%>
