<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="blank"/><c:set var="tabmode" value="${empty cookie.tabmode.value ? '0' : cookie.tabmode.value}"/>
    <c:if test="${tabmode eq '1'}"><link rel="Stylesheet" href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
        <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script></c:if>
    <style type="text/css">
        #main {padding:0;margin:0;}
        .container-bg{
            width: 100%;
            height: 100%;
            background: url("${ctxStatic}/images/bg.jpg") no-repeat;
            background-size: 100%;
            overflow: hidden;
            padding-top: 30px;
            padding-bottom: 30px;
        }
        #main .container-fluid{padding:0 4px 0 6px;width: 90%;margin: 0 auto;background-color: #fff;height: 90%}
        #header {position:static;} #header li {font-size:14px;_font-size:12px;}
        #header .brand {font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:26px;padding-left:33px;}
        #footer {margin:8px 0 0 0;padding:3px 0 0 0;font-size:11px;text-align:center;border-top:2px solid #0663A2;}
        #footer, #footer a {color:#999;} #left{overflow-x:hidden;overflow-y:auto;} #left .collapse{position:static;}
        #userControl>li>a{/*color:#fff;*/text-shadow:none;} #userControl>li>a:hover, #user #userControl>li.open>a{background:transparent;}
        #productName{height: 20px;display: block;margin-left: 100px;}
        header{
            width: 100%;
            line-height: 100px;
            text-align: left;
            padding: 0 40px;
        }
        #creat_shop{
            height: 60px;
            width: 150px;
            font-size: 14px;
            cursor: pointer;
            display: inline-block;
            line-height: 60px;
            text-align: center;
            background: url('${ctxStatic}/images/wechatmanage/creat_bg.png') no-repeat;
        }
        .shop-wrap{
            padding: 50px 0;
            overflow: hidden;
            background: rgba(242, 242, 242, 1);
            margin-top: 20px;
        }
        .shop-wrap .item{
            border:1px solid #d8d8d8;
            border-top: 4px solid #3f5185;
            padding: 30px 20px;
            width: 390px;
            float: left;
            margin-left: 20px;
            height: 125px;
            margin-top: 20px;
            position: relative;
            background: #fff;
            box-sizing: border-box;
        }
        .shop-wrap .item .desc{
            width: 260px;
            float: left;
            padding-left: 20px;
            height: 60px;
            vertical-align: middle;
            display: table;
        }
        .set{
            cursor: pointer;
            position: absolute;
            display: block;
            width: 24px;
            height: 24px;
            right: 20px;
            top: 20px;
            background: url('${ctxStatic}/images/wechatmanage/set.png') no-repeat;
        }
        .grey{
            color: #d8d8d8;
        }
        .pointer{
            cursor: pointer;
        }
        .avatar{
            width: 60px;
            height: 60px;
            background: #333;
            float: left;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {

            // 获取通知数目  <c:set var="oaNotifyRemindInterval" value="${fns:getConfig('oa.notify.remind.interval')}"/>
            function getNotifyNum(){
                $.get("${ctx}/oa/oaNotify/self/count?updateSession=0&t="+new Date().getTime(),function(data){
                    var num = parseFloat(data);
                    if (num > 0){
                        $("#notifyNum,#notifyNum2").show().html("("+num+")");
                    }else{
                        $("#notifyNum,#notifyNum2").hide()
                    }
                });
            }
            getNotifyNum(); //<c:if test="${oaNotifyRemindInterval ne '' && oaNotifyRemindInterval ne '0'}">
            setInterval(getNotifyNum, ${oaNotifyRemindInterval}); //</c:if>
        });
    </script>
</head>
<body>
<div id="main">
    <div id="header" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="brand"><image id="productName" src="${ctxStatic}/images/login/logo.png" /></div>
            <ul id="userControl" class="nav pull-right">
                <li style="padding: 10px 0;"><image src="${ctxStatic}/images/useravatar.png" style="height: 20px;" /></li>
                <li><a class="" data-toggle="" href="#" title="个人信息">您好, ${fns:getUser().name}&nbsp;<span id="notifyNum" class="label label-info hide"></span></a></li>
                <li id="userInfo" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息"><image src="${ctxStatic}/images/top_more.png" style="height: 20px;" /></a>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx}/sys/user/info" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
                        <li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
                        <li><a href="${ctx}/logout" title="退出登录">退出</a></li>
                    </ul>
                </li>
                <li>&nbsp;</li>
            </ul>
        </div>
    </div>
    <div class="container-bg">
        <div class="container-fluid">
            <div id="content" class="row-fluid">
                <div id="right" style="overflow-y:hidden;overflow-x: hidden; ">
                    <iframe id="menuFrame" name="menuFrame" src="${ctx}/wechat/link_get_list" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
                </div>
            </div>
            <div id="footer" class="row-fluid">
                Copyright &copy; 2018-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="http://m.haiyn.com/" target="_blank">DONGYIN</a> ${fns:getConfig('version')}
            </div>
        </div>

    </div>
</div>
<script type="text/javascript">
    var leftWidth = 160; // 左侧窗口大小
    var tabTitleHeight = 33; // 页签的高度
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $("#header"), footerObj = $("#footer");
    var frameObj = $("#left, #openClose, #right, #right iframe");
    function wSize(){
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({"overflow-x":strs[1] < minWidth ? "auto" : "hidden", "overflow-y":strs[0] < minHeight ? "auto" : "hidden"});
        mainObj.css("width",strs[1] < minWidth ? minWidth - 10 : "auto");
        // frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
        frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28)-40);
        $("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}">
        $(".jericho_tab iframe").height($("#right").height() - tabTitleHeight); // </c:if>
        wSizeWidth();
    }
    function wSizeWidth(){
        if (!$("#openClose").is(":hidden")){
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
            $("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
        }else{
            $("#right").width("100%");
        }
    }// <c:if test="${tabmode eq '1'}">
    function openCloseClickCallBack(b){
        $.fn.jerichoTab.resize();
    } // </c:if>
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>