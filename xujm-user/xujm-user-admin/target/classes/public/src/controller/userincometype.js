/**

 @Name：layuiAdmin 收入类型
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

    //关注列表
    table.render({
        elem: '#LAY-users-incometype-list'
        , url: '/user/incometype/list'
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'incomeCode', title: '收入对应码', minWidth: 80, align: "center"}
            , {field: 'isEnable', title: '是否启用', align: "center", templet: '#isEnableTpl'}
            , {field: 'mark', title: '收入说明', align: "center"}
            , {title: '操作', minWidth: 155, align: 'center', fixed: 'right', toolbar: '#table-incometype-list'}
        ]]
        , text: '对不起，加载出现异常！'
        , height: 'full'
    });

    //监听工具条
    table.on('tool(LAY-users-incometype-list)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            admin.popup({
                title: '编辑收入类型'
                ,area: ['450px', '270px']
                ,id: 'LAY-popup-incometype-edit'
                ,success: function(layero, index){
                    view(this.id).render('user/incometype/incometypeform', data).done(function(){
                        form.render(null, 'layuiadmin-incometype-form-list');
                        //监听提交
                        form.on('submit(LAY-incometype-form-submit)', function(data){
                            var field = data.field; //获取提交的字段
                            formSubmit("user/addIncometype",field,function () {
                                layui.table.reload('LAY-users-incometype-list'); //重载表格
                                layer.close(index); //执行关闭
                            })
                        });
                    });
                }
            });
        }
    });

    exports('userincometype', {})
});