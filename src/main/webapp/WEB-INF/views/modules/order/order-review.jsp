<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单审核</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/page.my.css">
    <script src="${ctxStatic}/page.my.js"></script>
    <style>
        .my-nav-tabs{
            background-color: #F7F7F7;
            /*padding-left: 20px;*/
            margin-left: 10px;
        }
        .my-nav-tabs li{
            float: left;
            font-size: 16px;
            line-height: 3;
            margin: 0 30px;
            padding: 0 20px;
            text-align: center;
            color: #000;
        }
        .my-nav-tabs li a{
            color: #000;
        }
        .my-nav-tabs li.active a{
            border-bottom: 5px solid #3F51B5;
        }
        .my-nav-tabs li a:hover{
            background-color: #F7F7F7;
        }
    </style>
</head>
<body>
<ul class="nav my-nav-tabs clearfix">
    <li><a href="${ctx}/activity/activity/activity-list">活动列表</a></li>
    <li><a href="${ctx}/trade/order/order_list">订单列表</a></li>
    <li class="active"><a href="${ctx}/trade/order/order_review">订单审核</a></li>
</ul><br/>
<script>
    var para = GetRequest();
    var paraStr = '?activity_type='+para.activity_type;
    $.each($('.my-nav-tabs li a'),function (index,selector) {
        var oldHref = $(selector).attr('href');
        $(selector).attr('href',oldHref+paraStr);
    })

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
</script>
</body>
</html>
