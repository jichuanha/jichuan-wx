<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>幸运抽奖活动管理</title>
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
		<li class="active"><a href="${ctx}/activity/activityLottery/">幸运抽奖活动列表</a></li>
		<shiro:hasPermission name="activity:activityLottery:edit"><li><a href="${ctx}/activity/activityLottery/form">幸运抽奖活动添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="activityLottery" action="${ctx}/activity/activityLottery/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动表：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动类型：</label>
				<form:input path="activityType" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>活动状态 ：0- 未开始  1-进行中 2-暂停 3-已结束：</label>
				<form:input path="status" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>活动有效开始时间：</label>
				<input name="activeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activityLottery.activeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>活动失效时间：</label>
				<input name="inactiveDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activityLottery.inactiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>关注类型  0 -需要 1-不需要：</label>
				<form:input path="isFollow" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>验证方式 ： 1-手机号：</label>
				<form:input path="auditType" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>店铺编号：</label>
				<form:input path="shopNo" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>删除标识：</label>
				<form:radiobuttons path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>活动表</th>
				<th>活动名称</th>
				<th>活动类型</th>
				<th>活动状态 ：0- 未开始  1-进行中 2-暂停 3-已结束</th>
				<th>活动有效开始时间</th>
				<th>活动失效时间</th>
				<th>订单时效时间</th>
				<th>订单时效时间</th>
				<th>公众号id</th>
				<th>url</th>
				<th>关注类型  0 -需要 1-不需要</th>
				<th>验证方式 ： 1-手机号</th>
				<th>短信校验 0：否  1：是</th>
				<th>限制订单总数量 0表示不限制</th>
				<th>版本号</th>
				<th>店铺名称</th>
				<th>店铺编号</th>
				<th>模板url</th>
				<th>删除标识</th>
				<th>create_by</th>
				<th>create_date</th>
				<th>update_by</th>
				<th>update_date</th>
				<shiro:hasPermission name="activity:activityLottery:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="activityLottery">
			<tr>
				<td><a href="${ctx}/activity/activityLottery/form?id=${activityLottery.id}">
					${activityLottery.id}
				</a></td>
				<td>
					${activityLottery.name}
				</td>
				<td>
					${activityLottery.activityType}
				</td>
				<td>
					${activityLottery.status}
				</td>
				<td>
					<fmt:formatDate value="${activityLottery.activeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activityLottery.inactiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activityLottery.orderActiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activityLottery.orderInactiveDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activityLottery.wechatPlatformId}
				</td>
				<td>
					${activityLottery.url}
				</td>
				<td>
					${activityLottery.isFollow}
				</td>
				<td>
					${activityLottery.auditType}
				</td>
				<td>
					${activityLottery.textAuditType}
				</td>
				<td>
					${activityLottery.totalOrder}
				</td>
				<td>
					${activityLottery.version}
				</td>
				<td>
					${activityLottery.shopName}
				</td>
				<td>
					${activityLottery.shopNo}
				</td>
				<td>
					${activityLottery.templateLink}
				</td>
				<td>
					${fns:getDictLabel(activityLottery.delFlag, 'del_flag', '')}
				</td>
				<td>
					${activityLottery.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${activityLottery.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activityLottery.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${activityLottery.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="activity:activityLottery:edit"><td>
    				<a href="${ctx}/activity/activityLottery/form?id=${activityLottery.id}">修改</a>
					<a href="${ctx}/activity/activityLottery/delete?id=${activityLottery.id}" onclick="return confirmx('确认要删除该幸运抽奖活动吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>