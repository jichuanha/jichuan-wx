<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抽奖记录管理</title>
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
		<li class="active"><a href="${ctx}/mobile/lotteryRecord/">抽奖记录列表</a></li>
		<shiro:hasPermission name="mobile:lotteryRecord:edit"><li><a href="${ctx}/mobile/lotteryRecord/form">抽奖记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="lotteryRecord" action="${ctx}/mobile/lotteryRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>抽奖记录id：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>奖品配置Id：</label>
				<form:input path="awardId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>活动id：</label>
				<form:input path="activityId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>activity_type：</label>
				<form:input path="activityType" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>是否中奖：0、未中奖，1、中奖：</label>
				<form:input path="lotteryType" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>红包是否发放：0、未发放，1、发放：</label>
				<form:input path="issueType" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>是否发放成功：0、失败，1、成功：</label>
				<form:input path="issueSuccess" htmlEscape="false" maxlength="8" class="input-medium"/>
			</li>
			<li><label>openId：</label>
				<form:input path="openId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>红包金额：</label>
				<form:input path="lotteryAmount" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<shiro:hasPermission name="mobile:lotteryRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lotteryRecord">
			<tr>
				<shiro:hasPermission name="mobile:lotteryRecord:edit"><td>
    				<a href="${ctx}/mobile/lotteryRecord/form?id=${lotteryRecord.id}">修改</a>
					<a href="${ctx}/mobile/lotteryRecord/delete?id=${lotteryRecord.id}" onclick="return confirmx('确认要删除该抽奖记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>