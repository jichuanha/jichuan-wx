<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>活动列表</title>
	<meta name="decorator" content="default"/>
	<%--<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">--%>
	<link rel="stylesheet" href="${ctxStatic}/page.my.css">
	<script src="${ctxStatic}/page.my.js"></script>
	<style>
		.h3-title{
			font-size: 16px;
			color: #000;
		}
		.h3-deco{
			display: inline-block;
			width: 5px;
			height: 20px;
			background-color: #3F51B5;
			vertical-align: middle;
			margin-right: 20px;
			margin-left: 20px;
		}
		.wrap{
			width: 100%;
			background-color: #fff;
		}
		.form-search{
			background-color: #F7F7F7;
			margin: 0 20px 20px;
			padding: 20px 0;
		}
		#searchForm li{
			width: 33%;
			margin: 5px 0;
		}
		.mid-input{
			height: 30px;
			box-sizing: border-box;
		}
		@media screen and (min-width: 1500px) {
			.mid-input{
				width: 200px;
			}
		}
		@media screen and (max-width: 1500px) {
			.mid-input{
				width: 200px;
			}
		}
		@media screen and (max-width: 1200px) {
			.mid-input{
				width: 180px;
			}
		}
		@media screen and (max-width: 1000px) {
			.mid-input{
				width: 150px;
			}
		}
		@media screen and (max-width: 800px) {
			.mid-input{
				width: 100px;
			}
		}
		input[type="text"]{
			height: 30px;
			box-sizing: border-box;
			font-size: 14px;
		}
		#searchForm .btns{
			width: 100%;
			text-align: center;
			margin-top: 10px;
		}
		.btns input{
			width: 200px;
			line-height: 1.5;
			font-size: 15px;
			background-image: none;
		}
		#btnSubmit{
			background-color: #3F51B5;
			color: #FFFFFF;
			letter-spacing: 10px;
			padding-right: 10px;
			margin-right: 40px;
		}
		#btnNew{
			border: 1px solid #3F51B5;
			color: #3F51B5;
		}
		#btnNew:hover{
			background-color: #fff;
		}
		.text-left{
			float: left;
			margin-left: 20px;
		}
		.text-right{
			float: right;
			margin-right: 50px;
		}
		.activity-lists{
			padding: 0 20px;
		}
		.activity-lists ul{
			list-style: none;
			margin: 0;
		}
		.activity-lists ul li{
			float: left;
		}
		.activity-lists p{
			background-color: #F7F7F7;
			line-height: 3;
			margin-top: 10px;
			border: 1px solid #F0F0F0;
			margin-bottom: 0;
		}
		.lists-title{
			display: block;
			width: 100%;
		}
		.lists-title li{
			background-color: rgba(242, 242, 242, 1);
			line-height: 3;
			text-align: center;
		}
		.activity-list li{
			text-align: center;
			border-bottom: 1px solid rgb(228, 228, 228);
			border-left: 1px solid rgb(228, 228, 228);
			line-height: 3;
			height: 40px;
			box-sizing: border-box;
		}
		.activity-list li:last-of-type{
			border-right: 1px solid rgb(228, 228, 228);
		}
		.mycol-5{
			width: 5%;
		}
		.mycol-10{
			width: 10%;
		}
		.mycol-15{
			width: 15%;
		}
		.mycol-25{
			width: 25%;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		}
		.list-time{
			color: #989898;
            margin-left: 20px;
		}
		.list-prompt,.activity-pause,.activity-cancel{
			color: #EA4343;
			cursor: pointer;
		}
		.activity-detail,
		.activity-contin{
			color: #5B6ABF;
            cursor: pointer;
            margin-right: 10px;
		}
		.activity-detail{
			font-style: normal;
		}
		.activity-detail:hover{
			text-decoration: none;
			color: #5B6ABF;
		}
		.list-right{
			display: inline-block;
			float: right;
		}
		.list-right span{
			margin-right: 10px;
			font-style: normal;
		}
		.search-cond{
			display: inline-block;
			font-size: 14px;
			border: 1px solid #F1F1F1;
			color: #7C7C7C;
			border-radius: 3px;
			padding: 3px 5px;
		}
		.search-close{
			display: inline-block;
			width: 15px;
			height: 15px;
			vertical-align: top;
		}
		.search-close img{
			width: 100%;
			height: 100%;
			vertical-align: text-top;
			margin-left: 2px;
		}
		.my-nav-tabs{
			background-color: #F7F7F7;
			/*padding-left: 20px;*/
			margin-left: 10px;
		}
		.my-nav-tabs li{
			float: left;
			font-size: 16px;
			line-height: 3;
			margin: 0 30px;
			padding: 0 20px;
			text-align: center;
			color: #000;
		}
		.my-nav-tabs li a{
			color: #000;
		}
		.my-nav-tabs li.active a{
			border-bottom: 5px solid #3F51B5;
		}
		.my-nav-tabs li a:hover{
			background-color: #F7F7F7;
		}
	</style>

