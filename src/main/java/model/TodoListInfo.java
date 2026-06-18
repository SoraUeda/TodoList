package model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TodoListInfo implements Serializable {
	
	private int id;
	private String taskName;
	private LocalDateTime deadLine;
	private String priority;
	private String start;
	private String completion;
	private LocalDateTime completionDate;
	private String delete;
	
	public TodoListInfo() {}

	public TodoListInfo(int id, String taskName, LocalDateTime deadLine, String priority, String start, String completion, LocalDateTime completionDate, String delete) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.deadLine = deadLine;
		this.priority = priority;
		this.start = start;
		this.completion = completion;
		this.completionDate = completionDate;
		this.delete = delete;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public LocalDateTime getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(LocalDateTime deadLine) {
		this.deadLine = deadLine;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}
		
}
