package cn.xuetang.common.dataTable;


/**
 * dataTable 传入的指标项列表
 * @author unhappydepig
 * *
 */

public class DataTableInputData {
	private boolean bRegex;
	/**
	 * 是否可查询
	 */
	private boolean bSearchable;
	/**
	 * 是否可排序
	 */
	private boolean bSortable;
	/**
	 * 指标名字
	 */
	private String  mDataProp;
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
	public boolean isbSearchable() {
		return bSearchable;
	}
	public void setbSearchable(boolean bSearchable) {
		this.bSearchable = bSearchable;
	}
	public boolean isbSortable() {
		return bSortable;
	}
	public void setbSortable(boolean bSortable) {
		this.bSortable = bSortable;
	}
	public String getmDataProp() {
		return mDataProp;
	}
	public void setmDataProp(String mDataProp) {
		this.mDataProp = mDataProp;
	}
	public String getsSearch() {
		return sSearch;
	}
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}	
	
}
