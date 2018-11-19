$(function (){
	// 分页器的调用。
		$(".tcdPageCode").createPage({
			pageCount:6,
			current:1,
			backFn:function(p){
				console.log(p);
			}
		});
		//  网络请求和模板的调用。
		function getGoodsData(argument) {
			$.ajax({
				type : "POST",
				url : "/bundleSaleList.do",
				data : {
					"id" : "value"
				},
				success : function(result) {
					var interText = doT.template($("#act-template").text());
					$("#interpolation").html(interText(result.data.data));
					console.log(result);
				}
			});
		};
		// 调用ajax获取数据。
		getGoodsData();
})