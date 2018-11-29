<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>活动列表</title>
	<meta name="decorator" content="default"/>
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
			vertical-align: bottom;
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
		}
		#searchForm li{
			width: 33%;
			margin: 5px 0;
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
		#searchForm .btns{
			width: 100%;
			text-align: center;
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
		}
		.list-prompt,.activity-pause,.activity-cancel{
			color: #EA4343;
		}
		.activity-detail,
		.activity-contin{
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
	</style>

</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/activity/activity/activity-new">新建活动</a></li>
	<li class="active"><a href="${ctx}/activity/activity/activity-list">活动列表</a></li>
</ul><br/>
<div class="wrap">
	<div class="wrap-header">
		<form id="searchForm"  class="form-search">
			<input id="current_page" name="current_page" type="hidden" value="1"/>
			<input id="page_size" name="page_size" type="hidden" value="1"/>
			<ul class="ul-form">
				<li><label>活动名称：</label>
					<input type="text" name="real_name" class="mid-input" id="real_name">
				</li>
				<li><label>活动状态：</label>
					<select name="status" id="status" class="mid-input">
						<option value="">全部</option>
						<option value="0">待审核</option>
						<option value="1">已审核</option>
						<option value="2">已取消</option>
					</select>
				</li>
				<li><label>返利类型：</label>
					<select name="status" id="type" class="mid-input">
						<option value="">全部</option>
						<option value="0">待审核</option>
						<option value="1">已审核</option>
						<option value="2">已取消</option>
					</select>
				</li>
				<li>
					<label>开始时间：</label>
					<input id="start_time" name="start_time" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate"/>
				</li>
				<li>
					<label>结束时间：</label>
					<input id="end_time" name="end_time" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate" />

				</li>
				<li><label>所属店铺：</label>
					<select name="status" id="shop" class="mid-input">
						<option value="">全部</option>
						<option value="0">待审核</option>
						<option value="1">已审核</option>
						<option value="2">已取消</option>
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
	<p class="h3-title"><i class="h3-deco"></i>活动类型</p>
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
			<p>
				<span class="list-time">2018-11-05 00:00:00 - 2018-11-05 00:00:00</span>
				<span class="list-prompt">(列表根据创建时间"倒序"排列)</span>
				<i class="list-right">
					<span class="activity-cancel">取消</span>
					<span class="activity-detail">查看详情</span>
				</i>
			</p>
			<ul class="activity-list clearfix">
				<li class="mycol-10">正常</li>
				<li class="mycol-10">否</li>
				<li class="mycol-10">好评返现</li>
				<li class="mycol-15">微信/即时到账</li>
				<li class="mycol-25">函数官方专卖店,韩束卡卡专卖店</li>
				<li class="mycol-15">100/50</li>
				<li class="mycol-15">¥100/¥50</li>
			</ul>
			<p><span>2018-11-05 00:00:00 - 2018-11-05 00:00:00</span> <span>暂停-URL</span></p>
			<ul class="activity-list clearfix">
				<li class="mycol-10">正常</li>
				<li class="mycol-10">否</li>
				<li class="mycol-10">好评返现</li>
				<li class="mycol-15">微信/即时到账</li>
				<li class="mycol-25">函数官方专卖店,韩束卡卡专卖店</li>
				<li class="mycol-15">100/50</li>
				<li class="mycol-15">¥100/¥50</li>
			</ul>
			<p><span>2018-11-05 00:00:00 - 2018-11-05 00:00:00</span> <span>暂停-URL</span></p>
			<ul class="activity-list clearfix">
				<li class="mycol-10">正常</li>
				<li class="mycol-10">否</li>
				<li class="mycol-10">好评返现</li>
				<li class="mycol-15">微信/即时到账</li>
				<li class="mycol-25">函数官方专卖店</li>
				<li class="mycol-15">100/50</li>
				<li class="mycol-15">¥100/¥50</li>
			</ul>
		</div>

	</div>
	<div class="pagination">
		<ul>
		</ul>
	</div>
</div>
<script>
    $(function () {
        ajaxFuc();
        function ajaxFuc(nextPage){
            $.ajax({
                url:"activityList",
                type:"post",
                success:function (msg) {
                    var msg = strToJson(msg);
                    var data = msg.data;
                    $('.lists-show').html('');
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
                        listShowEach += '<span class="activity-detail">查看详情</span>' +
							'<input type="hidden" value="el.id"></i></p>';
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
                        listShowEach += '<li class="mycol-25" title="'+el.shop_name+'">'+el.shop_name+'</li>';
                        listShowEach += '<li class="mycol-15">100/50</li><li class="mycol-15">¥100/¥50</li></ul>';
						$('.lists-show').append(listShowEach);
                    })
                    $('#pageCount').val(data.count);
                    // pageList(10,1);
                }
            })
		}

        $('#btnSubmit').click(function () {
                console.log($('#searchForm').serialize());
        })
        // 选择开始时间方法
        $('#start_time').live('click',function () {
            var end_time=$dp.$('end_time');
            WdatePicker({
                onpicked:function(){
                    if($dp.$('end_time').value == ''){
                        $dp.$('end_time').value=$dp.cal.getP('y')+'-'+$dp.cal.getP('M')+'-'+$dp.cal.getP('d')+' '+(parseInt($dp.cal.getP('H'))+1)+':'+$dp.cal.getP('m')+':'+$dp.cal.getP('s');
                        end_time.click();
                    }

                },
                isShowClear:false,
                dateFmt:'yyyy-MM-dd HH:mm:ss',
                maxDate:'#F{$dp.$D(\'end_time\')}'
            })
        })
        //选择结束时间方法
        $('#end_time').live('click',function () {
            WdatePicker({
                minDate:'#F{$dp.$D(\'start_time\')}',
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
        pageList(10,1);

    })

</script>
</body>
</html>