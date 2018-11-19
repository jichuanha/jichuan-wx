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

	module.exports = __webpack_require__(23);


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

/***/ 23:
/***/ function(module, exports, __webpack_require__) {

	var self_tpl = __webpack_require__(22);
	var api_path = __webpack_require__(3).api_path;
	$(function(){
		//商品弹窗
		var goodsFilter = {
			brand_key: '',
			category_id: '',
			current_page: 1,
			item_status: undefined,
			key: '',//搜索框
			page_size: 10,
			category_two_id: '',
			page_count: 1
		};
		//角标弹窗
		var iconFilter = {
			icon_name: '',
			page_size: 20,
			current_page: 1,
			page_count: 1
		};
		var activeId = ""; // 活动ID
		$(".container").on("click","#icon-remove",function(){
			$(".head-tips").hide();
		});
		//初始化限时购活动信息
		function GetQueryString(name){
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		};
		var status = GetQueryString("status");
		var run_status = GetQueryString("run_status");
		function init(){
			var parmId = GetQueryString("id");
			$.post(api_path + '/marketing/limtedPurchase/limitedPurchaseById.do', {id: parmId}, function(data){
				if(data.code == 10000){
					activeId = data.data.id;
					if(data.data.goods_info.length == 0){
						layer.msg("无关联商品");
					};
					var timepickerCfg = {
						minInterval: (1000 * 60),
						showSecond: true,
						timeFormat: 'HH:mm:ss',
						start: {
							minDate: new Date()
						},
						end: {}
					};
					$('#edit-campaign-container').html(doT.template(self_tpl.editTpl)(data.data));
					$('#goods-info-body').html(doT.template(self_tpl.goodsEditTpl)(data.data.goods_info));
					if(status == 1 || run_status == 2){
						$('#save-selected-btn').attr("disabled", true);
					};
					$('#publish-time').datetimepicker();
					$.timepicker.datetimeRange(
						$('#campaign-start-time'),
						$('#campaign-end-time'),
						timepickerCfg
					);
					bindEvent();
				} else{
					layer.msg(data.msg);
				}
				
			});
		};
		init();
		function getCate() {
			$.get(api_path + '/item/category/query.do', {parent_id: 0}, function(data){
				$('#cate_key').html(doT.template(self_tpl.cate_key)(data));
				// bindEvent();
			});
		};
		function getTwoCate(id) {
			$.get(api_path + '/item/category/query.do', {parent_id: id}, function(data){
				var interText = doT.template($("#j-template-category").text());
				$('#category_two').html(interText({
					items: data.data,
					category_value: 2
				}));
			});
		};

		function getBrand() {
			$.get(api_path + '/item/brand/query.do', {}, function(data){
				$('#brand_key').html(doT.template(self_tpl.brand_key)(data.data));
			});
		};

		var goods_count = 1;
		function getGoodsList() {
			$.get(api_path + '/item/query.do',
				{
					key: goodsFilter.key,
					brand_id: goodsFilter.brand_key,
					parent_category_id: goodsFilter.category_id,
					category_id: goodsFilter.category_two_id,
					item_status: goodsFilter.item_status,
					current_page: goodsFilter.current_page,
					page_size: goodsFilter.page_size,
					item_type: 1
				},
				function(data){
					var $render = $('#j-render-goods');
					$(data.data.data).each(function(k, value) {
						if (value.create_time && typeof value.create_time == "undefined") {
							value.create_time = '';
						}
					});
					var template = doT.template($('#j-template-goods').html());
					$render.html(template({
						items: data.data.data,
						type: 0//1
					}));
					goodsFilter.page_count = Math.ceil(data.data.total_count/goodsFilter.page_size);
					if(goods_count === 1) {
						pageCtrl($("#goods-list-pager"), goodsFilter.current_page, goodsFilter.page_count);
						goods_count++;
					};
				}
			);
		};

		function pageCtrl(pager, pageIndex, pageCount) {
			pager.createPage({
				pageCount: pageCount,
				current: pageIndex,
				backFn:function(currentPage){
					if(pager.data('pagertype') === 'goods') {
						goodsFilter.current_page = currentPage;
						getGoodsList();
					}
					if(pager.data('pagertype') === 'mark') {
						iconFilter.current_page = currentPage;
						getMark();
					};
				}
			});
		};
		var mark_count = 1;
		//获取角标
		function getMark() {
			$.get(api_path + '/item/icon/query.do',
				{
					icon_name: iconFilter.icon_name,
					page_size: iconFilter.page_size,
					current_page: iconFilter.current_page,
				},
				function(data){
					$('#mark-icon-list').html(doT.template(self_tpl.markTpl)(data));
					iconFilter.page_count = Math.ceil(data.data.total_count/iconFilter.page_size);
					if(mark_count === 1) {
						pageCtrl($('#icon-list-pager'), goodsFilter.current_page, iconFilter.page_count);
						mark_count++;
					};
				}
			);
		};
		function renderSelectedGoods(goodsID_array) {
			$.get(api_path + '/item/query.do',
				{
					item_id_list: JSON.stringify(goodsID_array),
					page_size:goodsID_array.length,
					current_page:1,
					need_skus: 1,
					need_sku_stocks: 1
				},
				function(data){
					$('#select-goods-modal').modal('hide');
					$('#goods-info-body').append(doT.template(self_tpl.goodsTpl)(data.data.data)).closest('.selected-goods').show();
					limitNumberRule();
				}
			);
		};
		//限购数量、价格规则
		function limitNumberRule() {
			$('#selected-goods').delegate('.limit-number', 'keyup', function(){
				var maxNumber = $(this).data('stock_num');
				var currNumber = $(this).val().trim();
				var newNumber = currNumber;
				newNumber = currNumber.replace(/\D/g,'');
				if(currNumber.length > 1) {
					newNumber = newNumber.replace(/^0/,'');
				}
				if(parseInt(newNumber) > maxNumber) {
					newNumber = 0;
				}
				$(this).val(newNumber);
			});
			$('#selected-goods').delegate('.limit-price', 'keyup', function(){
				var currPrice = $(this).val().trim();
				var newPrice = currPrice;
				//只能输入一个小数点 禁止...
				if(currPrice.split('.').length > 2) {
					$(this).val('')
					return;
				}
				//禁止000
				if(currPrice.length > 1 && currPrice.substr(1,1)==="0") {
					newPrice = newPrice.replace(/^0/,'');
				}
				newPrice = newPrice.replace(/^\./g,'').replace(/[^\d.]/g,'');
				$(this).val(newPrice);
			});
		}
		function bindEvent() {
			$('#add-goods-btn').click(function(){
				$('#keyword-input').val("");
				goodsFilter = {
					category_two_id: '',
					brand_key: '',
					category_id: '',
					current_page: 1,
					item_status: undefined,
					key: '',//搜索框
					page_size: 10
				};
				$('#select-goods-modal').modal('show');
				$('#select-all').removeClass('active').html('全部选择');
				//商品类目
				getCate();
				//品牌
				getBrand();
				getGoodsList();
			});

			$('#isOnSale').change(function(){
				// goodsFilter.item_status === '4' ? goodsFilter.item_status = undefined : goodsFilter.item_status = '4';
				goodsFilter.item_status = goodsFilter.item_status === '4' ? undefined : '4';
				getGoodsList();
			});
			$('#cate_key').change(function(){
				goodsFilter.category_id = $(this).val();
				$('#category_two').closest('.form-group').show();
				if(goodsFilter.category_id == "--请选择一级类目--"){
					goodsFilter.category_id = "";
				};
				getTwoCate($(this).val());
			});
			$('#brand_key').change(function(){
				goodsFilter.brand_key = $(this).val();
				if(goodsFilter.brand_key == "请选择品牌"){
					goodsFilter.brand_key = "";
				};
			});
			$('#category_two').change(function(){
				goodsFilter.category_two_id = $(this).val();
				if(goodsFilter.category_two_id == "--请选择二级类目--"){
					goodsFilter.category_two_id = "";
				};
			});
			$('#seach-bth').click(function(){
				goods_count = 1;
				goodsFilter.current_page = 1;
				goodsFilter.key = $('#keyword-input').val().trim();
				getGoodsList();
			});

			$('#j-render-goods').delegate('.select-goods', 'click', function(){
				$(this).toggleClass('active');
				$('#j-render-goods .select-goods').text("选择");
				$('#j-render-goods .active').text("取消");
				var item_id = $(this).data('id');
			});

			$('#select-all').click(function(){
				$(this).toggleClass('active');
				if($(this).hasClass('active')) {
					$('.select-goods').removeClass('active');
					$(this).html('取消全选');
				} else {
					$('.select-goods').addClass('active');
					$(this).html('全选本页');
				}
				$('.select-goods').toggleClass('active');
				$('#j-render-goods .select-goods').text("选择");
				$('#j-render-goods .active').text("取消");
			});
			$('#confirm-select').click(function(){
				//选择的商品id数组
				var goodsID_array = [];
				$('.select-goods.active').each(function(){
					var tempID = $(this).data('id');
					//tempID是否已存在与限时购列表
					var isExists = false;
					$('#goods-info-body .parent-tr').each(function(){
						if($(this).data('item_id') === tempID) {
							isExists = true;
						};
					});
					if(isExists) {
						layer.msg('本商品已添加');
						return true;
					};
					goodsID_array.push($(this).data('id'));
				});
				//渲染已选择的商品列表
				goodsID_array.length !=0 ? renderSelectedGoods(goodsID_array) : undefined;
			});
			$('#icon-confirm-select').click(function(){
				var selectedIcon = $('#mark-icon-list .img.active');
				if(selectedIcon.length != 0) {
					$('#add-icon-btn').html('<img src="' + selectedIcon.data('url') + '" class="img-circle" data-iconID="' + selectedIcon.data('id') + '">');
					$('#select-icon-modal').modal('hide');
				}
			});

			$('#edit-campaign-container').delegate('#add-icon-btn', 'click', function(){
				iconFilter.icon_name = "";
				$('#keyword-icon-input').val("");
				$('#select-icon-modal').modal('show');
				getMark();
			});

			$('#icon-seach-bth').click(function(){
				mark_count = 1;
				iconFilter.icon_name = $('#keyword-icon-input').val().trim();
				getMark();
			});

			$('#mark-icon-list').delegate('.img', 'click', function(){
				$(this).closest('li').siblings().find('.img').removeClass('active');
				$(this).toggleClass('active');
			});

			$('#selected-goods').delegate('.delete-icon', 'click', function(){
				var _this = this;
				layer.confirm('确定要删除此关联商品？', {
					icon: 3,
					title: '提示'
					},
					function(index) {
						if ($(".parent-tr").length == 1) {
							$(".selected-goods").hide();
						};
						var target = $(_this).closest('tr');
						target.remove();
						layer.close(index);
					});
			});
			//保存
			$('#save-selected-btn').click(function(){
				$('#publish-selected-btn').attr("disabled", true);
				//活动名称
				var campaign_name = $('#campaign-name').val().trim();
				if(campaign_name === ''){
					layer.msg('请填写活动名称');
					return;
				};
				//活动开始时间
				var campaign_start_time = $('#campaign-start-time').val().trim();
				if(campaign_start_time === ''){
					layer.msg('请填写活动开始时间');
					return;
				};
				//活动结束时间
				var campaign_end_time = $('#campaign-end-time').val().trim();
				if(campaign_end_time === ''){
					layer.msg('请填写活动结束时间');
					return;
				};
				if($('#add-icon-btn img').length === 0) {
					layer.msg('请选择活动角标');
					return;
				};
				var campaign_limit_number = $('.parent-table .limit-number');
				var campaign_limit_number_len = campaign_limit_number.length;
				for(var i=0;i<campaign_limit_number_len;i++){
					if(campaign_limit_number.eq(i).val().trim() === ''){
						layer.msg('请填写活动限购数量');
						return;
					};
				};
				var campaign_limit_price = $('.parent-table .limit-price');
				var campaign_limit_price_len = campaign_limit_price.length;
				for(var i=0; i<campaign_limit_price_len; i++){
					if(campaign_limit_price.eq(i).val().trim() === ''){
						layer.msg('请填写全部活动售价');
						return;
					};
				};
				var campaignInfo = {
					id:$('#campaign-name').attr("data-campaign_id"),
					activity_name: $('#campaign-name').val().trim(),
					start_time: $('#campaign-start-time').val().trim(),
					end_time: $('#campaign-end-time').val().trim(),
					activity_tag: $('#add-icon-btn img').attr('src'),
					voucher_type: $('input[name="coupon-radio"]:checked').val(),
					goods_info: []
				};
				var tempSpuInfo = null;
				var tempSkuInfo = null;
				$('#goods-info-body .parent-tr').each(function(){
					tempSpuInfo = {};
					tempSpuInfo.item_id = $(this).data('item_id');
					tempSpuInfo.item_name = $(this).find("div.name p").eq(0).text();
					tempSkuInfo = [];
					var skuItem = null;
					$(this).find('.child-table .child-tr').each(function(){
						skuItem = {};
						skuItem.sku_id = $(this).data('sku_id');
						skuItem.goods_price = $(this).find(".price-td input").val() * 100;
						skuItem.goods_quantity = $(this).find('input:eq(0)').val();
						tempSkuInfo.push(skuItem);
					});
					tempSpuInfo.sku_info = tempSkuInfo;
					campaignInfo.goods_info.push(tempSpuInfo);
				});
				if(campaignInfo.goods_info.length != 0) {
					var parm = "goodsInfo="+JSON.stringify(campaignInfo);
					$.post(api_path + '/marketing/limtedPurchase/updateLimitedPurchase.do', parm, function(msg){
							if(msg.code == 10000){
								layer.msg('保存成功');
								location.href = "/jeesite/a/stock/front/time-purchase/time-purchase-list";
								$('#publish-selected-btn').attr("disabled", false);
							} else {
								layer.alert(msg.msg);
							};
					});
				} else {
					layer.msg('请选择活动商品');
					return;
				};
			});
			$('#cancel-selected-btn').click(function(){
				location.href = "/jeesite/a/stock/front/time-purchase/time-purchase-list";
			});
			//发布
			$('#publish-selected-btn').click(function(){
				
				$.get(api_path + '/marketing/limtedPurchase/startLimitedPurchase.do', {id:activeId}, function(data){
					if(data.data == true){
						location.href = "/jeesite/a/stock/front/time-purchase/time-purchase-list";
					};
				});
			});
			limitNumberRule();
		}
	});

/***/ }

/******/ });