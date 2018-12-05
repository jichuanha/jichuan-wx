<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/page.my.css">
    <script src="${ctxStatic}/page.my.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/webuploader-0.1.5/webuploader.css">
    <script src="${ctxStatic}/webuploader-0.1.5/webuploader.js"></script>
    <style>
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
            text-decoration: none;
        }
        #btnImport{
            background-color: #fff;
            color: #3F51B5;
            border: 1px solid #3F51B5;
            text-decoration:none;
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
            font-style: normal;
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
        .search-box{
            width: 96%;
            margin: 0 auto;
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
        .import-ul{
            list-style: none;
            width: 320px;
            margin: 0 auto;
        }
        .import-ul label{
            margin-right: 20px;
            vertical-align: text-bottom;
        }
        .import-excel label{
            margin-bottom: 0;
        }
        .import-ul li{
            position: relative;
            text-align: center;
        }
        .import-ul a,.choose-execl{
            width: 120px;
            font-size: 14px;
            line-height: 2.2;
            border-radius: 5px;
            text-align: center;
            background-color: #3F51B5;
            color: #fff;
            display: inline-block;
            margin-right: 20px;
        }
        input[type = 'file']{
            width: 120px;
        }
        .choose-execl{
            opacity: 0;
            position: absolute;
            left: 0;
            top: 0;
        }
        #chooseExcel{
            position: relative;
        }
        .import-ul li{
            margin-bottom: 10px;
        }
        .import-ul .select2-container ,.import-ul .select2-choice{
            width: 200px;
            height: 30px;
            box-sizing: border-box;
            overflow: hidden;
        }
        .import-ul a:hover{
            text-decoration: none;
        }

        .radio-box{
            margin-bottom: 10px;
        }
        .radio-box>input
        {
            display: none;
        }
        .radio-box>label{
            position: relative;
        }
        .radio-box>label::before{
            display: inline-block;
            content: "";
            width: 16px;
            height: 16px;
            margin-right: 6px;
            vertical-align: text-top;
            background: url("${ctxStatic}/images/no-checked.png") no-repeat;
            background-size: 100%;
        }
        .radio-box>input:checked+label::before{
            background: url("${ctxStatic}/images/checked.png") no-repeat;
            background-size: 100%;

        }
        .choosed-excel{
            margin-top: 10px;
        }
        .prompt-msg{
            color: #7c7c7c;
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
                <li><label>导入状态：</label>
                    <select name="status" id="status" class="mid-input">
                        <option value="">请选择</option>
                        <option value="1">校验中</option>
                        <option value="2">校验失败</option>
                        <option value="3">待发布</option>
                        <option value="4">发布失败</option>
                        <option value="5">已发布</option>
                    </select>
                </li>
                <li><label>所属平台：</label>
                    <select name="platform_type" id="platform_type" class="mid-input">
                        <option value="">请选择</option>
                    </select>
                </li>
                <li><label>所属店铺：</label>
                    <select name="shop_no" id="shop_no" class="mid-input">
                        <option value="">请选择</option>
                    </select>
                </li>
                <li>
                    <label>下单时间：</label>
                    <input id="start_data" name="start_data" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate"/>
                </li>
                <li>
                    <label>结束时间：</label>
                    <input id="end_data" name="end_data" type="text" readonly="readonly" maxlength="20" class="mid-input Wdate" />

                </li>
                <li class="clearfix"></li>
                <li class="btns">
                    <a href="#" id="btnSubmit" class="review-btn">查询</a>
                    <a href="#" id="btnImport" class="review-btn" data-toggle="modal" data-target="#myModal">导入</a>
                </li>
                <li class="clearfix"></li>
            </ul>
        </form>
    </div>
    <input id="pageCount" type="hidden" value=""/>
    <p class="search-box"></p>
    <div class="order-lists">
        <ul class="lists-title clearfix">
            <li class="mycol-20">状态</li>
            <li class="mycol-20">所属平台</li>
            <li class="mycol-20">所属店铺</li>
            <li class="mycol-20">订单数据</li>
            <li class="mycol-20">备注</li>
        </ul>
        <div class="lists-show">

        </div>
    </div>
    <div class="pagination">
        <ul>
        </ul>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">导入</h4>
                </div>
                <div class="modal-body">
                    <ul class="import-ul">
                        <li class="radio-box"><input type="radio" name="table_type" id="table_type1" value="1">
                            <label for="table_type1" style="cursor:pointer"> <span>订单</span></label>'
                            <input type="radio" name="table_type" id="table_type2" value="2">
                            <label for="table_type2" style="cursor:pointer"> <span>评价</span></label>
                            <input type="radio" name="table_type" id="table_type3" value="3">
                            <label for="table_type3" style="cursor:pointer"> <span>顾客信息</span></label></li>
                        <li>
                            <label>所属平台</label>
                            <select name="platform_type" class="mid-input platform-type">
                                <option value="">请选择</option>
                            </select>
                        </li>
                        <li>
                            <label>所属店铺</label>
                            <select name="shop_no" class="mid-input shop-no">
                                <option value="">请选择</option>
                            </select>
                        </li>
                        <li>
                            <label>其他备注</label>
                            <input type="text" class="mid-input import-message"/>
                        </li>
                        <li>
                            <%--<a href="#" id="chooseExcel">选择文件--%>
                                <%--<input href="#" class="choose-execl" type="file" value="选择文件"/>--%>
                            <%--</a>--%>
                            <a href="#" class="import-excel" id="importExcel">选择文件</a>
                        </li>
                        <li class="choosed-excel"></li>
                        <li class="prompt-msg">*请选择所有信息之后再选择文件</li>
                        </ul>'
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
<script>
    $(function () {
        var shopStr = {};
        var platName = '',shopName = '';
        $.ajax({
            url:'/dongyin-CRM/a/activity/activity/platformShopList',
            type:'post',
            async:false,
            success:function (msg) {
                var msg = strToJson(msg);
                if(msg.code == 10000){
                    var data = msg.data;
                    $.each(data,function (index,value) {
                        value.forEach(function (el,indexshop) {
                            if(indexshop == 0){
                                $('#platform_type').append('<option value="'+el.platform+'">'+el.platform_name+'</option>')
                                $('.platform-type').append('<option value="'+el.platform+'">'+el.platform_name+'</option>');

                                shopStr[el.platform] = [];
                            }
                            shopStr[el.platform].push({name:el.shop_name,id:el.shop,platform_name:el.platform_name});
                        })
                    })
                    // console.log(shopStr);
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
        $('.platform-type').change(function () {
            $('.shop-no option').remove();
            $('.shop-no').append('<option value="">请选择</option>');
            var shopValue =  $(this).val();
            $.each(shopStr,function (key,value) {
                if(key == shopValue){
                    value.forEach(function (el) {
                        $('.shop-no').append('<option value="'+el.id+'">'+el.name+'</option>');
                    })
                }
            })
            $('.shop-no').val('').trigger("change");


        })
        var uploader;
        //在点击弹出模态框的时候再初始化WebUploader，解决点击上传无反应问题
        $("#myModal").on("shown.bs.modal",function(){
            uploader = WebUploader.create({
                auto: true, // 选择文件后自动上传
                runtimeOrder: 'html5', // 直接使用html5模式，还有flash的我就忽略了..
                pick: {
                    id: '#importExcel', // 按钮元素
                    multiple: false // 是否支持文件多选，false表示只能选一个
                },
                formData:{
                    id:1
                },
                server: 'tableImport', // 上传文件的接口（替换成你们后端给的接口路径）
                accept: {
                    extensions: 'xls,xlsx', // 允许的文件后缀，不带点，多个用逗号分割，这里支持老版的Excel和新版的
                    mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'

                },
                disableGlobalDnd: true, // 禁掉全局的拖拽功能。
                fileNumLimit: 1, // 验证文件总数量, 超出则不允许加入队列
                fileSizeLimit: 30 * 1024 * 1024, // 限制所有上传文件的大小
                fileSingleSizeLimit: 30 * 1024 * 1024 // 限制单个上传文件的大小
            });

            // 当有文件被添加进队列的时候
            uploader.on( 'fileQueued', function( file ) {

            });
            uploader.on('uploadBeforeSend',function (obj,data,headers) {

            })
            // 文件上传过程中创建进度条实时显示。
            uploader.on( 'uploadProgress', function( file, percentage ) {

            });

            // 上传成功
            uploader.on('uploadSuccess', function(file, response) {
                //hideBatchDialog();
                console.log(response)
                if (response.code === 10000) {
                    layer.msg(response.msg);
                    $('#myModal').modal('hide');
                }
                else{
                    layer.msg(response.msg);
                    $('#myModal').modal('hide');
                }
            });

            // 上传失败
            uploader.on('uploadError', function(file) {
                layer.msg('上传失败');

            });
            uploader.on( 'uploadBeforeSend', function( block, data ) {
                // block为分块数据。

                // file为分块对应的file对象。
                var file = block.file;


                // 修改data可以控制发送哪些携带数据。
                data.platform_type = $('.platform-type option:selected').val();
                data.shop_no = $('.shop-no option:selected').val();
                data.table_type = $('.radio-box input[type="radio"]:checked').val();
                data.message = $('.import-message').val();
                if(data.table_type == undefined){
                    layer.msg('请选择文件类型');
                    uploader.cancelFile( file );
                }
                else if(data.platform_type == ''){
                    layer.msg('请选择所属平台');
                    uploader.cancelFile( file );
                }
                else if(data.shop_no == ''){
                    layer.msg('请选择所属店铺');
                    uploader.cancelFile( file );
                }
                else if(data.message == ''){
                    layer.msg('请填写备注');
                    uploader.cancelFile( file );
                }
            });
            // 上传完成（不论成功或失败都会执行）
            uploader.on( 'uploadComplete', function( file ) {

            });

            // 上传错误
            uploader.on('error', function(status) {
                var errorTxt = '';
                if(status == 'Q_TYPE_DENIED') {
                    errorTxt = '文件类型错误';
                } else if(status == 'Q_EXCEED_SIZE_LIMIT') {
                    errorTxt = '文件大小超出限制，请控制在30M以内哦';
                } else {
                    errorTxt = '其他错误';
                }
                alert('提示:'+ errorTxt);
            });
        });
        //关闭模态框销毁WebUploader，解决再次打开模态框时按钮越变越大问题
        $('#myModal').on('hide.bs.modal', function () {
            $('.platform-type').val("").trigger('change');
            $('.shop-no').val("").trigger('change');
            $('.radio-box input[type="radio"]:checked').attr("checked",false);
            $('.import-message').val('');
            $("#responeseText").text("");
            uploader.destroy();
        });
        var searchCon = [{
            name:'导入状态',
            type:'select',
            id:'status'
        },{
            name:'所属平台',
            type:'select',
            id:'platform_type'
        },{
            name:'所属店铺',
            type:'select',
            id:'shop_no'
        },{
            name:'下单时间',
            type:'input',
            id:'start_data'
        },{
            name:'结束时间',
            type:'input',
            id:'end_data'
        }];
        $('#btnSubmit').live('click',function () {
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
        var searchVal;
        function addSearch(dataObject){
            $('.search-box').html('');
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
        $('.release-btn').live('click',function () {
            var id = $(this).attr('data-id');
            $.ajax({
                url:'orderIssue',
                type:'post',
                data:{
                    table_id:id
                },
                success:function (msg) {
                    var msg = strToJson(msg);
                    if(msg.code == 10000){
                        layer.msg(msg.msg);
                        ajaxFuc();
                    }
                    else{
                        layer.msg(msg.msg);
                    }
                }
            })
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
            if(nextPage != null){
                dataObject.current_page = nextPage;
                nextPageSec = nextPage;
            }
            else{
                dataObject.current_page = 1;
                nextPageSec = 1;
            }
            $.ajax({
                url:'tableShow',
                type:'post',
                async:false,
                data:dataObject,
                success:function (msg) {
                    var msg = strToJson(msg);
                    if(msg.code == 10000){
                        var data = msg.data;
                        $('.lists-show').html('');
                        var listStr = '';
                        data.list.forEach(function (el,index) {
                            listStr += '<p class="clearfix"><span class="start-time">下单时间:'+el.import_date+'</span>';
                            listStr += ' <i class="list-right">';
                            if(el.status == 1){
                                listStr += '校验中';
                            }
                            else if(el.status == 2){
                                listStr += '校验失败';
                            }
                            else if(el.status == 3){
                                listStr += '<a href="#" class="release-btn" data-id="'+el.id+'">发布</a>';
                            }
                            else if(el.status == 4){
                                listStr += '发布失败';
                            }
                            else if(el.status == 5){
                                listStr += '已发布';
                            }
                            listStr += '- <a href = "orderDownLoad?table_id='+el.id+'">下载</a></i>';
                            listStr += ' <ul class="order-list clearfix">';
                            listStr += '<li class="mycol-20">'+el.status_str+'</li>';
                            $.each(shopStr[el.platform_type],function (index,value) {
                                if(index == el.shop_no){
                                    platName = value.platform_name;
                                    shopName = value.name;
                                }
                            })
                            listStr += '<li class="mycol-20">'+platName+'</li>';
                            listStr += '<li class="mycol-20">'+shopName+'</li>';
                            listStr += '<li class="mycol-20">'+el.total_num+'/'+el.success_num+'</li>';
                            listStr += '<li class="mycol-20">'+el.error_message+'</li>';
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

    })
</script>
</body>
</html>
