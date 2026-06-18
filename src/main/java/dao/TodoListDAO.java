package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.TodoListInfo;

public class TodoListDAO  {
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() throws SQLException {
		String url = "jdbc:sqlserver://localhost:1433;" 
					+ "integeratedSecurity=false;"
					+ "encrypt=true;"
					+ "trustServerCertificate=true;"
					+ "databaseName=todolist_task;";
		return DriverManager.getConnection(url, "sa", "password");
	}
	
	//全件取得
	public List<TodoListInfo> getTodoList(){
		List<TodoListInfo> todoList = new ArrayList<>();
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement("SELECT * FROM todolist;");
				ResultSet rs = ps.executeQuery();){
			while(rs.next()) {
				int id = rs.getInt("id");
				String taskName = rs.getString("task_name");
				LocalDateTime deadLine = rs.getObject("deadline", LocalDateTime.class);
				String priority = rs.getString("priority");
				String start = rs.getString("isStart");
				String completion = rs.getString("completion");
				LocalDateTime completionDate = rs.getObject("completion_date", LocalDateTime.class);
				String delete = rs.getString("isDelete");
				
				TodoListInfo task = new TodoListInfo();
				
				task.setId(id);
				task.setTaskName(taskName);
				task.setDeadLine(deadLine);
				task.setPriority(priority);
				task.setStart(start);
				task.setCompletion(completion);
				task.setCompletionDate(completionDate);
				task.setDelete(delete);
				
				todoList.add(task);
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return todoList;
	}
	//新規作成
	public void insertTodoList(String taskName, LocalDateTime deadLine, String priority) {
		String sql = "INSERT INTO todolist(task_name, deadline, priority) VALUES (?, ?, ?);";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setString(1, taskName);
			ps.setObject(2, deadLine);
			ps.setString(3, priority);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//着手
	public void updateStart(int id) {
		String sql = "UPDATE todolist SET isStart='着手' WHERE id=?;";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//中断
	public void updateInterruption(int id) {
		String sql = "UPDATE todolist SET isStart='中断' WHERE id=?;";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//完了
	public void updateCompletion(int id) {
		String sql = "UPDATE todolist SET completion='完了' WHERE id=?;";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateCompletionDate(LocalDateTime date, int id) {
		String sql = "UPDATE todolist SET completion_date=? WHERE id=?;";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setObject(1, date);
			ps.setInt(2, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//未完了
	public void updateNotCompletion(int id) {
		String sql = "UPDATE todolist SET completion='未完了' WHERE id=?;";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//削除
	public void updateDelete(int id) {
		String sql = "UPDATE todolist SET isDelete='削除' WHERE id=?;";
		try(Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(sql);){
			ps.setInt(1, id);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
