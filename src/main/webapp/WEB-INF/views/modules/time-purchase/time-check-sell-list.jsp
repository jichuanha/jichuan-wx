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
		<link rel="stylesheet" type="text/css" href="/jeesite/static/res/css/time-check-sell-list.css?v=1.0.9">
		<link href="/jeesite/static/res/libs/Font-Awesome-3.2.1/css/font-awesome.min.css" rel="stylesheet">
		<link href="/jeesite/static/res/libs/jquery.page/jquery.page.css" rel="stylesheet">
	</head>
	<body>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">限时购销售列表</a>
				</div>
			</div>
		</nav>
		<div class="container">
    		<form class="form-inline form-new" role="form">
    		  <button type="button" class="btn btn-default"><a href="time-purchase-list">返回</a></button>
			  <!-- <button type="button" id="search" class="btn btn-default btn-rigth">搜索</button>
			  <input type="text" class="form-control  btn-rigth" id="inputConditions" placeholder="请输入限时购商品名称"> -->
			</form>
			<div class="table-div">
				<table class="table table-striped  table-hover table-time" id="tableSell">
					<thead>
						<tr>
							<th>编号</th>
							<th>商品名/规格</th>
							<th>商品编码</th>
							<th>订单编码</th>
							<th>支付时间</th>
							<th>活动价</th>
							<th>购买数量</th>
							<th>下单账号</th>
							<th>分享人</th>
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
            	<td>{{= i}}</td>
				<td>{{= it.data[i].goods_name}}</td>
				<td>{{= it.data[i].goods_id}}</td>
				<td>{{= it.data[i].order_sn}}</td>
				<td>{{= it.data[i].pay_time}}</td>
				<td>{{= it.data[i].limited_price/100}}</td>
                <td>{{= it.data[i].goods_num}}</td>
                <td>{{= it.data[i].user_phone || '-'}}</td>
                <td>{{= it.data[i].share_phone || '-'}}</td>
			</tr>
            {{ } }}
	    </script>
		<script src="/jeesite/static/res/js/jquery-1.10.2.js"></script>
		<script src="/jeesite/static/res/js/doT.min.js"></script>
		<script src="/jeesite/static/res/libs/bootstrap-3.3.0/dist/js/bootstrap.min.js"></script>
		<script src="/jeesite/static/res/libs/jquery.page/jquery.page.js"></script>
		<script src="/jeesite/static/res/bundle/time-check-sell-list-entry.js?v=1.0.9"></script>
	</body>
</html>
