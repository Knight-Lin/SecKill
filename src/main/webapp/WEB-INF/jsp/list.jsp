<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@include file="common/tag.jsp" %>
<!doctype html>
<html lang="en">
  <head>
    <%@include file="common/head.jsp"%>
    <title>秒杀列表</title>
  </head>
  <body>
    <div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>

        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <td>名称</td>
                    <td>库存</td>
                    <td>开始时间</td>
                    <td>结束时间</td>
                    <td>创建时间</td>
                    <td>详情页</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="sk">
                    <tr>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td><fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                 <td><fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                 <td><fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><a class="btn btn-info" href="${sk.seckillId}/detail" target="_blank">详情</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
	
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>

  
</html>