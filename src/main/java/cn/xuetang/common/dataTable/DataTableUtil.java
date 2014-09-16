package cn.xuetang.common.dataTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.nutz.castor.Castors;
import org.nutz.dao.Cnd;
import org.nutz.dao.sql.Criteria;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
/**
 * DataTable对象转换类
 * @author unhappydepig
 *
 */
public class DataTableUtil {
	private static final Log log = Logs.get();
	/**
	 * 把服务器传过来的参数转换为DataTable对象
	 * @param contion
	 * @return
	 */
	public static DataTableInput getInput4Contion(String contion) {
		JSONParam[] map = null;
		map = Json.fromJsonAsArray(JSONParam.class, contion);		
		Map<String,JSONParam> paramMap =StringUtils.CoverListToMapping(Arrays.asList(map),"name");
		DataTableInput dataTableInput = new DataTableInput();
		dataTableInput.setsEcho(Castors.me().cast(paramMap.get("sEcho").getValue(),String.class,int.class));
		dataTableInput.setsSearch(paramMap.get("sSearch").getValue());
		dataTableInput.setiColumns(Castors.me().cast(paramMap.get("iColumns").getValue(),String.class,int.class));
		dataTableInput.setiDisplayStart(Castors.me().cast(paramMap.get("iDisplayStart").getValue(),String.class,int.class));				
		dataTableInput.setiDisplayLength(Castors.me().cast(paramMap.get("iDisplayLength").getValue(),String.class,int.class));
		dataTableInput.setiSortingCols(null!=paramMap.get("iSortingCols")? Castors.me().cast(paramMap.get("iSortingCols").getValue(),String.class,int.class):0);
		dataTableInput.setsColumns(paramMap.get("sColumns").getValue());
		Map<String,DataTableInputData> dataPropMap = new HashMap<String,DataTableInputData>();//指标项列表
		for(int i =0;i<dataTableInput.getiColumns();i++){
			DataTableInputData dataTableInputData = new DataTableInputData();
			dataTableInputData.setsSearch(null!=paramMap.get("sSearch_"+i)?paramMap.get("sSearch_"+i).getValue():"");			
			dataTableInputData.setbSortable(null!=paramMap.get("sSortDir_"+i)?Castors.me().cast(paramMap.get("bSortable_"+i).getValue(),String.class,boolean.class):false);  
			dataTableInputData.setbSearchable(null!=paramMap.get("bSearchable_"+i)?Castors.me().cast(paramMap.get("bSearchable_"+i).getValue(),String.class,boolean.class):false);//Castors.me().cast(paramMap.get("bSearchable_"+i).getValue(),String.class,boolean.class));
			dataTableInputData.setmDataProp(null!=paramMap.get("mDataProp_"+i)?paramMap.get("mDataProp_"+i).getValue():"");// paramMap.get("mDataProp_"+i).getValue() );
			dataTableInputData.setbRegex(null!=paramMap.get("bRegex_"+i)?Castors.me().cast(paramMap.get("bRegex_"+i).getValue(),String.class,boolean.class):false);//Castors.me().cast(paramMap.get("bRegex_"+i).getValue(),String.class,boolean.class));
			dataPropMap.put(""+i, dataTableInputData);
		}
		dataTableInput.setDataMap(dataPropMap);
		List<DataTableInputSort>  sortList = new ArrayList<DataTableInputSort>();//排序列列表
		for(int i =0;i<dataTableInput.getiSortingCols();i++){
			DataTableInputSort dataTableInputSort = new DataTableInputSort();
			String iSortCol = null!=paramMap.get("iSortCol_"+i)?paramMap.get("iSortCol_"+i).getValue():"";
			dataTableInputSort.setiSortCol(iSortCol);
			dataTableInputSort.setmDataProp(null!=dataPropMap.get(iSortCol)?dataPropMap.get(iSortCol).getmDataProp():"");
			dataTableInputSort.setSortDir(null!=paramMap.get("sSortDir_"+i)?paramMap.get("sSortDir_"+i).getValue():"");
			sortList.add(dataTableInputSort);
		}
		dataTableInput.setSortList(sortList);
		return dataTableInput;
	}
	/**
	 * 根据入参生成查询条件
	 * @param dataTableInput
	 * @param contionLevel 查询条件级别 设计用来控制是否全局模糊查询
	 * @return
	 */
	public static Criteria createContion(DataTableInput dataTableInput, int contionLevel) {
		Criteria cri = Cnd.cri() ;
		//增加查询条件
		String contionValue = dataTableInput.getsSearch();
		if (!Strings.isBlank(contionValue)) {//如果查询条件不为空
			Map<String, DataTableInputData> paramMap = dataTableInput.getDataMap();
			Iterator iter = paramMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				DataTableInputData val = (DataTableInputData) entry.getValue();
					cri.where().orLike(val.getmDataProp(), contionValue);
			}
		}
		//对字段排序
		List<DataTableInputSort>  sortList = dataTableInput.getSortList();
		for(int i=0;i<sortList.size();i++){
			if("desc".equals(sortList.get(i).getSortDir())){
				cri.getOrderBy().desc(sortList.get(i).getmDataProp());
			}else if("asc".equals(sortList.get(i).getSortDir())) {
				cri.getOrderBy().asc(sortList.get(i).getmDataProp());
			}
		}
		log.debug(cri.toString());
		return cri;
	}	 
	//把Nutz查询结果的对象List转换为DataTableReturnItem方便前台解析处理
	
}
