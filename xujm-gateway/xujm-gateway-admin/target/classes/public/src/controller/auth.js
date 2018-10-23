layui.define(['table', 'form'], function(exports){
    var $ = layui.$
        ,admin = layui.admin
        ,view = layui.view
        ,table = layui.table
        ,form = layui.form;


    //菜单管理
    table.render({
        elem: '#LAY-user-back-menu'
        ,url: '/auth/menu/list' //模拟接口
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'menuId', width: 80, title: 'ID', sort: true}
            ,{field: 'menuName', title: '菜单名称'}
            ,{field: 'menuUrl', title: '菜单地址'}
            ,{field: 'menuIcon', title: '菜单图标'}
            ,{field: 'sort', title: '排序'}
            ,{field: 'isShow', title: '是否显示'}
            ,{title: '操作', width: 100, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        ,text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-manage)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.prompt({
                formType: 1
                ,title: '敏感操作，请验证口令'
            }, function(value, index){
                layer.close(index);
                layer.confirm('确定删除此管理员？', function(index){
                    console.log(obj)
                    obj.del();
                    layer.close(index);
                });
            });
        }else if(obj.event === 'edit'){
            admin.popup({
                title: '编辑管理员'
                ,area: ['420px', '450px']
                ,id: 'LAY-popup-user-add'
                ,success: function(layero, index){
                    view(this.id).render('user/administrators/adminform', data).done(function(){
                        form.render(null, 'layuiadmin-form-admin');

                        //监听提交
                        form.on('submit(LAY-user-back-submit)', function(data){
                            var field = data.field; //获取提交的字段

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //$.ajax({});
                            layui.table.reload('LAY-user-back-manage'); //重载表格
                            layer.close(index); //执行关闭
                        });
                    });
                }
            });
        }
    });

    //管理员管理
    table.render({
        elem: '#LAY-user-back-manage'
        ,url: './json/useradmin/mangadmin.js' //模拟接口
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'id', width: 80, title: 'ID', sort: true}
            ,{field: 'loginname', title: '登录名'}
            ,{field: 'telphone', title: '手机'}
            ,{field: 'email', title: '邮箱'}
            ,{field: 'role', title: '角色'}
            ,{field: 'jointime', title: '加入时间', sort: true}
            ,{field: 'check', title:'审核状态', templet: '#buttonTpl', minWidth: 80, align: 'center'}
            ,{title: '操作', width: 150, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        ,text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-manage)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.prompt({
                formType: 1
                ,title: '敏感操作，请验证口令'
            }, function(value, index){
                layer.close(index);
                layer.confirm('确定删除此管理员？', function(index){
                    console.log(obj)
                    obj.del();
                    layer.close(index);
                });
            });
        }else if(obj.event === 'edit'){
            admin.popup({
                title: '编辑管理员'
                ,area: ['420px', '450px']
                ,id: 'LAY-popup-user-add'
                ,success: function(layero, index){
                    view(this.id).render('user/administrators/adminform', data).done(function(){
                        form.render(null, 'layuiadmin-form-admin');

                        //监听提交
                        form.on('submit(LAY-user-back-submit)', function(data){
                            var field = data.field; //获取提交的字段

                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            //$.ajax({});
                            layui.table.reload('LAY-user-back-manage'); //重载表格
                            layer.close(index); //执行关闭
                        });
                    });
                }
            });
        }
    });

    //角色管理
    table.render({
        elem: '#LAY-user-back-role'
        ,url: '/auth/role/list' //模拟接口
        ,headers:{
            Authorization:layui.data('layuiAdmin').access_token
        }
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field: 'roleId', width: 80, title: 'ID', sort: true}
            ,{field: 'roleName', title: '角色名称'}
            ,{field: 'roleCode', title: '角色代号'}
            ,{field: 'roleMark', title: '角色描述'}
            ,{title: '操作', width: 250, align: 'center', fixed: 'right', toolbar: '#table-useradmin-admin'}
        ]]
        ,text: '对不起，加载出现异常！'
    });

    //监听工具条
    table.on('tool(LAY-user-back-role)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            layer.confirm('确定删除此角色？', function(index){
                var apiData = {};
                apiData.roleIds = data.roleId;
                formSubmit("auth/delRole",apiData, function() {
                    obj.del();
                    layer.close(index);
                });

            });
        }else if(obj.event === 'edit'){
            admin.popup({
                title: '编辑角色'
                ,area: ['500px', '480px']
                ,id: 'LAY-popup-user-add'
                ,success: function(layero, index){
                    view(this.id).render('auth/role/add', data).done(function(){
                        form.render(null, 'layuiadmin-form-role');

                        //监听提交
                        form.on('submit(LAY-user-role-submit)', function(data){
                            var field = data.field; //获取提交的字段
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            formSubmit("auth/addRole",field, function() {
                                layui.table.reload('LAY-user-back-role'); //重载表格
                                layer.close(index); //执行关闭
                            });
                        });
                    });
                }
            });
        }else if(obj.event === 'authorize'){
            admin.popup({
                title: '设置权限'
                ,area: ['500px', '480px']
                ,id: 'LAY-popup-user-add'
                ,success: function(layero, index){
                    view(this.id).render('auth/role/authList', data).done(function(){
                        form.render(null, 'layuiadmin-form-auth');
                        //监听提交
                        form.on('submit(LAY-user-auth-submit)', function(data){
                            console.log(data);
                            // var field = data.field; //获取提交的字段
                            // data.field.
                            var a = {};
                            var menuIdList = [];
                            for(var i in data.field) {
                                if(i.indexOf('options[') >= 0) {
                                    menuIdList.push(data.field[i]);
                                }
                            }
                            // menuIdList
                            a.roleMenuIds = menuIdList.join(',');
                            console.log(a.roleMenuIds);
                            a.roleId = data.field.roleId;
                            console.log(a);
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            formSubmit("auth/addRoleMenu",a, function() {
                                layui.table.reload('LAY-user-back-role'); //重载表格
                                layer.close(index); //执行关闭
                            });
                        });
                    });
                }
            });
        }
    });

    exports('auth', {})
});