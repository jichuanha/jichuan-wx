$(function (){
	var item_id = location.href.substring(location.href.indexOf('id=')+3);
	var count = 1;

	function getGoodsSkuListData(currentPage) {
		$.ajax({
			type: "get",
			url: ctx + "/supplier/StoreItemSku/query.do",
			data: {
				item_id: parseInt(item_id),
				offset:	currentPage,
				page_size: 6
			},
			success : function(result) {
				var result = JSON.parse(result);
				var interText =doT.template($("#act-template").text());
				$("#interpolation").html(interText(result.data.data));
				if(count === 1) {
					pager(currentPage+1, Math.ceil(result.data.total_count/6));
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
				getGoodsSkuListData(currentPage);
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
		//  需要同步的数据
		var stockNum = parent.children('.stockNum');
		var salesNum = parent.children('.salesNum');
		var frozenStockNum = parent.children('.frozenStockNum');
		var soldNum = parent.children('.soldNum');
		$.ajax({
			type : "get",
			url : ctx + "/mainweb/sync/update/stock.do",
			data : {
				"itemSkuId": itemSkuId,
				"storeId": storeId,
				"itemSkuSn": itemSkuSn
			},
			success : function(result) {
				var data = JSON.parse(result).data;
				if(data){
					stockNum.html(data.stock_num);
					salesNum.html(data.sales_um);
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