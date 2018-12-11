<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信自动回复内容管理</title>
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
		<li class="active"><a href="${ctx}/wechat/wechatReplyRuleContent/">微信自动回复内容列表</a></li>
		<shiro:hasPermission name="wechat:wechatReplyRuleContent:edit"><li><a href="${ctx}/wechat/wechatReplyRuleContent/form">微信自动回复内容添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wechatReplyRuleContent" action="${ctx}/wechat/wechatReplyRuleContent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>id：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>公众号ID：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>规则ID：</label>
				<form:input path="ruleId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>回复消息内容类型：0.文字回复1.图片 2-语音 3-视频 4-图文 5-自定义：</label>
				<form:input path="contentType" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>回复消息内容：</label>
				<form:input path="content" htmlEscape="false" class="input-medium"/>
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
				<th>规则ID</th>
				<th>回复消息内容类型：0.文字回复1.图片 2-语音 3-视频 4-图文 5-自定义</th>
				<th>回复消息内容</th>
				<th>创建时间</th>
				<th>修改时间</th>
				<th>创建者</th>
				<th>修改者</th>
				<th>0.存在1.删除</th>
				<shiro:hasPermission name="wechat:wechatReplyRuleContent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wechatReplyRuleContent">
			<tr>
				<td><a href="${ctx}/wechat/wechatReplyRuleContent/form?id=${wechatReplyRuleContent.id}">
					${wechatReplyRuleContent.id}
				</a></td>
				<td>
					${wechatReplyRuleContent.wechatId}
				</td>
				<td>
					${wechatReplyRuleContent.ruleId}
				</td>
				<td>
					${wechatReplyRuleContent.contentType}
				</td>
				<td>
					${wechatReplyRuleContent.content}
				</td>
				<td>
					<fmt:formatDate value="${wechatReplyRuleContent.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${wechatReplyRuleContent.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wechatReplyRuleContent.creator}
				</td>
				<td>
					${wechatReplyRuleContent.updator}
				</td>
				<td>
					${wechatReplyRuleContent.deleted}
				</td>
				<shiro:hasPermission name="wechat:wechatReplyRuleContent:edit"><td>
    				<a href="${ctx}/wechat/wechatReplyRuleContent/form?id=${wechatReplyRuleContent.id}">修改</a>
					<a href="${ctx}/wechat/wechatReplyRuleContent/delete?id=${wechatReplyRuleContent.id}" onclick="return confirmx('确认要删除该微信自动回复内容吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>