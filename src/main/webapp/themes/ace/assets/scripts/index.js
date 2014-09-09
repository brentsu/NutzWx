/**
 * Created by Wizzer on 14-9-3.
 */
var Index = {
    init: function () {
        this.handleIndex();
    },
    handleIndex: function () {
        //设置Loading样式
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
            top: '50%', // Top position relative to parent
            left: '50%' // Left position relative to parent
        };
        var spinContainer = $("#spin-container").get(0);
        var spinner = new Spinner(opts);
        var last_gritter;
        //实现菜单URL内容的AJAX加载
        $("#page_menu").find("a").each(function () {
            $(this).click(function () {
                var url = $(this).attr("href");
                if (url && url.indexOf("javascript") < 0 && url != "index") {
                    spinner.spin(spinContainer);
                    $("#page-content").load($(this).attr("href"), function () {
                        spinner.spin();
                    });
                    return false;
                }

            });
        });
        //修改个人资料
        $("#user-info").click(function () {
            var form = $("<form class='form-horizontal'><label>个人资料 &nbsp;</label></form>");
            form.load(APP_BASE + "/private/sys/user/info");

            var div = bootbox.dialog({
                message: form,
                buttons: {
                    "cancel": {
                        "label": "关闭",
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
                    realname: {
                        required: true
                    },
                    email:{
                        email:true
                    }
                },

                messages: {
                    realname: "姓名不能为空",
                    email:"请输入正确的Email地址"
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
                    if ($('#password').val() != '' || $('#password2').val() != '') {
                        if ($('#oldpassword').val() == '') {
                            $("#oldpassword").closest('label').removeClass('has-info').addClass('has-error');
                            $('<div for="oldpassword" class="help-block">请输入原密码</div>').insertAfter($("#oldpassword").parent());
                            return false;
                        } else if ($('#password').val() == '') {
                            $("#password").closest('label').removeClass('has-info').addClass('has-error');
                            $('<div for="password" class="help-block">请输入新密码</div>').insertAfter($("#password").parent());
                            return false;
                        } else if ($('#password2').val() == '') {
                            $("#password2").closest('label').removeClass('has-info').addClass('has-error');
                            $('<div for="password2" class="help-block">请再输一次新密码</div>').insertAfter($("#password2").parent());
                            return false;
                        } else if ($('#password').val() != $('#password2').val()) {
                            $("#password2").closest('label').removeClass('has-info').addClass('has-error');
                            $('<div for="password2" class="help-block">两次输入密码不一致</div>').insertAfter($("#password2").parent());
                            return false;
                        }
                    }

                    if (last_gritter) $.gritter.remove(last_gritter);
                    spinner.spin(spinContainer);
                    $.ajax({
                        type: "POST",
                        url: APP_BASE + "/private/sys/user/updateInfo",
                        data: form.serialize(),
                        dataType: "json",
                        success: function (data) {
                            spinner.spin();
                            if (data.type == "success") {
                                div.modal("hide");
                                if($('#avatarid').val()!=''){
                                    $('#nav-user-photo').get(0).src=$('#avatarid').val();
                                }
                                last_gritter = $.gritter.add({
                                    title: '操作结果',
                                    text: '修改成功',
                                    class_name: 'gritter-error gritter-center',
                                    time: 600
                                });
                            } else {
                                last_gritter = $.gritter.add({
                                    title: '操作结果',
                                    text: data.content,
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
    }
};