package cn.xuetang.modules.app.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Wizzer
 * @time 2014-04-03 20:39:13
 */
@Table("app_info")
public class App_info {
	@Id
	private int id;
	@Column
	private int pid;
	@Column
	private String name;
	@Column
	private String token;
	@Column("app_name")
	private String appName;
	@Column("app_type")
	private String appType;
	@Column("app_key")
	private String appKey;
	@Column("app_secret")
	private String appSecret;
	@Column
	private String mykey;
	@Column
	private String mysecret;
	@Column
	private String txt;
	@Column("access_time")
	@ColDefine(type = ColType.TIMESTAMP)
	private Date accessTime;
	@Column("access_token")
	private String accessToken;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMykey() {
		return mykey;
	}

	public void setMykey(String mykey) {
		this.mykey = mykey;
	}

	public String getMysecret() {
		return mysecret;
	}

	public void setMysecret(String mysecret) {
		this.mysecret = mysecret;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}