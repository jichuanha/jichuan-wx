$(function (){
	var parm = {
		"currentPage":"0",
		"total_count":""
	}
	function getCheckSellListData(currentPage) {
		$.ajax({
			type: "get",
			url : "/getCheckSellList.do",
			data: {
				offset:	currentPage,
				page_size: 10
			},
			success : function(result) {
				parm.total_count = Math.ceil(result.total_count/10);
				var interText =doT.template($("#act-template").text());
				$("#interpolation").html(interText(result));
				pager(currentPage, parm.total_count);
			}
		});
	};
	getCheckSellListData(parm.currentPage);
	function pager(currentPage, pageCount) {
		$(".tcdPageCode").createPage({
			pageCount:	pageCount,
			current: currentPage,
			backFn:function(currentPage){
				parm.currentPage = currentPage;
				getCheckSellListData(parm.currentPage);
			}
		});
	};
	// 搜索展示列表
	$("#btn-search").on("click",function(){
		parm.currentPage = 0;
		parm.conditions = $("#input-search").val();
		getCheckSellListData(parm.currentPage);
	});
});