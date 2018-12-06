<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>选择主体</title>
  <link href="${ctxStatic}/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet">
  <link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.css" rel="stylesheet">
  <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
  <script src="${ctxStatic}/bootstrap-3.3.0/js/bootstrap.min.js"></script>
  <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js"></script>
  <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.min.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
  <style>
    body{
      padding: 20px;
    }
    header{
      width: 100%;
      line-height: 100px;
      text-align: left;
      padding: 0 40px;
    }
    #creat_shop{
      height: 60px;
      width: 150px;
      font-size: 14px;
      cursor: pointer;
      display: inline-block;
      line-height: 60px;
      text-align: center;
        background: url('${ctxStatic}/images/wechatmanage/creat_bg.png') no-repeat;
    }
    .shop-wrap{
      padding: 50px 0;
      overflow: hidden;
      background: rgba(242, 242, 242, 1);
      margin-top: 20px;
    }
    .shop-wrap .item{
      border:1px solid #d8d8d8;
      border-top: 4px solid #3f51B5;
      padding: 30px 20px;
      width: 390px;
      float: left;
      margin-left: 20px;
      height: 125px;
      margin-top: 20px;
      position: relative;
      background: #fff;
    }
    .shop-wrap .item .desc{
      width: 240px;
      float: left;
      padding-left: 20px;
      height: 60px;
      vertical-align: middle;
      display: table;
    }
    .set{
      cursor: pointer;
      position: absolute;
      display: block;
      width: 24px;
      height: 24px;
      right: 20px;
      top: 20px;
        background: url('${ctxStatic}/images/wechatmanage/set.png') no-repeat;
    }
    .grey{
      color: #d8d8d8;
    }
    .pointer{
      cursor: pointer;
    }
    .avatar{
      width: 60px;
      height: 60px;
      background: #333;
      float: left;
    }
  </style>
</head>
<body>
    <header>
      <div id="creat_shop">
        <img src="${ctxStatic}/images/wechatmanage/plus.png">
        创建公众号
      </div>
    </header>
    <div class="shop-wrap">

    </div>
</body>
    <script>
        $.ajax({
            url:'${ctx}/wechat/wechat_latform_list',
            type:'POST',
            success:function (data) {
                typeof data != 'Object'&& (data = JSON.parse(data));
                if(data.code=='10000'){
                    var _html = [];
                    data.data.forEach(function(item){
                        _html.push('<div class="item pointer" data-id='+ item.id +'>' +
                            '        <img class="avatar" src="${ctxStatic}/images/wechatmanage/avatar.png">' +
                            '        <div class="desc">' +
                            '          <div>'+ item.name +'</div>' +
                            '          <div style="margin-top:20px;color: #808080;">主体信息：'+ item.main_part +'</div>' +
                            '        </div>' +
                            '        <span class="set" data-id='+ item.id +'></span>' +
                            '      </div>')
                    })
                    $('.shop-wrap').html(_html.join(''));
                    $('.item').click(function (e) {
                        if(e.target.className == 'set') {
                            window.location = '${ctx}/wechat/link_update?id='+$(this).data('id');
                        }else{
                            window.parent.location = '${ctx}/wechat/link_home_page?id='+$(this).data('id');
                        }
                    })
                }
            }
        });
        $('#creat_shop').click(function(){
            window.location = '${ctx}/wechat/link_add';
        })
    </script>
</html>