<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="description" content="">
		<meta name="author" content="">
		<!-- Note there is no responsive meta tag here -->
		<link rel="icon" href="/jeesite/static/res/favicon.ico">
		<title>Non-responsive Template for Bootstrap</title>
		<!-- Bootstrap core CSS -->
		<link href="/jeesite/static/res/libs/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
		<!-- Custom styles for this template -->
		<link href="/jeesite/static/res/css/non-responsive.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/jeesite/static/res/css/time-purchase-list.css?v=1.0.9">
		<link href="/jeesite/static/res/libs/Font-Awesome-3.2.1/css/font-awesome.min.css" rel="stylesheet">
		<link href="/jeesite/static/res/libs/jquery.page/jquery.page.css" rel="stylesheet">
	</head>
	<body>
		<!-- Fixed navbar -->
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">限时购列表页</a>
				</div>
			</div>
		</nav>
		<div class="container">
			<div class="prompt">
				<span class="span1">创建的限时购，买家购买相应商品，满足条件自动享受活动价格。</span>
				<i class=" icon-remove-circle close"></i>
			</div>
			<div class="btn-tab">
			  <span class="first">全部</span>
			  <span>未开始</span>
			  <span>进行中</span>
			  <span class="last">已结束</span>
			</div>
			<form class="form-inline form-new" role="form">
    		  <button type="button" class="btn btn-default"><a href="new">新建限时购</a></button>
			  <button type="button" id="search" class="btn btn-default btn-rigth">搜索</button>
			  <input type="text" class="form-control  btn-rigth" id="inputConditions" placeholder="请输入限时购活动名称">
			</form>
			<div class="table-div">
				<table class="table table-striped  table-hover table-time">
					<thead>
						<tr>
							<th>编号</th>
							<th>限时购名称</th>
							<th>有效时间</th>
							<th>活动状态</th>
							<th>发布状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="interpolation"></tbody>
				</table>
			</div>
			<div class="tcdPageCode"></div>
		</div> <!-- /container -->
		<script id="act-template" type="text/x-dot-template">
            {{ for(var i=0; i < it.data.length; i++) { }}
            <tr>
				<td>{{= i+1}}</td>
				<td>{{= it.data[i].activity_name}}</td>
				<td>{{= it.data[i].start_time}} 至 {{= it.data[i].end_time}}</td>
				<td >{{? it.data[i].run_status == 0 }}<span class="green">未开始</span>{{?? it.data[i].run_status == 1 }}<span class="black">进行中</span>{{??}}<span class="gray">已结束</span>{{?}}</td>
				
				<td >{{? it.data[i].issue_status == 0 }}<span class="green">未发布</span>{{?? it.data[i].issue_status == 1 }}<span class="black">已发布{{?}}</span></td>
               
                <td class="tc" data-id="{{= it.data[i].id}}"><a class="edit" href="edit?id={{= it.data[i].id}}&status={{= it.data[i].issue_status}}&run_status={{= it.data[i].run_status}}">查看与编辑</a>-{{? it.data[i].issue_status == 0 }}<a class="publish-selected-btn" active-id="{{= it.data[i].id}}">发布</a>-{{? }}{{? it.data[i].run_status == 0 }}<a class="avtive-disable">使失效</a>-{{?? it.data[i].run_status == 1 }}<a class="avtive-disable">使失效</a>-{{?}}<a href="time-check-sell-list?id={{= it.data[i].id}}">查看销售</a></td>
			</tr>
            {{ } }}
	    </script>
		<script src="/jeesite/static/res/js/jquery-1.10.2.js"></script>
		<script src="/jeesite/static/res/js/doT.min.js"></script>
		<script src="/jeesite/static/res/libs/bootstrap-3.3.0/dist/js/bootstrap.min.js"></script>
		<script src="/jeesite/static/res/libs/jquery.page/jquery.page.js"></script>
		<script src="/jeesite/static/res/libs/layer/layer.js"></script>
		<script src="/jeesite/static/res/bundle/time-purchase-list-entry.js?v=1.0.9"></script>
	</body>
</html>
