$(function (){
	var parm = {
		"status": "",
		"conditions": "", // 搜索条件
		"currentPage": 1
	};
	var count = 0;
	function timePurchaseListData(currentPageKey, statusKey, conditionsKey){
		$.get(ctx + '/marketing/limtedPurchase/list.do',{
			// 查询条件
			run_status: statusKey,
			activity_name: conditionsKey,
			current_page: currentPageKey,
			page_size: "20"
		}, function(result){
			count++;
			var interText =doT.template($("#act-template").text());
			$("#interpolation").html(interText(result.data.data));
			if(count == 0){
				pager(parm.currentPage, Math.ceil(result.total_count/20));
			};
		});
	};
	timePurchaseListData(parm.currentPage, parm.status, parm.conditions); // 参数1：展示的第一页 参数23：搜索条件
	function pager(currentPage, pageCount) {
		$(".tcdPageCode").createPage({
			pageCount:	pageCount,
			current: currentPage,
			backFn:function(currentPage){
				parm.currentPage = currentPage;
				timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
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
			parm.status = "";
		};
		parm.conditions = $("#inputConditions").val();
		parm.currentPage = 1;
		timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
	});

	$(".container .first").css({
		"background-color":'#e6e6e6',
		"border-bottom":"1px solid #969696"
	});

	$('#search').on('click',function(){
		parm.conditions = $("#inputConditions").val();
		parm.currentPage = 1;
		timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
	});
	$(".disable").on("click",function () {
		selectedId = $(this).attr("data-id");
		$.get(ctx + '/marketing/limtedPurchase/list.do',{
			id:selectedId
		}, function(result){
			timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
		});
	})
});