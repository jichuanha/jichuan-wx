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

        .article-block .item.active::after {
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
            margin-top: 20px;
        }
        .add-content-btn img{
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
            <select name="key_type" id="key_type" style="vertical-align: top;width: 150px">
                <option value="1">半匹配</option>
                <option value="2">全匹配</option>
            </select>
            <input type="text" name="keywords">
        </li>
        <li class="clearfix">
            <label>回复内容</label>
            <div class="clearfix" style="display: inline-block;vertical-align: middle">
                <div class="clearfix">
                    <div class="radio-input">
                        <input type="radio" name="content_type" id="content_type0" value="0">
                        <label for="content_type0" style="cursor:pointer">文字</label>
                    </div>
                    <div class="radio-input">
                        <input type="radio" name="content_type" id="content_type1" value="1">
                        <label for="content_type1" style="cursor:pointer">图文</label>
                    </div>
                    <div class="radio-input">
                        <input type="radio" name="content_type" id="content_type2" value="2">
                        <label for="content_type2" style="cursor:pointer">语音</label>
                    </div>
                    <div class="radio-input">
                        <input type="radio" name="content_type" id="content_type3" value="3">
                        <label for="content_type3" style="cursor:pointer">视频</label>
                    </div>
                </div>
                <div class="choosed-maters">

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
<script>
    $('.add-btn').click(function () {
    })
    $('input[name=content_type]').change(function () {
        $('.choosed-maters').html('');
    })
    $('.add-content-btn').click(function () {
        var val = $('input[name=content_type]:checked').val();

        if (val == 1) {
            getArtcleData();
            $('#myModalArtcle').modal('show');
        }
        else if (val == 2) {
            getSound();
            $('#myModalArtcle').modal('show');
        }
        else{
            layer.msg('请先选择类型')
            return;
        }

    })
    $('.item').live('click', function () {
        $(this).addClass('active');
    })
    $('.choose-mater').live('click', function () {
        var val = $('input[name=content_type]:checked').val();
        addMater(val)
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
</script>
</body>
</html>
