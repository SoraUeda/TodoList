<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="java.util.List"%>
<%@page import="model.TodoListInfo"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
<style>
	.mb{
		margin-bottom: 15px;
	}
</style>
</head>
<%
List<TodoListInfo> todoList = (List<TodoListInfo>) application.getAttribute("todoList");
%>
<body>
	<h1>タスク一覧</h1>
	<form action="TodoListServlet" method="post">
		<div class="mb">タスク名: <input type="text" name="taskName"></div>
		<div class="mb">${ error_taskName }</div>
		<div class="mb">締切時刻: <input type="datetime-local" name="deadLine" value="${ date_tomorrow }"></div>
		<div class="mb">${ error_deadLine }</div>
		<div class="mb">
			優先度: <select name="priority">
				<option value="★">★</option>
				<option value="★★">★★</option>
				<option value="★★★">★★★</option>
				<option value="★★★★">★★★★</option>
			</select>
		</div>
		<input class="mb" type="submit" name="action" value="新規作成">
	</form>
	<div>${ msg }</div>
	<table>
		<tr>
			<td>タスク名</td>
			<td>締切</td>
			<td>優先度</td>
		</tr>
		<c:forEach var="task" items="${ todoList }">
			<c:if test="${ task.completion eq '未完了' and task.delete ne '削除' }">
				<tr>
					<td>${ task.taskName }</td>
					<td>${ task.deadLine }</td>
					<td>${ task.priority }</td>
					<c:choose>
						<c:when test="${ task.start eq '着手' }">
							<td>
								<form action="TodoListServlet" method="post">
									<input type="hidden" name="id" value="${ task.id }"> 
									<input type="submit" name="action" value="中断">
								</form>
							</td>
						</c:when>
						<c:otherwise>
							<td>
								<form action="TodoListServlet" method="post">
									<input type="hidden" name="id" value="${ task.id }"> 
									<input type="submit" name="action" value="着手">
								</form>
							</td>
						</c:otherwise>
					</c:choose>
					<td>
						<form action="TodoListServlet" method="post">
							<input type="hidden" name="id" value="${ task.id }"> 
							<input type="hidden" name="date" value="${ LocalDateTime.now() }">
							<input type="submit" name="action" value="完了">
						</form>
					</td>
					<td>
						<form action="TodoListServlet" method="post">
							<input type="hidden" name="id" value="${ task.id }"> 
							<input type="submit" name="action" value="削除">
						</form>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>

	<h3>完了済みタスク一覧</h3>
	<table>
		<tr>
			<td>完了済みタスク</td>
			<td>完了日時</td>
		</tr>
		<c:forEach var="task" items="${ todoList }">
			<c:if test="${task.completion eq '完了' and task.delete ne '削除' }">

				<tr>
					<td>${ task.taskName }</td>
					<td>${ task.completionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) }</td>
					<td>
						<form action="TodoListServlet" method="post">
							<input type="hidden" name="id" value="${ task.id }">
							<input type="submit" name="action" value="未完了">
						</form>
					</td>
					<td>
						<form action="TodoListServlet" method="post">
							<input type="hidden" name="id" value="${ task.id }">
							<input type="submit" name="action" value="削除">
						</form>
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</body>
</html>
