/**

 @Name：layuiAdmin 每月注册统计
 @Author：star1029
 @Site：http://www.layui.com/admin/
 @License：GPL-2

 */


layui.define(function (exports) {

    //区块轮播切换
    layui.use(['admin', 'carousel', 'table'], function () {
        var $ = layui.$
            , admin = layui.admin
            , carousel = layui.carousel
            , element = layui.element
            , table = layui.table
            , device = layui.device()
            ,form = layui.form;

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
        init();
        //监听搜索
        form.on('submit(LAY-user-front-search)', function(data){
            var field = data.field;
            console.log(field)
            //执行重载
            table.reload('LAY-user-count-register', {
                where: field
            });
        });


        function init() {
            $.get('/user/getLoginMode').done(function (d) {
                console.log(d)
                var data = d.data;
                var cols_info = [];
                cols_info[0] = {"field": "date", "title": "日期", align: "center",width:200 ,fixed: 'left', unresize: true, sort: true, totalRowText: '合计'}
                for (var i=0;i<data.length;i++ ){
                    cols_info[i+1] = {"field": data[i].providerId, "title": data[i].providerName, align: "center",width:300, totalRow: true}
                }
                table.render({
                    elem: '#LAY-user-count-register'
                    ,title: '每月注册统计'
                    ,totalRow: true
                    ,where: {'register':"month"}
                    , url: 'user/count/listRegister_Month' //模拟接口
                    , toolbar: true
                    ,defaultToolbar: ['filter', 'print', 'exports']
                    , headers: {
                        Authorization: layui.data('layuiAdmin').access_token
                    }
                    , cols: [cols_info]
                    , height: 'full-400'
                    , text: '对不起，加载出现异常！'
                    , done: function (res, page, count) {
                        initRegiter(res,d);
                    }
                });

            });
        }

        function initRegiter(res, mode) {
            var d = res;
            //折线图
            layui.use(['echarts'], function () {
                var $ = layui.$
                    , i = layui.echarts;

                //标准折线图
                /*$.get('/user/count/listRegister',{"addTime":addTime}).done(function (d) {*/
                    var reg_date = [];
                    var data = d.data;
                    var reg_data = [];
                    for (var i=0;i<data.length;i++ ){
                        reg_date.push(data[i].date);
                    }
                    var loginMode = [];
                    for (var i=0;i<mode.data.length;i++ ){
                        loginMode.push(mode.data[i].providerId)
                    }
                    for (var i=0;i<loginMode.length;i++ ){
                        var mode_date = [];
                        data.map(function(row) {
                            var temp = row[loginMode[i]]
                            mode_date.push(temp);
                        });
                        reg_data[i] = {"name": loginMode[i], "type": "line", "data": mode_date};
                    }
                    var echnormline = [],
                        normline = [
                            {
                                title: {
                                    text: '每月注册统计',
                                    subtext: '人数统计'
                                },
                                tooltip: {
                                    trigger: 'axis'
                                },
                                legend: {
                                    data: loginMode
                                },
                                toolbox: {
                                    show : true,
                                    feature : {
                                        mark : {show: true},
                                        dataZoom : {
                                            show : true,
                                        },
                                        /*dataView : {show: true, readOnly: false},*/
                                        magicType : {show: true, type: ['line', 'bar']},
                                        restore : {show: true},
                                        saveAsImage : {show: true}
                                    }
                                },
                                calculable: true,
                                xAxis: [
                                    {
                                        type: 'category',
                                        boundaryGap: false,
                                        data : reg_date
                                    }
                                ],
                                yAxis: [
                                    {
                                        type: 'value',
                                        show: true,
                                        axisLabel: {
                                            formatter: '{value} 人'
                                        }
                                    }
                                ],
                                dataZoom : {
                                    show : true,
                                    realtime : true,
                                    start : 0,
                                    end : 50
                                },
                                series: reg_data
                            }
                        ]
                        , elemnormline = $('#LAY-index-normline').children('div')
                        , rendernormline = function (index) {
                            echnormline[index] = echarts.init(elemnormline[index], layui.echartsTheme);
                            echnormline[index].setOption(normline[index]);
                            window.onresize = echnormline[index].resize;
                        };
                    if (!elemnormline[0]) return;
                    rendernormline(0);
               /* })*/
            });
        }

    });



    exports('registerMonth', {})

});