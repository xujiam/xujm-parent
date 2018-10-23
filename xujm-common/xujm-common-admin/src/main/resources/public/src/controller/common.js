/**

 @Name：layuiAdmin 公共业务
 @Author：ZhengYP
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.define(['table', 'form'],function (exports) {
    var $ = layui.$
        , layer = layui.layer
        , laytpl = layui.laytpl
        , setter = layui.setter
        , view = layui.view
        , table = layui.table
        , admin = layui.admin;

    //公共业务的逻辑处理可以写在此处，切换任何页面都会执行
    //时间戳的处理begin
    layui.laytpl.numToDateString = function (d, format) {
        d = '' + d;
        if (d != 0) {
            var date = new Date(d || new Date())
                , ymd = [
                d.substr(0, 4)
                , d.substr(4, 2)
                , d.substr(6, 2)
            ]
                , hms = [
                d.substr(8, 2)
                , d.substr(10, 2)
                , d.substr(12, 2)
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

    function formatDate(d, format) {
        d = '' + d;
        if (d != 0) {
            var date = new Date(d || new Date())
                , ymd = [
                d.substr(0, 4)
                , d.substr(4, 2)
                , d.substr(6, 2)
            ]
                , hms = [
                d.substr(8, 2)
                , d.substr(10, 2)
                , d.substr(12, 2)
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
    }

    //时间戳的处理end


    //打赏模块-获取打赏信息begin
    admin.events.reward = function(othis){
        console.log(othis)
        var modulename = othis.data("modulename");
        console.log(modulename);
        var objectid = othis.data("objectid");
        console.log(objectid);
        admin.popup({
            title: '打赏详情'
            ,area: ['50%', "60%"]
            ,id: 'LAY-popup-reward-reward'
            ,success: function(layero, index){
                view(this.id).render('reward/reward/listreward').done(function(){
                    table.render({
                        elem: '#LAY-reward-reward-list'
                        , url: '/reward/getRewardList'
                        , skin: 'line' //行边框风格
                        ,even: false //开启隔行背景
                        , where: {objectId: objectid, moduleName : modulename}
                        , headers: {
                            Authorization: layui.data('layuiAdmin').access_token
                        }
                        , data: []
                        , text: {
                            none: '暂无打赏记录'
                        }
                        , cols: [[
                            {field: 'account', title: '用户账号',  align: "center"}
                            , {field: 'nickname', title: '用户昵称',  align: "center"}
                            , {field: 'rewardAmount', title: '打赏金额', align: "center"}
                            , {field: 'addTime', title: '添加时间', align: "center", templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>' ,minWidth: 180}
                        ]]
                        , text: '对不起，加载出现异常！'
                        , height: 'full'
                        , page : true
                        , limit :10
                        , done: function(res, curr, count){
                            $(".layui-none").html("暂无打赏记录")
                        }
                    });
                });
            }
        });
    };
    //打赏模块-获取打赏信息end


    //收藏模块-获取收藏信息begin
    admin.events.collect = function(othis){
        var modulename = othis.data("modulename");
        var objectid = othis.data("objectid");
        admin.popup({
            title: '收藏详情'
            ,area: ['50%', "60%"]
            ,id: 'LAY-popup-collect-collect'
            ,success: function(layero, index){
                view(this.id).render('collect/collect/listcollect').done(function(){
                    table.render({
                        elem: '#LAY-collect-collect-list'
                        , url: '/collect/getCollect'
                        , skin: 'line' //行边框风格
                        ,even: false //开启隔行背景
                        , where: {objectId: objectid, moduleName : modulename}
                        , headers: {
                            Authorization: layui.data('layuiAdmin').access_token
                        }
                        , data: []
                        , text: {
                            none: '暂无收藏记录'
                        }
                        , cols: [[
                            {field: 'account', title: '用户账号',  align: "center"}
                            , {field: 'nickname', title: '用户昵称',  align: "center"}
                            , {field: 'addTime', title: '收藏时间', align: "center", templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>' ,minWidth: 180}
                        ]]
                        , text: '对不起，加载出现异常！'
                        , height: 'full'
                        , page : true
                        , limit :10
                        , done: function(res, curr, count){
                            $(".layui-none").html("暂无收藏记录")
                        }
                    });
                });
            }
        });
    };
    //收藏模块-获取收藏信息end


    //退出begin
    admin.events.logout = function () {
        //执行退出接口
        admin.req({
            url: '/admin/user/logout'
            , type: 'post'
            , data: {}
            , done: function (res) { //这里要说明一下：done 是只有 response 的 code 正常才会执行。而 succese 则是只要 http 为 200 就会执行

                //清空本地记录的 token，并跳转到登入页
                admin.exit();
            }
        });
    };
    //退出end

    //对外暴露的接口
    exports('common', {});
});