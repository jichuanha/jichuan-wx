<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>活动管理管理</title>
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
		<li class="active"><a href="${ctx}/activity/activity/">活动管理列表</a></li>
		<shiro:hasPermission name="activity:activity:edit"><li><a href="${ctx}/activity/activity/form">活动管理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="activity" action="${ctx}/activity/activity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>活动表：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>活动名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动有效时间：</label>
				<input name="effectiveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activity.effectiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>订单时效时间：</label>
				<input name="orderAgingTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activity.orderAgingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>url：</label>
				<form:input path="url" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>关注类型  0 -需要 1-不需要：</label>
				<form:input path="attentionType" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>返利类型 ： 1-固定金额：</label>
				<form:input path="rebateType" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>返利渠道 1-红包领取：</label>
				<form:input path="rebateChannel" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>单笔金额 单位：分：</label>
				<form:input path="singleAmount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>订单总数：</label>
				<form:input path="totalOrder" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>返利总额 单位分：</label>
				<form:input path="totalRebate" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否需要人工审核 ： 0 -不需要 1-需要：</label>
				<form:input path="isAudit" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>店铺名称：</label>
				<form:input path="shopName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>店铺编号：</label>
				<form:input path="shopNo" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>模板url：</label>
				<form:input path="templateLink" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>活动状态 ：0- 未开始  1-进行中 2-已结束：</label>
				<form:input path="status" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>删除标识：</label>
				<form:radiobuttons path="delFlag" items="${fns:getDictList('del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li><label>create_by：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>create_date：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>update_by：</label>
				<form:input path="updateBy.id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>update_date：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${activity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
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
				<th>活动表</th>
				<th>活动名称</th>
				<th>活动有效时间</th>
				<th>订单时效时间</th>
				<th>url</th>
				<th>关注类型  0 -需要 1-不需要</th>
				<th>返利类型 ： 1-固定金额</th>
				<th>返利渠道 1-红包领取</th>
				<th>单笔金额 单位：分</th>
				<th>订单总数</th>
				<th>返利总额 单位分</th>
				<th>是否需要人工审核 ： 0 -不需要 1-需要</th>
				<th>店铺名称</th>
				<th>店铺编号</th>
				<th>模板url</th>
				<th>活动状态 ：0- 未开始  1-进行中 2-已结束</th>
				<th>删除标识</th>
				<th>create_by</th>
				<th>create_date</th>
				<th>update_by</th>
				<th>update_date</th>
				<shiro:hasPermission name="activity:activity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="activity">
			<tr>
				<td><a href="${ctx}/activity/activity/form?id=${activity.id}">
					${activity.id}
				</a></td>
				<td>
					${activity.name}
				</td>
				<td>
					<fmt:formatDate value="${activity.effectiveTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${activity.orderAgingTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activity.url}
				</td>
				<td>
					${activity.attentionType}
				</td>
				<td>
					${activity.rebateType}
				</td>
				<td>
					${activity.rebateChannel}
				</td>
				<td>
					${activity.singleAmount}
				</td>
				<td>
					${activity.totalOrder}
				</td>
				<td>
					${activity.totalRebate}
				</td>
				<td>
					${activity.isAudit}
				</td>
				<td>
					${activity.shopName}
				</td>
				<td>
					${activity.shopNo}
				</td>
				<td>
					${activity.templateLink}
				</td>
				<td>
					${activity.status}
				</td>
				<td>
					${fns:getDictLabel(activity.delFlag, 'del_flag', '')}
				</td>
				<td>
					${activity.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${activity.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${activity.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${activity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="activity:activity:edit"><td>
    				<a href="${ctx}/activity/activity/form?id=${activity.id}">修改</a>
					<a href="${ctx}/activity/activity/delete?id=${activity.id}" onclick="return confirmx('确认要删除该活动管理吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>