<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- Note there is no responsive meta tag here -->
	<link rel="icon" href="../../favicon.ico">
	<title>限时购</title>
	<!-- Bootstrap core CSS -->
	<link href="/jeesite/static/res/libs/bootstrap-3.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="/jeesite/static/res/libs/jquery.page/jquery.page.css" rel="stylesheet">
	<!-- Custom styles for this template -->
	<link href="/jeesite/static/res/css/non-responsive.css" rel="stylesheet">
	<link rel="stylesheet" href="/jeesite/static/res/libs/jquery.ui/jquery-ui.css" type="text/css">
	<link rel="stylesheet" href="/jeesite/static/res/libs/jquery.ui/jquery-ui-timepicker-addon.css" type="text/css">
	<link href="/jeesite/static/res/css/time-purchase-new.css?v=1.0.9" rel="stylesheet">
	<link href="/jeesite/static/res/libs/Font-Awesome-3.2.1/css/font-awesome.min.css" rel="stylesheet">
	<script src="/jeesite/static/res/js/jquery-1.10.2.js"></script>
	<script src="/jeesite/static/res/libs/bootstrap-3.3.0/dist/js/bootstrap.min.js"></script>
	<script src="/jeesite/static/res/libs/jquery.page/jquery.page.js"></script>
	<script src="/jeesite/static/res/libs/doT-master/doT.min.js"></script>
	<script src="/jeesite/static/res/libs/underscore/underscore.min.js"></script>
	<script src="/jeesite/static/res/libs/jquery.ui/jquery-ui.min.js"></script>
	<script src="/jeesite/static/res/libs/layer/layer.js"></script>
	<script src="/jeesite/static/res/libs/jquery.ui/jquery-ui-timepicker-addon.js"></script>
	<script src="/jeesite/static/res/libs/jquery.ui/jquery-ui-timepicker-zh-CN.js"></script>
	<script src="/jeesite/static/res/bundle/time-purchase-edit-entry.js?v=1.0.9"></script>
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
				<a class="navbar-brand" href="#">限时购</a>
			</div>
		</div>
	</nav>
	<div class="container whole" id="edit-campaign-container">

	</div>
	<!-- /container -->

<div class="modal fade select-goods-modal" id="select-goods-modal">
  <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<span>全部商品&nbsp;&nbsp;</span>
		<span class="checkbox">
			<label>
			  <input type="checkbox" id="isOnSale"> 只显示上架商品
			</label>
		</span>
	  </div>
	  <div class="modal-body">
		<div class="filter-condition">
			<div class="form-group">
				<select class="form-control form-control-sm" id="cate_key">
					<option value="">请选择类目</option>
				</select>
			</div>
			<div class="form-group" style="display: none;">
				<select class=" form-control form-control-md" id="category_two">
					<option value="">请选择二级类目</option>
				</select>
			</div>
			<div class="form-group">
				<select class="form-control form-control-sm" id="brand_key">
					<option value="">请选择品牌</option>
				</select>
			</div>
			<div class="form-group">
				<label class="sr-only"></label>
				<input type="text" class="form-control" placeholder="关键字" id="keyword-input">
			</div>
			<button class="btn btn-default" id="seach-bth">搜索</button>
		</div>
		<div class="goods-list">
		<table class="table table-striped goods-table">
		  <thead>
			<tr>
			  <th>图片</th>
			  <th>标题</th>
			  <th>创建时间</th>
			  <th>状态</th>
			  <th>类型</th>
			  <th>操作</th>
			</tr>
		  </thead>
		  <tbody id="j-render-goods"></tbody>
		</table>
		</div>
	  </div>
	  <div class="modal-footer">
		<button type="button" class="btn btn-default select-all" id="select-all">全选本页</button>
		<button type="button" class="btn btn-primary confirm-select" id="confirm-select">确定使用</button>
		<div class="tcdPageCode goods-list-pager" id="goods-list-pager" data-pagertype="goods"></div>
	  </div>
	</div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade select-icon-modal" id="select-icon-modal">
  <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	  </div>
	  <div class="modal-body">
		<div class="filter-condition">
			<div class="form-group">
				<label class="sr-only"></label>
				<input type="text" class="form-control" placeholder="请输入角标名称" id="keyword-icon-input">
			</div>
			<button class="btn btn-success" id="icon-seach-bth">搜索</button>
		</div>
		<ul class="mark-icon-list" id="mark-icon-list">
		
		</ul>
	  </div>
	  <div class="modal-footer">
		<button type="button" class="btn btn-default select-all" id="icon-confirm-select">确定</button>
		<button type="button" class="btn btn-primary confirm-select" data-dismiss="modal">取消</button>
		<div class="tcdPageCode icon-list-pager" id="icon-list-pager" data-pagertype="mark"></div>
	  </div>
	</div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 渲染类目 -->
<script type="text/template" id="j-template-category">
	{{ if( it.category_value == 1 ){ }}
	<option>--请选择一级类目--</option>
	{{ }else{ }}
	<option>--请选择二级类目--</option>
	{{ } }}
	{{~ it.items :item:index }}
	<option value="{{= item.id }}" data-name="{{= item.cate_name }}">{{= item.cate_name }}</option>
	{{~ }}
</script>

<!-- 根据type类型进行判断：type=1选择的是sku级别，没有type选择的是商品级别 -->
<script type="text/template" id="j-template-goods">
	{{~ it.items:item:index }}
	<tr class="j-goods-box">
		<td class="tc"><span><img src="{{= item.icon_url }}" style="width: 50px;height: 50px;"></span></td>
		<td><span class="popup-g-name">{{= item.item_name }}</span></td>
		<td class="tc"><span>{{= item.gmt_created }}</span></td>
		<td class="tc">
			{{ if( item.item_status == 4 ){ }}
				<span class="green">{{= item.status_name || '-' }}</span>
			{{ }else if( item.item_status == 1 ){ }}
				<span class="red">{{= item.status_name || '-' }}</span>
			{{ }else{ }}
				<span class="black">{{= item.status_name || '-' }}
			</span>
			{{ } }}
		</td>
		<td class="tc">{{= ['国内','跨境'][item.higo_mark] }}</td>
		{{ if( it.type && it.type == 1 ){ }}
		<td><a class="j-open-sku" data-id="{{= item.id }}" data-name="{{= item.item_name }}" data-sid="{{= item.seller_id }}" style="cursor: pointer">展开并添加</a></td>
		{{ }else{ }}
		<td><a class="select-goods select-group-goods j-select-g" data-status="0" data-id="{{= item.id }}" data-info="{{= encodeURIComponent(JSON.stringify(item)) }}">选择</a></td>
		{{ } }}
	</tr>
	<tr class="j-sku-box" style="display: none;" id="sku-box-{{= item.id }}">
		<td colspan="6">
			<table class="ui-table">
				<thead>
				<tr>
					<th class="tc">图片</th>
					<th class="tc">sku_id</th>
					<th class="tc">sku属性</th>
					<th class="tc">原价</th>
					<th class="tc">现价</th>
					<th class="tc">库存</th>
					<th class="tc">操作</th>
				</tr>
				</thead>
				<tbody id="sku-item-{{= item.id }}">

				</tbody>
			</table>
		</td>
	</tr>
	{{~ }}
</script>
</body>
</html>
