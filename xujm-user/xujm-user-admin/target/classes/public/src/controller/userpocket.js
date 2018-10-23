/**

 @Name：layuiAdmin 后台充值
 @Author：ZhengYP
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.define(['table', 'form'], function (exports) {
    var $ = layui.$
        , admin = layui.admin
        , view = layui.view
        , table = layui.table
        , form = layui.form;

    //后台充值
    table.render({
        elem: '#LAY-users-pocket-list'
        , url: '/user/pocket/list'
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'account', title: '账号', minWidth: 80, align: "center"}
            , {field: 'nickname', title: '昵称',  align: "center"}
            , {field: 'surplusTicket', title: '剩余收入', align: "center"}
            , {field: 'surplusDiamond', title: '剩余虚拟币', align: "center"}
            , {field: 'totalTicket', title: '总收入', align: "center"}
            , {field: 'totalDiamond', title: '总虚拟币', sort: true, align: "center"}
            , {field: 'totalConsume', title: '总消费', align: "center"}
            , {field: 'frozenDiamond', title: '冻结虚拟币', sort: true, align: "center"}
            , {title: '操作', minWidth: 155, align: 'center', fixed: 'right', toolbar: '#table-pocket-list'}
        ]]
        , done: function(res, curr, count){
            $(".layui-none").html("请输入账号查询")
        }
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-users-pocket-list)', function (obj) {
        var data = obj.data;
        if (obj.event === 'recharge') {
            admin.popup({
                title: '后台充值'
                ,area: ['480px', '380px']
                ,id: 'LAY-popup-pocket-recharge'
                ,success: function(layero, index){
                    view(this.id).render('user/pocket/pocketform', data).done(function(){
                        form.render(null, 'layuiadmin-pocket-form-list');
                        //监听提交
                        form.on('submit(LAY-pocket-form-submit)', function(data){
                            var field = data.field;
                            formSubmit("user/recharge",field, function() {
                                layui.table.reload('LAY-users-pocket-list');
                                layer.close(index); //执行关闭
                            });
                        });
                    });
                }
            });
        }
    });

    exports('userpocket', {})
});