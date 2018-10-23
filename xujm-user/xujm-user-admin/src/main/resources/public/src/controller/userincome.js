/**

 @Name：layuiAdmin 收入记录
 @Author：ZhengYP
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */
//时间戳的处理
layui.laytpl.numToDateString = function(d, format){
    d = '' + d;
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
};

layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , view = layui.view
        , table = layui.table
        , form = layui.form;

    //收入记录
    table.render({
        elem: '#LAY-users-income-list'
        , url: '/user/income/list'
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'account', title: '用户账号', minWidth: 80, align: "center"}
            , {field: 'nickname', title: '用户昵称',  align: "center"}
            , {field: 'incomeNum', title: '收益金额',  align: "center"}
            , {field: 'incomeMark', title: '消费对应码',  align: "center"}
            , {field: 'addTime', title: '关注时间', align: "center", templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>'}
        ]]
        , text: '对不起，加载出现异常！'
        , page: true
        , limit: 15
        , height: 'full'
    });
    exports('userincome', {})
});