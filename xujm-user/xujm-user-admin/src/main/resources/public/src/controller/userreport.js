/**

 @Name：layuiAdmin 用户举报
 @Author：ZhengYP
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

//时间戳的处理
layui.laytpl.numToDateString = function(d, format){
    d = '' + d;
    if (d != 0) {
        var date = new Date(d || new Date())
            ,ymd = [
            d.substr(0, 4)
            ,d.substr(4, 2)
            ,d.substr(6, 2)
        ]
            ,hms = [
            d.substr(8, 2)
            ,d.substr(10, 2)
            ,d.substr(12, 2)
        ];
        format = format || 'yyyy-MM-dd HH:mm:ss';

        return format.replace(/yyyy/g, ymd[0])
            .replace(/MM/g, ymd[1])
            .replace(/dd/g, ymd[2])
            .replace(/HH/g, hms[0])
            .replace(/mm/g, hms[1])
            .replace(/ss/g, hms[2]);
    }
    return 0;
};

layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , view = layui.view
        , table = layui.table
        , form = layui.form;

    //用户举报
    table.render({
        elem: '#LAY-users-report-list'
        , url: '/user/report/list' //模拟接口
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'account', title: '账号', minWidth: 80, align: "center"}
            , {field: 'nickname', title: '昵称',  align: "center"}
            , {field: 'moduleName', title: '对象模块', align: "center"}
            , {field: 'reportMsg', title: '举报信息', align: "center"}
            , {field: 'addTime', title: '提出时间',align: "center" , templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>'}
            , {title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-report-list'}
        ]]
        , page: true
        , limit: 15
        , height: 'full'
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-users-report-list)', function (obj) {
        var data = obj.data;
        if (obj.event === 'detail') {
            admin.popup({
                title: '举报详情'
                ,area: ['1580px', "210px"]
                ,id: 'LAY-popup-users-report'
                ,success: function(layero, index){
                    /*sessionStorage.setItem('objectId', data.objectId);*/
                    layui.data('report', {
                        key: 'objectId'
                        ,value: data.objectId
                    });
                    var info = data.moduleName + "/" + data.moduleName + "/" + data.moduleName;
                    view(this.id).render(info, data).done(function(){
                        /*sessionStorage.removeItem('objectId');*/
                        layui.data('report', null); //删除report表
                    });
                }
            });
        }
    });

    exports('userreport', {})
});