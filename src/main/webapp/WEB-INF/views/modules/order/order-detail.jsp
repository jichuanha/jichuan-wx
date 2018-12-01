<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单详情</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/layer.js"></script>

    <style>
        .activity-title{
            font-size: 17px;
            padding: 10px 20px;
            border-bottom: 1px solid #F2F2F2;
        }
        .activity-title a{
            display: inline-block;
            width: 20px;
            height: 20px;
            margin-right: 30px;
        }
        .activity-title i img{
            width: 100%;
        }
        h3{
            font-size: 16px;
            background-color: #F7F7F7;
            line-height: 3.5;
            padding: 0 3%;
            width: 90%;
            margin: 0 auto;
            /*border: 1px solid #eee;*/
            margin-top: 20px;
            margin-bottom: 10px;
            color: #8E8E8E;
            font-weight: 400;
        }
        .activity-info,.order-info{
            list-style: none;
            width: 90%;
            margin: 0 auto;
            padding: 0 3%;
        }
        .activity-info li,
        .order-info li{
            font-size: 15px;
            line-height: 2.5;
            position: relative;
            /*vertical-align: top;*/

        }
        .order-info li img{
            width: 30%;
        }
        .import-deco{
            display: inline-block;
            color: red;
            position: absolute;
            top: 0;
            left: -15px;
        }
    </style>
</head>
<body>
<div class="wrap">
    <p class="activity-title"><a href="javascript:history.back(-1)"><img src="${ctxStatic}/images/prev-btn.png" alt=""></a>订单详情</p>
    <h3>活动信息</h3>
    <ul class="activity-info">
        <li><i class="import-deco">*</i>活动名称：活动名称1</li>
        <li>活动状态：进行中</li>
        <li><i class="import-deco">*</i>活动类型：活动类型1</li>
        <li>有效时间：2018-11-11 11:11:11 至 2018-11-11 11:11:11</li>
    </ul>
    <h3>订单信息</h3>
    <ul class="order-info clearfix">
        <li>订单编号：1234567891547</li>
        <li>买家姓名：买家姓名</li>
        <li>收货手机号：17606549036</li>
        <li>买家微信号：12365987</li>
        <li>所属店铺：店铺名称1</li>
        <li>店铺平台：淘宝</li>
        <li>活动金额：¥ 123.00</li>
        <li>订单状态：已审核</li>
        <li>审核时间：2018-11-11 11:11:11</li>
        <li>绑定状态：是</li>
        <li><span style="vertical-align: top">评价截图：</span><img class="img_ifram" src="http://localhost:8181/dongyin-CRM/static/images/bg.jpg" alt=""></li>

    </ul>
</div>

<script>
    $(function () {
        var para = GetRequest();

        $(".wrap").on("click",".img_ifram", function(){
            var src = $(this).attr("src");
            $('#preview-layer').remove();
            $('body').append('<div id="preview-layer" style="display:none;"><img src="' + src + '" style="width:100%;"></div>');
            layer.open({
                type: 1,
                closeBtn: 1,
                title: "信息",
                area: '640px',
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: $('#preview-layer')
            });
        })
        //对url的处理
        function GetRequest() {
            var url = location.search; //获取url中"?"符后的字串
            var theRequest = new Object();
            if (url.indexOf("?") != -1) {
                var str = url.substr(1);
                strs = str.split("&");
                for(var i = 0; i < strs.length; i ++) {
                    theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
                }
            }
            return theRequest;
        }
    })

</script>
</body>
</html>