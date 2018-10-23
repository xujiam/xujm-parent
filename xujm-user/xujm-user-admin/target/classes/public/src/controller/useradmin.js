/**

 @Name：layuiAdmin 用户管理 管理员管理 角色管理
 @Author：star1029
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

    //自定义验证
    form.verify({
        nickname: function(value, item){ //value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
        }

        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        ,pass: [
            /^[\S]{6,12}$/
            ,'密码必须6到12位，且不能出现空格'
        ]

        //确认密码
        ,repass: function(value){
            if(value !== $('#LAY_password').val()){
                return '两次密码输入不一致';
            }
        }
    });


    //用户列表
    table.render({
        elem: '#LAY-user-manage'
        , url: '/user/userinfo/list' //模拟接口
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            /*, {field: 'userId', title: 'ID', minWidth: 100, align: "center"}*/
             {field: 'account', title: '账号',width:120, align: "center"}
            , {field: 'nickname', title: '昵称', width:120,align: "center"}
            , {field: 'avatar', title: '头像', width:90,align: "center", templet: '#avatarTpl'}
            , {field: 'sex', title: '性别',width:60, align: "center"}
            , {field: 'level', title: '等级',width:60, align: "center"}
            , {field: 'signature', title: '个性签名',  align: "center"}
            , {field: 'role', title: '账号角色',width:90,align: "center"}
            , {field: 'mobile', title: '手机',width:140, align: "center"}
            , {field: 'isBlack', title: '状态',width:60,  align: "center"}
            , {field: 'insideRole', title: '内部角色', width:100,align: "center"}
            , {field: 'addTime', title: '注册时间', align: "center", minWidth: 180,templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>' }
            , {title: '操作', align: 'center', fixed: 'right', minWidth: 200,toolbar: '#table-useradmin-webuser'}
        ]]
        , page: true
        , limit: 15
        , height: 'full-320'
        , text: '对不起，加载出现异常！'
        , done: function (res, page, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            //分类显示中文名称
            $("[data-field='sex']").children().each(function () {
                if ($(this).text() == '1') {
                    $(this).text("男")
                } else if ($(this).text() == '2') {
                    $(this).text("女")
                } else if ($(this).text() == '0') {
                    $(this).text("未知")
                }
            })
            $("[data-field='role']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("普通")
                } else if ($(this).text() == '1') {
                    $(this).text("投资顾问")
                }
            })
            $("[data-field='isBlack']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("正常")
                } else if ($(this).text() == '1') {
                    $(this).text("封号")
                }
            })
            $("[data-field='insideRole']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("普通账户")
                } else if ($(this).text() == '1') {
                    $(this).text("机器人")
                } else if ($(this).text() == '2') {
                    $(this).text("内部账户")
                } else if ($(this).text() == '3') {
                    $(this).text("测试账户")
                }
            })
        }
    });

    //监听工具条
    table.on('tool(LAY-user-manage)', function (obj) {
        var data = obj.data;
        if (obj.event === 'black') {
            layer.confirm('确定拉黑此用户？', function (index) {
                var apiData = {};
                apiData.userId = data.userId;
                formSubmit("user/blackUser", apiData, function () {
                    layui.table.reload('LAY-user-manage'); //重载表格
                    layer.close(index);
                });

            });
        } else if (obj.event === 'removeblack') {
            layer.confirm('确定洗白此用户？', function (index) {
                var apiData = {};
                apiData.userId = data.userId;
                formSubmit("user/removeblack", apiData, function () {
                    layui.table.reload('LAY-user-manage'); //重载表格
                    layer.close(index);
                });

            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑用户'
                , area: ['500px', '450px']
                , id: 'LAY-popup-user-add'
                , success: function (layero, index) {
                    view(this.id).render('user/userinfo/userform', data).done(function () {
                        form.render(null, 'layuiadmin-form-useradmin');
                        //监听提交
                        form.on('submit(LAY-user-front-submit)', function (data) {
                            var field = data.field; //获取提交的字段
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            formSubmit("user/editUser", field, function () {
                                layui.table.reload('LAY-user-manage'); //重载表格
                                layer.close(index); //执行关闭
                            });
                        });
                    });
                }
            });
        }
    });

    //后台用户
    table.render({
        elem: '#LAY-user-back-account'
        , url: '/user/account/list'
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'userId', title: 'ID', minWidth: 100, align: "center"}
            , {field: 'username', title: '用户名', minWidth: 100, align: "center"}
            , {field: 'mobile', title: '手机', align: "center"}
            , {field: 'roleNames', title: '角色', align: "center"}
            /*, {field: 'roleKeys', title: '角色id', align: "center",display:'none'}*/
            , {field: 'isBlack', title: '状态', align: "center"}
            , {title: '操作', width: 300, align: 'center', fixed: 'right', toolbar: '#table-useradmin-account'}
        ]]
        , text: '对不起，加载出现异常！'
        , done: function (res, page, count) {
            $("[data-field='isBlack']").children().each(function () {
                if ($(this).text() == '0') {
                    $(this).text("正常")
                } else if ($(this).text() == '1') {
                    $(this).text("封号")
                }
            })
        }
    });

    //监听工具条
    table.on('tool(LAY-user-back-account)', function (obj) {
        var data = obj.data;
        if (obj.event === 'edit') {
            admin.popup({
                title: '编辑管理员'
                , area: ['500px', '350px']
                , id: 'LAY-popup-account-edit'
                , success: function (layero, index) {
                    view(this.id).render('user/account/accountform', data).done(function () {
                        var keys = data.roleKeys;
                        $.ajax({
                            url:"user/getAdminRole",
                            dataType:"json",   //返回的数据是json 格式
                            success: function(d) {
                                var info = "";
                                $.each(d.data, function(index,values){   // 解析出data对应的Object数组
                                    info += "<input type='checkbox' name='roles' title='"+values.roleName+"' value='"+values.roleId+"' lay-skin='primary'";
                                    if (keys != null) {
                                        if (keys.indexOf(values.roleId) != -1){
                                            info += "checked";
                                        }
                                    }
                                    info += ">";
                                });
                                $("#roles").html(info);
                                form.render('checkbox');
                            },
                            error : function() {
                                alert("请重新获取");
                            }
                        })
                        form.render(null, 'layuiadmin-form-account');
                        //监听提交
                        form.on('submit(LAY-account-front-submit)', function (data) {
                            var roles = "";
                            $("input:checkbox[name='roles']:checked").each(function() { // 遍历name=roles的多选框
                                $(this).val();  // 每一个被选中项的值
                                roles += $(this).val() + ",";
                            });
                            roles = roles.substring(0, roles.lastIndexOf(','));
                            var field = data.field; //获取提交的字段
                            field.roles=roles;
                            //提交 Ajax 成功后，关闭当前弹层并重载表格
                            formSubmit("user/editAccount", field, function () {
                                layui.table.reload('LAY-user-back-account'); //重载表格
                                layer.close(index); //执行关闭
                            });
                        });
                    });
                }
            });
        }else if (obj.event === 'editpwd') {

        }
    });
    exports('useradmin', {})
});