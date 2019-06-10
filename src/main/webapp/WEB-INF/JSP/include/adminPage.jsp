<%--
  Created by IntelliJ IDEA.
  User: L
  Date: 2019/5/9
  Time: 8:15
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8"%>

<nav>
    <ul class="pagination">
        <li <c:if test="${!page.hasPre}"> class="disabled"</c:if>>
            <a href="?start=0" aria-label="Previous" <c:if test="${!page.hasPre}">disabled="true"</c:if>>
                <span aria-hidden="true">‹‹</span>
            </a>
        </li>
        <li <c:if test="${!page.hasPre}">class="disabled"</c:if>>
            <a href="?start=${page.start-page.count}" aria-label="Previous" <c:if test="${!page.hasPre}">disabled="true"</c:if>>
                <span aria-hidden="true">‹</span>
            </a>
        </li>

        <c:forEach begin="0" end="${page.total-1}" varStatus="status">
            <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
                <a <c:if test="${status.index*page.count==page.start}">class="current"</c:if>  href="?start=${status.index*page.count}">${status.index+1}</a>
            </li>
        </c:forEach>



        <li <c:if test="${!page.hasNext}"> class="disabled"</c:if>>
            <a href="?start=${page.start+page.count}" aria-label="Next" <c:if test="${!page.hasNext}">disabled="true" </c:if>>
                <span aria-hidden="true">›</span>
            </a>
        </li>
        <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
            <a href="?start=${page.finalPage}" aria-label="Next" <c:if test="${!page.hasNext}">disabled="true" </c:if>>
                <span aria-hidden="true">››</span>
            </a>
        </li>
    </ul>

</nav>

