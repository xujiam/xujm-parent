/**

 @Name：layuiAdmin 消费类型
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

    //消费类型
    table.render({
        elem: '#LAY-users-consumetype-list'
        , url: '/user/consumetype/list'
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'consumeCode', title: '消费对应码', minWidth: 80, align: "center"}
            , {field: 'transRate', title: '消费币转换率',  align: "center"}
            , {field: 'roundWay', title: '收益取整方式', align: "center"}
            , {field: 'isEnable', title: '是否启用', align: "center", templet: '#isEnableTpl'}
            , {field: 'isFrozen', title: '是否冻结方式', align: "center"}
            , {field: 'mark', title: '消费说明', align: "center"}
            , {title: '操作', minWidth: 155, align: 'center', fixed: 'right', toolbar: '#table-consumetype-list'}
        ]]
        , text: '对不起，加载出现异常！'
        , height: 'full'
        , done: function (res, page, count) {
            $("[data-field='roundWay']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("不变")
                } else if ($(this).text() == '1') {
                    $(this).text("向下取整")
                } else if ($(this).text() == '2') {
                    $(this).text("四舍五入")
                } else if ($(this).text() == '3') {
                    $(this).text("向上取整")
                }
            })
            $("[data-field='isFrozen']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("否")
                } else if ($(this).text() == '1') {
                    $(this).text("是")
                }
            })
            $("[data-field='isEnable']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("不启用")
                } else if ($(this).text() == '1') {
                    $(this).text("启用")
                }
            })
        }
    });

    //监听工具条
    table.on('tool(LAY-users-consumetype-list)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            admin.popup({
                title: '编辑消费类型'
                ,area: ['480px', '440px']
                ,id: 'LAY-popup-consumetype-edit'
                ,success: function(layero, index){
                    view(this.id).render('user/consumetype/consumetypeform', data).done(function(){
                        form.render(null, 'layuiadmin-consumetype-form-list');
                        //监听提交
                        form.on('submit(LAY-consumetype-form-submit)', function(data){
                            var field = data.field; //获取提交的字段
                            formSubmit("user/addConsumetype",field,function () {
                                layui.table.reload('LAY-users-consumetype-list'); //重载表格
                                layer.close(index); //执行关闭
                            })
                        });
                    });
                }
            });
        } else if (obj.event === 'del') {
            layer.confirm('确定删除这条消费类型吗？', function (index) {
                var apiData = {};
                apiData.id = data.id;
                formSubmit("user/delConsumetype", apiData, function () {
                    obj.del();
                    layer.close(index);
                });
            });
        }
    });

    exports('userconsumetype', {})
});