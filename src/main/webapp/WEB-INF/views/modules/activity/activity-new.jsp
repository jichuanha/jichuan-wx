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
	</style>
</head>
<body>
<div class="nav">
	<form id="searchForm"  class="form-search">
		<h3>基本信息</h3>
		<div class="base-info">
			<ul class="ul-form">
				<li>
					<label><span class="requ-span">*</span>活动名称：</label>
					<input type="text" name="name" id="name" style="width: 422px">
				</li>
				<li>
					<label><span class="requ-span">*</span>活动类型：</label>
					<select name="activity_type" id="activity_type" class="mid-input">
						<option value="">请选择</option>
						<option value="0">待审核</option>
						<option value="1">已审核</option>
						<option value="2">已取消</option>
					</select>
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
				<li><label><span class="requ-span">*</span>强制关注：</label>
					<select name="is_follow" id="is_follow" class="mid-input">
						<option value=""></option>
						<option value="1">需要</option>
						<option value="2">不需要</option>
					</select>
				</li>
				<li><label><span class="requ-span">*</span>返利渠道：</label>
					<select name="rebate_channel" id="rebate_channel" class="mid-input">
						<option value=""></option>
						<option value="1">红包领取</option>
					</select>
				</li>
				<li class="clearfix"></li>
				<li><label><span class="requ-span">*</span>返利类型：</label>
					<input type="text" class="mid-input" id="rebate_type" name="rebate_type" disabled="disabled" value="固定金额">
				</li>
				<li><label><span class="requ-span">*</span>单笔金额：</label>
					<span class="deco-box"><i class="deco-rmb">¥</i><input type="text" placeholder="0.00" class="mid-input" name="per_amount" id="per_amount"></span>
				</li>
				<li class="clearfix"></li>
				<li><label>订单总数：</label>
					<input type="text" placeholder="不填写则不限制" class="mid-input" name="max_order_limit" id="max_order_limit">
				</li>
				<li><label>返利总额：</label>
					<input type="text" placeholder="不填写则不限制" class="mid-input" name="total_amount" id="total_amount">
				</li>
				<li class="clearfix"></li>
				<li><label><span class="requ-span">*</span>人工审核：</label>
					<select name="is_audit" id="is_audit" class="mid-input" >
						<option value=""></option>
						<option value="0">不需要</option>
						<option value="1">需要</option>
					</select>
				</li>
				<%--<li class="clearfix"></li>--%>
			</ul>

		</div>
		<h3>参与店铺</h3>
		<div class="choose-shop clearfix">
			<table>
				<tr>
					<td></td>
					<td style="padding-left: 25px"><input type="checkbox" id="chooseAll" /><label for="chooseAll">全选</label></td>
				</tr>
				<tr>
					<td>淘宝:</td>
					<td>
						<ul class="shop-list">
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb1" value="1"/><label for="choosetb1">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb2" value="2"/><label for="choosetb2">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb3" value="3"/><label for="choosetb3">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb4" value="4"/><label for="choosetb4">店铺名称1</label>

							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb5" value="5"/><label for="choosetb5">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb6" value="6"/><label for="choosetb6">店铺名称1</label>

							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb7" value="7"/><label for="choosetb7">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb8" value="8"/><label for="choosetb8">店铺名称1</label>

							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb9" value="9"/><label for="choosetb9">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" class="choose-shop-name" id="choosetb10" value="10"/><label for="choosetb10">店铺名称1</label>

							</li>

						</ul>
					</td>
				</tr>
				<tr>
					<td>京东:</td>
					<td>
						<ul class="shop-list">
							<li>
								<input type="checkbox" id="choosejd1" value="11"/><label for="choosejd1">店铺名称1</label>
							</li>
							<li>
								<input type="checkbox" id="choosejd2" value="12"/><label for="choosejd2">店铺名称1</label>

							</li>
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
		$('#chooseAll').click(function () {
            var flag = $('#chooseAll')[0].checked;
            var shopValue = $('.shop-list input[type="checkbox"]');
            for (var i = 0; i < shopValue.length; i++) {
                shopValue[i].checked = flag
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
                dataObject.per_amount = dataObject.per_amount*100;
                var shopValue = $('.shop-list input[type="checkbox"]');
                var shopNameArr = [];
                var shopNoArr = [];
                for (var i = 0; i < shopValue.length; i++) {
                    if(shopValue[i].checked){
                        shopNameArr.push($(shopValue[i]).next().html());
                        shopNoArr.push($(shopValue[i]).val());
                    }
                }
                var shopName = shopNameArr.join(',');
                var shopNo = shopNoArr.join(',');
                var tempValue = $('input[name="template_link"]');
                var templateLinkArr;
                for (var i = 0; i < tempValue.length; i++) {
                    if(tempValue[i].checked){
                        templateLinkArr = $(tempValue[i]).val();
                    }
                }
                var templateLink = templateLinkArr;
                dataObject.shop_name = shopName;
                dataObject.shop_no = shopNo;
                dataObject.template_link = templateLink;
                dataObject.rebate_type = '1';
                console.log(dataObject);
                $.ajax({
                    url: "saveActivity",
                    type:"post",
                    // async:false,
                    data:dataObject,
                    success:function (msg) {

                    }
                })
            }
        })
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
            var tempValue = $('input[name="template_link"]');
            var tempFlag;
            for (var i = 0; i < tempValue.length; i++) {
                if(tempValue[i].checked){
                    tempFlag = true;
                }
            }
            if(dataObject.name != ""
                && dataObject.activity_type != ""
                && dataObject.active_date != ""
                && dataObject.inactive_date != ""
                && dataObject.order_active_date != ""
                && dataObject.order_inactive_date != ""
                && dataObject.is_follow != ""
                && dataObject.rebate_channel != ""
                && dataObject.per_amount != ""
                && dataObject.is_audit != ""
                && tempFlag
                && shopFlag) {
                return true;
            }
            else{
                return false;
            }
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

        var clipboard = new ClipboardJS('.copy-btn');

        clipboard.on('success', function(e) {
            layer.msg('已复制到粘贴板上');
        });

        clipboard.on('error', function(e) {
            console.log(e);
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


    })



</script>

</body>
</html>