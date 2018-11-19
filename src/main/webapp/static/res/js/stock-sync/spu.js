$(function (){
		var count = 1;
		function getGoodsData(pageIndex) {
			$.ajax({
				type : "get",
				url : ctx + '/item/query.do',
				data : {
					current_page: pageIndex,
					page_size: 6
				},
				success : function(result) {
					var interText = doT.template($("#act-template").text());
					$("#interpolation").html(interText(result.data.data));
					if(count === 1) {
						pager(1, Math.ceil(result.data.total_count/6));
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
})