package cn.xuetang.modules.sys.bean;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Wizzer
 * @time 2014-02-27 10:01:23
 */
@Table("sys_task")
public class Sys_task {
	@Id
	@Column("task_id")
	private int taskId;
	@Column("task_code")
	private String taskCode;
	@Column("task_type")
	private int taskType;
	@Column("task_name")
	private String taskName;
	@Column("job_class")
	private String jobClass;
	@Column
	private int execycle;
	@Column("day_of_month")
	private int dayOfMonth;
	@Column("day_of_week")
	private int day_of_week;
	@Column
	private int hour;
	@Column
	private int minute;
	@Column("interval_hour")
	private int intervalHour;
	@Column("interval_minute")
	private int intervalMinute;
	@Column("task_interval_unit")
	private int taskIntervalUnit;
	@Column("cron_expression")
	private String cronExpression;
	@Column("is_enable")
	@ColDefine(type=ColType.BOOLEAN)
	private boolean enable;
	@Column("task_remark")
	private String taskRemark;
	@Column("user_id")
	private long userId;
	@Column("createTime")
	private String create_time;
	@Column("param_value")
	private String paramValue;
	@Column("task_interval")
	private int taskInterval;
	@Column("task_threadnum")
	private int taskThreadnum;
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public int getExecycle() {
		return execycle;
	}
	public void setExecycle(int execycle) {
		this.execycle = execycle;
	}
	public int getDayOfMonth() {
		return dayOfMonth;
	}
	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	public int getDay_of_week() {
		return day_of_week;
	}
	public void setDay_of_week(int day_of_week) {
		this.day_of_week = day_of_week;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getIntervalHour() {
		return intervalHour;
	}
	public void setIntervalHour(int intervalHour) {
		this.intervalHour = intervalHour;
	}
	public int getIntervalMinute() {
		return intervalMinute;
	}
	public void setIntervalMinute(int intervalMinute) {
		this.intervalMinute = intervalMinute;
	}
	public int getTaskIntervalUnit() {
		return taskIntervalUnit;
	}
	public void setTaskIntervalUnit(int taskIntervalUnit) {
		this.taskIntervalUnit = taskIntervalUnit;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getTaskRemark() {
		return taskRemark;
	}
	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public int getTaskInterval() {
		return taskInterval;
	}
	public void setTaskInterval(int taskInterval) {
		this.taskInterval = taskInterval;
	}
	public int getTaskThreadnum() {
		return taskThreadnum;
	}
	public void setTaskThreadnum(int taskThreadnum) {
		this.taskThreadnum = taskThreadnum;
	}

}