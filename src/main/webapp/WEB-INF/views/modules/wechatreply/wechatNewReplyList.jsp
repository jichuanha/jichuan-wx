<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信公众号自动回复管理</title>
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
		<li class="active"><a href="${ctx}/wechatreply/reply/">微信公众号自动回复列表</a></li>
		<shiro:hasPermission name="wechatreply:reply:edit"><li><a href="${ctx}/wechatreply/reply/form">微信公众号自动回复添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="reply" action="${ctx}/wechatreply/reply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>id：</label>
				<form:input path="id" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>开启：0-开启 1.关闭：</label>
				<form:input path="unOpen" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>类型 0.关键词回复 1-收到消息回复  2-被关注回复：</label>
				<form:input path="replyType" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>回复内容类型0.文字回复1.图文回复 2-语音 3-视频：</label>
				<form:input path="contentType" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>关键字类型1-半匹配 2 -全匹配：</label>
				<form:input path="keyType" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>关键字：</label>
				<form:input path="keywords" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>
			<li><label>1-全部回复 2-随机回复一条 关键字回复不能为空：</label>
				<form:input path="replyWay" htmlEscape="false" maxlength="1" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${reply.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${reply.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>0.存在1.删除：</label>
				<form:input path="deleted" htmlEscape="false" maxlength="4" class="input-medium"/>
			</li>
			<li><label>wechat_id：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>备注</th>
				<th>修改时间</th>
				<shiro:hasPermission name="wechatreply:reply:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reply">
			<tr>
				<td><a href="${ctx}/wechatreply/reply/form?id=${reply.id}">
					${reply.remarks}
				</a></td>
				<td>
					<fmt:formatDate value="${reply.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="wechatreply:reply:edit"><td>
    				<a href="${ctx}/wechatreply/reply/form?id=${reply.id}">修改</a>
					<a href="${ctx}/wechatreply/reply/delete?id=${reply.id}" onclick="return confirmx('确认要删除该微信公众号自动回复吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>