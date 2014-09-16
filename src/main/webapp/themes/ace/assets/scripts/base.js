/**
 * Created by unhappydepig on 14-9-16.
 * 公共JS引用
 */
//格式化DataTable入参
function retrieveData(sSource, aoData, fnCallback) {
    var contion = JSON.stringify(aoData);
    $.ajax({
        "dataType": 'json',
        "type": "POST",
        "url": sSource,
        "data": "contion=" + contion,
        "success": fnCallback
    });
}