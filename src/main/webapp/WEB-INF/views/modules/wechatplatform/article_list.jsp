
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <title>图文</title>
    <style>
        #list-wrap{padding: 50px;}
        header{width: 100%;height: 55px;border-bottom: 2px solid #eee;}
        header ul{text-decoration: none;list-style: none}
        header ul li{font-size: 20px;line-height: 50px;display: inline-block;margin-right: 50px;box-sizing: border-box;}
        .actived{border-bottom: 5px solid #3f5185;}
        .creat-wrap{margin-bottom: 20px;}
        .creat-wrap .btn{font-size: 20px;padding: 10px 30px;display: inline-block;}
        .creat-wrap span{font-size: 20px;margin-left: 20px;}
        #list-block{padding: 50px 0;}
        .item{width: 300px;margin-right: 20px;float: left;border:1px solid #eee;padding: 20px;position: relative;}
        .item img{display: block;width: 100%;height: 200px;margin: 20px 0;}
        .floatL{float: left} .floatR{float: right;}.desc{border-bottom: 1px solid #eee;padding-bottom: 20px;}
        .opration{display: none;position: absolute;top:0;left: 0;right: 0;bottom: 0;background: rgba(255,255,255,0.8);padding: 20px;}
        .opration i{font-size: 30px;margin-right: 10px;cursor: pointer}
    </style>
</head>
<body>
<header>
    <ul>
        <li class="actived">高级图文</li>
        <li>图片</li>
        <li>语音</li>
    </ul>
</header>
<div id="list-wrap">
    <div class="creat-wrap"><button class="btn btn-primary">新建</button><span>共同3条</span></div>
    <div>高级图文不可用于群发，如须群发请请返回微信公众号“素材管理”创建群发图文素材。</div>
    <div id="list-block">
        <div class="item">
            <div class="opration">
                <i class="icon-remove-sign"></i>
                <i class="icon-edit"></i>
            </div>
            <div>
                <div>标题</div>
                <div>2018-12-05</div>
            </div>
            <img>
            <p class="desc">摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示摘要展示</p>
            <div><span class="floatL">阅读原文</span><span class="floatR"><i class="icon-chevron-right"></i></span></div>
        </div>
    </div>
</div>
<script>
    $(function(){
        var params = {
            current_page:1,
            page_size:50,
            type:4,
            wechat_id:1,
        };
        function getData(){
            $.ajax({
                url:'${ctx}/wechat_material/list_material',
                type:'POST',
                data:params,
                success:function(data){
                    console.log(data);
                }
            })
        }
        getData();

        function initEvent() {
            $('.item').hover(function () {
                $(this).find('.opration').show();
            },function(){
                $(this).find('.opration').hide();
            })
        }
        initEvent();
    })
</script>
</body>
</html>
