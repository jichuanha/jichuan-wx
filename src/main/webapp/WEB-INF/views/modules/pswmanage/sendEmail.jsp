<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link href="${ctxStatic}/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet">
  <link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.css" rel="stylesheet">
  <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
  <script src="${ctxStatic}/bootstrap-3.3.0/js/bootstrap.min.js"></script>
  <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.js"></script>
  <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.js"></script>
  <script src="${ctxStatic}/layer/layer.js"></script>
  <title>找回密码</title>
  <style>
    #found-block{
      width: 500px;
      margin: 0 auto;
      position: relative;
      margin-top: 150px;
      background: #fff;
      padding: 50px;
      border-radius: 10px;
    }
    body{
      background: url('${ctxStatic}/images/login/bg.jpg') no-repeat;
      background-size:cover;
    }
    #code{
      width: 300px;
      display: block;
    }
    #sendEmail{
      display: block;
      margin: 0 auto;
      font-size: 16px;
      width: 240px;
      height: 40px;
    }
    #logo{
      position: relative;
      margin: 0 auto;
      display: block;
      margin-bottom: 50px;
    }
    #found-block .doingEmail{
      padding-left: 40px;
    }
    #doingEmail{
      <%--background: url('${fns:getConfig('service.url')}/dongyin-CRM/static/images/login/email.png')  20px 50%  no-repeat;--%>
    }
    .login_name_icon{
        width: 20px;
        height: 20px;
        position: absolute;
        left: 10px;
        transform: translateY(30%);
        background: url('${ctxStatic}/images/login/email.png') no-repeat;
    }
    #password{
      background: url('${ctxStatic}/images/login/psw.png')  20px 50%  no-repeat;
    }
    #code{
      background: url('${ctxStatic}/images/login/code.png')  20px 50%  no-repeat;
    }
  </style>
</head>

<body>
  <form id="found-block">
    <img src="${ctxStatic}/images/login/logo.png" id="logo">
    <div class="form-group">
      <label for="doingEmail">邮箱</label>
        <div style="position: relative">
            <div class="login_name_icon"></div>
            <input id="doingEmail" name="doingEmail" class="form-control doingEmail" placeholder="email">
        </div>
    </div>
    <div class="form-group">
      <label for="verify_code">验证码</label>
      <div style="position:relative;">
          <sys:validateCode name="verify_code" inputCssStyle="margin-bottom:0;"/>
      </div>
    </div>
    <button type="submit" class="btn btn-primary" id="sendEmail">发送邮件</button>
    <p style="color: darkred;margin-top: 20px;">请确认您的邮箱名是否正确，系统会自动发送修改密码的邮件到您的公司邮箱账户！如果有问题，请及时联系系统管理员！</p>
  </form>
  <p style="text-align: center;color: #fff;margin-top:20px;">Copyright © 2018-2018东印CRM管理后台 - Powered By DONGYIN V1.0.0</p>
</body>
<script>
  // 在键盘按下并释放及提交后验证提交表单
  $("#found-block").validate({
    rules: {
        doingEmail: {
        required: true,
      }
    },
    messages: {
        doingEmail: {
        required: "请输入邮箱",
      }
    },
    submitHandler:function(){
        var url = "${fns:getConfig('service.url')}/dongyin-CRM/changePassword/sendMail";
        $.ajax({
            url:url,
            type:'POST',
            data:{
                login_name:$('#doingEmail').val(),
                verify_code:$('#verify_code').val(),
            },
            success:function(data){
                typeof data != 'Object'&& (data = JSON.parse(data));
                if(data.code=='10000'){
                    layer.open({
                        content:'系统已发送确认邮件到'+$('#doingEmail').val() +',请及时查收邮件',
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