package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TodoListInfo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import dao.TodoListDAO;

@WebServlet("/TodoListServlet")
public class TodoListServlet extends HttpServlet {
	
	TodoListDAO dao = new TodoListDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<TodoListInfo> todoList = dao.getTodoList();
		
		HttpSession session = request.getSession();
		
		todoList.sort(Comparator.<TodoListInfo, LocalDateTime>comparing(TodoListInfo::getDeadLine).thenComparing(TodoListInfo::getPriority));
		
		String errorTaskName = (String)session.getAttribute("error_taskName");
		String errorDeadLine = (String)session.getAttribute("error_deadLine");
		String msg = (String)session.getAttribute("msg");
		
		if(errorTaskName != null) {
			session.removeAttribute("error_taskName");
			request.setAttribute("error_taskName", errorTaskName);
		}
		if(errorDeadLine != null) {
			session.removeAttribute("error_deadLine");
			request.setAttribute("error_deadLine", errorDeadLine);
		}
		if(msg != null) {
			session.removeAttribute("msg");
			request.setAttribute("msg", msg);
		}
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime tomorrow = now.plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		String formattedDate = tomorrow.format(formatter);
		
		request.setAttribute("todoList", todoList);
		request.setAttribute("date_tomorrow", formattedDate);
		request.getRequestDispatcher("WEB-INF/todolist.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		String taskName = request.getParameter("taskName");
		String deadLineStr = request.getParameter("deadLine");
		LocalDateTime deadLine = null;
		if (deadLineStr != null) {
			deadLine = LocalDateTime.parse(deadLineStr);
		}
		String completionDateStr = request.getParameter("date");
		LocalDateTime completionDate = null;
		if (completionDateStr != null) {
			completionDate = LocalDateTime.parse(completionDateStr);
		}
		String priority = request.getParameter("priority");
		String action = request.getParameter("action");
		String idStr = request.getParameter("id");
		int id = 0;
		if(idStr != null) {
			id = Integer.parseInt(idStr);
		}
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		String formattedDateNow = now.format(formatter);
		
		
		switch(action) {
		case "新規作成":
			if (taskName.equals("") || deadLine.isBefore(now)) {
				if(taskName.equals("")) {
					session.setAttribute("error_taskName", "空白は許可されていません。");
				}
				if(deadLine.isBefore(now)) {
					session.setAttribute("error_deadLine", formattedDateNow + "以降を選択してください");
				}
			}else {
				session.setAttribute("msg", "新しいタスクを追加しました!!");
				dao.insertTodoList(taskName, deadLine, priority);
			}
			break;
		case "着手":
			session.setAttribute("msg", "ステータスを更新しました");
			dao.updateStart(id);
			break;
		case "中断":
			session.setAttribute("msg", "ステータスを更新しました");
			dao.updateInterruption(id);
			break;
		case "完了":
			session.setAttribute("msg", "ステータスを更新しました");
			dao.updateCompletion(id);
			dao.updateCompletionDate(completionDate, id);
			break;
		case "未完了":
			session.setAttribute("msg", "ステータスを更新しました");
			dao.updateNotCompletion(id);
			break;
		case "削除":
			session.setAttribute("msg", "ステータスを更新しました");
			dao.updateDelete(id);
			break;
		}
		response.sendRedirect("TodoListServlet");
	}
}
