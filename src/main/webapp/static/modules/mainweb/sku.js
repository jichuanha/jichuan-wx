$(function (){
	var item_id = location.href.substring(location.href.indexOf('id=')+3);
	var count = 1;

	function getGoodsSkuListData(currentPage) {
		$.ajax({
			type: "post",
			url: ctx + "/supplier/StoreItemSku/query.do",
			data: {
				item_id: parseInt(item_id),
				offset:	currentPage,
				page_size: 20
			},
			success : function(result) {
				var result = JSON.parse(result);
				var interText =doT.template($("#act-template").text());
				$("#interpolation").html(interText(result.data.data));
				if(count === 1) {
					pager(currentPage+1, Math.ceil(result.data.total_count/20));
					count++;
				}
			}
		});
	};
	getGoodsSkuListData(0);
	function pager(currentPage, pageCount) {
		$(".tcdPageCode").createPage({
			pageCount:	pageCount,
			current: currentPage,
			backFn:function(currentPage){
				getGoodsSkuListData(currentPage-1);
			}
		});
	}
	// 同步更新数据。
	$(".container").on("click",'.sync', function(event) {
		$(".container .sync").removeClass("addBank");
		$(this).addClass('addBank');
		// 需要传递的参数
		var target = $(event.target);
		var itemSkuId = target.attr("itemskuid");
		var storeId   =  target.attr("storeid");
		var itemSkuSn =  target.attr("itemskusn");
		var parent = target.parent().parent();
		//总库存量
		var stockNum = parent.children('.stockNum');
		//sales_num
		var salesNum = parent.children('.salesNum');
		//锁定量
		var frozenStockNum = parent.children('.frozenStockNum');
		//预扣量
		var soldNum = parent.children('.sold_num');
		$.ajax({
			type : "get",
			url : ctx + "/mainweb/sync/update/stock.do",
			data : {
				"itemSkuId": itemSkuId,
				"storeId": storeId,
				"itemSkuSn": itemSkuSn
			},
			success : function(result) {
				var result = JSON.parse(result);
				if(result.data){
					var data = result.data;
					stockNum.html(data.stock_num);
					salesNum.html(data.sales_num);
					frozenStockNum.html(data.frozen_stock_num);
					soldNum.html(data.sold_num);
				}
				layer.tips('同步成功', '.addBank');
			},
			error: function () {
				layer.tips('同步失败', '.addBank');
			}
		});
	});
});