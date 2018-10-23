/**

 @Name：layuiAdmin 系统说明
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

    //系统说明
    table.render({
        elem: '#LAY-cms-sysdesc-list'
        , url: '/cms/sysdesc/list'
        , where: {categoryId: 1}
        , headers: {
            Authorization: layui.data('layuiAdmin').access_token
        }
        , cols: [[
           /* {field: 'cmsId', title: '内容ID', minWidth: 70, align: "center"}*/
             {field: 'title', title: '标题',  align: "center"}
            , {field: 'description', title: '描述',  align: "center"}
            , {field: 'cover', title: '封面', align: "center", templet: '#coverTpl'}
            , {field: 'readNum', title: '阅读数', align: "center"}
            , {field: 'commentNum', title: '评论数', align: "center"}
            , {field: 'likeNum', title: '点赞数', align: "center"}
            , {field: 'status', title: '数据状态', align: "center"}
            , {field: 'addTime', title: '添加时间', align: "center", templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>' ,minWidth: 180}
            , {title: '操作', width: 200, align: 'center', fixed: 'right', toolbar: '#table-stockhotspot-list'}
        ]]
        , text: '对不起，加载出现异常！'
        , page: true
        , limit: 10
        , height: 'full'
        , done: function (res, page, count) {
            $("[data-field='status']").children().each(function () {
                if ($(this).text() == '-1') {
                    $(this).text("删除")
                } else if ($(this).text() == '0') {
                    $(this).text("禁用")
                } else if ($(this).text() == '1') {
                    $(this).text("正常")
                } else if ($(this).text() == '2') {
                    $(this).text("待审核")
                } else if ($(this).text() == '3') {
                    $(this).text("草稿")
                }
            })
        }
    });

    //监听工具条
    table.on('tool(LAY-cms-sysdesc-list)', function (obj) {
        var data = obj.data;
        if (obj.event === 'details') {
            admin.popup({
                title: '文章详情'
                ,area: ['1200px', '400px']
                ,id: 'LAY-popup-cms-details'
                ,success: function(layero, index){
                    view(this.id).render('cms/cms/details', data).done(function(){
                        //股票热点详情
                        table.render({
                            elem: '#LAY-cms-stockhotspot-details'
                            , url: '/cms/getCmsArticleByCmsId'
                            , skin: 'line' //行边框风格
                            ,even: false //开启隔行背景
                            , where: {cmsId: data.cmsId}
                            , headers: {
                                Authorization: layui.data('layuiAdmin').access_token
                            }
                            , cols: [[
                                {field: 'title', title: '标题',  align: "center"}
                                , {field: 'description', title: '描述',  align: "center"}
                                , {field: 'langCode', title: '语言代号', align: "center"}
                                , {field: 'isEnable', title: '是否启用', align: "center"}
                                , {field: 'addTime', title: '添加时间', align: "center", templet: '<div>{{ layui.laytpl.numToDateString(d.addTime) }}</div>' ,minWidth: 180}
                                , {title: '操作', width: 220, align: 'center', fixed: 'right', toolbar: '#table-stockhotspot-details'}
                            ]]
                            , text: '对不起，加载出现异常！'
                            , height: 'full'
                            , done: function (res, page, count) {
                                $("[data-field='isEnable']").children().each(function () {
                                    if ($(this).text() == '0') {
                                        $(this).text("不启用")
                                    } else if ($(this).text() == '1') {
                                        $(this).text("启用")
                                    }
                                })
                            }
                        });
                    });
                }
            });
        } else if (obj.event === 'edit') {
            admin.popup({
                title: '编辑文章'
                ,area: ['1200px', '850px']
                ,id: 'LAY-popup-cms-edit'
                ,success: function(layero, index){
                    view(this.id).render('cms/cms/editform', data).done(function(){
                        $.get(
                            "/cms/getCmsByCmsId",
                            { cmsId: data.cmsId},
                            function(articleData){
                                $("#LAY_avatarSrc").val(articleData.data.cover)
                            })
                        console.log(data)
                        $.ajax({
                            type: "GET",
                            url:"/cms/getCmsLanguage",
                            dataType: "json",
                            success: function(d){
                                var article = "";
                                var lang = "";
                                var content ;
                                var description ;
                                var title ;
                                $.each(d.data, function(index,values){   // 解析出data对应的Object数组
                                    if (index != 'zh_CN') {
                                        lang  += "<input type='button'  value='"+values+"' class='layui-btn layui-btn-primary change' layadmin-event='change' " +
                                            "data-index='"+index+"' data-cmsid='"+data.cmsId+"'>";
                                    }
                                    if (index == 'zh_CN') {
                                        lang += "<input type='button'  value='" + values + "' class='layui-btn layui-btn-normal change' layadmin-event='change' " +
                                            "data-index='" + index + "' data-cmsid='"+data.cmsId+"'>";
                                    }
                                    if (index == 'zh_CN') {         // 如果为中文
                                        $.get(
                                            "/cms/getCmsArticleByCmsIdandLang",
                                            { cmsId: data.cmsId, langCode: index },
                                            function(articleData){
                                                title = articleData.data.title;
                                                description = articleData.data.description;
                                                content = articleData.data.content;
                                                article +=
                                                    "<div class='cms' id='"+index+"'>\n" +
                                                    "            <input type='hidden' name='langCode[]' value='"+index+"'>" +
                                                    "            <div class=\"layui-form-item\">\n" +
                                                    "                <label class=\"layui-form-label\">标题</label>\n" +
                                                    "                <div class=\"layui-input-inline\">\n" +
                                                    "                        <input type=\"text\" name='title[]' id='title' lay-verify=\"required\" class=\"layui-input\" value='"+title+"'>\n" +
                                                    "                </div>\n" +
                                                    "            </div>\n" +
                                                    "            <div class=\"layui-form-item\">\n" +
                                                    "                <label class=\"layui-form-label\">描述</label>\n" +
                                                    "                <div class=\"layui-input-block\">\n" +
                                                    "                    <textarea  class=\"layui-textarea\" name='description[]' id='description' lay-verify=\"required\">"+description+"</textarea>\n" +
                                                    "                </div>\n" +
                                                    "            </div>\n" +
                                                    "            <div class=\"layui-form-item\">\n" +
                                                    "                <label class=\"layui-form-label\">内容</label>\n" +
                                                    "                <div class=\"layui-input-inline layui-btn-container\" style=\"width: auto;\">\n" +
                                                    "                        <textarea name='content[]' class='content' id='content"+index+"'>"+content+"</textarea>\n" +
                                                    "                </div>\n" +
                                                    "            </div>\n" +
                                                    "        </div>";
                                                $("#article").html(article);
                                                if(!window.CKEDITOR) {
                                                    layui.config({
                                                        base: 'src/ckeditor/'
                                                    }).extend({
                                                        ckeditor: 'ckeditor'
                                                    }).use('ckeditor', function () {
                                                        CKEDITOR.replace('contentzh_CN', {
                                                            language: 'zh-cn',
                                                            baseFloatZIndex: 9999999999999,
                                                            filebrowserImageUploadUrl : "/cms/upload/file?type_code=cms"
                                                        });
                                                    });
                                                } else {
                                                    CKEDITOR.replace('contentzh_CN', {
                                                        language: 'zh-cn',
                                                        baseFloatZIndex: 9999999999999,
                                                        filebrowserImageUploadUrl : "/cms/upload/file?type_code=cms"
                                                    });
                                                }
                                            }
                                        );
                                    }
                                });
                                $("#lang").html(lang);
                            }
                        });
                        //监听提交
                        form.on('submit(LAY-cms-form-submit)', function(data){
                            var field = data.field; //获取提交的字段
                            var llength = 0;            // 对应多少个语言的文章
                            for(var i in data.field) {
                                if(i.indexOf('title[') >= 0) {      // 因为title为必填，所以根据提交的feield中有多少个title来判断写了多少个语言
                                    llength += 1;
                                }
                            }
                            var post_datas = [];
                            var post_data;
                            for(var i=0; i<llength; i++) {
                                post_data = new Object();
                                var temp1 = "content" + field['langCode['+i+']'];
                                content11 = CKEDITOR.instances[temp1].getData();
                                if(field['langCode['+i+']'] != '' && field['title['+i+']'] != '' && field['description['+i+']'] != '' && content11 != '') {
                                    post_data["langCode"] = field['langCode['+i+']'];
                                    post_data["title"] =  field['title['+i+']'];
                                    post_data["description"] =  field['description['+i+']'];
                                    post_data["content"] =  content11;
                                    post_datas.push(post_data)
                                }
                            }
                            post_datas = JSON.stringify(post_datas);      // JSON数组转换为字符串
                            field.post_datas = post_datas;
                            // --- ---- ----- ------    遍历文章json 结束
                            field.status = 1;
                            field.categoryId = 1;   // 代表添加的是系统说明
                            field.title = $("#title").val();
                            field.description = $("#description").val();
                            console.log(field)
                            formSubmit("cms/updateCmses",field,function () {
                                layui.table.reload('LAY-cms-sysdesc-list'); //重载表格
                                layer.close(index); //执行关闭
                            })
                        });
                    });
                }
            });
        }
    });

    //监听工具条
    table.on('tool(LAY-cms-stockhotspot-details)', function (obj) {
        var data = obj.data;
        if (obj.event === 'preview') {
            $.ajax({
                url:"cms/previewCmsArticle",
                data : {cmsId:data.cmsId,langCode:data.langCode},
                dataType:"json",   //返回的数据是json 格式
                success: function(d) {
                    window.open(d.message);
                    //window.location.href=d.message;
                    //alert(d.message);
                },
                error : function() {
                    alert("请重新获取");
                }
            })
        }
    });

    exports('sysdesc', {})
});