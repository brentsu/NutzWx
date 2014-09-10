/**
 * Created by Wizzer on 14-9-9.
 */
var Unit = {
    init: function () {
        this.handleUnit();
    },
    handleUnit:function(){
        $('#tree1').ace_tree({
            dataSource: Unit.loadData(""),
            multiSelect: true,
            loadingHTML: '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
            'open-icon': 'icon-minus',
            'close-icon': 'icon-plus',
            'selectable': true,
            'selected-icon': 'icon-ok',
            'unselected-icon': 'icon-remove'
        });
    },
    loadData:function(id){
        var res="";
        $.ajax({
            type: "POST",
            url: APP_BASE + "/private/sys/unit/list",
            data: {"id":id},
            dataType: "json",
            async:true,
            success: function (data) {
                alert(data);
            }
        });
        return res;

    }
};