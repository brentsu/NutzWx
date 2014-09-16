package cn.xuetang.common.dataTable;

import java.util.List;



public class DataTableReturn {
	/**
	 * 就是一个当前查询的表示
	 */
	private int sEcho;
	/**
	 * 实际的行数
	 */
	private int iTotalRecords = 0;
	/**
	 * 过滤之后，实际的行数
	 */
	private int iTotalDisplayRecords = 0;
	/**
	 * 查询结果的List
	 */
	private List aaData = null;
	public int getsEcho() {
		return sEcho;
	}
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List getAaData() {
		return aaData;
	}
	public void setAaData(List aaData) {
		this.aaData = aaData;
	}
	
	
	
}
