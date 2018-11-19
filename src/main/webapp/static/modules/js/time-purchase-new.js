$(function(){
	//商品弹窗
	var goodsFilter = {
		brand_key: '',
		category_id: '',
		current_page: 1,
		item_status: undefined,
		key: '',//搜索框
		page_size: 10
	};
	//角标弹窗
	var iconFilter = {
		icon_name: '',
		page_size: 20,
		current_page: 1
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
	$("#icon-remove").on("click",function(){
		$(".head-tips").hide();
	});
	$('#publish-time').datetimepicker();
	$.timepicker.datetimeRange(
		$('#campaign-start-time'),
		$('#campaign-end-time'),
		timepickerCfg
	);
	$('#add-goods-btn').click(function(){
		$('#select-goods-modal').modal('show');
		$('#select-all').removeClass('active').html('全部选择');
		//商品类目
		getCate();
		//品牌
		getBrand();
		getGoodsList();
	});
	function getCate() {
		$.get(ctx + '/item/category/query.do', {parent_id: 0}, function(data){
			$('#cate_key').html(doT.template(self_tpl.cate_key)(data));
			// bindEvent();
		});
	};
	function getTwoCate(id) {
		$.get(ctx + '/item/category/query.do', {parent_id: id}, function(data){
			var interText = doT.template($("#j-template-category").text());
			$('#category_two').html(interText({
				items: data.data,
				category_value: 2
			}));
		});
	};

	function getBrand() {
		$.get(ctx + '/item/brand/query.do', {}, function(data){
			$('#brand_key').html(doT.template(self_tpl.brand_key)(data.data));
		});
	};

	var goods_count = 1;
	function getGoodsList() {
		$.get(ctx + '/item/query.do',
			{
				key: goodsFilter.key,
				brand_id: goodsFilter.brand_key,
				category_id: goodsFilter.category_id,
				item_status: goodsFilter.item_status,
				current_page: goodsFilter.current_page,
				page_size: goodsFilter.page_size
			},
			function(data){
				var $render = $('#j-render-goods');
				$(data.data.data).each(function(k, value) {
					if (typeof value.create_time == "undefined") {
						value.create_time = '';
					}
				});
				var template = doT.template($('#j-template-goods').html());
				$render.html(template({
					items: data.data.data,
					type: 0//1
				}));
				if(goods_count === 1) {
					pageCtrl($("#goods-list-pager"), goodsFilter.current_page, Math.ceil(data.data.total_count/goodsFilter.page_size));
					goods_count++;
				};
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
			};
			if(parseInt(newNumber) > maxNumber) {
				newNumber = 0;
			};
			$(this).val(newNumber);
		});
		$('#selected-goods').delegate('.limit-price', 'keyup', function(){
			var currPrice = $(this).val().trim();
			var newPrice = currPrice;
			//只能输入一个小数点 禁止...
			if(currPrice.split('.').length > 2) {
				$(this).val('')
				return;
			};
			//禁止000
			if(currPrice.length > 1 && currPrice.substr(1,1)==="0") {
				newPrice = newPrice.replace(/^0/,'');
			};
			newPrice = newPrice.replace(/^\./g,'').replace(/[^\d.]/g,'');
			$(this).val(newPrice);
		});
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
		$.get(ctx  +'/item/icon/query.do',
			{
				icon_name: iconFilter.icon_name,
				page_size: iconFilter.page_size,
				current_page: iconFilter.current_page,
			},
			function(data){
				$('#mark-icon-list').html(doT.template(self_tpl.markTpl)(data));
				if(mark_count === 1) {
					pageCtrl($('#icon-list-pager'), goodsFilter.current_page, Math.ceil(data.data.total_count/iconFilter.page_size));
					mark_count++;
				};
			}
		);
	};
	function renderSelectedGoods(goodsID_array) {
		$.get('/bossmanager/goods/query.do',
			{
				goodsID: goodsID_array
			},
			function(data){
				$('#select-goods-modal').modal('hide');
				$('#goods-info-body').append(doT.template(self_tpl.goodsTpl)(data.data.data)).closest('.selected-goods').show();
				limitNumberRule();
			}
		);
	};
	function bindEvent() {
		$('#isOnSale').change(function(){
			goodsFilter.item_status = goodsFilter.item_status === '4' ? undefined : '4';
			getGoodsList();
		});
		$('#cate_key').change(function(){
			goodsFilter.category_id = $(this).val();
			$('#category_two').closest('.form-group').show();
			getTwoCate($(this).val());
		});
		$('#brand_key').change(function(){
			goodsFilter.brand_key = $(this).val();
		});

		$('#seach-bth').click(function(){
			goodsFilter.key = $('#keyword-input').val().trim();
			getGoodsList();
		});
		$('#j-render-goods').delegate('.select-goods', 'click', function(){
			$(this).toggleClass('active');
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
			};
			$('.select-goods').toggleClass('active');
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
			};
		});

		$('#add-icon-btn').click(function(){
			$('#select-icon-modal').modal('show');
			getMark();
		});

		$('#icon-seach-bth').click(function(){
			iconFilter.icon_name = $('#keyword-icon-input').val().trim();
			getMark();
		});

		$('#mark-icon-list').delegate('.img', 'click', function(){
			// $('#mark-icon-list .img').removeClass('active');
			$(this).closest('li').siblings().find('.img').removeClass('active');
			$(this).toggleClass('active');
		});

		$('#selected-goods').delegate('.delete-icon', 'click', function(){
			var target = $(this).closest('tr');
			target.remove();
		});
		//保存
		$('#save-selected-btn').click(function(){
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
					layer.msg('请填写活动售价');
					return;
				};
			};
			var campaignInfo = {
				id: $('#campaign-name').data('campaign_id'),
				activity_name: $('#campaign-name').val().trim(),
				start_time: $('#campaign-start-time').val().trim(),
				end_time: $('#campaign-end-time').val().trim(),
				activity_tag: $('#add-icon-btn img').attr('src'),
				voucher_type: $('input[name="coupon-radio"]:checked').val(),
				goodsInfo: []
			};
			var tempSpuInfo = null;
			var tempSkuInfo = null;
			$('#goods-info-body .parent-tr').each(function(){
				tempSpuInfo = {};
				tempSpuInfo.item_id = $(this).data('item_id');
				tempSkuInfo = [];
				var skuItem = null;
				$(this).find('.child-table .child-tr').each(function(){
					skuItem = {};
					skuItem.sku_id = $(this).data('sku_id');
					skuItem.goods_price = $(this).find('input:eq(0)').val();
					skuItem.goods_quantity = $(this).find('input:eq(1)').val();
					tempSkuInfo.push(skuItem);
				});
				tempSpuInfo.skuInfo = tempSkuInfo;
				campaignInfo.goodsInfo.push(tempSpuInfo);
			});
			if(campaignInfo.goodsInfo.length != 0) {
				$.post('/modifyTimelimitpurchase.do', campaignInfo, function(data){
					$('#publish-selected-btn').attr("disabled", false);
				});
			} else {
				layer.msg('请选择活动商品');
				return;
			};
		});
		$('#cancel-selected-btn').click(function(){
			location.href = "/time-purchase-list.html";
		});
		//发布
		$('#publish-selected-btn').click(function(){
			$.post(' marketing/limtedPurchase/startLimtedPurchase.do',{id: $('#campaign-name').data('campaign_id')}, function(data){
				location.href = "/time-purchase-list.html";
			});
		});
	};
	bindEvent();
});