</head>
<body>
<ul class="nav my-nav-tabs clearfix">
	<li class="active"><a href="${ctx}/activity/activity/activity-list">活动列表</a></li>
	<li><a href="${ctx}/trade/order/order_list">订单列表</a></li>
	<li><a href="${ctx}/trade/order/order_review">订单审核</a></li>
</ul><br/>
<div class="wrap">
	<div class="wrap-header">
		<form id="searchForm"  class="form-search">
			<input id="current_page" name="current_page" type="hidden" value="1"/>
			<input id="page_size" name="page_size" type="hidden" value="10"/>
			<ul class="ul-form">
				<li><label>活动名称：</label>
					<input type="text" name="name" class="mid-input" id="name">
				</li>
				<li><label>活动状态：</label>
					<select name="status" id="status" class="mid-input">
						<option value="">请选择</option>
						<option value="0">未开始</option>
						<option value="1">进行中</option>
						<option value="2">暂停</option>
						<option value="3">已结束</option>
					</select>
				</li>
				<li><label>返利类型：</label>
					<select name="rebate_type" id="rebate_type" class="mid-input">
						<option value="">请选择</option>
						<option value="1">固定金额</option>
					</select>
				</li>
				<li>
					<label>开始时间：</label>
					<input id="active_date" name="active_date" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate"/>
				</li>
				<li>
					<label>结束时间：</label>
					<input id="inactive_date" name="inactive_date" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate" />

				</li>
				<li><label>所属店铺：</label>
					<select name="shop_no" id="shop_no" class="mid-input">
						<option value="">请选择</option>
					</select>
				</li>
				<li class="clearfix"></li>
				<li class="btns">
					<input id="btnSubmit" class="btn" type="button" value="搜索"/>
					<input id="btnNew" class="btn" type="button" value="新建活动"/>
				</li>
				<li class="clearfix"></li>
			</ul>
		</form>
	</div>
	<input id="pageCount" type="hidden" value=""/>
	<p class="h3-title search-box"><i class="h3-deco"></i><span class="activity-type">活动类型2</span></p>
	<div class="activity-lists">
		<ul class="lists-title clearfix">
			<li class="mycol-10">活动状态</li>
			<li class="mycol-10">强制关联</li>
			<li class="mycol-10">活动名称</li>
			<li class="mycol-15">返利渠道</li>
			<li class="mycol-25">参与店铺</li>
			<li class="mycol-15">活动订单</li>
			<li class="mycol-15">活动金额</li>
		</ul>
		<div class="lists-show">

		</div>

	</div>
	<div class="pagination">
		<ul>
		</ul>
	</div>
