<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单列表</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/page.my.css">
    <script src="${ctxStatic}/page.my.js"></script>
    <style>
        a{
            color: #3E55BD;
        }
        a:hover{
            text-decoration: none;
        }
        .my-nav-tabs{
            background-color: #F7F7F7;
            /*padding-left: 20px;*/
            margin-left: 10px;
            /*position: fixed;*/
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
        .current-data{
            display: inline-block;
            height: 30px;
            width: 50px;
            text-align: center;
            vertical-align: middle;
            background-color: #fff;
            color: #3E55BD;
            padding: 0 4px;
            border-radius: 5px;
            border: 1px solid transparent;
            margin-right: 15px;
        }
        .current-data:hover{
            text-decoration: none;
            background-color: #F2F2F2;
        }
        .current-data.active{
            border: 1px solid #3E55BD;
        }
        .order-lists{
            width: 96%;
            margin: 0 auto;

        }
        .order-lists ul{
            list-style: none;
        }
        .order-lists ul li{
            float: left;
            text-align: center;
            font-size: 14px;
        }
        .mycol-5{
            width: 5%;
        }
        .mycol-10{
            width: 10%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .mycol-15{
            width: 15%;
        }
        .mycol-20{
            width: 20%;
        }
        .lists-title li{
            background-color: #F7F7F7;
            line-height: 3;
            text-align: center;
        }
        .order-lists p{
            background-color: #F7F7F7;
            line-height: 3;
            margin-top: 10px;
            border: 1px solid #F0F0F0;
            margin-bottom: 0;
            color: #AAAAAA;
        }
        .order-lists ul{
            list-style: none;
            margin: 0;
        }
        .order-list li{
            float: left;
            text-align: center;
            border-bottom: 1px solid rgb(228, 228, 228);
            border-left: 1px solid rgb(228, 228, 228);
            line-height: 3;
            height: 40px;
            box-sizing: border-box;
        }
        .list-right{
            float: right;
            margin-right: 20px;
        }
        .order-detail{
            color: #485891;
            font-style: normal;
        }
        .status-name,.start-time,.receive-time{
            display: inline-block;
            margin-left: 40px;
            width: 20%;
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
    </style>
</head>
<body>
<ul class="nav my-nav-tabs clearfix">
    <li><a href="${ctx}/activity/activity/activity-list">活动列表</a></li>
    <li class="active"><a href="${ctx}/trade/order/order_list">订单列表</a></li>
    <li><a href="${ctx}/trade/order/order_review">订单审核</a></li>
</ul><br/>
<div class="wrap">
    <div class="wrap-header">
        <form id="searchForm"  class="form-search">
            <input id="current_page" name="current_page" type="hidden" value="1"/>
            <input id="page_size" name="page_size" type="hidden" value="10"/>
            <ul class="ul-form">
                <li><label>活动名称：</label>
                    <input type="text" name="act_name" class="mid-input" id="act_name">
                </li>
                <li><label>活动状态：</label>
                    <select name="act_status" id="act_status" class="mid-input">
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
                <li class="clearfix"></li>
                <li>
                    <label>下单时间：</label>
                    <input id="start_data" name="start_data" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate"/>
                </li>
                <li>
                    <label>结束时间：</label>
                    <input id="end_data" name="end_data" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate" />

                </li>
                <li>
                    <a href="#" class="current-data current30">近30天</a>
                    <a href="#" class="current-data current60">近60天</a>
                </li>
                <li><label>店铺平台：</label>
                    <select name="platform_type" id="platform_type" class="mid-input">
                        <option value="">请选择</option>
                    </select>
                </li>
                <li><label>所属店铺：</label>
                    <select name="shop_no" id="shop_no" class="mid-input">
                        <option value="">请选择</option>
                    </select>
                </li>
                <li><label>订单编号：</label>
                    <input type="text" name="order_sn" class="mid-input" id="order_sn">
                </li>
                <li class="clearfix"></li>
                <li class="btns">
                    <input id="btnSubmit" class="btn" type="button" value="搜索"/>
                </li>
                <li class="clearfix"></li>
            </ul>
        </form>
    </div>
    <input id="pageCount" type="hidden" value=""/>
    <p class="h3-title search-box"><i class="h3-deco"></i><span class="activity-type">活动类型2</span></p>
    <div class="order-lists">
        <ul class="lists-title clearfix">
            <li class="mycol-10">订单状态</li>
            <li class="mycol-10">绑定状态</li>
            <li class="mycol-10">店铺平台</li>
            <li class="mycol-15">所属店铺</li>
            <li class="mycol-15">订单编号</li>
            <li class="mycol-10">买家姓名</li>
            <li class="mycol-10">收货手机号</li>
            <li class="mycol-10">活动金额</li>
            <li class="mycol-10">评价截图</li>
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
        var shopStr = {};
        $('.activity-type').html('活动类型'+para.activity_type);
        var paraStr = '?activity_type='+para.activity_type;
        $.each($('.my-nav-tabs li a'),function (index,selector) {
            var oldHref = $(selector).attr('href');
            $(selector).attr('href',oldHref+paraStr);
        })
        $.ajax({
            url:'/dongyin-CRM/a/activity/activity/platformShopList',
            type:'post',
            success:function (msg) {
                var msg = strToJson(msg);
                if(msg.code == 10000){
                    var data = msg.data;
                    $.each(data,function (index,value) {
                        value.forEach(function (el,indexshop) {
                            if(indexshop == 0){
                                $('#platform_type').append('<option value="'+el.platform+'">'+el.platform_name+'</option>')
                                shopStr[el.platform] = [];
                            }
                            shopStr[el.platform].push({name:el.shop_name,id:el.id});
                        })
                    })
                }
            }
        })
        ajaxFuc();
        $('#platform_type').change(function () {
            $('#shop_no').html('<option value="">请选择</option>');
            var shopValue =  $(this).val();
            if(shopValue == ""){
                $('#shop_no').val('').trigger("change");
            }
            else{
                $.each(shopStr,function (key,value) {
                    if(key == shopValue){
                        value.forEach(function (el) {
                            $('#shop_no').append('<option value="'+el.id+'">'+el.name+'</option>')

                        })
                    }
                })
            }
            // console.log($(this).val())
        })
        var searchCon = [{
            name:'活动名称',
            type:'input',
            id:'act_name'
        },{
            name:'活动状态',
            type:'select',
            id:'act_status'
        },{
            name:'返利类型',
            type:'select',
            id:'rebate_type'
        },{
            name:'下单时间',
            type:'input',
            id:'start_data'
        },{
            name:'结束时间',
            type:'input',
            id:'end_data'
        },{
            name:'店铺平台',
            type:'select',
            id:'platform_type'
        },{
            name:'所属店铺',
            type:'select',
            id:'shop_no'
        },{
            name:'订单编号',
            type:'input',
            id:'order_sn'
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
            var para = $(this).next().attr('data-pram');
            if(para == 'platform_type'){
                $(this).parent().css('display','none');
                var spans =  $('.search-cond');
                $.each(spans,function (index,selector) {
                    if($(selector).find('input').attr('data-pram') == 'shop_no'){
                        $(selector).css('display','none');
                    }
                })
                $('#platform_type').val('').trigger("change");
                $('#shop_no').val('').trigger("change");
            }
            else{
                $(this).parent().css('display','none');
                $('#'+para).val('').trigger("change");
            }
            ajaxFuc();
        })
        $('.current30').click(function () {
            $('#start_data').val(currentDate(30).active_date);
            $('#end_data').val(currentDate(30).inactive_date);
            $('.current-data').removeClass('active');
            $(this).addClass('active');

        })
        $('.current60').click(function () {
            $('#start_data').val(currentDate(60).active_date);
            $('#end_data').val(currentDate(60).inactive_date);
            $('.current-data').removeClass('active');
            $(this).addClass('active');

        })
        // 选择开始时间方法
        $('#start_data').live('click',function () {
            var inactive_date=$dp.$('end_data');
            WdatePicker({
                onpicked:function(){
                    if($dp.$('end_data').value == ''){
                        $dp.$('end_data').value=$dp.cal.getP('y')+'-'+$dp.cal.getP('M')+'-'+$dp.cal.getP('d')+' '+(parseInt($dp.cal.getP('H'))+1)+':'+$dp.cal.getP('m')+':'+$dp.cal.getP('s');
                        inactive_date.click();
                    }

                },
                isShowClear:false,
                dateFmt:'yyyy-MM-dd HH:mm:ss',
                maxDate:'#F{$dp.$D(\'end_data\')}'
            })
        })
        //选择结束时间方法
        $('#end_data').live('click',function () {
            WdatePicker({
                minDate:'#F{$dp.$D(\'start_data\')}',
                dateFmt:'yyyy-MM-dd HH:mm:ss'
            })
        })
        $(".wrap").on("click",".img_ifram", function(){
            var src = $(this).attr("data-src");
            $('#preview-layer').remove();
            $('body').append('<div id="preview-layer" style="display:none;"><img src="' + src + '" style="width:100%;"></div>');
            layer.open({
                type: 1,
                closeBtn: 1,
                title: "信息",
                area: '640px',
                skin: 'layui-layer-nobg', //没有背景色
                shadeClose: true,
                content: $('#preview-layer')
            });
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
        function ajaxFuc(nextPage) {
            var dataObject = {};
            dataSer = ($("#searchForm").serializeArray());
            $.each(dataSer,function(i,item){
                dataObject[item.name] = item.value;
            });
            dataObject.page_type = 1;
            if(nextPage != null){
                dataObject.current_page = nextPage;
                nextPageSec = nextPage;
            }
            else{
                dataObject.current_page = 1;
                nextPageSec = 1;
            }
            dataObject.act_type = para.activity_type;
            //待改 公众号
            dataObject.wechat_id = 1;
            $.ajax({
                url:'orderListDate',
                type:'post',
                data:dataObject,
                success:function (msg) {
                    var msg = strToJson(msg);
                    if(msg.code == 10000){
                        var data = msg.data;
                        $('.lists-show').html('');
                        var listStr = '';
                        data.list.forEach(function (el,index) {
                            listStr += '<p class="clearfix"><span class="status-name">';
                            if(el.act_status == 0){
                                listStr += '未开始';
                            }
                            else if(el.act_status == 1){
                                listStr += '进行中';
                            }
                            else if(el.act_status == 2){
                                listStr += '暂停';
                            }
                            else if(el.act_status == 3){
                                listStr += '已结束';
                            }
                            listStr += '_'+el.act_name+'</span><span class="start-time">下单时间:'+el.pay_data+'</span>';
                            if(el.draw_date){
                                listStr += '<span class="receive-time">领取时间:'+el.draw_date+'</span>';
                            }
                            listStr += ' <i class="list-right">' +
                                '<a href="${ctx}/trade/order/order_detail?id='+el.id+'&activity_type='+para.activity_type+'" class="order-detail">订单详情</a>' +
                                '<input type="hidden" value="'+el.id+'">' +
                                '</i>';
                            listStr += ' <ul class="order-list clearfix">';
                            listStr += '<li class="mycol-10">'+el.status_str+'</li>';
                            listStr += '<li class="mycol-10">'+el.attention_str+'</li>';
                            listStr += '<li class="mycol-10">'+el.platform_name+'</li>';
                            listStr += '<li class="mycol-15">'+el.shop_name+'</li>';
                            listStr += '<li class="mycol-15">'+el.order_sn+'</li>';
                            listStr += '<li class="mycol-10" title="'+el.member_name+'">'+el.member_name+'</li>';
                            listStr += '<li class="mycol-10">'+el.mobile+'</li>';
                            listStr += '<li class="mycol-10">¥ '+el.act_money+'</li>';
                            listStr += '<li class="mycol-10"><a class="img_ifram" href="#" data-src="'+el.picture_url+'">点击查看</a></li>';
                            listStr += '</ul>';
                        })
                        $('.lists-show').html(listStr);
                        $('#current_page').val(nextPageSec);
                        $('#pageCount').val(data.count);
                        pageList(10,nextPageSec);
                    }
                }
            })
        }
        function currentDate(count){
            var time1 = new Date()
            time1.setTime(time1.getTime() - (24 * 60 * 60 * 1000))
            var nowDate = time1.getFullYear()+'-'+dateParrent(time1.getMonth()+1)+"-"+dateParrent(time1.getDate())+" "+dateParrent(time1.getHours())+':'+dateParrent(time1.getMinutes())+":"+dateParrent(time1.getSeconds());
            var time2 = new Date();
            time2.setTime(time2.getTime() - (24 * 60 * 60 * 1000 * count))
            var lastDate = time2.getFullYear()+'-'+dateParrent(time2.getMonth()+1)+"-"+dateParrent(time2.getDate())+" "+dateParrent(time2.getHours())+':'+dateParrent(time2.getMinutes())+":"+dateParrent(time2.getSeconds());
            return{
                active_date:lastDate,
                inactive_date:nowDate
            }
        }
        function dateParrent(s) {
            return s < 10 ? '0' + s: s;
        }
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
