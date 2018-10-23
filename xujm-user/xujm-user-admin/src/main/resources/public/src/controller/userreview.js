/**

 @Name：layuiAdmin 用户反馈
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

//时间戳的处理
layui.laytpl.toDateString = function(d, format){
    var date = new Date(d || new Date())
        ,ymd = [
        this.digit(date.getFullYear(), 4)
        ,this.digit(date.getMonth() + 1)
        ,this.digit(date.getDate())
    ]
        ,hms = [
        this.digit(date.getHours())
        ,this.digit(date.getMinutes())
        ,this.digit(date.getSeconds())
    ];
    format = format || 'yyyy-MM-dd HH:mm:ss';

    return format.replace(/yyyy/g, ymd[0])
        .replace(/MM/g, ymd[1])
        .replace(/dd/g, ymd[2])
        .replace(/HH/g, hms[0])
        .replace(/mm/g, hms[1])
        .replace(/ss/g, hms[2]);
};

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

//数字前置补零
layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
        str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
};


layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , view = layui.view
        , table = layui.table
        , form = layui.form;

    //用户反馈
    table.render({
        elem: '#LAY-user-feedback'
        , url: '/user/feedback/list' //模拟接口
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'account', title: '账号', minWidth: 80, align: "center"}
            , {field: 'nickname', title: '昵称',  align: "center"}
            , {field: 'content', title: '反馈内容', align: "center"}
            , {field: 'contactWay', title: '联系方式', align: "center"}
            , {field: 'status', title: '状态', align: "center",width:100}
            , {field: 'addTime', title: '提出时间', sort: true, align: "center" , templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>'}
            , {title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-webuser'}
        ]]
        , page: true
        , limit: 13
        , height: 'full-320'
        , text: '对不起，加载出现异常！'
        , done: function (res, page, count) {
            $("[data-field='status']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("忽略")
                } else if ($(this).text() == '1') {
                    $(this).text("已处理")
                } else if ($(this).text() == '2') {
                    $(this).text("待处理")
                }
            })
        }
    });

    //监听工具条
    table.on('tool(LAY-user-feedback)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('确定删除这条信息吗？', function (index) {
                var apiData = {};
                apiData.id = data.id;
                formSubmit("user/delFeedBack", apiData, function () {
                    layui.table.reload('LAY-user-feedback'); //重载表格
                    layer.close(index);
                });

            });
        } else if (obj.event === 'ignore') {
            layer.confirm('确定忽略这条信息吗？', function (index) {
                var apiData = {};
                apiData.id = data.id;
                formSubmit("user/ignoreFeedBack", apiData, function () {
                    layui.table.reload('LAY-user-feedback'); //重载表格
                    layer.close(index);
                });

            });
        } else if (obj.event === 'review') {
            layer.confirm('确定审阅？', function (index) {
                var apiData = {};
                apiData.id = data.id;
                formSubmit("user/reviewFeedBack", apiData, function () {
                    layui.table.reload('LAY-user-feedback'); //重载表格
                    layer.close(index);
                });

            });
        }
    });

    exports('userreview', {})
});