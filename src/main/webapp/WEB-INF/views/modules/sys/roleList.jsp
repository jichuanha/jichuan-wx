]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
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
	</style>
</head>
<body>
	<ul class="nav my-nav-tabs clearfix">
		<li class="active"><a href="${ctx}/sys/role/">角色列表</a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a href="${ctx}/sys/role/form">角色添加</a></li></shiro:hasPermission>
	</ul>
	<p class="h3-title"><i class="h3-deco"></i>角色列表</p>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr><th>角色名称</th><th>角色描述</th><th>角色人数</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="role">
			<tr>
				<td><a href="form?id=${role.id}">${role.name}</a></td>
				<td><a href="#">${role.remarks}</a></td>
				<td>${role.number}</td>
				<shiro:hasPermission name="sys:role:edit"><td>
					<a href="${ctx}/sys/role/assign?id=${role.id}">分配</a>
					<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
						<a href="${ctx}/sys/role/form?id=${role.id}">修改</a>
					</c:if>
					<a href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)">删除</a>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
</body>
</html>