</div>
<script>
    $(function () {
        var para = GetRequest();
        $('.activity-type').html('活动类型'+para.activity_type);
        var paraStr = '?activity_type='+para.activity_type;
        $.each($('.my-nav-tabs li a'),function (index,selector) {
            var oldHref = $(selector).attr('href');
			$(selector).attr('href',oldHref+paraStr);
        })
        $.ajax({
            url:'platformShopList',
            type:'POST',
            success:function (msg) {
                var msg = strToJson(msg);
                if(msg.code == 10000){
                    var data = msg.data;
                    $.each(data,function (index,value) {
                        value.forEach(function (el,indexshop) {
                            $('#shop_no').append('<option value="'+el.shop+'">'+el.shop_name+'</option>');
                        })

                    })
				}
            }
        })
        ajaxFuc();
        function ajaxFuc(nextPage){
            var dataObject = {};
            dataSer = ($("#searchForm").serializeArray());
            $.each(dataSer,function(i,item){
                dataObject[item.name] = item.value;
            });
            if(nextPage != null){
                dataObject.current_page = nextPage;
                nextPageSec = nextPage;
            }
            else{
                dataObject.current_page = 1;
                nextPageSec = 1;
            }
            dataObject.activity_type = para.activity_type;
            $.ajax({
                url:"activityList",
                type:"post",
				data:dataObject,
                success:function (msg) {
                    var msg = strToJson(msg);
                    var data = msg.data;
                    $('.lists-show').html('');
                    var shopName;
                    var shopNameArr = [];
                    var values = [];
                    var shopNameStr = '';
					data.list.forEach(function (el,index) {
					    var listShowEach = '';
                        listShowEach += '<p><span class="list-time">'+el.active_date+' - '+el.inactive_date+'</span>';
                        if(index == 0){
                            listShowEach += '<span class="list-prompt">(列表根据创建时间"倒序"排列)</span>';
						}
						listShowEach += '<i class="list-right">';
						if(el.status == 1){
                            listShowEach += '<span class="activity-pause">暂停</span>';
						}
						else if(el.status == 2){
                            listShowEach += '<span class="activity-contin">继续</span>';
                        }
                        if(el.status != 3){
                            listShowEach += '<span class="activity-cancel">取消</span>';
						}
                        listShowEach += '<a href="activity-detail?id='+el.id+'&activity_type='+para.activity_type+'&act_name='+el.name+'" class="activity-detail">活动详情</a>' +
							'<input type="hidden" value="'+el.id+'"></i></p>';
                        listShowEach += '<ul class="activity-list clearfix">';
                        if(el.status == 0){
                            listShowEach += '<li class="mycol-10">未开始</li>'
						}
						else if(el.status == 1){
                            listShowEach += '<li class="mycol-10">进行中</li>'
                        }
                        else if(el.status == 2){
                            listShowEach += '<li class="mycol-10">暂停</li>'
                        }
                        else if(el.status == 3){
                            listShowEach += '<li class="mycol-10">已结束</li>'
                        }
                        else{
                            listShowEach += '<li class="mycol-10"></li>'
                        }
                        if(el.is_follow == '0'){
                            listShowEach += '<li class="mycol-10">否</li>'
						}
						else{
                            listShowEach += '<li class="mycol-10">是</li>'
                        }
                        listShowEach += '<li class="mycol-10">'+el.name+'</li>'
                        if(el.rebate_channel == 1){
                            listShowEach += '<li class="mycol-15">红包领取</li>'
						}
						else{
                            listShowEach += '<li class="mycol-15"></li>';
						}

                        shopNameArr = [];
						shopName  = strToJson(el.shop_name);
                        $.each(shopName,function (key,value) {
							values = value.split(',');
							values.forEach(function (el) {
                                shopNameArr.push(key+el);
							})
                        })
                        shopNameStr = shopNameArr.join(',');
                        listShowEach += '<li class="mycol-25" title="'+shopNameStr+'">'+shopNameStr+'</li>';
                        listShowEach += '<li class="mycol-15">'+(el.max_order_limit == 0?"-":el.max_order_limit)+' / '+el.order_count+'</li><li class="mycol-15">'+(el.total_amount == 0?"-":"¥ "+el.total_amount/100)+' / ¥ '+(el.order_count*el.per_amount/100)+'</li></ul>';
						$('.lists-show').append(listShowEach);
                    })
                    $('#current_page').val(nextPageSec);
                    $('#pageCount').val(data.count);
                    pageList(10,nextPageSec);
                }
            })
		}
		var searchCon = [{
            name:'活动名称',
			type:'input',
			id:'name'
		},{
            name:'活动状态',
            type:'select',
            id:'status'
        },{
            name:'返利类型',
            type:'select',
            id:'rebate_type'
        },{
            name:'开始时间',
            type:'input',
            id:'active_date'
        },{
            name:'结束时间',
            type:'input',
            id:'inactive_date'
        },{
            name:'所属店铺',
            type:'select',
            id:'shop_no'
        }];
        //搜索
        $('#btnSubmit').click(function () {
            $('.search-box').html('<i class="h3-deco"></i>活动类型 '+para.activity_type);
            var dataObject = {};
            dataSer = ($("#searchForm").serializeArray());
            $.each(dataSer,function(i,item){
                dataObject[item.name] = item.value;
            });
            addSearch(dataObject);
            ajaxFuc();
        })
		var searchVal;
		function addSearch(dataObject){
            searchCon.forEach(function (el) {
                var id = el.id;
                searchVal = '';
                if(dataObject[id] != ''){
                    if(el.type == 'input'){
                        searchVal = $('#'+el.id).val();
					}
					else{
                        searchVal = $('#'+el.id).find("option:selected").text();
					}
                    $('.search-box').append('<span class="search-cond">'+el.name+':'+searchVal+'<i class="search-close"><img src="${ctxStatic}/images/search-close.png" alt=""></i><input type="hidden" data-pram="'+el.id+'"></span>')
                }
			})
		}
		$('.search-close').live('click',function () {
			$(this).parent().css('display','none');
			var para = $(this).next().attr('data-pram');
			$('#'+para).val('').trigger("change");
			ajaxFuc();
        })
		//新建活动
		$('#btnNew').click(function () {
			location.href = 'activity-new?activity_type='+para.activity_type;
        })
        //继续活动
		$('.activity-contin').live('click',function () {
		    var activityId = $(this).nextAll('input').val();
            activityHandle(activityId,2);
        })
        //暂停活动
        $('.activity-pause').live('click',function () {
            var activityId = $(this).nextAll('input').val();
            activityHandle(activityId,1);
        })
        //取消活动
        $('.activity-cancel').live('click',function () {
            var activityId = $(this).nextAll('input').val();
            activityHandle(activityId,3);
        })
		function activityHandle(activityId,type){
            $.ajax({
                url:'updateStatus',
                type:'POST',
                data:{
                    id:activityId,
                    type:type
                },
                success:function (msg) {
                    var msg = strToJson(msg);
                    if(msg.code == 10000){
                        ajaxFuc();
                    }
                    else{
                        layer.msg(msg.msg);
                    }
                }
            })
		}
        // 选择开始时间方法
        $('#active_date').live('click',function () {
            var inactive_date=$dp.$('inactive_date');
            WdatePicker({
                onpicked:function(){
                    if($dp.$('inactive_date').value == ''){
                        $dp.$('inactive_date').value=$dp.cal.getP('y')+'-'+$dp.cal.getP('M')+'-'+$dp.cal.getP('d')+' '+(parseInt($dp.cal.getP('H'))+1)+':'+$dp.cal.getP('m')+':'+$dp.cal.getP('s');
                        inactive_date.click();
                    }

                },
                isShowClear:false,
                dateFmt:'yyyy-MM-dd HH:mm:ss',
                maxDate:'#F{$dp.$D(\'inactive_date\')}'
            })
        })
        //选择结束时间方法
        $('#inactive_date').live('click',function () {
            WdatePicker({
                minDate:'#F{$dp.$D(\'active_date\')}',
                dateFmt:'yyyy-MM-dd HH:mm:ss'
            })
        })
        // /分页跳转方法
//         下一页点击事件
        $('.nextli').live('click',function () {
            currPage = $('#current_page').val();
            nextPage = parseInt(currPage) + 1;
            pageCount  = $('#pageCount').val();
            pageNum = $('#page_size').val();
            //当前页码 小于 总数/一页数
            if(currPage >= Math.ceil(pageCount/pageNum)){
                return;
            }
            else{
                ajaxFuc(nextPage);
            }
        })
// //    上一页点击事件
        $('.prevli').live('click',function () {
            currPage = $('#current_page').val();
            if(currPage < 2){
                return;
            }
            else{
                nextPage = currPage - 1;
                ajaxFuc(nextPage);
            }
        })
//    某一页点击事件
        $('.page-lis').live('click',function () {
            nextPage = $(this).children().html();
            ajaxFuc(nextPage);
        })
//    input回车跳转事件
        $('.curr-page').live('keyup',function (event) {
            if(event.keyCode == 13){
                nextPage = $(this).val();
                if (nextPage != '' && !isNaN(nextPage)) {
                    ajaxFuc(nextPage);
                }
                else{
                    $.alert({
                        title: '提示',
                        content: '请检查后重新输入!'
                    });
                    return;
                }
            }
        })
		$('.btn-link').live('click',function () {
            nextPage = $('.curr-page').val();
            if (nextPage != '' && !isNaN(nextPage)) {
                ajaxFuc(nextPage);
            }
            else{
                $.alert({
                    title: '提示',
                    content: '请检查后重新输入!'
                });
                return;
            }
        })
        // pageList(10,1);

    })
    function GetRequest() {
        var url = location.search; //获取url中"?"符后的字串
        var theRequest = new Object();
        if (url.indexOf("?") != -1) {
            var str = url.substr(1);
            strs = str.split("&");
            for(var i = 0; i < strs.length; i ++) {
                theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
            }
        }
        return theRequest;
    }
</script>
</body>
</html>