<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
      html,body,table{background-color:#f5f5f5;width:100%;text-align:center;}.form-signin-heading{font-family:Helvetica, Georgia, Arial, sans-serif, 黑体;font-size:36px;margin-bottom:20px;color:#0663a2;}
      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;} .form-signin #themeSwitch{position:absolute;right:15px;bottom:10px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:180px;padding-top:20px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      /*label.error{background:none;width:270px;font-weight:normal;color:inherit;margin:0;}*/
		body{
			background: url('${ctxStatic}/images/login/bg.jpg') no-repeat;
			background-size:cover;
			position: absolute;
			top: 0;
			left: 0;
			right: 0;
			bottom: 0;
			z-index: 2;
		}
	  #logo{
		  position: relative;
		  margin: 0 auto;
		  display: block;
		  margin-bottom: 30px;
		  z-index: 999;
          width: 200px;
	  }
	  #username,#password{
		  padding-left: 40px;
	  }
	  .usernameicon{
		  width: 20px;
		  height: 20px;
		  position: absolute;
		  left: 10px;
		  transform: translateY(50%);
		  background: url('${ctxStatic}/images/login/email.png') no-repeat;
	  }
	  .pswicon{
		  width: 20px;
		  height: 20px;
		  position: absolute;
		  left: 10px;
		  transform: translateY(50%);
		  background: url('${ctxStatic}/images/login/psw.png') no-repeat;
	  }
      .code_icon{
          width: 20px;
          height: 20px;
          position: absolute;
          left: 10px;
          transform: translateY(30%);
          background: url('${ctxStatic}/images/login/code.png') no-repeat;
      }
	  .footer{
		  color: #ffffff;
	  }
	  #login-btn{
		  display: block;
		  margin: 10px auto;
		  font-size: 16px;
		  width: 240px;
		  height: 40px;
	  }
		#foundPsw{float: right;color:#555;text-decoration: underline;}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
					<%--validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}--%>
				},
				messages: {
					username: {required: "请填写用户名"},password: {required: "请填写密码"},
					validateCode: {
					    // remote: "验证码不正确",
						required: "请填写验证码."}
				},
				// errorLabelContainer: "#messageBox",
				// errorPlacement: function(error, element) {
				// 	if($('input[name=validateCode]').val().length==4){
                 //        $('.validateCodeRefresh').click();
                 //    }
				// }
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">

	</div>
	<%--<h1 class="form-signin-heading">${fns:getConfig('productName')}</h1>--%>
	<form id="loginForm" class="form-signin" action="${ctx}/login" method="post">
		<img src="${ctxStatic}/images/login/logo.png" id="logo">
		<label class="input-label" for="username">登录名</label>
		<div style="position: relative">
			<div class="usernameicon"></div>
			<input type="text" id="username" name="username" class="input-block-level required" value="${username}">
		</div>
		<label class="input-label" for="password">密码</label>
		<div style="position: relative">
			<div class="pswicon"></div>
			<input type="password" id="password" name="password" class="input-block-level required">
		</div>
		<c:if test="${isValidateCodeLogin}"><div class="validateCode">
			<label class="input-label mid" for="validateCode">验证码</label>
			<sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;padding-left:40px;"/>
		</div></c:if><%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
		<div>
			<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
			<a id="foundPsw" href="${ctx}/changePassword/link_send_mail" target="_blank">找回密码</a>
		</div>
		<input class="btn btn-large btn-primary" type="submit" value="登 录" id="login-btn"/>&nbsp;&nbsp;

		<div  class=" ${empty message ? 'hide' : ''}">
			<label id="loginError" class="error">${message}</label>
		</div>
		<div id="themeSwitch" class="dropdown">
			<%--<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a>--%>
			<ul class="dropdown-menu">
			  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
			</ul>
			<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
		</div>
	</form>
	<div class="footer">
		Copyright &copy; 2018-${fns:getConfig('copyrightYear')}${fns:getConfig('productName')} - Powered By DONGYIN ${fns:getConfig('version')}
	</div>
	<script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script>
</body>
</html>