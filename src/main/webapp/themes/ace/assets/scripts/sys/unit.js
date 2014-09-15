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
        //初始化loading
        var opts = {
            lines: 12, // The number of lines to draw
            length: 7, // The length of each line
            width: 5, // The line thickness
            radius: 10, // The radius of the inner circle
            corners: 1, // Corner roundness (0..1)
            rotate: 0, // The rotation offset
            direction: 1, // 1: clockwise, -1: counterclockwise
            color: '#ff892a', // #rgb or #rrggbb or array of colors
            speed: 1, // Rounds per second
            trail: 100, // Afterglow percentage
            shadow: false, // Whether to render a shadow
            hwaccel: true, // Whether to use hardware acceleration
            className: 'spinner', // The CSS class to assign to the spinner
            zIndex: 2e9, // The z-index (defaults to 2000000000)
            top: '40%', // Top position relative to parent
            left: '50%' // Left position relative to parent
        };
        var spinContainer = $("#spin-container").get(0);
        var spinner = new Spinner(opts);
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

        //重写dialog的title，加载html
        $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
            _title: function (title) {
                var $title = this.options.title || '&nbsp;'
                if (("title_html" in this.options) && this.options.title_html == true)
                    title.html($title);
                else title.text($title);
            }
        }));
        //添加
        $("#btnAdd").on('click', function (e) {
            var form = $("<form class='form-horizontal'><label>单位资料 &nbsp;</label></form>");
            form.load(APP_BASE + "/private/sys/unit/add?id=" + $('#unitid').val());
            var div = bootbox.dialog({
                message: form,
                buttons: {
                    "cancel": {
                        "label": "取消",
                        "className": "btn btn-default"
                    },
                    "confirm": {
                        "label": "确定",
                        "className": "btn btn-primary",
                        "callback": function () {
                            form.submit();
                            return false;
                        }
                    }
                }

            });
            form.validate({
                errorElement: 'div',
                errorClass: 'help-block',
                focusInvalid: false,
                onfocusout: function (element) {
                    $(element).valid();
                },
                rules: {
                    name: {
                        required: true
                    },
                    email: {
                        email: true
                    }
                },

                messages: {
                    name: "单位名称不能为空",
                    email: "请输入正确的Email地址"
                },
                invalidHandler: function (event, validator) {
                },
                highlight: function (e) {
                    $(e).closest('label').removeClass('has-info').addClass('has-error');
                },
                success: function (e) {
                    $(e).closest('label').removeClass('has-error').addClass('has-info');
                    $(e).remove();
                },
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                },
                submitHandler: function (formme) {
                    spinner.spin(spinContainer);
                    $.ajax({
                        type: "POST",
                        url: APP_BASE + "/private/sys/unit/addSave",
                        data: form.serialize(),
                        dataType: "json",
                        success: function (data) {
                            spinner.spin();
                            if (data) {
                                div.modal("hide");
                                $.gritter.add({
                                    title: '操作结果',
                                    text: '添加成功',
                                    class_name: 'gritter-error gritter-center',
                                    time: 600
                                });
                                Unit.initTree();
                            } else {
                                $.gritter.add({
                                    title: '操作结果',
                                    text: '添加失败',
                                    class_name: 'gritter-error gritter-center',
                                    time: 600
                                });
                            }
                        }
                    });
                    return false;
                }
            });

        });
        //修改
        $("#btnUpdate").on('click', function (e) {
            var form = $("<form class='form-horizontal'><label>单位资料 &nbsp;</label></form>");
            form.load(APP_BASE + "/private/sys/unit/update?id=" + $('#unitid').val());
            var div = bootbox.dialog({
                message: form,
                buttons: {
                    "cancel": {
                        "label": "取消",
                        "className": "btn btn-default"
                    },
                    "confirm": {
                        "label": "确定",
                        "className": "btn btn-primary",
                        "callback": function () {
                            form.submit();
                            return false;
                        }
                    }
                }

            });
            form.validate({
                errorElement: 'div',
                errorClass: 'help-block',
                focusInvalid: false,
                onfocusout: function (element) {
                    $(element).valid();
                },
                rules: {
                    name: {
                        required: true
                    },
                    email: {
                        email: true
                    }
                },

                messages: {
                    name: "单位名称不能为空",
                    email: "请输入正确的Email地址"
                },
                invalidHandler: function (event, validator) {
                },
                highlight: function (e) {
                    $(e).closest('label').removeClass('has-info').addClass('has-error');
                },
                success: function (e) {
                    $(e).closest('label').removeClass('has-error').addClass('has-info');
                    $(e).remove();
                },
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                },
                submitHandler: function (formme) {
                    spinner.spin(spinContainer);
                    $.ajax({
                        type: "POST",
                        url: APP_BASE + "/private/sys/unit/updateSave",
                        data: form.serialize(),
                        dataType: "json",
                        success: function (data) {
                            spinner.spin();
                            if (data) {
                                div.modal("hide");
                                $.gritter.add({
                                    title: '操作结果',
                                    text: '修改成功',
                                    class_name: 'gritter-error gritter-center',
                                    time: 600
                                });
                                Unit.initTree();
                            } else {
                                $.gritter.add({
                                    title: '操作结果',
                                    text: '修改失败',
                                    class_name: 'gritter-error gritter-center',
                                    time: 600
                                });
                            }
                        }
                    });
                    return false;
                }
            });

        });
        //删除
        $("#btnDel").on('click', function (e) {
            e.preventDefault();
            if ('' == $('#unitid').val()) {
                $.gritter.add({
                    title: '提示',
                    text: '请选择一个单位',
                    class_name: 'gritter-error gritter-center',
                    time: 600
                });
                return false;
            }
            if ('0001' == $('#unitid').val()) {
                $.gritter.add({
                    title: '提示',
                    text: '不可删除系统管理',
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
                            spinner.spin(spinContainer);
                            var $t = $(this);
                            $.ajax({
                                type: "POST",
                                url: APP_BASE + "/private/sys/unit/del",
                                data: {"id": $('#unitid').val()},
                                dataType: "json",
                                success: function (data) {
                                    spinner.spin();
                                    if ("true" == data) {
                                        $t.dialog("close");
                                        $.gritter.add({
                                            title: '提示',
                                            text: '删除成功',
                                            class_name: 'gritter-error gritter-center',
                                            time: 600
                                        });
                                        Unit.initTree();
                                    } else {
                                        $.gritter.add({
                                            title: '提示',
                                            text: '删除失败',
                                            class_name: 'gritter-error gritter-center',
                                            time: 600
                                        });
                                    }
                                }
                            });

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
                    if (value == "")
                        return;
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