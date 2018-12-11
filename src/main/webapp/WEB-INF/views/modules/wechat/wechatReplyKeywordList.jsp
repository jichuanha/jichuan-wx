<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信自动回复关键词管理</title>
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
		<li class="active"><a href="${ctx}/wechat/wechatReplyKeyword/">微信自动回复关键词列表</a></li>
		<shiro:hasPermission name="wechat:wechatReplyKeyword:edit"><li><a href="${ctx}/wechat/wechatReplyKeyword/form">微信自动回复关键词添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatReplyKeyword" action="${ctx}/wechat/wechatReplyKeyword/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>id：</label>
				<form:input path="id" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>微信ID：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>规则ID：</label>
				<form:input path="ruleId" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>关键词类型： 0-半匹配  1-全匹配：</label>
				<form:input path="keywordType" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>关键词：</label>
				<form:input path="keyword" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>del_flag：</label>
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
				<th>id</th>
				<th>微信ID</th>
				<th>规则ID</th>
				<th>关键词类型： 0-半匹配  1-全匹配</th>
				<th>关键词</th>
				<th>create_by</th>
				<th>create_date</th>
				<th>update_by</th>
				<th>update_date</th>
				<th>del_flag</th>
				<shiro:hasPermission name="wechat:wechatReplyKeyword:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatReplyKeyword">
			<tr>
				<td><a href="${ctx}/wechat/wechatReplyKeyword/form?id=${wechatReplyKeyword.id}">
					${wechatReplyKeyword.id}
				</a></td>
				<td>
					${wechatReplyKeyword.wechatId}
				</td>
				<td>
					${wechatReplyKeyword.ruleId}
				</td>
				<td>
					${wechatReplyKeyword.keywordType}
				</td>
				<td>
					${wechatReplyKeyword.keyword}
				</td>
				<td>
					${wechatReplyKeyword.createBy.id}
				</td>
				<td>
					${wechatReplyKeyword.createDate}
				</td>
				<td>
					${wechatReplyKeyword.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${wechatReplyKeyword.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(wechatReplyKeyword.delFlag, 'del_flag', '')}
				</td>
				<shiro:hasPermission name="wechat:wechatReplyKeyword:edit"><td>
    				<a href="${ctx}/wechat/wechatReplyKeyword/form?id=${wechatReplyKeyword.id}">修改</a>
					<a href="${ctx}/wechat/wechatReplyKeyword/delete?id=${wechatReplyKeyword.id}" onclick="return confirmx('确认要删除该微信自动回复关键词吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>