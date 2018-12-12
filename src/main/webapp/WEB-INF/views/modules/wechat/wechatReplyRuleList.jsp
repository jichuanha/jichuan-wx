<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信自动回复规则管理</title>
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
		<li class="active"><a href="${ctx}/wechat/wechatReplyRule/">微信自动回复规则列表</a></li>
		<shiro:hasPermission name="wechat:wechatReplyRule:edit"><li><a href="${ctx}/wechat/wechatReplyRule/form">微信自动回复规则添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatReplyRule" action="${ctx}/wechat/wechatReplyRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>id：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>公众号ID：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>回复：0-关闭回复 1.开启回复：</label>
				<form:input path="status" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>规则名称：</label>
				<form:input path="ruleName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类型：1-关键词回复 2-收到消息回复  3-被关注回复：</label>
				<form:input path="ruleType" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>备注：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>回复方式：1-全部回复 2-随机回复一条 关键字回复不能为空：</label>
				<form:input path="replyWay" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>0.存在1.删除：</label>
				<form:input path="deleted" htmlEscape="false" maxlength="4" class="input-medium"/>
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
				<th>公众号ID</th>
				<th>回复：0-关闭回复 1.开启回复</th>
				<th>规则名称</th>
				<th>类型：1-关键词回复 2-收到消息回复  3-被关注回复</th>
				<th>备注</th>
				<th>回复方式：1-全部回复 2-随机回复一条 关键字回复不能为空</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>创建者</th>
				<th>修改者</th>
				<th>0.存在1.删除</th>
				<shiro:hasPermission name="wechat:wechatReplyRule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatReplyRule">
			<tr>
				<td><a href="${ctx}/wechat/wechatReplyRule/form?id=${wechatReplyRule.id}">
					${wechatReplyRule.id}
				</a></td>
				<td>
					${wechatReplyRule.wechatId}
				</td>
				<td>
					${wechatReplyRule.status}
				</td>
				<td>
					${wechatReplyRule.ruleName}
				</td>
				<td>
					${wechatReplyRule.ruleType}
				</td>
				<td>
					${wechatReplyRule.remarks}
				</td>
				<td>
					${wechatReplyRule.replyWay}
				</td>
				<td>
					<fmt:formatDate value="${wechatReplyRule.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${wechatReplyRule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wechatReplyRule.creator}
				</td>
				<td>
					${wechatReplyRule.updator}
				</td>
				<td>
					${wechatReplyRule.deleted}
				</td>
				<shiro:hasPermission name="wechat:wechatReplyRule:edit"><td>
    				<a href="${ctx}/wechat/wechatReplyRule/form?id=${wechatReplyRule.id}">修改</a>
					<a href="${ctx}/wechat/wechatReplyRule/delete?id=${wechatReplyRule.id}" onclick="return confirmx('确认要删除该微信自动回复规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>