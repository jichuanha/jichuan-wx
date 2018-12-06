<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单列表</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <%--<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">--%>
    <link rel="stylesheet" href="${ctxStatic}/page.my.css">
    <script src="${ctxStatic}/page.my.js"></script>
    <style>
        a{
            color: #3E55BD;
        }
        a:hover{
            text-decoration: none;
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
        .review-btn{
            width: 180px;
            font-size: 15px;
            line-height: 2.2;
            border-radius: 5px;
            text-align: center;
            background-color: #3F51B5;
            color: #fff;
            display: inline-block;
            letter-spacing: 10px;
            padding-left: 10px;
            margin-right: 20px;
        }
        .review-btn:hover{
            color: #fff;
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
        .mycol-10{
            width: 10%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }
        .mycol-15{
            width: 15%;
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
        .search-box{
            width: 96%;
            margin: 0 auto;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div class="wrap-header">
        <form id="searchForm"  class="form-search">
            <input id="current_page" name="current_page" type="hidden" value="1"/>
            <input id="page_size" name="page_size" type="hidden" value="10"/>
            <ul class="ul-form">
                <li>
                    <label>下单时间：</label>
                    <input id="start_data" name="start_data" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate"/>
                </li>
                <li>
                    <label>结束时间：</label>
                    <input id="end_data" name="end_data" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate" />

                </li>
                <li><label>订单编号：</label>
                    <input type="text" name="order_sn" class="mid-input" id="order_sn">
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

                <li class="clearfix"></li>
                <li class="btns">
                    <a href="#" id="btnSubmit" class="review-btn">搜索</a>
                </li>
                <li class="clearfix"></li>
            </ul>
        </form>
    </div>
    <input id="pageCount" type="hidden" value=""/>
    <div class="search-box"></div>
   <div class="order-lists">
        <ul class="lists-title clearfix">
            <li class="mycol-10">订单状态</li>
            <li class="mycol-15">所属平台</li>
            <li class="mycol-15">所属店铺</li>
            <li class="mycol-15">下单姓名</li>
            <li class="mycol-15">收件电话</li>
            <li class="mycol-15">下单金额</li>
            <li class="mycol-15">订单详情</li>
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
        var paraStr = '?activity_type='+para.activity_type;
        $.each($('.my-nav-tabs li a'),function (index,selector) {
            var oldHref = $(selector).attr('href');
            $(selector).attr('href',oldHref+paraStr);
        })
        $.ajax({
            url:'${ctx}/activity/activity/platformShopList',
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
                            shopStr[el.platform].push({name:el.shop_name,id:el.shop});
                        })
                    })
                }
            }
        })
        ajaxFuc();
        $('#platform_type').change(function () {
            $('#shop_no').html('');
            $('#shop_no').html('<option value="">请选择</option>');
            var shopValue =  $(this).val();
            $.each(shopStr,function (key,value) {
                if(key == shopValue){
                    value.forEach(function (el) {
                        $('#shop_no').append('<option value="'+el.id+'">'+el.name+'</option>');
                    })
                }
            })
            $('#shop_no').val('').trigger("change");

        })
        var searchCon = [{
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
            var dataObject = {};
            dataSer = ($("#searchForm").serializeArray());
            $.each(dataSer,function(i,item){
                dataObject[item.name] = item.value;
            });
            addSearch(dataObject);
            ajaxFuc();
        })

        $('.search-close').live('click',function () {
            var para = $(this).next().attr('data-pram');
            if(para == 'platform_type'){
                $(this).parent().css('display','none');
                var spans =  $('.search-cond');
                $.each(spans,function (index,selector) {
                    if($(selector).find('input').attr('data-pram') == 'shop_no'){
                        console.log($(selector));
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
        function ajaxFuc(nextPage) {
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
            $.ajax({
                url:'orderList',
                type:'post',
                data:dataObject,
                success:function (msg) {
                    var msg = strToJson(msg);
                    if(msg.code == 10000){
                        var data = msg.data;
                        $('.lists-show').html('');
                        var listStr = '';
                        data.list.forEach(function (el,index) {
                            listStr += '<p class="clearfix"><span class="status-name">订单编号:';
                            listStr += el.order_sn+'</span><span class="start-time">下单时间:'+el.order_time+'</span></p>';
                            listStr += ' <ul class="order-list clearfix">';
                            listStr += '<li class="mycol-10">'+el.order_status+'</li>';
                            listStr += '<li class="mycol-15">'+el.platform_type_str+'</li>';
                            listStr += '<li class="mycol-15">'+el.shop_no_str+'</li>';
                            listStr += '<li class="mycol-15">'+el.buyer_name+'</li>';
                            listStr += '<li class="mycol-15">'+el.mobile+'</li>';
                            listStr += '<li class="mycol-15">'+el.pay_amount_str+'</li>';
                            listStr += '<li class="mycol-15">查看详情</li>';
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
    })
</script>
</body>
</html>
