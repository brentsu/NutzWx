package cn.xuetang.common.dataTable;


/**
 * dataTable 传入的指标项排序项
 * @author unhappydepig
 * *
 */

public class DataTableInputSort {
	/**
	 * 指标项序号
	 */
	private String iSortCol;
	/**
	 * 指标名
	 */
	private String mDataProp;
	/**
	 * 排序方式
	 */
	private String sortDir;
	public String getiSortCol() {
		return iSortCol;
	}
	public void setiSortCol(String iSortCol) {
		this.iSortCol = iSortCol;
	}
	public String getmDataProp() {
		return mDataProp;
	}
	public void setmDataProp(String mDataProp) {
		this.mDataProp = mDataProp;
	}
	public String getSortDir() {
		return sortDir;
	}
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}
	
	
	
}
