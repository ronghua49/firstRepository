<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<aside class="main-sidebar">
	<section class="sidebar">
		<ul class="sidebar-menu">

			<li class="treeview <c:if test="${param.menu=='filmlist'}"> active </c:if>"  >
			<a href="/film/filmList"> <i class="fa fa-film "></i> <span>电影管理</span> </a>
			</li>
			<li class="treeview <c:if test="${param.menu=='typesmanage'}"> active </c:if>" >
			<a href="/type/filmTypes"> <i class="fa fa-files-o"></i> <span>类型管理</span> </a>
			</li>
			<li class="treeview <c:if test="${param.menu=='commentarymanage'}"> active </c:if>" >
			<a href="/commentary/commentManage"> <i class="fa fa-comments"></i> <span>评论管理</span></a>
			</li>
		</ul>
	</section>
</aside>
