/**

 @Name：layuiAdmin 平台配置
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

    //平台配置
    table.render({
        elem: '#LAY-platform-config'
        , url: '/platform/config/list' //模拟接口
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'configKey', title: '配置键', minWidth: 80, align: "center"}
            , {field: 'configValue', title: '配置值',  align: "center"}
            , {field: 'configType', title: '数据类型', align: "center"}
            , {field: 'configLocal', title: '配置所用地', align: "center"}
            , {field: 'configMark', title: '配置说明', align: "center"}
            , {title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-common-platform'}
        ]]
        , page: true
        , limit: 13
        , height: 'full-320'
        , text: '对不起，加载出现异常！'
        , done: function (res, page, count) {
            $("[data-field='configType']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text("字符串")
                } else if ($(this).text() == '2') {
                    $(this).text("布尔")
                } else if ($(this).text() == '3') {
                    $(this).text("json")
                } else if ($(this).text() == '4') {
                    $(this).text("整型")
                }
            })
            $("[data-field='configLocal']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("所有")
                } else if ($(this).text() == '1') {
                    $(this).text("后台")
                } else if ($(this).text() == '2') {
                    $(this).text("APP")
                } else if ($(this).text() == '3') {
                    $(this).text("后台管理")
                }
            })
        }
    });

    //监听工具条
    table.on('tool(LAY-platform-config)', function (obj) {
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确定删除此配置？', function(index){
                var apiData = {};
                apiData.id = data.id;
                formSubmit("platform/delConfig",apiData, function() {
                    obj.del();
                    layer.close(index);
                });

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑配置'
                ,area: ['560px', '500px']
                ,id: 'LAY-popup-config-edit'
                ,success: function(layero, index){
                    view(this.id).render('platform/config/configform', data).done(function(){
                        form.render(null, 'layuiadmin-form-platform');
                        //监听提交
                        form.on('submit(LAY-platform-config-submit)', function(data){
                            var field = data.field;
                            formSubmit("platform/addConfig",field, function() {
                                layui.table.reload('LAY-platform-config');
                                layer.close(index); //执行关闭
                            });
                        });
                    });
                }
            });
        }
    });

    // 模块组件扩展
    table.render({
        elem: '#LAY-platform-module'
        , url: '/platform/module/list' //模拟接口
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {field: 'moduleName', title: '模块名称', minWidth: 80, align: "center"}
            , {field: 'moduleValue', title: '模块',  align: "center"}
            , {field: 'pluginCode', title: '模块扩展组件', align: "center"}
            , {title: '操作', minWidth: 150, align: 'center', fixed: 'right', toolbar: '#table-platform-moduleplugin'}
        ]]
        , height: 'full'
        , text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-platform-module)', function (obj) {
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确定删除此配置？', function(index){
                var apiData = {};
                apiData.id = data.id;
                formSubmit("platform/delModulePlugin",apiData, function() {
                    obj.del();
                    layer.close(index);
                });

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑配置'
                ,area: ['460px', '400px']
                ,id: 'LAY-popup-module-edit'
                ,success: function(layero, index){
                    view(this.id).render('platform/module/moduleform', data).done(function(){
                        form.render(null, 'layuiadmin-form-module');
                        //监听提交
                        form.on('submit(LAY-platform-module-submit)', function(data){
                            var field = data.field; //获取提交的字段
                            formSubmit("platform/addModulePlugin",field,function () {
                                layui.table.reload('LAY-platform-module'); //重载表格
                                layer.close(index); //执行关闭
                            })
                        });
                    });
                }
            });
        }
    });


    exports('platform', {})
});