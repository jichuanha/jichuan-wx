<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>自动回复</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/webuploader-0.1.5/webuploader.css">
    <script src="${ctxStatic}/webuploader-0.1.5/webuploader.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/jquery.page/page.css">
    <script src="${ctxStatic}/jquery.page/page.js"></script>
    <style>
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
        .title{
            border-bottom: 1px solid #E4E4E4;
            padding-bottom: 30px;
            margin: 10px 20px;
        }
        .title h3{
            font-size: 20px;
            font-weight: 400;
            line-height: 1;
            padding-bottom: 20px;
        }
        .title p{
            color: #9A9A9A;
            font-size: 14px;
            margin-bottom: 0;
        }
        /*checkbox开关*/
        .title .checkbox_div{
            display: inline-block;
            margin-left: 10px;
            vertical-align: text-bottom;
        }
        .checkbox_div  input[type=checkbox]{
            height: 0px;
            width: 0px;
            visibility: hidden;
        }
        .checkbox_div label{
            position: relative;
            width: 42px;
            height: 22px;
            border: 1px solid #C9C9C9;
            outline: 0;
            border-radius: 20px;
            box-sizing: border-box;
            background-color: #C9C9C9;
            transition: background-color 0.3s, border 0.3s;
            cursor: pointer;
            text-indent: -9999px;
        }
        .checkbox_div label::before {
            content: " ";
            position: absolute;
            top: 0;
            left: 0;
            width: 20px;
            height: 20px;
            border-radius: 20px;
            background-color: #FFFFFF;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.4);
            transition: all 0.25s ease-in-out;
        }
        .checkbox_div input:checked + label {
            background: #3F51B5;
            border: 1px solid #3F51B5;
        }
        .checkbox_div input:checked + label:before {
            left: calc(100%);
            transform: translateX(-100%);
        }
        .checkbox_div label:active:before{
            width:20px;
        }
        .res-content{
            width: 96%;
            margin: 0 auto;
        }
        .res-head{
            list-style: none;
            display: block;
            border: 1px solid #E4E4E4;
            border-bottom: none;
            margin: 0;
            padding-left: 20px;
        }
        .res-head li{
            float: left;
            margin-right: 40px;
            font-size: 15px;
            line-height: 3.5;
            cursor: pointer;
        }
        .res-head li.active{
            color: #3F51B5;
        }
        .res-input{
            padding: 14px 20px;
            outline: 0;
            word-wrap: break-word;
            -webkit-hyphens: auto;
            -ms-hyphens: auto;
            hyphens: auto;
            background-color: #FFFFFF;
            height: 238px;
            overflow-y: auto;
            border-bottom: 1px solid #E4E4E4;
            font-size: 15px;
        }
        .res-msg{
            height: 50px;
            line-height: 50px;
        }
        .right-msg{
            float: right;
            color: #6B6B6B;
            font-size: 14px;
            margin-right: 20px;
        }
        .res-iframe{
            /*width: 96%;*/
            border: 1px solid #E4E4E4;
            height: 318px;
            display: none;
        }
        .res-txt{
            display: block;
        }
        /*.res-img{*/
        /*display: block;*/
        /*}*/
        .res-btn{
            width: 96%;
            margin: 0 auto;
            margin-top: 20px;
        }
        .res-btn a{
            width: 120px;
            font-size: 15px;
            line-height: 2.2;
            border-radius: 5px;
            text-align: center;
            display: inline-block;
            margin-right: 20px;
        }
        .review-btn{
            background-color: #3F51B5;
            color: #fff;
            border: 1px solid transparent;
        }
        .review-btn:hover{
            color: #fff;
            text-decoration: none;
        }
        .delete-btn{
            background-color: #fff;
            color: #3F51B5;
            border: 1px solid #3F51B5;
        }
        .delete-btn:hover{
            color: #3F51B5;
            text-decoration: none;
        }
        .res-block{
            padding: 17px 20px;
        }
        .res-box{
            width: 49%;
            height: 278px;
            border: 2px dotted #E4E8EB;
            box-sizing: border-box;
            float: left;
            margin: 0;
            position: relative;
            text-align: center;
        }
        .res-box:hover{
            border-color: #B3B3B3;
        }
        .left-res-box{
            margin-right: 2%;
        }
        .res-upload-btn{
            display: inline-block;
            vertical-align: middle;
            color: #9A9A9A;
            margin: 0 10px;
            text-decoration: none;
            margin-top: 100px;
            line-height: 2.2;
            font-size: 16px;
        }
        .res-upload-btn::before{
            content: " ";
            display: block;
            width: 48px;
            height: 48px;
            margin: 0 auto 5px;
            background: transparent url(https://res.wx.qq.com/mpres/en_US/htmledition/pages/modules/msg_sender/images/icon36_add_gray.png) no-repeat 0 0;
            background-size: 100%;
        }
        .res-upload-btn:hover{
            color: #9A9A9A;
            text-decoration: none;
        }
        .webuploader-pick{
            background-color: transparent;
            color: #9A9A9A;
            font-size: 16px;
            vertical-align: bottom;
            padding-top: 80px;
            margin-top: -75px;
        }
        #sound-select .webuploader-pick{
            color: #fff;
            height: 30px;
            padding: 0;
            line-height: 30px;
            font-size: 15px;
            margin: 0 ;
        }
        #myModalArtcle {
            width: 800px;
            margin-left: -400px;
        }

        #list-block {
            padding: 50px 0;
            overflow: hidden;
        }

        .article-block .item {
            width: 325px;
            margin-right: 20px;
            margin-bottom: 20px;
            float: left;
            border: 1px solid #eee;
            padding: 20px;
            position: relative;
            box-sizing: border-box;
        }

        .article-block .item img {
            display: block;
            width: 100%;
            height: 200px;
            margin: 20px 0;
        }

        .article-block .item.active::after ,
        .pic-block .item.active::after
        {
            content: url("${ctxStatic}/images/choose.png");
            text-align: center;
            line-height: 390px;
            position: absolute;
            top: 0;
            left: 0;
            width: 325px;
            height: 390px;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 2;
        }

        .choosed-mater {
            border-bottom: 1px solid #E4E8EB;
        }

        .choosed-mater img {
            max-width: 300px;
            max-height: 300px;
        }
        .choosed-maters{
            border: 1px solid #E4E4E4;
            height: 300px;
        }
        .choosed-maters .item {
            float: none;
        }

        .choosed-maters .item.active::after {
            content: '';
            background-color: transparent;
        }
        .sound-block{
            width: 100%;
        }
        .sound-block th{
            text-align: center;
        }
        .sound-block td{
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            /*width: 25%;*/
            text-align: center;
        }
        .sound-block td .radio-input{
            margin: 0;
            margin-left: 10px;
        }
        .pic-block .item{
            width: 50%;
            height: 200px;
            float: left;
            text-align: center;
            position: relative;
        }
        .pic-block .item img{
            height: 100%;
        }
        .pic-block .item.active::after{
            height: 200px;
            width: 100%;
            line-height: 250px;
        }
        .radio-input {
            height: 50px;
            line-height: 50px;
            float: left;
            margin-right: 10px;
        }

        .radio-input > input {
            display: none;
        }

        .radio-input > label {
            position: relative;
        }

        .radio-input > label::before {
            display: inline-block;
            content: "";
            width: 16px;
            height: 16px;
            margin-right: 6px;
            vertical-align: text-top;
            background: url("${ctxStatic}/images/no-checked.png") no-repeat;
            background-size: 100%;
        }

        .radio-input > input:checked + label::before {
            background: url("${ctxStatic}/images/checked.png") no-repeat;
            background-size: 100%;

        }
        .img-choosed{
            display: inline-block;
            height: 300px;
        }
        .img-choosed img{
            height: 300px;
        }
        .less-key-btn{
            display: inline-block;
            width: 30px;
            margin-left: 30px;
        }
        .less-key-btn img{
            width: 100%;
        }
    </style>
