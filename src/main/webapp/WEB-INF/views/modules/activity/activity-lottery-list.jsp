<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>幸运抽奖活动管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/jquery.page/page.css">
	<script src="${ctxStatic}/jquery.page/page.js"></script>
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
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		}
		.mycol-10{
			width: 10%;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		}
		.mycol-15{
			width: 15%;
			overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
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
			margin-right: 8px;
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
	</style>

</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/activity/activityLottery/getLotteryList">活动列表</a></li>
		<li><a href="${ctx}/trade/order/order_lottery_list">订单列表</a></li>
	</ul>
	<div class="wrap">
		<div class="wrap-header">
			<form id="searchForm"  class="form-search">
				<%--<input id="current_page" name="current_page" type="hidden" value="1"/>--%>
				<%--<input id="page_size" name="page_size" type="hidden" value="5"/>--%>
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
					<li><label>强制关注：</label>
						<select name="is_follow" id="is_follow" class="mid-input">
							<option value="">请选择</option>
							<option value="0">是</option>
							<option value="1">否</option>
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
					<li><label>参与店铺：</label>
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
		<%--<input id="pageCount" type="hidden" value=""/>--%>
		<p class="h3-title search-box"><span class="activity-type"><i class="h3-deco"></i></span></p>
		<div class="activity-lists">
			<ul class="lists-title clearfix">
				<li class="mycol-10">活动状态</li>
				<li class="mycol-15">强制关注</li>
				<li class="mycol-10">活动名称</li>
				<li class="mycol-25">奖品设置</li>
				<li class="mycol-25">参与店铺</li>
				<li class="mycol-15">活动订单</li>
			</ul>
			<div class="lists-show">

			</div>

		</div>
		<div id="pagenation"></div>
	</div>
	<script>
        $(function () {
            var params = {
                current_page:1,
                page_size:10,
                wechat_platform_id:$.cookie('platFormId'),
            };
            var para = GetRequest(); //url参数
            var activityType = {}; //活动类型数据
            var hasInit = false;
            //带上活动类型参数
            var paraStr = '?activity_type='+para.activity_type;
            $.each($('.nav-tabs li a'),function (index,selector) {
                var oldHref = $(selector).attr('href');
                $(selector).attr('href',oldHref+paraStr);
            })
            //获取活动类型参数
            $.ajax({
                url:'${ctx}/activity/activity/activityTypeList',
                type:'post',
                async:false,
                success:function (msg) {
                    var msg = JSON.parse(msg);
                    if(msg.code == 10000){
                        activityType = msg.data;
                        $('.search-box').html('<span class="activity-type"><i class="h3-deco"></i>'+activityType[para.activity_type]+'</span>');
                    }
                }
            })
            //切换的时候加上activity_type 参数
            var paraStr = '?activity_type='+para.activity_type;
            $.each($('.my-nav-tabs li a'),function (index,selector) {
                var oldHref = $(selector).attr('href');
                $(selector).attr('href',oldHref+paraStr);
            })
            //获取店铺参数
            $.ajax({
                url:'${ctx}/activity/activity/platformShopList',
                type:'POST',
                success:function (msg) {
                    var msg = JSON.parse(msg);
                    if(msg.code == 10000){
                        var data = msg.data;
                        $.each(data,function (index,value) {
                            value.forEach(function (el,indexshop) {
                                $('#shop_no').append('<option value="'+el.id+'">'+el.platform_name+el.shop_name+'</option>');
                            })

                        })
                    }
                }
            })
            //获取列表
            ajaxFuc();
            function ajaxFuc(){
                dataSer = ($("#searchForm").serializeArray());
                $.each(dataSer,function(i,item){
                    params[item.name] = item.value;
                });
                params.activity_type = para.activity_type;
                $.ajax({
                    url:"${ctx}/activity/activityLottery/activityLotteryList",
                    type:"post",
                    data:params,
                    success:function (msg) {
                        var msg = JSON.parse(msg);
                        var data = msg.data;
                        $('.lists-show').html('');
                        var shopName;
                        var shopNameArr = [];
                        var values = [];
                        var shopNameStr = '';
                        data.list.forEach(function (el,index) {
                            var listShowEach = '';
                            listShowEach += '<p><span class="list-time">活动时间:'+el.active_date+' - '+el.inactive_date+'</span>';
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
                            listShowEach += '<a href="${ctx}/activity/activityLottery/getLotteryDetail?id='+el.id+'&activity_type='+para.activity_type+'&act_name='+el.name+'" class="activity-detail">活动详情</a>' +
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
                                listShowEach += '<li class="mycol-15">是</li>'
                            }
                            else{
                                listShowEach += '<li class="mycol-15">否</li>'
                            }
                            listShowEach += '<li class="mycol-10">'+el.name+'</li>'
                            // if(el.rebate_channel == 1){
                            //     listShowEach += '<li class="mycol-15">红包领取</li>'
                            // }
                            // else{
                            //     listShowEach += '<li class="mycol-15"></li>';
                            // }
                            listShowEach += '<li class="mycol-25">'
							el.lottery_prize_list.forEach(function (prizeEl,prizeIndex) {
                                listShowEach += prizeEl.prize_name + '('+ prizeEl.prize_rate*100 +'%)';
                                if(prizeIndex != el.lottery_prize_list.length-1){
                                    listShowEach += ',';
								}
                            })
							listShowEach += '</li>';
                            shopNameArr = [];
                            shopName  = JSON.parse(el.shop_name);
                            $.each(shopName,function (key,value) {
                                values = value.split(',');
                                values.forEach(function (el) {
                                    shopNameArr.push(key+el);
                                })
                            })
                            shopNameStr = shopNameArr.join(',');
                            listShowEach += '<li class="mycol-25" title="'+shopNameStr+'">'+shopNameStr+'</li>';
                            listShowEach += '<li class="mycol-15">'+(el.total_order == 0?"-":el.total_order)+' / '+el.order_count+'</li></ul>';
                            $('.lists-show').append(listShowEach);
                        })
                        if(!hasInit){
                            var obj = {
                                obj_box: '#pagenation',
                                total_item: data.count,
                                per_num: params.page_size,
                                current_page: params.current_page,
                                change_content: function(per_num, current_page) {
                                    if(hasInit){
                                        params.current_page = current_page;
                                        ajaxFuc();
                                    }
                                }
                            };
                            page_ctrl(obj);
                            hasInit=true;
                        }
                    }
                })
            }
            //查询条件数组
            var searchCon = [{
                name:'活动名称',
                type:'input',
                id:'name'
            },{
                name:'活动状态',
                type:'select',
                id:'status'
            },{
                name:'强制关注',
                type:'select',
                id:'is_follow'
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
            //搜索事件
            $('#btnSubmit').click(function () {
                $('.search-box').html('<span class="activity-type"><i class="h3-deco"></i>'+activityType[para.activity_type]+'</span>');
                var dataObject = {};
                dataSer = ($("#searchForm").serializeArray());
                $.each(dataSer,function(i,item){
                    dataObject[item.name] = item.value;
                });
                addSearch(dataObject);
                ajaxFuc();
            })
            //搜索点击出现条件
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
                            console.log($('#'+el.id).find("option:selected"))
                            searchVal = $('#'+el.id).find("option:selected").text();
                        }
                        $('.search-box').append('<span class="search-cond">'+el.name+':'+searchVal+'<i class="search-close"><img src="${ctxStatic}/images/search-close.png" alt=""></i><input type="hidden" data-pram="'+el.id+'"></span>')
                    }
                })
            }
            // 搜索条件关闭
            $('.search-close').live('click',function () {
                var para = $(this).next().attr('data-pram');
                var _this = $(this);
                if(para == 'active_date'){
                    cancelEach(_this,'active_date','inactive_date');
                }
                else if(para == 'inactive_date'){
                    cancelEach(_this,'inactive_date','active_date');
                }
                else{
                    $(this).parent().css('display','none');
                    $('#'+para).val('').trigger("change");
                }
                ajaxFuc();
            })
            //互相关联的元素的关闭事件
            function cancelEach(_this,parent,child){
                $(_this).parent().css('display','none');
                var spans =  $('.search-cond');
                $.each(spans,function (index,selector) {
                    if($(selector).find('input').attr('data-pram') == child){
                        console.log($(selector))
                        $(selector).css('display','none');
                    }
                })
                $('#'+parent).val('').trigger("change");
                $('#'+child).val('').trigger("change");
            }
            //点击清空搜索条件
            $('.activity-type').live('click',function () {
                $.each(searchCon,function (index,value) {
                    $('#'+value.id).val('').trigger('change');
                    $('.search-cond').remove();
                    ajaxFuc();
                })
            })
            //新建活动
            $('#btnNew').click(function () {
                location.href = 'saveLottery?activity_type='+para.activity_type;
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
            //活动操作事件
            function activityHandle(activityId,type){
                $.ajax({
                    url:'${ctx}/activity/activity/updateStatus',
                    type:'POST',
                    data:{
                        id:activityId,
                        type:type
                    },
                    success:function (msg) {
                        var msg = JSON.parse(msg);
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