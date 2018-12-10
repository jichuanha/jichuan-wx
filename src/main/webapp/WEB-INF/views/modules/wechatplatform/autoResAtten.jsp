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
        /*.res-txt{*/
            /*display: block;*/
        /*}*/
        .res-img{
            display: block;
        }
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

    </style>
</head>
<body>
<ul class="nav my-nav-tabs clearfix">
    <li class="active"><a href="${ctx}/wechat_reply/link_auto_res_atten">被关注回复</a></li>
    <li><a href="${ctx}/wechat_reply/link_auto_res_key">关键词回复</a></li>
    <li><a href="${ctx}/wechat_reply/link_auto_res_rece">收到信息回复</a></li>
</ul><br/>
<div class="title">
    <h3>被关注回复 <i class="checkbox_div"><input type='checkbox' id='switch' checked><label for='switch'>自动回复开关</label></i></h3>
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
<script>
    $(function () {
        ajaxFuc();
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
        function ajaxFuc() {
            $.ajax({
                url:'${ctx}/wechat_reply/list_reply',
                type:'post',
                data:{
                    // wechat_id:$.cookie().platFormId,
                    wechat_id:10,
                    reply_type:2
                },
                success:function (msg) {
                    console.log(msg)
                }
            })
        }
            var acceptImg = {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            };
            uploadFile($('.upload-img-btn'),acceptImg,2,1)
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
                server: '${ctx}/wechat_reply/update_reply', // 上传文件的接口（替换成你们后端给的接口路径）
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
            // 文件上传过程中创建进度条实时显示。
            uploader.on( 'uploadProgress', function( file, percentage ) {
                console.log(percentage);
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

    })


</script>
</body>
</html>