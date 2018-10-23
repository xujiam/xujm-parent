/**

 @Name：layuiAdmin 文章管理
 @Author：ZhengYP
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.define(['form', 'upload'], function(exports){
    var $ = layui.$
        ,layer = layui.layer
        ,laytpl = layui.laytpl
        ,setter = layui.setter
        ,view = layui.view
        ,admin = layui.admin
        ,form = layui.form
        ,upload = layui.upload;

    var $body = $('body');
    form.render();


    //上传头像
    var avatarSrc = $('#LAY_avatarSrc');
    upload.render({
        url: '/oss/upload/file'
        , data: {type_code: "cms"}
        ,elem: '#LAY_avatarUpload'
        ,done: function(res){
            if(res.code == 0){
                avatarSrc.val(res.data.fileUrl);
            } else {
                layer.msg(res.msg, {icon: 5});
            }
        }
    });

    //查看头像
    admin.events.avartatPreview = function(othis){
        var s = "http://p.triplegain.chidaotv.com/";
        var src = avatarSrc.val();
        layer.photos({
            photos: {
                "title": "查看头像" //相册标题
                ,"data": [{
                    "src": s + src //原图地址
                }]
            }
            ,shade: 0.01
            ,closeBtn: 1
            ,anim: 5
        });
    };
    exports('cms', {})
});