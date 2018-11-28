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
    #submit{
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
    #found-block input{
      padding-left: 50px;
    }
    #doingEmail{
      background: url('${ctxStatic}/images/login/email.png')  20px 50%  no-repeat;
    }
    #password{
      background: url('${ctxStatic}/images/login/psw.png')  20px 50%  no-repeat;
    }
    #confirm_password{
      background: url('${ctxStatic}/images/login/confirmpsw.png')  20px 50%  no-repeat;
    }
  </style>
</head>
<body>
  <form id="found-block">
    <img src="${ctxStatic}/images/login/logo.png" id="logo">
    <div class="form-group">
      <!-- <label for="password">新密码</label> -->
      <input id="password" name="password" type="password"  class="form-control" placeholder="新密码">
    </div>
    <div class="form-group">
      <!-- <label for="confirm_password">确认密码</label> -->
      <input id="confirm_password" name="confirm_password" type="password" class="form-control" placeholder="确认密码">
    </div>
    <button type="submit" class="btn btn-primary" id="submit">提交</button>
  </form>
  <p style="text-align: center;color: #fff;margin-top:20px;">Copyright © 2018-2018东印CRM管理后台 - Powered By DONGYIN V1.0.0</p>
</body>
<script>
      // 在键盘按下并释放及提交后验证提交表单
      $("#found-block").validate({
        rules: {
          password: {
            required: true,
            minlength: 6
          },
          confirm_password: {
            required: true,
            minlength: 6,
            equalTo: "#password"
          },
        },
        messages: {
          password: {
            required: "请输入密码",
            minlength: "密码长度不能小于 6 位"
          },
          confirm_password: {
            required: "请输入密码",
            minlength: "密码长度不能小于 6 位",
            equalTo: "两次密码输入不一致"
          },
        },
          submitHandler:function(form){
              var url = "${fns:getConfig('service.url')}/dongyin-CRM/changePassword/modifyPwd";
              $.ajax({
                  url:url,
                  type:'GET',
                  data:{
                      id:'${id}',
                      new_password:$('#password').val(),
                  },
                  success:function(data){
                      typeof data != 'Object'&& (data = JSON.parse(data));
                      if(data.code=='10000'){
                          layer.open({
                              content:'重新设置密码成功!',
                              closeBtn:0,
                              yes:function(){
                                  window.location.href="${fns:getConfig('service.url')}/dongyin-CRM/";
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