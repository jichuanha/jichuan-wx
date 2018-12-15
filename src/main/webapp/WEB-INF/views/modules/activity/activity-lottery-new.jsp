<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>新建活动</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/swiper/swiper.min.css">
	<script src="${ctxStatic}/swiper/swiper.min.js"></script>
	<script src="${ctxStatic}/clipboard.js"></script>
	<script src="${ctxStatic}/layer/layer.js"></script>
	<style>
		.activity-title{
			font-size: 17px;
			padding: 10px 20px;
			border-bottom: 1px solid #F2F2F2;
		}
		.activity-title a{
			display: inline-block;
			width: 20px;
			height: 20px;
			margin-right: 30px;
		}
		.activity-title i img{
			width: 100%;
		}
		a:hover{
			text-decoration: none;
		}
		body{
			font-size: 15px;
		}
		h3{
			font-size: 16px;
			background-color: #F7F7F7;
			line-height: 3;
			padding: 0 30px;
			width: 90%;
			margin: 0 auto;
			/*border: 1px solid #eee;*/
			margin-top: 20px;
			color: #8E8E8E;
			font-weight: 400;
		}
		.mid-input{
			width: 200px;
			height: 30px;
			box-sizing: border-box;
		}
		input[type="text"]{
			height: 30px;
			box-sizing: border-box;
			font-size: 14px;
		}
		.copy-btn{
			vertical-align: middle;
		}
		select{
			font-size: 14px;
		}
		label{
			font-size: 15px;
		}
		.nav{
			background-color: #fff;
		}
		.form-search .base-info ul li{
			float: none;
			/*margin: 20px 0;*/
		}
		.form-search .ul-form{
			width: 1000px;
		}
		.form-search .ul-form li{
			margin: 5px 0;
		}
		.form-search .base-info .ul-form li{
			margin: 10px 0;
		}
		.form-search .ul-form li.clearfix{
			margin: 0;
			height: 0;
		}
		.requ-span{
			color: red;
			float: none;
			margin-right: 10px;
		}
		.form-search .ul-form li label {
			width: 120px;
		}
		.font-template ul{
			overflow-y:hidden;
			overflow-x:auto;
			white-space: nowrap;
		}
		.font-template ul li{
			display: inline-table;
			/*float: left;*/
			width: 300px;
			height: 300px;
			border: 1px solid #000;
		}
		.swiper-container {
			/*margin: 0 20px;*/
			padding: 0 30px;
			width: 90%;
			margin: 0 auto;
			overflow: hidden;
			background-color: #F7F7F7;
		}
		.left-bg,.right-bg{
			width: 30px;
			height: 100%;
			background-color: #F7F7F7;
			position: absolute;
			top: 0;
			z-index:9;
		}
		.left-bg{
			left: 0;
		}
		.right-bg{
			right: 0;
		}
		.swiper-slide {
			text-align: center;
			font-size: 18px;
			background-color: #fff;
			height: 300px;
		}
		.swiper-img{
			height: 250px;
			border: 1px solid #000;
		}
		.swiper-button-next, .swiper-button-prev{
			width: 30px;
			height: 30px;
			background-size: 100%;
		}
		.swiper-button-next{
			right: 0;
		}
		.swiper-button-prev{
			left: 0;
		}
		.swiper-input{
			height: 50px;
			background-color: #F7F7F7;
			line-height: 50px;
		}
		.swiper-input>input,
		.choose-shop input
		{
			display: none;
		}
		.swiper-input>label,
		.choose-shop label{
			position: relative;
		}
		.swiper-input>label::before,
		.choose-shop label::before{
			display: inline-block;
			content: "";
			width: 16px;
			height: 16px;
			margin-right: 6px;
			vertical-align: text-top;
			background: url("${ctxStatic}/images/no-checked.png") no-repeat;
			background-size: 100%;
		}
		.swiper-input>input:checked+label::before,
		.choose-shop input:checked+label::before{
			background: url("${ctxStatic}/images/checked.png") no-repeat;
			background-size: 100%;

		}
		.choose-shop{
			width: 90%;
			padding: 0 30px;
			margin: 0 auto;
		}
		.choose-shop label{
			margin-left: 0;
		}
		.choose-shop table{
			margin-top: 20px;
		}
		.choose-shop table tr td:nth-of-type(1){
			width: 50px;
			vertical-align: text-top;
		}
		.choose-shop table tr td li{
			margin-bottom:15px;
		}
		.choose-shop table tr:nth-of-type(1) td{
			padding-bottom: 15px;
		}
		.shop-list{
			list-style: none;
		}
		.shop-list li{
			float: left;
		}
		.choose-all-p{
			padding-left: 50px;
			margin-top: 20px;
		}
		.shop-list li{
			padding-right: 30px;
		}
		.swiper-button-prev{
			background-image: url("${ctxStatic}/images/prev-btn.png");
		}
		.swiper-button-next{
			background-image: url("${ctxStatic}/images/next-btn.png");
		}
		#btnRelease{
			display: block;
			width: 200px;
			margin: 0 auto;
			background-color: #808080;
			line-height: 50px;
			color: #fff;
			text-align: center;
			letter-spacing: 5px;
			line-height: 2.2;
			border-radius: 5px;
			margin-top: 30px;
		}
		#btnRelease.active{
			background-color: #3E50B4;
		}
		.deco-box{
			position: relative;
		}
		.deco-box input{
			padding-left: 20px;
		}
		.deco-rmb{
			display: inline-block;
			/*width: 20px;*/
			height: 30px;
			position: absolute;
			top: 50%;
			margin-top: -15px;
			left: 5px;
			font-style: normal;
			font-weight: 400;
			color: #888888;
			font-size: 16px;

		}
		.form-search .prize-form li{
			float: none;
		}
		.hanle-block{
			display: inline-block;
			width: 55px;
		}
		.hanle-block a{
			width: 25px;
			display: inline-block;
		}
		.hanle-block a img{
			width: 100%;
		}
		.hanle-block .less-key-btn{
			width: 21px;
			margin-left: 2px;
		}
		.rate-unit{
			display: inline-block;
			position: relative;
			font-style: normal;
		}
		.rate-unit-con{
			position: absolute;
			right: 8px;
			top: 50%;
			height: 30px;
			margin-top: -15px;
			color: #858585;
		}
	</style>
