<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动类型管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/activity/tActivityType/">活动类型列表</a></li>
		<shiro:hasPermission name="activity:tActivityType:edit"><li><a href="${ctx}/activity/tActivityType/form">活动类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tActivityType" action="${ctx}/activity/tActivityType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动类型id：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>活动类型名称：</label>
				<form:input path="typeName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>注释：</label>
				<form:input path="remark" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>del_flag：</label>
				<form:radiobuttons path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li><label>create_by：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>update_by：</label>
				<form:input path="updateBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>create_date：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tActivityType.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>update_date：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tActivityType.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动类型id</th>
				<th>活动类型名称</th>
				<th>注释</th>
				<th>del_flag</th>
				<th>create_by</th>
				<th>update_by</th>
				<th>create_date</th>
				<th>update_date</th>
				<shiro:hasPermission name="activity:tActivityType:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tActivityType">
			<tr>
				<td><a href="${ctx}/activity/tActivityType/form?id=${tActivityType.id}">
					${tActivityType.id}
				</a></td>
				<td>
					${tActivityType.typeName}
				</td>
				<td>
					${tActivityType.remark}
				</td>
				<td>
					${fns:getDictLabel(tActivityType.delFlag, 'del_flag', '')}
				</td>
				<td>
					${tActivityType.createBy.id}
				</td>
				<td>
					${tActivityType.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${tActivityType.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${tActivityType.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="activity:tActivityType:edit"><td>
    				<a href="${ctx}/activity/tActivityType/form?id=${tActivityType.id}">修改</a>
					<a href="${ctx}/activity/tActivityType/delete?id=${tActivityType.id}" onclick="return confirmx('确认要删除该活动类型吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>