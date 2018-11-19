/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ({

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(24);


/***/ },

/***/ 3:
/***/ function(module, exports) {

	module.exports = {
		"api_path": "/jeesite/a",
		"preview_path":"http://m.leaf.com/"}

/***/ },

/***/ 22:
/***/ function(module, exports) {

	var self_tpl = {
		 //品牌列表
		'brand_key': '<option value="">请选择品牌</option>{{~it.data:val:inx}}<option value="{{=val.id}}">{{=val.brand_name}}</option>{{~}}',
		//类目列表
		'cate_key': '<option value="">请选择类目</option>{{~it.data:val:inx}}<option value="{{=val.id}}">{{=val.cate_name}}</option>{{~}}',
		//角标
		'markTpl': '{{~ it.data.data:item }}\
			<li>\
				<div class="img" data-id="{{= item.id }}" data-url="{{= item.icon_url }}"><img src="{{= item.icon_url }}"></div>\
				<p class="name">{{= item.icon_name }}</p>\
			</li>\
			{{~}}',
		'goodsTpl': '{{~ it:item:index }}\
						<tr class="parent-tr" data-item_id="{{= item.id }}">\
							<td>\
								<div class="name" style="margin-top: {{= (item.sku_list.length*80+20-114)/2 }}px;">\
									<img src="{{= item.icon_url }}">\
									<p>{{= item.item_name }}</p>\
									<p>贸易类型：{{? item.higo_mark == 0 }}国内商品{{?? item.higo_mark == 1 }}跨境商品{{?}}</p>\
									{{? item.item_status == 4 }}\
										<p>当前状态：上架</p>\
									{{?? item.item_status == 5 }}\
										<p>当前状态：下架</p>\
									{{?? item.item_status == 7 }}\
										<p>当前状态：预售</p>\
									{{?}}\
								</div>\
							</td>\
							<td colspan="1">\
								<table class="child-table">\
									<thead>\
										<tr>\
											<th class="tc">限购</th>\
											<th class="tc">规格</th>\
											<th class="tc">价格</th>\
										</tr>\
									</thead>\
									<tbody>\
										{{~ item.sku_list:skuItem }}\
											<tr class="child-tr" data-sku_id="{{= skuItem.id }}">\
												<td>\
													<input type="text" class="limit-number" data-stock_num="{{= skuItem.stock_num }}"/>\
													<p>0为不限</p>\
													<p>当前库存:{{= skuItem.stock_num }}</p>\
												</td>\
												<td><p>规格型号：{{= skuItem.sku_code || "-"}}</p></td>\
												<td class="price-td">\
													<input type="text" class="limit-price"/>\
													<p>现售价：￥{{= skuItem.promotion_price/100 }}</p>\
												</td>\
											</tr>\
										{{~}}\
									</tbody>\
								</table>\
							</td>\
							<td><i class="icon-remove icon-large delete-icon" style="margin-top: {{= (item.sku_list.length*100+40-20)/2 }}px;"></i></td>\
						</tr>\
					{{~}}',
		'goodsEditTpl': '{{~ it:item:index }}\
								<tr class="parent-tr" data-item_id="{{= item.item_id }}">\
									<td>\
										<div class="name" style="margin-top: {{= (item.sku_info.length*80+20-114)/2 }}px;">\
											<img src="{{= item.map.spu.icon_url }}">\
											<p>{{= item.map.spu.item_name }}</p>\
											<p>贸易类型：{{? item.map.spu.higo_mark == 0 }}国内商品{{?? item.map.spu.higo_mark == 1 }}跨境商品{{?}}</p>\
											{{? item.map.spu.item_status == 4 }}\
												<p>当前状态：上架</p>\
											{{?? item.map.spu.item_status == 5 }}\
												<p>当前状态：下架</p>\
											{{?? item.map.spu.item_status == 7 }}\
												<p>当前状态：预售</p>\
											{{?}}\
										</div>\
									</td>\
									<td colspan="1">\
										<table class="child-table">\
											<thead>\
												<tr>\
													<th class="tc">限购</th>\
													<th class="tc">规格</th>\
													<th class="tc">价格</th>\
												</tr>\
											</thead>\
											<tbody>\
												{{~ item.sku_info:skuItem }}\
													<tr class="child-tr" data-sku_id="{{= skuItem.sku_id }}">\
														<td>\
															<input type="text" class="limit-number" data-stock_num="{{= skuItem.stock_num }}" value="{{= skuItem.goods_quantity }}"/>\
															<p>0为不限</p>\
															<p>当前库存:{{= skuItem.stock_num }}</p>\
														</td>\
														<td>规格：{{= skuItem.sku_code || "-"}}</td>\
														<td class="price-td">\
															<input type="text" value="{{= skuItem.goods_price/100 }}" class="limit-price"/>\
															<p>现售价：￥{{= skuItem.promotion_price/100 }}</p>\
														</td>\
													</tr>\
												{{~}}\
											</tbody>\
										</table>\
									</td>\
									<td><i class="icon-remove icon-large delete-icon" style="margin-top: {{= (item.sku_info.length*100+40-20)/2 }}px;"></i></td>\
								</tr>\
							{{~}}',
		'editTpl':  '<div class="content">\
						<form class="form-horizontal" role="form">\
							<div class="form-group">\
								<label for="campaign-name" class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动名称：</label>\
								<div class="col-xs-5">\
									<input type="text" class="form-control" id="campaign-name" placeholder="" maxlength="50" data-campaign_id="{{= it.id }}" value="{{= it.activity_name }}">\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动时间：</label>\
								<div class="col-xs-3">\
									<input type="text" class="form-control"  placeholder="" id="campaign-start-time" value="{{= it.start_time }}">\
								</div>\
								<div class="col-xs-1 col-to">\
									<span class="form-control to" >-</span>\
								</div>\
								<div class="col-xs-3">\
									<input type="text" class="form-control" placeholder="" id="campaign-end-time" value="{{= it.end_time }}">\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动角标：</label>\
								<div class="col-xs-3">\
									<div class="img-container" id="add-icon-btn">\
										<img src="{{= it.activity_tag }}" class="img-circle">\
									</div>\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label">与优惠券叠加：</label>\
								<div class="col-xs-1">\
									<div class="radio">\
										<label>\
											<input type="radio" name="coupon-radio" value="0" {{? !it.voucher_type}} checked {{?}}>是\
										</label>\
									</div>\
								</div>\
								<div class="col-xs-1">\
									<div class="radio">\
										<label>\
											<input type="radio" name="coupon-radio"  value="1" {{? it.voucher_type}} checked {{?}}>否\
										</label>\
									</div>\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动商品：</label>\
								<div class="col-xs-2 add-link-goods-col">\
									<div class="form-control" id="add-goods-btn">\
										<i class="icon-plus"></i>添加关联商品\
									</div>\
								</div>\
							</div>\
							<div class="form-group selected-goods" id="selected-goods">\
								<table class="table parent-table">\
									<thead>\
										<tr>\
											<th>名称</th>\
											<th>SKU</th>\
											<th>操作</th>\
										</tr>\
									</thead>\
									<tbody id="goods-info-body">\
									</tbody>\
								</table>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2">&nbsp;</label>\
								<div class="col-xs-3">\
									<button type="button" class="btn btn-success" id="save-selected-btn">保存</button>\
									<button type="button" class="btn btn-default" id="cancel-selected-btn">返回</button>\
								</div>\
							</div>\
						</form>\
					</div>'
	}
	module.exports = self_tpl;

/***/ },

/***/ 24:
/***/ function(module, exports, __webpack_require__) {

	var self_tpl = __webpack_require__(22);
	var api_path = __webpack_require__(3).api_path;
	$(function (){
		var parm = {
			"status": "",
			"page_size":20,
			"active_name":"",
			"current_page": 1,
			"page_count": 1
		};
		var count = 0;
		function timePurchaseListData(){
			$.post(api_path + '/marketing/limtedPurchase/list.do',{
				// 查询条件
				run_status: parm.status,
				activity_name: parm.active_name,
				current_page: parm.current_page,
				page_size: parm.page_size
			}, function(result){
				count++;
				var interText =doT.template($("#act-template").text());
				$("#interpolation").html(interText(result.data));
				parm.page_count = result.data.total_count/parm.page_size;
				if(count == 1){
					pager(Math.ceil(parm.page_count));
				};
			});
		};
		timePurchaseListData(); // 参数1：展示的第一页 参数23：搜索条件
		function pager(pageCount) {
			$(".tcdPageCode").createPage({
				pageCount:	pageCount,
				current: parm.current_page,
				backFn:function(currentPage){
					parm.current_page = currentPage;
					timePurchaseListData();
				}
			});
		};

		// 点击叉号消失提示
		$('.close').on('click',function(){
			$(".close").parent().hide();
		});

		//处理选项卡
		$('.btn-tab span').on('click',function(){
			$('.btn-tab span').removeClass("clicked");
			$('.btn-tab span').css({
				"background-color":'#fff',
				"border-bottom":"1px solid #383838"
			});
			$(this).css({
				"background-color":'#e6e6e6',
				"border-bottom":"1px solid #969696"
			});
			parm.status = $(this).index() - 1;
			if(parm.status == -1){
				count = 0;
				$("#inputConditions").val("");
				parm.status = "";
			};
			parm.active_name = $("#inputConditions").val();
			parm.conditions = $("#inputConditions").val();
			parm.currentPage = 1;
			timePurchaseListData();
		});

		$(".container .first").css({
			"background-color":'#e6e6e6',
			"border-bottom":"1px solid #969696"
		});

		$('#search').on('click',function(){
			count = 0;
			parm.active_name = $("#inputConditions").val();
			parm.currentPage = 1;
			timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
		});
		$(".container").on("click",".avtive-disable",function(){
			var parm = $(this).parent().attr("data-id");
			layer.confirm('活动正在前台展示，确定要结束此活动？', {
				icon: 3,
				title: '提示'
			}, function(index) {
				$.post(api_path + '/marketing/limtedPurchase/stop.do', {id:parm}, function(data){
					if(data.code == 10000){
						count = 0;
						layer.msg('活动撤销成功');
						timePurchaseListData();
					} else {
						layer.msg('活动撤销失败');
					}
				});
				layer.close(index);
			});
		});
		$(".table-div").on("click",".publish-selected-btn",function(){
			var _this = this;
			layer.confirm('确定要发布此活动？', {
				icon: 3,
				title: '提示'
			},
			function(index) {
				var activeId = $(_this).attr("active-id");
				$.get(api_path + '/marketing/limtedPurchase/startLimitedPurchase.do', {id:activeId}, function(data){
					if(data.data == true){
						count = 0;
						timePurchaseListData();
						layer.msg('活动发布成功');
					}else{
						layer.msg(data.msg);
					};
				});
				layer.close(index);
			});
		});
	});



/***/ }

/******/ });