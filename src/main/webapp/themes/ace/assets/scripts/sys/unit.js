/**
 * Created by Wizzer on 14-9-9.
 */
var ztree;
var setting;
var Unit = {
    init: function () {
        this.handleUnit();
    },
    handleUnit:function(){
        setting = {
            async: {
                enable: true,
                url:APP_BASE+"/private/sys/unit/list",
                autoParam:["id"]
            },
            view:{
                nameIsHTML: true
            },
            callback: {
                onAsyncSuccess: this.zTreeOnAsyncSuccess
            }
        };
        this.initTree();
    },
    initTree:function(){
        ztree =jQuery.fn.zTree.init(jQuery("#tree1"), setting);
    },
    zTreeOnAsyncSuccess:function(){
        var tid=$("#id").html().replaceAll("&nbsp;","");
        ztree.selectNode(ztree.getNodeByParam("id", tid, null));
        if(tid!=""){
            ztree.expandNode(ztree.getNodeByParam("id", tid.substring(0,tid.length-4), null), true);
        }
    },
    view:function(id){
        $.ajax({
            type: "POST",
            url: APP_BASE + "/private/sys/unit/view",
            data: {"id":id},
            dataType: "json",
            success: function (data) {
                //清空内容
                $('.widget-box').find(".editable").each(function(i){
                    if ($(this).prop('tagName') == "INPUT") {
                        $(this).val("");
                    } else {
                        $(this).html("&nbsp;");
                    }
                });
                //赋值
                $.each(data, function(index, value) {
                    if ($('#' + index).prop('tagName') == "INPUT") {
                        $('#' + index).val(value);
                    } else {
                        $('#' + index).html(value);
                    }
                });
            }
        });
    }
};