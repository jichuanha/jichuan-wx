<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>找回密码</title>
  <style>
    #found-block{
      width: 500px;
      margin: 0 auto;
      position: relative;
      top: 150px;
      background: #fff;
      padding: 50px;
      border-radius: 10px;
      text-align: center;
    }
    body{
        background: url('${fns:getConfig('service.url')}/dongyin-CRM/static/images/login/bg.jpg') no-repeat;
      background-size:cover;
    }    
    #code{
      width: 300px;
      display: block;
    }
    #verify_code{
      position: absolute;
      right: 0;
      top: 0;
      height: 34px;
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
    #found-block input{
      padding-left: 50px;
    }
    #doingEmail{
      background: url('./login/email.png')  20px 50%  no-repeat;
    }
    #password{
      background: url('./login/psw.png')  20px 50%  no-repeat;
    }
    #code{
      background: url('./login/code.png')  20px 50%  no-repeat;
    }
  </style>
</head>

<body>
  <div id="found-block">
    链接已过期，请重新发起邮件
  </div>
</body>
<script>

</script>

</html>