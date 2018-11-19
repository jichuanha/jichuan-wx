$(function (){
	var parm = {
		"status": "", 
		"conditions": "", // 搜索条件
		"currentPage": 1
	};
	var count = 0;
	function timePurchaseListData(currentPageKey, statusKey, conditionsKey){
		$.ajax({
			type: "get",
			url: '/getTimePurchaseList.do',
			data: {
				// 查询条件
				status: statusKey,
				conditions: conditionsKey,
				offset: currentPageKey,
				page_size: "20"
			},
			success : function(result) {
				count++;
				var interText =doT.template($("#act-template").text());
				$("#interpolation").html(interText(result));
				if(count == 0){
					pager(parm.currentPage, Math.ceil(result.total_count/20));
				};
			}
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
		parm.conditions = $("#inputConditions").val();
		parm.status = $(".clicked").text();
		parm.currentPage = 0;
		timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
	});

	$(".container .first").css({
		"background-color":'#e6e6e6',
		"border-bottom":"1px solid #969696"
	});

	$('#search').on('click',function(){
		parm.conditions = $("#inputConditions").val();
		parm.status = $(".clicked").text();
		parm.currentPage = 0;
		$("#inputConditions").val()
		timePurchaseListData(parm.currentPage, parm.status, parm.conditions);
	});
});