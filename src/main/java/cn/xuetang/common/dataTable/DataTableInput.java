package cn.xuetang.common.dataTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DataTableInput {
	private boolean bRegex;
	/**
	 * 总指标数
	 */
	private int iColumns;
	/**
	 * 显示的行数
	 */
	private int iDisplayLength;
	/**
	 * 显示的起始索引
	 */
	private int iDisplayStart;
	/**
	 * 排序的列数
	 */
	private int iSortingCols;
	/**
	 * 需要显示的总指标数
	 */
	private String sColumns;
	/**
	 * 参数列表序号指标名
	 */
	private Map<String,DataTableInputData> dataMap = new HashMap<String,DataTableInputData>();
	/**
	 * 需要排序的列表项目
	 */
	private List<DataTableInputSort>  sortList = new ArrayList<DataTableInputSort>();
	
	/**
	 * 查询序号用来避免重复的没什么意义
	 */
	private int sEcho;
	/**
	 * 查询条件
	 */
	private String sSearch;
	public boolean isbRegex() {
		return bRegex;
	}
	public void setbRegex(boolean bRegex) {
		this.bRegex = bRegex;
	}
	public int getiColumns() {
		return iColumns;
	}
	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}
	public int getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public int getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public int getiSortingCols() {
		return iSortingCols;
	}
	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}
	
	public String getsColumns() {
		return sColumns;
	}
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	public int getsEcho() {
		return sEcho;
	}
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	public Map<String, DataTableInputData> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, DataTableInputData> dataMap) {
		this.dataMap = dataMap;
	}
	public List<DataTableInputSort> getSortList() {
		return sortList;
	}
	public void setSortList(List<DataTableInputSort> sortList) {
		this.sortList = sortList;
	}
}