</head>
<body>
<div class="nav">
	<p class="activity-title"><a href="javascript:history.back(-1)"><img src="${ctxStatic}/images/prev-btn.png" alt=""></a>新建活动(<span class="activity-type"></span>)</p>
	<form id="searchForm"  class="form-search">
		<h3>基本信息</h3>
		<div class="base-info">
			<ul class="ul-form">
				<li>
					<label><span class="requ-span">*</span>活动名称：</label>
					<input type="text" name="name" id="name" style="width: 422px">
				</li>
				<li>
					<label><span class="requ-span">*</span>有效时间：</label>
					<input id="active_date" name="active_date" type="text" readonly="readonly" maxlength="20" class="start-time mid-input Wdate"/>
					<span> 至 </span>
					<input id="inactive_date" name="inactive_date" type="text" readonly="readonly" maxlength="20" class="end-time mid-input Wdate" />
				</li>
				<li>
					<label><span class="requ-span">*</span>订单时效：</label>
					<input id="order_active_date" name="order_active_date" type="text" readonly="readonly" maxlength="20" class="start-time mid-input Wdate"/>
					<span> 至 </span>
					<input id="order_inactive_date" name="order_inactive_date" type="text" readonly="readonly" maxlength="20" class="end-time mid-input Wdate" />
				</li>
				<%--<li><label>URL链接：</label>--%>
				<%--<input type="text" name="real_name" id="url" style="width: 422px" readonly="readonly" value="https://hehuorentest.yyzws.com/leaf/a/login">--%>
				<%--<a href="#" class="copy-btn" data-clipboard-target="#url"> 复制</a>--%>
				<%--</li>--%>
			</ul>

		</div>
		<h3>营销类型</h3>
		<div>
			<ul class="ul-form">
				<li><label><span class="requ-span">*</span>验证方式：</label>
					<select name="audit_type" id="audit_type" class="mid-input">
						<option value="1">手机号</option>
					</select>
				</li>
				</li>
				<li><label><span class="requ-span">*</span>短信验证：</label>
					<select name="text_audit_type" id="text_audit_type" class="mid-input">
						<option value=""></option>
						<option value="0">不需要</option>
						<option value="1">需要</option>
					</select>
				</li>
				<li><label><span class="requ-span">*</span>强制关注：</label>
					<select name="is_follow" id="is_follow" class="mid-input">
						<option value=""></option>
						<option value="0">需要</option>
						<option value="1">不需要</option>
					</select>
				</li>
				<li class="clearfix"></li>
				<li><label>订单总数：</label>
					<input type="text" placeholder="不填写则不限制" class="mid-input" name="total_order" id="total_order">
				</li>
			</ul>

		</div>
		<h3>奖品设置</h3>
		<ul class="ul-form prize-form">
			<li><label><span class="requ-span">*</span>奖品设置：</label>
				<select name="prize_name"  class="mid-input prize-name">

				</select>
				<div class="hanle-block">
					<a href="javascript:;" class="add-key-btn"><img src="${ctxStatic}/images/add.png" alt=""></a>
					<a href="javascript:;" class="less-key-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>
				</div>
				<label><span class="requ-span">*</span>奖品比例：</label>
				<i class="rate-unit"><input type="text"  class="mid-input" name="prize_rate"><span class="rate-unit-con">%</span></i>
			</li>
		</ul>

		<h3>参与店铺</h3>
		<div class="choose-shop clearfix">
			<table>
				<tr>
					<td></td>
					<td style="padding-left: 25px"><input type="checkbox" id="chooseAll" /><label for="chooseAll">全选</label></td>
				</tr>
				<tr class="tb-shop">
					<td>淘宝:</td>
					<td class="tb-shop-per">
						<ul class="shop-list">


						</ul>
					</td>
				</tr>
				<tr class="jd-shop">
					<td>京东:</td>
					<td class="jd-shop-per">
						<ul class="shop-list">

						</ul>
					</td>
				</tr>
			</table>
		</div>
		<h3>活动页面</h3>
		<div class="swiper-container">
			<div class="left-bg"></div>
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<div class="swiper-img">Slide 1</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu1" value="1">
						<label for="paixu1" style="cursor:pointer"> <span>选择</span></label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 2</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu2" value="2">
						<label for="paixu2" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 3</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu3" value="3">
						<label for="paixu3" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 4</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu4" value="4">
						<label for="paixu4" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 5</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu5" value="5">
						<label for="paixu5" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 6</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu6" value="6">
						<label for="paixu6" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 7</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu7" value="7">
						<label for="paixu7" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 8</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu8" value="8">
						<label for="paixu8" style="cursor:pointer">选择</label>
					</div>
				</div>
				<div class="swiper-slide">
					<div class="swiper-img">Slide 9</div>
					<div class="swiper-input">
						<input type="radio" name="template_link" id="paixu9" value="9">
						<label for="paixu9" style="cursor:pointer">选择</label>
					</div>
				</div>
			</div>
			<!-- Add Pagination -->
			<!--<div class="swiper-pagination"></div>-->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
			<div class="right-bg"></div>

		</div>
		<a href="#" id="btnRelease">发布</a>
	</form>

