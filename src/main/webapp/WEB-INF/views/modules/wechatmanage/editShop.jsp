<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>编辑公众号</title>
    <%--<meta name="decorator" content="blank"/><c:set var="tabmode" value="${empty cookie.tabmode.value ? '0' : cookie.tabmode.value}"/>--%>
    <link href="${ctxStatic}/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.css" rel="stylesheet">
    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctxStatic}/bootstrap-3.3.0/js/bootstrap.min.js"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js"></script>
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <style>
        body{
            padding: 20px;
        }
        header{
            width: 100%;
            background: rgba(242, 242, 242, 1);
            text-align: left;
            padding: 0 40px;
        }
        #creat_shop{
            height: 60px;
            font-size: 20px;
            cursor: pointer;
            display: inline-block;
            line-height: 60px;
            text-align: center;
        }
        .shop-wrap{
            padding: 50px 0;
            overflow: hidden;
        }
    </style>
</head>
<body>
<header>
    <div id="creat_shop">
        <img src="${ctxStatic}/images/back_arrow.png">
        编辑公众号
    </div>
</header>
<div class="shop-wrap">
    <form class="form-horizontal">
        <div class="form-group">
            <label for="name" class="col-sm-1 control-label"><font style="color: red">*</font>公众号名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="name" placeholder="请输入公众号名称" name="name" disabled value="${name}">
            </div>
        </div>
        <div class="form-group">
            <label for="main_part" class="col-sm-1 control-label"><font style="color: red">*</font>账号主体</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="main_part" placeholder="请输入账号主体名称" name="main_part" value="${mainPart}">
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">提交修改</button>
            </div>
        </div>
    </form>
</div>
</body>
<script>
    $('#creat_shop').click(function(){
        window.location.href="${ctx}/wechat/link_get_list";
    });
    $(".form-horizontal").validate({
        rules: {
            main_part: {
                required: true,
            },
        },
        messages: {
            main_part: {
                required: "请输入账号主体名称",
            },
        },
        submitHandler:function(form){
            var url = "update_wechat_latform";
            $.ajax({
                url:url,
                type:'GET',
                data:{
                    id:'${id}',
                    new_main_part:$('#main_part').val(),
                },
                success:function(data){
                    typeof data != 'object'&& (data = JSON.parse(data));
                    if(data.code=='10000'){
                        layer.open({
                            content:'修改公众号成功!',
                            closeBtn:0,
                            yes:function(){
                                window.location.href="${ctx}/wechat/link_get_list";
                            }
                        })
                    }else{
                        layer.open({
                            content:data.msg,
                        })
                    }
                }
            })
        }
    })
</script>
</html>