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
	<script src="/jeesite/static/res/libs/jquery.ui/jquery-ui-timepicker-addon.js"></script>
	<script src="/jeesite/static/res/libs/jquery.ui/jquery-ui-timepicker-zh-CN.js"></script>
	<script src="/jeesite/static/res/libs/layer/layer.js"></script>
	<script src="/jeesite/static/res/bundle/time-purchase-new-entry.js?v=1.0.9"></script>
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
	<div class="container whole">
		<div class="head-tips">
			<span>创建的限时购，买家购买相应商品时，满足条件自动享受活动价格。</span>
			<span class="remove"><i class="icon-remove" id="icon-remove"></i></span>
		</div>
		<div class="content">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="campaign-name" class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动名称：</label>
					<div class="col-xs-5">
						<input type="text" class="form-control" id="campaign-name" placeholder="" maxlength="50" data-campaign_id="">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动时间：</label>
					<div class="col-xs-3">
						<input type="text" class="form-control"  placeholder="" id="campaign-start-time">
					</div>
					<div class="col-xs-1 col-to">
						<!-- <input type="text" class="form-control to"  placeholder="" value="-"> -->
						<span class="form-control to" >-</span>
					</div>
					<div class="col-xs-3">
						<input type="text" class="form-control" placeholder="" id="campaign-end-time">
					</div>
				</div>
				<div class="form-group" style="display: none;">
					<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>发布时间：</label>
					<div class="col-xs-3">
						<input type="text" class="form-control"  placeholder="" id="publish-time">
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动角标：</label>
					<div class="col-xs-3">
						<div class="img-container" id="add-icon-btn">
							<!-- <img src="http://leafoss.leaf.com/images/20160815/09/20160815094046931.png" class="img-circle"> -->
							<i class="icon-plus"></i>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label">与优惠券叠加：</label>
					<div class="col-xs-1">
						<div class="radio">
							<label>
								<input type="radio" name="coupon-radio" value="0" checked>是
							</label>
						</div>
					</div>
					<div class="col-xs-1">
						<div class="radio">
							<label>
								<input type="radio" name="coupon-radio"  value="1">否
							</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动商品：</label>
					<div class="col-xs-2 add-link-goods-col">
						<div class="form-control" id="add-goods-btn">
							<i class="icon-plus"></i>添加关联商品
						</div>
					</div>
				</div>
				<div class="form-group selected-goods" id="selected-goods" style="display: none;">
					<table class="table parent-table">
						<thead>
							<tr>
								<th>名称</th>
								<th>SKU</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="goods-info-body">
						</tbody>
					</table>
				</div>
				<div class="form-group">
					<label class="col-xs-2">&nbsp;</label>
					<div class="col-xs-3">
						<button type="button" class="btn btn-success" id="save-selected-btn">保存</button>
						<button type="button" class="btn btn-default" id="cancel-selected-btn">取消</button>
					</div>
				</div>
			</form>
		</div>
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
			  <th class="col-xs-3">标题</th>
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
		<td class="col-xs-3"><span class="popup-g-name">{{= item.item_name }}</span></td>
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


<!-- 弹窗 展开商品对应的sku -->
<script type="text/template" id="j-template-sku">
	{{~ items:item:index }}
		<tr>
			<td class="tc"><img src="{{= item.image_url }}"></td>
			<td class="tc"><span>{{= item.sku_id %></span></td>
			<td class="tc"><span>{{= item.sku_code ? item.sku_code : '-' }}</span></td>
			<td class="tc"><span>{{= Number(item.origin_price/100).toFixed(2) }}</span></td>
			<td class="tc"><span>{{= Number(item.price/100).toFixed(2) }}</span></td>
			<td class="tc"><span>{{= item.num %></span></td>
			<td class="tc"><a href="javascript:;" class="j-s-sku" data-info="{{= encodeURIComponent(JSON.stringify(item)) }}">选择</a></td>
		</tr>
	{{~ }}
</script>
</body>
</html>