</div>
<script>
    $(function () {
        var para = GetRequest();
        var prizeObj = {};
        var activityType = {}; //活动类型数据
        //获取活动类型参数
        $.ajax({
            url:'${ctx}/activity/activity/activityTypeList',
            type:'post',
            async:false,
            success:function (msg) {
                var msg = strToJson(msg);
                if(msg.code == 10000){
                    activityType = msg.data;
                    $('.activity-type').html(activityType[para.activity_type]);

                }
            }
        })
        //获取店铺列表
        $.ajax({
            url:"${ctx}/activity/activity/platformShopList",
            type:"post",
            success:function (msg) {
                var msg = strToJson(msg);
                if(msg.code == 10000){
                    var data = msg.data;
                    $('.choose-shop table').html('');
                    var shopStr = '';
                    shopStr += '<tr><td></td><td style="padding-left: 25px"><input type="checkbox" id="chooseAll" />' +
                        '<label for="chooseAll">全选</label></td></tr>';
                    $.each(data,function (index,value) {
                        value.forEach(function (el,indexshop) {
                            if(indexshop == 0){
                                shopStr += '<tr><td>'+el.platform_name+':</td><td><ul class="shop-list">';
                            }
                            shopStr += '<li>' +
                                '<input type="checkbox" id="choose'+el.id+'" value="'+el.id+'" data-name="'+el.platform_name+'"/><label for="choose'+el.id+'">'+el.shop_name+'</label>' +
                                '</li>';
                        })
                        shopStr += '</ul></td></tr>';

                    })
                    $('.choose-shop table').html(shopStr);

                }
            }
        })

		//获取奖品列表
		$.ajax({
			url:'${ctx}/activity/activityLottery/prizeTypeList',
			type:'post',
			success:function (msg) {
				var msg = JSON.parse(msg);
				if(msg.code == 10000){
				    var data = msg.data;
                    prizeObj = data;
                    $.each(prizeObj,function (key,value) {
                        $('select[name=prize_name]').append('<option value="'+key+'">'+value+'</option>')
                    })
				}
            }
		})

        $('#chooseAll').live('click',function () {
            var flag = $('#chooseAll')[0].checked;
            var shopValue = $('.shop-list input[type="checkbox"]');
            for (var i = 0; i < shopValue.length; i++) {
                shopValue[i].checked = flag
            }
        })
		$('.add-key-btn').live('click',function () {
			var lastName = $('.prize-form li:last').find('select[name=prize_name]').val();
		    var lastRate = $('.prize-form li:last').find('input[name=prize_rate]').val();
		    if(lastName != '' && lastRate != ''){
                if($('.prize-form li:last').find('input[name=prize_rate]').val() )
                    $('.prize-form li:last').find('.add-key-btn').remove();
                var prizeStr = '<li><label><span class="requ-span">*</span>奖品设置：</label>\n' +
                    '\t\t\t\t<select name="prize_name"  class="mid-input prize-name">\n' ;
                $.each(prizeObj,function (key,value) {
                    prizeStr += '<option value="'+key+'">'+value+'</option>';
                })
				prizeStr += '\t\t\t\t</select>\n' +
                    '\t\t\t\t<div class="hanle-block">\n' +
                    '\t\t\t\t\t<a href="javascript:;" class="add-key-btn"><img src="${ctxStatic}/images/add.png" alt=""></a>\n' +
                    '\t\t\t\t\t<a href="javascript:;" class="less-key-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>\n' +
                    '\t\t\t\t</div>\n' +
                    '\t\t\t\t<label><span class="requ-span">*</span>奖品比例：</label>\n' +
                    '\t\t\t\t\t\t\t\t<i class="rate-unit"><input type="text"  class="mid-input" name="prize_rate"><span class="rate-unit-con">%</span></i>\n' +
                    '\t\t\t</li>';
                $('.prize-form').append(prizeStr);
                $('select').select2();

			}
			else{
		        layer.msg('请选择奖品和比例');
			}

        })
		$('.less-key-btn').live('click',function () {
			if($('.prize-form li').length > 1){
                $(this).parents('li').remove();
			}
			if($('.prize-form li .add-key-btn').length == 0){
                $('.prize-form li:last').find('.less-key-btn').before('<a href="javascript:;" class="add-key-btn"><img src="${ctxStatic}/images/add.png" alt=""></a>')
			}
        })
        $('#searchForm input').change(function () {
            checkInput();
        })
        $('#searchForm select').change(function () {
            checkInput();
        })
        $('#btnRelease').click(function () {
            if(checkCon()){
                var dataObject = {};
                var dataSer = ($("#searchForm").serializeArray());
                $.each(dataSer,function(i,item){
                    dataObject[item.name] = item.value;
                });
                dataObject.wechat_platform_id = $.cookie().platFormId;
                var shopValue = $('.shop-list input[type="checkbox"]');
                var shopNameArr = {};
                var shopplatArr = [];
                var shopNoArr = [];
                for (var i = 0; i < shopValue.length; i++) {
                    if(shopValue[i].checked){
                        var platId = $(shopValue[i]).attr('data-name');
                        if(shopplatArr.indexOf(platId) == '-1'){
                            shopplatArr.push(platId);
                        }
                    }
                }
                shopplatArr.forEach(function (el, index) {
                    shopNameArr[el] = [];
                    $.each(shopValue,function (index2) {
                        if(shopValue[index2].checked){
                            if($(shopValue[index2]).attr('data-name') == el){
                                shopNameArr[el].push($(shopValue[index2]).next().html());
                                shopNoArr.push($(shopValue[index2]).val());
                            }
                        }
                    })
                    shopNameArr[el] = shopNameArr[el].join(',');
                })
                var shopNo = shopNoArr.join(',');
                var tempValue = $('input[name="template_link"]');
                var templateLinkArr;
                for (var i = 0; i < tempValue.length; i++) {
                    if(tempValue[i].checked){
                        templateLinkArr = $(tempValue[i]).val();
                    }
                }
                var templateLink = templateLinkArr;
                var prizeValue = $('.prize-form li');
                var prizeContent = [];
                $.each(prizeValue,function (index,value) {
                    var nameText = $(this).find('select[name=prize_name]').find("option:selected").text();
                    var rateVal = $(this).find('input[name=prize_rate]').val();
                    if(nameText != '' && rateVal != ''){
                        prizeContent.push({
                            prize_name:nameText,
                            prize_rate:rateVal/100
                        })
					}
                })
                dataObject.shop_name = JSON.stringify(shopNameArr);
                dataObject.shop_no = shopNo;
                dataObject.template_link = templateLink;
                dataObject.rebate_type = '1';
                dataObject.activity_type = para.activity_type;
                dataObject.content = JSON.stringify(prizeContent);
                console.log(dataObject);
                $.ajax({
                    url: "${ctx}/activity/activityLottery/save_Lottery",
                    type:"post",
                    data:dataObject,
                    success:function (msg) {
                        var msg = strToJson(msg);
                        if(msg.code == 10000){
                            location.href = '${ctx}/activity/activityLottery/getLotteryList?activity_type='+para.activity_type;
                        }
                        else{
                            layer.msg(msg.msg);
                        }
                    }
                })
            }
        })
        //获取接口数据 如果是字符串转json
        function strToJson(msg) {
            if(typeof msg == 'string'){
                var json = JSON.parse(msg);
                return json;
            }
            return msg;
        }
        function checkInput() {
            if(checkCon()) {
                $('#btnRelease').addClass('active');
            }
            else {
                $('#btnRelease').removeClass('active');
            }
        }
        function checkCon() {
            var dataObject = {};
            var dataSer = ($("#searchForm").serializeArray());
            $.each(dataSer,function(i,item){
                dataObject[item.name] = item.value;
            });
            var shopValue = $('.shop-list input[type="checkbox"]');
            var shopFlag;
            for (var i = 0; i < shopValue.length; i++) {
                if(shopValue[i].checked){
                    shopFlag = true;
                }
            }
			if($('select[name=prize_name]').eq(0).val() != '' && $('input[name=prize_rate]').eq(0).val() != ''){
                var prizeFlag = true;
			}
            var tempValue = $('input[name="template_link"]');
            var tempFlag;
            for (var i = 0; i < tempValue.length; i++) {
                if(tempValue[i].checked){
                    tempFlag = true;
                }
            }
            console.log(dataObject)
            if(dataObject.name != ""
                && dataObject.activity_type != ""
                && dataObject.active_date != ""
                && dataObject.inactive_date != ""
                && dataObject.order_active_date != ""
                && dataObject.order_inactive_date != ""
                && dataObject.is_follow != ""
                && dataObject.audit_type != ""
                && dataObject.text_audit_type != ""
                && dataObject.is_audit != ""
                && tempFlag
                && shopFlag
				&& prizeFlag
			) {
                return true;
            }
            else{
                return false;
            }
        }
        function menuHref(url,menuFlag,para,level) {
            var as = $("#left li a" , parent.document);
            as.each(function () {
                if($(this).attr('href') == url){
                    if(menuFlag){
                        $(this).parents('.accordion-group').find('.accordion-heading a')[0].click();
                    }
                    if(para){
                        $(this).attr('href',url+'?order_status='+para);
                    }
                    if(level == 'three'){
                        if($(this).parent().css('display') == 'none'){
                            $(this).parent().parent().siblings('a')[0].click();
                        }
                        $(this)[0].click();
                    }
                    else{
                        $(this)[0].click();
                    }

                }
            })
        }


        var swiper = new Swiper('.swiper-container', {
            slidesPerView: 5,
            spaceBetween: 15,
            // pagination: {
            //     el: '.swiper-pagination',
            //     clickable: true,
            // },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
        });



        function currentDate(interval){
            var myDate = new Date();
            //获取当前年
            var year=myDate.getFullYear();
            //获取当前月
            var month=myDate.getMonth()+1;
            //获取当前日
            var date=myDate.getDate();
            var h=myDate.getHours();       //获取当前小时数(0-23)
            var m=myDate.getMinutes();     //获取当前分钟数(0-59)
            var s=myDate.getSeconds();
            var nowDate = year+'-'+dateParrent(month)+"-"+dateParrent(date)+" "+dateParrent(h)+':'+dateParrent(m)+":"+dateParrent(s);
            if(month > interval){
                var lastDate = year+'-'+dateParrent(month-interval)+"-"+dateParrent(date)+" "+dateParrent(h)+':'+dateParrent(m)+":"+dateParrent(s);
            }
            else{
                var lastDate = (year-1)+'-'+dateParrent(month+12-interval)+"-"+dateParrent(date)+" "+dateParrent(h)+':'+dateParrent(m)+":"+dateParrent(s);
            }
            return{
                start_time:lastDate,
                end_time:nowDate
            }
        }
        function dateParrent(s) {
            return s < 10 ? '0' + s: s;
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
        // 选择开始时间方法
        $('#order_active_date').live('click',function () {
            var order_inactive_date=$dp.$('order_inactive_date');
            WdatePicker({
                onpicked:function(){
                    if($dp.$('order_inactive_date').value == ''){
                        $dp.$('order_inactive_date').value=$dp.cal.getP('y')+'-'+$dp.cal.getP('M')+'-'+$dp.cal.getP('d')+' '+(parseInt($dp.cal.getP('H'))+1)+':'+$dp.cal.getP('m')+':'+$dp.cal.getP('s');
                        order_inactive_date.click();
                    }

                },
                isShowClear:false,
                dateFmt:'yyyy-MM-dd HH:mm:ss',
                maxDate:'#F{$dp.$D(\'order_inactive_date\')}'
            })
        })
        //选择结束时间方法
        $('#order_inactive_date').live('click',function () {
            WdatePicker({
                minDate:'#F{$dp.$D(\'order_active_date\')}',
                dateFmt:'yyyy-MM-dd HH:mm:ss'
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


    })



</script>

</body>
</html>