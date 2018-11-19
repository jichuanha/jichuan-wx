$(function (){
		var count = 1;
		function getGoodsData(pageIndex) {
			$.ajax({
				type : "post",
				url : ctx + '/supplier/itemRepo/query.do',
				data : {
					current_page: pageIndex,
					page_size: 20,
					key: $('#keywords').val()
				},
				success : function(result) {
					//$('#keywords').val('');
					var result = JSON.parse(result);
					var interText = doT.template($("#act-template").text());
					if(result.data.data) {
						$("#interpolation").html(interText(result.data.data));
					} else {
						$("#interpolation").html([]);
					}

					if(count === 1) {
						pager(1, Math.ceil(result.data.total_count/20));
						count++;
					}
				}
			});
		};
		getGoodsData(1);
		function pager(currentPage, pageCount) {
			$(".tcdPageCode").createPage({
				pageCount:	pageCount,
				current: currentPage,
				backFn: function(currentPage){
					getGoodsData(currentPage);
				}
			});
		}
		$('#search-btn').click(function () {
			// if(!$('#keywords').val()){
			// 	//`return;
			// }
			count = 1;
			getGoodsData(1);
		});
})