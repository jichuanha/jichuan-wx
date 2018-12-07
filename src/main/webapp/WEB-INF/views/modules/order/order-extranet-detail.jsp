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
    <h3>订单信息</h3>
    <ul class="order-info">
        <%--<li><i class="import-deco">*</i>活动名称：活动名称1</li>--%>
        <%--<li>活动状态：进行中</li>--%>
        <%--<li><i class="import-deco">*</i>活动类型：活动类型1</li>--%>
        <%--<li>有效时间：2018-11-11 11:11:11 至 2018-11-11 11:11:11</li>--%>
    </ul>
</div>

<script>
    $(function () {
        var para = GetRequest();
        $.ajax({
            url:'${ctx}/trade/tableFlow/orderDetail',
            type:'post',
            data:{
                id:para.id
                // wechat_id:$.cookie().platFormId
            },
            success:function (msg) {
                var msg = JSON.parse(msg);
                if(msg.code == 10000){
                    var data = msg.data;
                    $('.order-info').html('<li>订单编号：'+data.order_sn+'</li>' +
                        ' <li>买家姓名：'+data.buyer_name+'</li>' +
                        '<li>收货手机号：'+data.mobile+'</li>' +
                        '<li>商品名称：'+data.item_name+'</li>' +
                        '<li>购买金额：¥ '+data.pay_amount_str+'</li>' +
                        '<li>所属店铺：'+data.shop_no_str+'</li>' +
                        '<li>店铺平台：'+data.platform_type_str+'</li>' +
                        '<li>订单状态：'+data.order_status+'</li>' +
                        '<li>下单时间：'+data.order_time+'</li>'
                    );
                }
            }
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