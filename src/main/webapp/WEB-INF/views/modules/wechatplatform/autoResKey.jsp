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
    <link rel="stylesheet" href="${ctxStatic}/webuploader-0.1.5/webuploader.css">
    <script src="${ctxStatic}/webuploader-0.1.5/webuploader.js"></script>
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
            background-color: #fff;
            color: #3F51B5;
            border: 1px solid #3F51B5;
            width: 80px;
        }
        .btns .add-btn:hover{
            color: #3F51B5;
        }
        .btns .search-btn{
            margin-left: 20px;
            width: 80px;
            letter-spacing: 0;
            padding-left: 0;
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

<div class="rules">
    <div class="btns clearfix">
        <input type="text" style="vertical-align: baseline"><label><a href="javascript:;" class="search-btn">搜索</a></label>
        <a href="#" class="add-btn">新建</a>
    </div>
    <table class="rules-list">
        <tr>
            <th>规则名称</th>
            <th>关键词</th>
            <th>回复内容</th>
            <th>回复方式</th>
            <th>操作</th>
        </tr>
        <tr>
            <td></td>
        </tr>
    </table>
</div>
<script>
$(function () {
    $('.add-btn').live('click',function () {
        location.href = '${ctx}/wechat_reply/link_auto_res_key_new'
    })
})
</script>
</body>
</html>
