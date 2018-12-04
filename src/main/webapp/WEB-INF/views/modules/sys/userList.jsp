<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<style>
		.my-nav-tabs{
			background-color: #F7F7F7;
			margin-bottom: 20px;
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
		.h3-title{
			font-size: 16px;
			color: #000;
		}
		.h3-deco{
			display: inline-block;
			width: 5px;
			height: 20px;
			background-color: #3F51B5;
			vertical-align: middle;
			margin-right: 20px;
			margin-left: 20px;
		}
        .my-table{
            width: 95%;
            margin: 0 auto;
            font-size: 15px;
        }
        .my-table th,.my-table td{
            font-size: 15px;
            line-height: 2.2;
            border: 1px solid #F7F7F7;
            text-align: center;
        }
        .my-table th{
            background-color: #F7F7F7;
            font-weight: 400;
        }
        .my-table td{
            border: 1px solid rgb(228, 228, 228);
            font-size: 14px;
        }
        .my-table th a,
        .my-table td a{
            color: #000;
        }
		.my-table td span{
			color: red;
		}
		.ul-form li{
			width: 33%;
		}
		.ul-form .btns {
			/*float: none;*/

		}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav my-nav-tabs clearfix">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>用户姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li><label>登录邮箱：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>

			<li><label>状    态：</label>
				<form:select id="loginFlag" path="loginFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:option value="0" label="禁用" htmlEscape="false"/>
					<form:option value="1" label="正常" htmlEscape="false"/>
					<%--<form:options items="${typeList}" htmlEscape="false"/>--%>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<p class="h3-title"><i class="h3-deco"></i>用户列表</p>
	<sys:message content="${message}"/>
	<table id="contentTable" class="my-table">
		<thead><tr><th class="name">用户姓名</th><th class="login_name">登录邮箱</th><th>创建时间</th><th>状态</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.name}</td>
				<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
				<td><fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${user.loginFlag eq 0?"<span>禁用</span>":"正常"}</td><%--
				<td>${user.roleNames}</td> --%>
				<shiro:hasPermission name="sys:user:edit"><td>
    				<a href="${ctx}/sys/user/form?id=${user.id}">修改</a>
					<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>