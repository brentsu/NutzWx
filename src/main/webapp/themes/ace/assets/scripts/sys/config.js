var Config = {
	init: function () {
	    $('#dg1').dataTable({
	        "bJQueryUI": false,
	        "bPaginate": true,
	        "bRetrieve": false,
	        "bInfo": true,
	        "bDestroy": true,
	        "bServerSide": false,
	        "bProcessing": true,
	        "bFilter": true,
	        "bLengthChange": true,
	        "bSort": true,
	        "bStateSave": true,
	        "bServerSide": true,
	        "sAjaxSource": APP_BASE + "/private/sys/config/list",
	        "fnServerData": retrieveData,
	        "aoColumnDefs": [
	         {
	             "sTitle": '选择',
	             "bSearchable": false,
	             "bSortable": false,
	             "mData": "id",
	             "fnRender": createOperate,
	             "aTargets": [0]
	         },                         
	        {
	            "sTitle": '参数名称',
	            "aDataSort": [0, 1],
	            "mData": "cname",
	            "aTargets": [1]
	        },
	        {
	            "sTitle": '参数值',
	            "aDataSort": [0, 1],
	            "mData": "cvalue",
	            "aTargets": [2]
	        },
	        {
	            "sTitle": '参数说明',
	            "aDataSort": [0, 1],
	            "mData": "note",
	            "aTargets": [3]
	        }
	        ],
	        "oLanguage": {
	            "sUrl": APP_BASE + "/themes/ace/assets/locale/jquery.dataTable.cn.txt"
	        },
	    });  

}
};
function createOperate(object,value) {
    return '<input type="checkbox" name="ck" value="'+value+'">';
}