</head>
<body>
<ul class="nav my-nav-tabs clearfix">
    <li><a href="${ctx}/wechat_reply/link_auto_res_atten">被关注回复</a></li>
    <li><a href="${ctx}/wechat_reply/link_auto_res_key">关键词回复</a></li>
    <li class="active"><a href="${ctx}/wechat_reply/link_auto_res_rece">收到信息回复</a></li>
</ul><br/>
<div class="title">
    <h3>收到消息回复 <i class="checkbox_div"><input type='checkbox' id='switch' checked><label for='switch'>自动回复开关</label></i></h3>
    <p>通过编辑内容或关键词规则，快速进行自动回复设置。</p>
    <p>关闭自动回复之后，将立即对所有用户生效。</p>
</div>
<div class="res-content">
    <ul class="res-head clearfix">
        <li class="choose-txt active" data-name="txt">文字</li>
        <li class="choose-img" data-name="img">图片</li>
        <li class="choose-voice" data-name="voice">语音</li>
    </ul>
    <div class="res-iframe res-txt">
        <div class="res-input" contenteditable="true"></div>
        <div class="res-msg clearfix">
            <span class="right-msg">还可以输入 <span class="last-num">600</span> 字，换下Enter换行</span>
        </div>
    </div>
    <div class="res-iframe res-img">
        <div class="img-block clearfix res-block">
            <div class="select-img res-box left-res-box">
                <a href="javascript:;" class="select-img-btn res-upload-btn">从素材库中选择</a>
            </div>
            <div class="upload-img res-box">
                <a href="javascript:;" class="upload-img-btn res-upload-btn">上传图片</a>
            </div>
        </div>
    </div>
    <div class="res-iframe res-voice">
        <div class="voice-block clearfix res-block">
            <div class="select-voice res-box left-res-box">
                <a href="javascript:;" class="select-voice-btn res-upload-btn">从素材库中选择</a>
            </div>
            <div class="upload-voice res-box">
                <a href="javascript:;" class="upload-voice-btn res-upload-btn">上传语音</a>
            </div>
        </div>
    </div>

