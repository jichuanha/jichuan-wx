<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/12/7 0007
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>Title</title>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/jquery.page/page.css">
    <script src="${ctxStatic}/jquery.page/page.js"></script>
    <style>
        .my-nav-tabs {
            background-color: #F7F7F7;
            /*padding-left: 20px;*/
            margin-left: 10px;
            /*position: fixed;*/
        }

        .my-nav-tabs li {
            float: left;
            font-size: 16px;
            line-height: 3;
            margin: 0 30px;
            padding: 0 20px;
            text-align: center;
            color: #000;
        }

        .my-nav-tabs li a {
            color: #000;
        }

        .my-nav-tabs li.active a {
            border-bottom: 5px solid #3F51B5;
        }

        .my-nav-tabs li a:hover {
            background-color: #F7F7F7;
        }

        .title {
            border-bottom: 1px solid #E4E4E4;
            padding-bottom: 30px;
            margin: 10px 20px;
        }

        .title h3 {
            font-size: 20px;
            font-weight: 400;
            line-height: 1;
            padding-bottom: 20px;
        }

        .title p {
            color: #9A9A9A;
            font-size: 14px;
            margin-bottom: 0;
        }

        /*checkbox开关*/
        .title .checkbox_div {
            display: inline-block;
            margin-left: 10px;
            vertical-align: text-bottom;
        }

        .checkbox_div input[type=checkbox] {
            height: 0px;
            width: 0px;
            visibility: hidden;
        }

        .checkbox_div label {
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

        .checkbox_div label:active:before {
            width: 20px;
        }

        .btns {
            width: 96%;
            margin: 0 auto;
        }

        .rules-list {
            width: 96%;
            margin: 0 auto;
            margin-top: 20px;
        }

        .rules-list th {
            font-size: 16px;
            line-height: 2.5;
            font-weight: 400;
            background-color: rgba(242, 242, 242, 1);
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

        .add ul {
            list-style: none;
            font-size: 15px;
        }

        .add ul li {
            margin: 10px 0;
        }

        .add ul li label {
            width: 120px;
            font-size: 15px;
            vertical-align: top;
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
        .add-content-btn{
            width: 30px;
            margin-top: 10px;
        }

        .add-content-btn img{
            width: 100%;
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
        }

        .btns a{
            width: 150px;
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
        .btns a:hover{
            color: #fff;
            text-decoration: none;
        }
        .btns .cancel-btn{
            background-color: #fff;
            color: #3F51B5;
            border: 1px solid #3F51B5;
        }
        .btns .cancel-btn:hover{
            color: #3F51B5;
        }
        .btns .add-btn{
            letter-spacing: 0;
            padding-left: 0;
        }
        .add{
            /*display: none;*/
        }
        .res-head{
            list-style: none;
            display: block;
            border: 1px solid #E4E4E4;
            border-bottom: none;
            margin: 0;
            padding-left: 20px;
            width: 800px;
        }
        .res-head li{
            width: 120px;
            float: left;
            margin-right: 40px;
            font-size: 15px;
            line-height: 2.5;
            cursor: pointer;
        }
        .res-head li.active{
            color: #3F51B5;
        }
        .keys-block{
            display: inline-block;
        }
        .add-key-btn,.less-key-btn{
            width: 30px;
            display: inline-block;
            vertical-align: super;
        }
        .add-key-btn img,.less-key-btn img{
            width: 100%;
        }
        .less-key-btn{
            width: 25px;
            margin-left: 2px;
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
        .content-block{
            margin-bottom: 20px;
        }
        .content-block:last-of-type{
            padding-bottom: 20px;
            border-bottom: 1px solid #E4E4E4;
        }
        .img-choosed{
            display: inline-block;
            height: 300px;
        }
        .img-choosed img{
            height: 100%;
        }
        .less-img-btn{
            display: inline-block;
            width: 30px;
            margin-left: 30px;
        }
        .less-img-btn img{
            width: 100%;
        }
    </style>
</head>
<body>
<ul class="nav my-nav-tabs clearfix">
    <li><a href="${ctx}/wechat_reply/link_auto_res_atten">被关注回复</a></li>
    <li class="active"><a href="${ctx}/wechat_reply/link_auto_res_key">关键词回复</a></li>
    <li><a href="${ctx}/wechat_reply/link_auto_res_rece">收到信息回复</a></li>
</ul>
<br/>
<div class="title">
    <h3>自动回复 <i class="checkbox_div"><input type='checkbox' id='switch' checked><label for='switch'>自动回复开关</label></i>
    </h3>
    <p>通过编辑内容或关键词规则，快速进行自动回复设置。</p>
    <p>关闭自动回复之后，将立即对所有用户生效。</p>
</div>
<div class="btns clearfix">
    <a href="#" class="add-btn">添加回复</a>
</div>
<div class="rules">
    <table class="rules-list">
        <tr>
            <th>规则名称</th>
            <th>关键词</th>
            <th>回复内容</th>
            <th>操作</th>
        </tr>
        <tr>
            <td></td>
        </tr>
    </table>
</div>
<div class="add">
    <ul>
        <li><label>规则名称</label><input type="text" name="rule_name"></li>
        <li><label>关键词</label>
            <div class="keys-block">
                <div class="key-block">
                    <select name="key_type" id="key_type" style="vertical-align: top;width: 150px">
                        <option value="1">半匹配</option>
                        <option value="2">全匹配</option>
                    </select>
                    <input type="text" name="keywords">
                    <a href="javascript:;" class="add-key-btn"><img src="${ctxStatic}/images/add.png" alt=""></a>
                    <a href="javascript:;" class="less-key-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>

                </div>
            </div>

            
        </li>
        <li class="clearfix">
            <label>回复内容</label>
            <div class="clearfix" style="display: inline-block;vertical-align: middle">
                <div class="contents-block">
                    <div class="content-block">
                        <ul class="res-head clearfix">
                            <li class="choose-txt active" data-name="txt" value="0">文字</li>
                            <li value="1" data-name="article" value="1">图文</li>
                            <li class="choose-img" data-name="img" value="4">图片</li>
                            <li class="choose-voice" data-name="voice" value="2">语音</li>
                        </ul>
                        <div class="res-iframe res-txt">
                            <div class="res-input" contenteditable="true"></div>
                            <div class="res-msg clearfix">
                                <span class="right-msg">还可以输入 <span class="last-num">600</span> 字，换下Enter换行</span>
                            </div>
                        </div>
                        <div class="res-iframe res-article">
                            <div class="article-block clearfix res-block">
                                <div class="select-article res-box left-res-box">
                                    <a href="javascript:;" class="select-article-btn res-upload-btn">从素材库中选择</a>
                                </div>
                                <div class="upload-article res-box">
                                    <a href="javascript:;" class="upload-article-btn res-upload-btn">上传图文</a>
                                </div>
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
                </div>

                <div class="add-content-btn">
                    <img src="${ctxStatic}/images/add-btn.png" alt="">
                </div>
            </div>
        </li>
        <li class="clearfix">
            <label>回复方式</label>
            <div class="clearfix" style="display: inline-block;vertical-align: middle">
                <div class="radio-input">
                    <input type="radio" name="reply_way" id="reply_way1" value="1">
                    <label for="reply_way1" style="cursor:pointer">全部回复</label>
                </div>
                <div class="radio-input">
                    <input type="radio" name="reply_way" id="reply_way2" value="2">
                    <label for="reply_way2" style="cursor:pointer">随机回复一条</label>
                </div>
            </div>
        </li>
        <li class="btns">
            <a href="javascrpt:;" class="save-btn">保存</a>
            <a href="javascrpt:;" class="cancel-btn">取消</a>
        </li>
    </ul>

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
    $('.save-btn').click(function () {
        //关键词处理
        var arr = [];
        $.each($('.keys-block .key-block'),function () {
            arr.push({
                key_type:$(this).find('#key_type').val(),
                keywords:$(this).find('input[name=keywords]').val()
            })

        })
        console.log(arr);
    })
    //切换
    $('.res-head li').live('click',function () {
        $('.res-head li').removeClass('active');
        $(this).addClass('active');
        $(this).parent().parent().find('.res-iframe').css('display','none');
        $(this).parent().parent().find('.res-'+$(this).attr('data-name')).css('display','block');
    })
    $('.add-content-btn').live('click',function () {
        $('.contents-block').append('                    <div class="content-block">\n' +
            '                        <ul class="res-head clearfix">\n' +
            '                            <li class="choose-txt active" data-name="txt" value="0">文字</li>\n' +
            '                            <li value="1" data-name="article" value="1">图文</li>\n' +
            '                            <li class="choose-img" data-name="img" value="4">图片</li>\n' +
            '                            <li class="choose-voice" data-name="voice" value="2">语音</li>\n' +
            '                        </ul>\n' +
            '                        <div class="res-iframe res-txt">\n' +
            '                            <div class="res-input" contenteditable="true"></div>\n' +
            '                            <div class="res-msg clearfix">\n' +
            '                                <span class="right-msg">还可以输入 <span class="last-num">600</span> 字，换下Enter换行</span>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div class="res-iframe res-article">\n' +
            '                            <div class="article-block clearfix res-block">\n' +
            '                                <div class="select-article res-box left-res-box">\n' +
            '                                    <a href="javascript:;" class="select-article-btn res-upload-btn">从素材库中选择</a>\n' +
            '                                </div>\n' +
            '                                <div class="upload-article res-box">\n' +
            '                                    <a href="javascript:;" class="upload-article-btn res-upload-btn">上传图文</a>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div class="res-iframe res-img">\n' +
            '                            <div class="img-block clearfix res-block">\n' +
            '                                <div class="select-img res-box left-res-box">\n' +
            '                                    <a href="javascript:;" class="select-img-btn res-upload-btn">从素材库中选择</a>\n' +
            '                                </div>\n' +
            '                                <div class="upload-img res-box">\n' +
            '                                    <a href="javascript:;" class="upload-img-btn res-upload-btn">上传图片</a>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                        <div class="res-iframe res-voice">\n' +
            '                            <div class="voice-block clearfix res-block">\n' +
            '                                <div class="select-voice res-box left-res-box">\n' +
            '                                    <a href="javascript:;" class="select-voice-btn res-upload-btn">从素材库中选择</a>\n' +
            '                                </div>\n' +
            '                                <div class="upload-voice res-box">\n' +
            '                                    <a href="javascript:;" class="upload-voice-btn res-upload-btn">上传语音</a>\n' +
            '                                </div>\n' +
            '                            </div>\n' +
            '                        </div>\n' +
            '                    </div>\n');
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
    $('.add-btn').click(function () {
        $('.add').css('display','block');
        $('.rules').css('display','none');
    })
    $('.cancel-btn').click(function () {
        $('.add').css('display','none');
        $('.rules').css('display','block');
    })
    $('.add-key-btn').live('click',function () {
        if($(this).parent().find('input[name=keywords]').val() != ''){
            $(this).remove();
            $('.keys-block').append('<div class="key-block">\n' +
                '                    <select name="key_type" id="key_type" style="vertical-align: top;width: 150px">\n' +
                '                        <option value="1">半匹配</option>\n' +
                '                        <option value="2">全匹配</option>\n' +
                '                    </select>\n' +
                '                    <input type="text" name="keywords">\n' +
                '                    <a href="javascript:;" class="add-key-btn"><img src="${ctxStatic}/images/add.png" alt=""></a>\n' +
                '                    <a href="javascript:;" class="less-key-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>\n                ' +
                '</div>');
            $("select").select2();
        }
        else{
            layer.msg('关键词不能为空且最多30个字');
        }

    })
    $('.less-key-btn').live('click',function () {
        if($('.less-key-btn').length > 1){
            $(this).parent().remove();
        }
        else{
            $(this).parent().find('input[name=keywords]').val('');
            $('#key_type').val('1').trigger('change');
        }
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
    $('.choose-mater').live('click', function () {
        var index = $(this).parents('#myModalArtcle').attr('data-index');
        var name = $(this).parents('#myModalArtcle').attr('data-name');
        if(name == 'img'){
            var para = $('.pic-block .item.active').attr('data-para');
            var paraJson = JSON.parse(para);
            $('.content-block').eq(index).find('.img-block').html('<div data-para="'+para+'">' +
                '<i class="img-choosed"><img src="//yiyezi.yyzws.com/ex/'+paraJson.cover_picture+'"/></i>' +
                '<a href="javascript:;" class="less-img-btn"><img src="${ctxStatic}/images/less.png" alt=""></a>' +
                '</div>')
        }
        else if(name == 'voice'){
            var para = $('.pic-block .item.active').attr('data-para');
            var paraJson = JSON.parse(para);
        }
    })
    var replyDesc = [];
    function addMater(type) {
        var para;
        if (type == 1) {
            $.each($('#myModalArtcle .article-block .item'), function () {
                if ($(this).hasClass('active')) {
                    para = JSON.parse($(this).attr('data-para'));
                    $('.choosed-maters').append('<div class="choosed-mater">\n' +
                        '                        <div class="item">' +
                        '            <div>\n' +
                        '                <div style="font-size:20px;">' + para.title + '</div>\n' +
                        '                <div>2018-12-05</div>\n' +
                        '            </div>\n' +
                        '            <img src="//yiyezi.yyzws.com/ex/' + para.cover_picture + '">\n' +
                        '            <p class="desc">' + para.brief + '</p>\n' +
                        '            <div><span class="floatL">阅读原文</span><span class="floatR"><i class="icon-chevron-right"></i></span></div>\n' +
                        '        </div>' +
                        '                    </div>')

                }
            })
        }
        else if (type == 2) {
            para = JSON.parse($('#myModalArtcle .sound-block input[name=sound]:checked').attr('data-para'));
            console.log(para);
            $('.choosed-maters').append(' 名称:'+para.title +' 文件名:'+para.cover_picture);
        }
        replyDesc.push(para);
        console.log(replyDesc)

        $('#myModalArtcle').modal('hide');
        // $('input[name=content_type]:checked').attr('checked', false);
    }
    $('.select-article-btn').live('click',function () {
        var index = $('.content-block').index($(this).parents('.content-block'));
        getArtcleData();
        $('#myModalArtcle').modal('show').attr('data-name','article').attr('data-index',index);
    })
    $('.select-img-btn').live('click',function () {
        var index = $('.content-block').index($(this).parents('.content-block'));
        getPic();
        $('#myModalArtcle').modal('show').attr('data-name','img').attr('data-index',index);
    })
    $('.select-voice-btn').live('click',function () {
        getSound();
        $('#myModalArtcle').modal('show');
    })
    $('.less-img-btn').live('click',function () {
        $('.img-block').html('<div class="select-img res-box left-res-box">\n' +
            '                <a href="javascript:;" class="select-img-btn res-upload-btn">从素材库中选择</a>\n' +
            '            </div>\n' +
            '            <div class="upload-img res-box">\n' +
            '                <a href="javascript:;" class="upload-img-btn res-upload-btn">上传图片</a>\n' +
            '            </div>');
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
                                getData();
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
                                getData();
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
</script>
</body>
</html>
