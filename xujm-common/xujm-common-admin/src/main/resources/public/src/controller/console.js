/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */


layui.define(['admin'],function (exports) {
    var  admin = layui.admin;
    /*
      下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
    */

    //区块轮播切换
    layui.use(['admin', 'carousel'], function () {
        var $ = layui.$
            , admin = layui.admin
            , carousel = layui.carousel
            , element = layui.element
            , device = layui.device();
        //轮播切换
        $('.layadmin-carousel').each(function () {
            var othis = $(this);
            carousel.render({
                elem: this
                , width: '100%'
                , arrow: 'none'
                , interval: othis.data('interval')
                , autoplay: othis.data('autoplay') === true
                , trigger: (device.ios || device.android) ? 'click' : 'hover'
                , anim: othis.data('anim')
            });
        });
        element.render('progress');

    });


    //数据概览
    layui.use(['carousel', 'echarts','admin'], function () {
        var $ = layui.$
            , carousel = layui.carousel
            , admin = layui.admin
            ,layer = layui.layer
            , echarts = layui.echarts;
        var week_date = [];
        var week_value = [];

        admin.req({
            url:'/user/count/listRegister_Week'
            , headers: {
                Authorization: layui.data('layuiAdmin').access_token
            }
            , done: function (d) {
                var data = d.data;
                for (var i = 0; i < data.length; i++) {
                    week_date.push(data[i].date);
                    week_value.push(data[i].num);
                }
            }
        });
        var echartsApp = [], options = [
            //新增的用户量
            {
                title: {
                    text: '最近一周新增的用户量',
                    x: 'center',
                    textStyle: {
                        fontSize: 14
                    }
                },
                tooltip: { //提示框
                    trigger: 'axis',
                    formatter: "{b}<br>新增用户：{c}"
                },
                xAxis: [{ //X轴
                    type: 'category',
                    data: week_date
                }],
                yAxis: [{  //Y轴
                    type: 'value'
                }],
                series: [{ //内容
                    type: 'line',
                    data: week_value
                }]
            }
        ]
            , elemDataView = $('#LAY-index-dataview').children('div')
            , renderDataView = function (index) {
            setTimeout(function() {
                echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
                echartsApp[index].setOption(options[index]);
                window.onresize = echartsApp[index].resize;
            }, 300)

        };


        //没找到DOM，终止执行
        if (!elemDataView[0]) return;


        renderDataView(0);

        //监听数据概览轮播
        var carouselIndex = 0;
        carousel.on('change(LAY-index-dataview)', function (obj) {
            renderDataView(carouselIndex = obj.index);
        });

        //监听侧边伸缩
        layui.admin.on('side', function () {
            setTimeout(function () {
                renderDataView(carouselIndex);
            }, 300);
        });

        //监听路由
        layui.admin.on('hash(tab)', function () {
            layui.router().path.join('') || renderDataView(carouselIndex);
        });
    });


    // 最新订单
    layui.use('table', function(){
      var $ = layui.$
      ,table = layui.table;

      //今日热搜
      table.render({
        elem: '#LAY-index-topSearch'
        ,url: '/ask/askfee/list' //模拟接口
          , headers: {
              Authorization: layui.data('layuiAdmin').access_token
          }
          , cols: [[
              {field: 'askFee', title: '问答费用', minWidth: 80, align: "center"}
              , {field: 'askNameExtend', title: '费用名称',  align: "center"}
              , {field: 'askMarkExtend', title: '费用说明', align: "center"}
          ]]
        ,skin: 'line'
      });

      //
      table.render({
        elem: '#LAY-index-topCard'
        ,url: '/platform/module/list' //模拟接口
          , headers: {
              Authorization: layui.data('layuiAdmin').access_token
          }
        ,cellMinWidth: 120
          , cols: [[
              {field: 'moduleName', title: '模块名称', minWidth: 80, align: "center"}
              , {field: 'moduleValue', title: '模块',  align: "center"}
              , {field: 'pluginCode', title: '模块扩展组件', align: "center"}
          ]]
        ,skin: 'line'
      });
    });

    layui.use('table', function(){
        var $ = layui.$;
        admin.req({
            url:'/record/getLoginNum'
            , headers: {
                Authorization: layui.data('layuiAdmin').access_token
            }
            , done: function (d) {
                $("#logonNum").append(d.data)
            }
        });
        admin.req({
            url:'/user/getUserNum'
            , headers: {
                Authorization: layui.data('layuiAdmin').access_token
            }
            , done: function (d) {
                $("#userNum").append(d.data)
            }
        });
    })

    exports('console', {})
});