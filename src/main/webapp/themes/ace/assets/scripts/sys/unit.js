/**
 * Created by Wizzer on 14-9-9.
 */
var ztree;
var setting;
var Unit = {
    init: function () {
        this.handleUnit();
    },
    handleUnit: function () {
        //初始化树
        setting = {
            async: {
                enable: true,
                url: APP_BASE + "/private/sys/unit/list",
                autoParam: ["id"]
            },
            view: {
                nameIsHTML: true
            },
            callback: {
                onAsyncSuccess: this.zTreeOnAsyncSuccess
            }
        };
        this.initTree();
        //删除
        //重写dialog的title，运行加载html
        $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
            _title: function (title) {
                var $title = this.options.title || '&nbsp;'
                if (("title_html" in this.options) && this.options.title_html == true)
                    title.html($title);
                else title.text($title);
            }
        }));
        $("#btnDel").on('click', function (e) {
            e.preventDefault();
            if ($('#unitid').val() == '') {
                $.gritter.add({
                    title: '提示',
                    text: '请选择一个单位',
                    class_name: 'gritter-error gritter-center',
                    time: 600
                });
                return false;
            }
            $("#dialogDel").removeClass('hide').dialog({
                resizable: false,
                modal: true,
                title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i> 删除单位？</h4></div>",
                title_html: true,
                buttons: [
                    {
                        html: "<i class='icon-trash bigger-110'></i>&nbsp; 删除",
                        "class": "btn btn-danger btn-xs",
                        click: function () {
                            $(this).dialog("close");
                        }
                    }
                    ,
                    {
                        html: "<i class='icon-remove bigger-110'></i>&nbsp; 取消",
                        "class": "btn btn-xs",
                        click: function () {
                            $(this).dialog("close");
                        }
                    }
                ]
            });
        });


    },
    initTree: function () {
        ztree = jQuery.fn.zTree.init(jQuery("#tree1"), setting);
    },
    zTreeOnAsyncSuccess: function () {
        var tid = $("#unitid").val();
        ztree.selectNode(ztree.getNodeByParam("id", tid, null));
        if (tid != "") {
            ztree.expandNode(ztree.getNodeByParam("id", tid.substring(0, tid.length - 4), null), true);
        }
    },
    view: function (id) {
        $('#unitid').val(id);
        $.ajax({
            type: "POST",
            url: APP_BASE + "/private/sys/unit/view",
            data: {"id": id},
            dataType: "json",
            success: function (data) {
                //清空内容
                $('.widget-box').find(".editable").each(function (i) {
                    if ($(this).prop('tagName') == "INPUT") {
                        $(this).val("");
                    } else {
                        $(this).html("&nbsp;");
                    }
                });
                //赋值
                $.each(data, function (index, value) {
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