</div>
<div class="res-btn">
    <a href="#" class="review-btn">保存</a>
    <a href="#" class="delete-btn">删除回复</a>
</div>
<div class="modal fade" id="myModalArtcle" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    选择素材
                </h4>
            </div>
            <div class="modal-body">
                <div class="content-body">

                </div>
                <div id="pagenation"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary choose-mater">
                    确定
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalArtcleLabel">
                    语音上传
                </h4>
            </div>
            <form id="sound-form">
                <div class="modal-body">
                    <div><label>语音名称：</label><input class="input-large" name="title" type="text" required></div>
                    <div style="display: none;"><input name="cover" class="input-large" disabled></div>
                    <div><label>语音文件：</label><div class="btn btn-primary" id="sound-select">选择语音</div>
                        <span id="percent" style="display: none;"></span>
                    </div>
                    <div style="color:red;">格式支持mp3、wma、wav、amr，文件大小不超过30M，
                        语音时长不超过30分钟</div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <button class="btn btn-primary">
                        保存
                    </button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script>
    $(function () {
        ajaxFuc();
        uploadPic();
        $('.res-head li').live('click',function () {
            $('.res-head li').removeClass('active');
            $(this).addClass('active');
            $('.res-iframe').css('display','none');
            $('.res-'+$(this).attr('data-name')).css('display','block');
        })
        $('.res-input').on('change',function () {
            $('.last-num').html(600-parseInt($('.res-input')[0].innerText.length));
        })
        $('body').on('focus', '[contenteditable]', function() {
            var $this = $(this);
            $this.data('before', $this.html());
        }).on('blur keyup paste input', '[contenteditable]', function() {
            $this = $(this);
            if ($this.data('before') !== $this.html()) {
                $this.data('before', $this.html());
                $this.trigger('change');
            }
        });
        //获取列表(是否有数据)
        function ajaxFuc() {
            $.ajax({
                url:'${ctx}/wechat_reply/list_reply',
                type:'post',
                data:{
                    wechat_id:$.cookie().platFormId,
                    // wechat_id:10,
                    reply_type:2
                },
                success:function (msg) {
                    var msg = JSON.parse(msg);
                    if(msg.data.list.length > 0){

                    }
                    else{

                    }
                }
            })
        }

        var acceptImg = {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        };
        // uploadFile($('.upload-img-btn'),acceptImg,2,1)
        //上传方法
        function uploadFile(selector,acceptImg,reply_type,content_type) {
            var uploader = WebUploader.create({
                auto: true, // 选择文件后自动上传
                runtimeOrder: 'html5', // 直接使用html5模式，还有flash的我就忽略了..
                pick: {
                    id: selector, // 按钮元素
                    multiple: false // 是否支持文件多选，false表示只能选一个
                },
                formData:{id:1},
                server: '${ctx}/wechat_reply/upload', // 上传文件的接口（替换成你们后端给的接口路径）
                accept: acceptImg,
                disableGlobalDnd: true, // 禁掉全局的拖拽功能。
                fileNumLimit: 1, // 验证文件总数量, 超出则不允许加入队列
                fileSizeLimit: 30 * 1024 * 1024, // 限制所有上传文件的大小
                fileSingleSizeLimit: 30 * 1024 * 1024 // 限制单个上传文件的大小
            });

            // 当有文件被添加进队列的时候
            uploader.on( 'fileQueued', function( file ) {
            });
            uploader.on( 'uploadBeforeSend', function( block, data ) {
                // block为分块数据。

                // file为分块对应的file对象。
                var file = block.file;


                // 修改data可以控制发送哪些携带数据。
                data.reply_type = reply_type;
                data.content_type = content_type;
            });

            // 上传成功
            uploader.on('uploadSuccess', function(file, response) {
                //hideBatchDialog();
                if (response.code === 10000) {
                    // 只要文件上传成功就可以了直接请求检测接口

                } else {
                    layer.msg(response.msg);
                }
            });

            // 上传失败
            uploader.on('uploadError', function(file) {
                alert('上传失败');
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
                layer.msg('提示:'+ errorTxt);
            });
        }

        $('.select-img-btn').live('click',function () {
            getPic();
            $('#myModalArtcle').modal('show');
        })
        $('.select-voice-btn').live('click',function () {
            getSound();
            $('#myModalArtcle').modal('show');
        })
        $('.item').live('click', function () {
            if($(this).hasClass('active')){
                $(this).removeClass('active');
            }
            else{
                $.each($('.item'),function () {
                    $(this).removeClass('active');
                })
                $(this).addClass('active');
            }
        })
        $('.choose-mater').live('click',function () {
            $.each($('.res-head li'),function () {
                if($(this).hasClass('active')){
                    var name = $(this).attr('data-name');
                    if(name == 'img'){
                        var para = $('.pic-block .item.active').attr('data-para');
                        var paraJson = JSON.parse(para);
                        $('.img-block').html('<div data-para="'+para+'">' +
                            '<i class="img-choosed"><img src="//yiyezi.yyzws.com/ex/'+paraJson.cover_picture+'"/></i>' +
                            '<a href="javascript:;" class="less-key-btn less-img-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>' +
                            '</div>')
                    }
                    else if(name == 'voice'){
                        var para = $('.sound-block input[name=sound]:checked').attr('data-para');
                        var paraJson = JSON.parse(para);
                        $('.voice-block').html('<p data-para="'+para+'">已选择:语音名称:'+paraJson.title+'&nbsp;&nbsp;文件名称:'+paraJson.cover_picture+'<a href="javascript:;" class="less-key-btn less-vedio-btn"><img src="${ctxStatic}/images/less.png" alt=""></a></p>');
                    }
                    $('#myModalArtcle').modal('hide');
                }
            })
        })
        $('.less-img-btn').live('click',function () {
            $('.img-block').html('<div class="select-img res-box left-res-box">\n' +
                '                <a href="javascript:;" class="select-img-btn res-upload-btn">从素材库中选择</a>\n' +
                '            </div>\n' +
                '            <div class="upload-img res-box">\n' +
                '                <a href="javascript:;" class="upload-img-btn res-upload-btn">上传图片</a>\n' +
                '            </div>');
            uploadPic();
        })
        $('.less-vedio-btn').live('click',function () {
            $('.voice-block').html('<div class="select-voice res-box left-res-box">\n' +
                '                <a href="javascript:;" class="select-voice-btn res-upload-btn">从素材库中选择</a>\n' +
                '            </div>\n' +
                '            <div class="upload-voice res-box">\n' +
                '                <a href="javascript:;" class="upload-voice-btn res-upload-btn">上传语音</a>\n' +
                '            </div>');
        })
        var params = {
            current_page: 1,
            page_size: 50,
            wechat_id: $.cookie('platFormId'),
        };

        var listArr = [];
        var count = 0;
        var btnType = '';
        var editIndex = 0;
        var hasInit = false;

        function getArtcleData() {
            params.type = 4;
            $.ajax({
                url: '${ctx}/wechat_material/list_material',
                type: 'POST',
                data: params,
                success: function (data) {
                    typeof data != 'object' && (data = JSON.parse(data));
                    listArr = listArr.concat(data.data.list);
                    count = data.data.count;
                    var _html = [];
                    data.data.list.forEach(function (item, index) {
                        _html.push('<div class="item" data-para=\'{"id":"' + item.id + '","title":"' + item.title + '","article_uri":"' + item.article_uri + '","brief":"' + item.brief + '","uri":"' + item.uri + '","cover_picture":"' + item.cover_picture + '","content":"' + item.content + '","type":"' + item.type + '"}\'>\n' +
                            '            <div class="opration">\n' +
                            '                <i class="icon-remove-sign" data-index="' + index + '"></i>\n' +
                            '                <i class="icon-edit" data-index="' + index + '"></i>\n' +
                            '            </div>\n' +
                            '            <div>\n' +
                            '                <div style="font-size:20px;">' + item.title + '</div>\n' +
                            '                <div>2018-12-05</div>\n' +
                            '            </div>\n' +
                            '            <img src="//yiyezi.yyzws.com/ex/' + item.cover_picture + '">\n' +
                            '            <p class="desc">' + item.brief + '</p>\n' +
                            '            <div><span class="floatL">阅读原文</span><span class="floatR"><i class="icon-chevron-right"></i></span></div>\n' +
                            '        </div>');
                    })
                    $('#myModalArtcle .content-body').html('<div id="list-block" class="article-block"></div>');
                    $('#myModalArtcle .content-body #list-block').html(_html.join(''));
                    if (!hasInit) {
                        var obj = {
                            obj_box: '#pagenation',
                            total_item: count,
                            per_num: 50,
                            current_page: '1',
                            change_content: function (per_num, current_page) {
                                if (hasInit) {
                                    params.page = current_page;
                                    getArtcleData();
                                }
                            }
                        };
                        page_ctrl(obj);
                        hasInit = true;
                    }
                }
            })
        }

        function getSound() {
            params.type = 3;
            $.ajax({
                url: '${ctx}/wechat_material/list_material',
                type: 'POST',
                data: params,
                success: function (data) {
                    typeof data != 'object' && (data = JSON.parse(data));
                    listArr = listArr.concat(data.data.list);
                    count = data.data.count;
                    var _html = '';
                    _html += '<table class="sound-block"><tr>' +
                        '        <th>选择</th>' +
                        '        <th>语音名称</th>' +
                        '        <th>文件名称</th>' +
                        '        <th>创建时间</th>' +
                        '    </tr>';
                    data.data.list.forEach(function (item, index) {
                        _html += '<tr><td><div class="radio-input">\n' +
                            '         <input type="radio" name="sound" id="sound'+index+'" value="index" data-para=\'{"id":"' + item.id + '","title":"' + item.title + '","article_uri":"' + item.article_uri + '","brief":"' + item.brief + '","uri":"' + item.uri + '","cover_picture":"' + item.cover_picture + '","content":"' + item.content + '","type":"' + item.type + '"}\'>\n' +
                            '         <label for="sound'+index+'" style="cursor:pointer"></label>\n' +
                            '     </div></td>';
                        _html +=
                            '<td>' + item.title + '</td>' +
                            '<td>' + item.cover_picture + '</td>' +
                            '<td>' + item.create_date + '</td></tr>'
                    })
                    _html += '</table>';
                    $('#myModalArtcle .content-body').html(_html);
                    $('.total').html('共同' + data.data.count + '条');
                    if (!hasInit) {
                        var obj = {
                            obj_box: '#pagenation',
                            total_item: count,
                            per_num: 50,
                            current_page: '1',
                            change_content: function (per_num, current_page) {
                                if (hasInit) {
                                    params.page = current_page;
                                    getSound();
                                }
                            }
                        };
                        page_ctrl(obj);
                        hasInit = true;
                    }
                }
            })
        }

        function getPic() {
            var params = {
                current_page: 1,
                page_size: 50,
                wechat_id: $.cookie('platFormId'),
            };
            params.type = 1;
            $.ajax({
                url:'${ctx}/wechat_material/list_material',
                type:'POST',
                data:params,
                success:function(data){
                    typeof data != 'object'&& (data = JSON.parse(data));
                    listArr = listArr.concat(data.data.list);
                    count = data.data.count;
                    var _html=[];
                    data.data.list.forEach(function(item,index){
                        _html.push('<div class="item" data-para=\'{"id":"' + item.id + '","title":"' + item.title + '","article_uri":"' + item.article_uri + '","brief":"' + item.brief + '","uri":"' + item.uri + '","cover_picture":"' + item.cover_picture + '","content":"' + item.content + '","type":"' + item.type + '"}\'>\n' +
                            '            <img src="//yiyezi.yyzws.com/ex/'+ item.cover_picture+'">\n' +
                            '        </div>');
                    })
                    $('.content-body').html('<div id="list-block" class="pic-block"></div>');
                    $('.pic-block').html(_html.join(''));
                    $('.total').html('共同'+ data.data.count +'条');
                    if(!hasInit){
                        var obj = {
                            obj_box: '#pagenation',
                            total_item: count,
                            per_num: 50,
                            current_page: '1',
                            change_content: function(per_num, current_page) {
                                if(hasInit){
                                    params.page = current_page;
                                    getPic();
                                }
                            }
                        };
                        page_ctrl(obj);
                        hasInit=true;
                    }
                }
            })
        }

        function uploadPic() {
            var uploader = WebUploader.create({
                auto: true, // 选择文件后自动上传
                runtimeOrder: 'html5', // 直接使用html5模式，还有flash的我就忽略了..
                pick: {
                    id: '.upload-img-btn', // 按钮元素
                    multiple: false // 是否支持文件多选，false表示只能选一个
                },
                server: '${ctx}/wechat/upload', // 上传文件的接口（替换成你们后端给的接口路径）
                accept: {
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*',
                },
                disableGlobalDnd: true, // 禁掉全局的拖拽功能。
                fileNumLimit: 1, // 验证文件总数量, 超出则不允许加入队列
                fileSizeLimit: 30 * 1024 * 1024, // 限制所有上传文件的大小
                fileSingleSizeLimit: 30 * 1024 * 1024,// 限制单个上传文件的大小
            });
            uploader.on('uploadSuccess',function(file,data){
                typeof data != 'object'&& (data = JSON.parse(data));
                var url = '${ctx}/wechat_material/save_material';
                var list = {
                    title:'图片',
                    wechat_id:$.cookie('platFormId'),
                    type:'1',
                    cover_picture:data.data,
                };
                $.post(url,list,function (data) {
                    typeof data != 'object'&& (data = JSON.parse(data));
                    if(data.code=='10000'){
                        // window.location.reload();
                        console.log(1);
                        $('.img-block').html('<div data-para="'+JSON.stringify(list)+'">' +
                            '<i class="img-choosed"><img src="//yiyezi.yyzws.com/ex/'+list.cover_picture+'"/></i>' +
                            '<a href="javascript:;" class="less-key-btn less-img-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>' +
                            '</div>');

                    }else{
                        layer.open({
                            content:data.msg
                        })
                    }
                })
            });
        }

        $('.upload-voice-btn').live('click',function () {
            $("#myModal").modal('show');
        })
        $("#myModal").on('shown.bs.modal',function () {
            var uploader = ''
            try {
                uploader.destroy();
            }catch (e) {}
            uploader = WebUploader.create({
                auto: true, // 选择文件后自动上传
                runtimeOrder: 'html5', // 直接使用html5模式，还有flash的我就忽略了..
                pick: {
                    id: '#sound-select', // 按钮元素
                    multiple: false // 是否支持文件多选，false表示只能选一个
                },
                server: '${ctx}/wechat/upload', // 上传文件的接口（替换成你们后端给的接口路径）
                accept: {
                    extensions: 'mp3,wma,wav,amr',
                    mimeTypes: 'audio/*',
                },
                disableGlobalDnd: true, // 禁掉全局的拖拽功能。
                fileNumLimit: 1, // 验证文件总数量, 超出则不允许加入队列
                fileSizeLimit: 30 * 1024 * 1024, // 限制所有上传文件的大小
                fileSingleSizeLimit: 30 * 1024 * 1024,// 限制单个上传文件的大小
            });
            uploader.on('uploadSuccess',function(file,data){
                typeof data != 'object'&& (data = JSON.parse(data));
                if(data.code==10000){
                    $('input[name=cover]').val(data.data);
                    $('#percent').show().html('文件上传完毕');
                }else{
                    $('#percent').show().html(data.msg);
                }
            });

            uploader.on( 'uploadProgress', function( file, percentage ) {
                if(percentage!=1){
                    var per = percentage.toFixed(4)*100;
                    $('#percent').show().html('上传进度:'+per+'%');
                }else{
                    $('#percent').show().html('文件处理中');
                }
            });
            $("#sound-form").validate({
                submitHandler:function(){
                    if(!$('input[name="cover"]').val()){
                        layer.open({
                            content:'请上传语音文件',
                        });
                        return;
                    }
                    var url = '${ctx}/wechat_material/save_material';
                    var list = {
                        title:$('input[name="title"]').val(),
                        wechat_id:$.cookie('platFormId'),
                        type:'3',
                        cover_picture:$('input[name="cover"]').val(),
                    };
                    $.post(url,list,function (data) {
                        typeof data != 'object'&& (data = JSON.parse(data));
                        if(data.code=='10000'){
                            $('.voice-block').html('<p data-para="'+JSON.stringify(list)+'">已选择:语音名称:'+list.title+'&nbsp;&nbsp;文件名称:'+list.cover_picture+'<a href="javascript:;" class="less-key-btn less-vedio-btn"><img src="${ctxStatic}/images/less.png" alt=""></a></p>');
                            $('#myModal').modal('hide');
                        }else{
                            layer.open({
                                content:data.msg
                            })
                        }
                    })
                }
            });
        });

    })


</script>
</body>
</html>