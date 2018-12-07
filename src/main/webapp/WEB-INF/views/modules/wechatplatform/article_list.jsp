
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxStatic}/webuploader-0.1.5/webuploader.css">
    <script src="${ctxStatic}/webuploader-0.1.5/webuploader.js"></script>
    <script src="${ctxStatic}/layer/layer.js"></script>
    <link rel="stylesheet" href="${ctxStatic}/jquery.page/page.css">
    <script src="${ctxStatic}/jquery.page/page.js"></script>
    <title>图文</title>
    <style>
        #list-wrap,#creat-wrap{padding: 50px;}
        header{width: 100%;height: 55px;border-bottom: 2px solid #eee;}
        header ul{text-decoration: none;list-style: none}
        header ul li{font-size: 20px;line-height: 50px;display: inline-block;margin-right: 50px;box-sizing: border-box;}
        header ul li a{color:#000; text-decoration: none;list-style: none;}
        .actived{border-bottom: 5px solid #3f5185;}
        .creat-wrap{margin-bottom: 20px;}
        .creat-wrap .btn{font-size: 20px;padding: 10px 30px;display: inline-block;}
        .creat-wrap span{font-size: 20px;margin-left: 20px;}
        #list-block,#creat-block{padding: 50px 0;overflow: hidden;}
        .item{width: 375px;margin-right: 20px;margin-bottom:20px;float: left;border:1px solid #eee;padding: 20px;position: relative;box-sizing: border-box;}
        #creat-block .item{height: 667px;}
        .item img{display: block;width: 100%;height: 200px;margin: 20px 0;}
        .floatL{float: left} .floatR{float: right;}.desc{border-bottom: 1px solid #eee;padding-bottom: 20px;}
        .opration{display: none;position: absolute;top:0;left: 0;right: 0;bottom: 0;background: rgba(255,255,255,0.8);padding: 20px;}
        .opration i{font-size: 30px;margin-right: 10px;cursor: pointer}
        .platName{height: 50px;line-height: 50px;width: 100%;font-size: 20px;text-align: center;background: #dddddd;color:#fff;position: absolute;
            top: 0;left: 0;}
        .info{margin-top: 50px;}
        #input-block{border:1px solid #eee;width: 400px;float: left;margin-left: 20px;height: 667px;}
        #input-block form>div{padding: 10px 20px;}
        .input-title{border-bottom: 1px solid #eee;}
        #save{margin:0 auto;display: block;width: 200px;padding: 10px 0;}
        textarea{min-height: 80px;}.back{cursor: pointer;margin-right: 0px;width: 30px;}
        .creat-block{display: none;}
    </style>
</head>
<body>
<header>
    <ul class="init-block">
        <li class="actived"><a href="${ctx}/wechat_material/link_article_list">高级图文</a></li>
        <li><a href="${ctx}/wechat_material/link_pic_list">图片</a></li>
        <li><a href="${ctx}/wechat_material/link_sound_list">语音</a></li>
    </ul>

    <ul class="creat-block">
        <li class="back"><i class="icon-chevron-left"></i></li>
        <li class="page-title">新建图文</li>
    </ul>
</header>
<div id="list-wrap" class="init-block">
    <div class="creat-wrap"><button class="btn btn-primary creat-btn">新建</button><span class="total">共同0条</span></div>
    <div>高级图文不可用于群发，如须群发请请返回微信公众号“素材管理”创建群发图文素材。</div>
    <div id="list-block">
        <%--<div class="item">--%>
            <%--<div class="opration">--%>
                <%--<i class="icon-remove-sign"></i>--%>
                <%--<i class="icon-edit"></i>--%>
            <%--</div>--%>
            <%--<div>--%>
                <%--<div>标题</div>--%>
                <%--<div>2018-12-05</div>--%>
            <%--</div>--%>
            <%--<img>--%>
            <%--<p class="desc">摘要展示</p>--%>
            <%--<div><span class="floatL">阅读原文</span><span class="floatR"><i class="icon-chevron-right"></i></span></div>--%>
        <%--</div>--%>
    </div>
    <div id="pagenation"></div>
</div>

<div id="creat-wrap" class="creat-block">
    <div>高级图文不可用于群发，如须群发请请返回微信公众号“素材管理”创建群发图文素材。</div>
    <div id="creat-block">
        <div class="item">
            <div class="platName"></div>
            <div class="info">
                <div class="title">标题</div>
                <div class="time">2018-12-05</div>
            </div>
            <img class="cover">
            <p class="desc">摘要展示</p>
            <div><span class="floatL">阅读原文</span><span class="floatR"><i class="icon-chevron-right"></i></span></div>
        </div>
        <div id="input-block">
            <form id="article-form">
            <div class="input-title">新建图文</div>
                 <div><label>图文标题：</label><input class="input-large" name="title" type="text" required></div>
                 <div><label>图片封面：</label><div class="btn btn-primary" id="pic-select">选择图片</div></div>
                <input name="cover" style="display: none">
                <div><label>图文摘要：</label><textarea class="input-large" name="brief" type="text" required></textarea></div>
                 <div><label>链接地址：</label><input class="input-large" name="url" type="text" required></div>
                <button class="btn btn-primary" id="save">保存</button>
            </form>
        </div>
    </div>
</div>


<script>
    $(function(){
        var params = {
            current_page:1,
            page_size:50,
            type:4,
            wechat_id:$.cookie('platFormId'),
        };
        var listArr = [];
        var count = 0;
        var btnType = '';
        var editIndex = 0;
        var hasInit = false;
        function getData(){
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
                        _html.push('<div class="item">\n' +
                            '            <div class="opration">\n' +
                            '                <i class="icon-remove-sign" data-index="'+ index +'"></i>\n' +
                            '                <i class="icon-edit" data-index="'+ index +'"></i>\n' +
                            '            </div>\n' +
                            '            <div>\n' +
                            '                <div style="font-size:20px;">'+ item.title+'</div>\n' +
                            '                <div>2018-12-05</div>\n' +
                            '            </div>\n' +
                            '            <img src="//yiyezi.yyzws.com/ex/'+ item.cover_picture+'">\n' +
                            '            <p class="desc">'+item.brief+'</p>\n' +
                            '            <div><span class="floatL">阅读原文</span><span class="floatR"><i class="icon-chevron-right"></i></span></div>\n' +
                            '        </div>');
                    })
                    $('#list-block').html(_html.join(''));
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
        getData();
        function initEvent() {
            $('#list-block .icon-remove-sign').live('click',function () {
                var index = $(this).data('index');
                var confirmL=layer.confirm('确认删除此素材？',{
                    btn:['确定','取消']
                },function(){
                    layer.close(confirmL);
                    $.post('${ctx}/wechat_material/remove_material',{id:listArr[index].id},function (data) {
                        typeof data != 'object'&& (data = JSON.parse(data));
                        if(data.code==10000){
                            listArr.splice(index,1);
                            $('#list-block .item').eq(index).remove();
                            $('.total').html('共同'+ --count +'条');
                            layer.close(confirmL);
                        }else{
                            layer.open(data.msg);
                        }

                        // window.location.reload();
                    })
                })
            });

            $('#list-block .icon-edit').live('click',function(){
                var index = $(this).data('index');
                var data = listArr[index];
                $('input[name="title"]').val(data.title);
                $('textarea[name="brief"]').val(data.brief);
                $('input[name="url"]').val(data.uri);
                $('input[name="cover"]').val(data.cover_picture);
                $('.cover').attr('src','//yiyezi.yyzws.com/ex/'+ data.cover_picture);
                btnType = 'edit';
                $('.page-title').html('编辑图文');
                initInputChange();
            });

            $('#list-block .item').live('mousemove',function () {
                $(this).find('.opration').show();
            });
            $('#list-block .item').live('mouseout',function () {
                $(this).find('.opration').hide();
            });
            var uploader = WebUploader.create({
                auto: true, // 选择文件后自动上传
                runtimeOrder: 'html5', // 直接使用html5模式，还有flash的我就忽略了..
                pick: {
                    id: '#pic-select', // 按钮元素
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
                console.log(data);
                typeof data != 'object'&& (data = JSON.parse(data));
                $('input[name="cover"]').val(data.data);
                $('.cover').attr('src','//yiyezi.yyzws.com/ex/'+ data.data);
            });
        }

        function initInputChange(){
            $('.init-block').hide();
            $('.creat-block').show();
            $('.platName').html($.cookie('platFormName'));
            $('.back').click(function(){
                window.location.reload();
            });
            $("#article-form").validate({
                submitHandler:function(){
                    if(!$('input[name="cover"]').val()){
                        layer.open({
                            content:'请上传封面',
                        });
                        return;
                    }
                    var list={};
                    var url = '';
                    if(btnType =='edit'){
                        url = '${ctx}/wechat_material/update_material';
                        list = {
                            id:listArr[editIndex].id,
                            title:$('input[name="title"]').val(),
                            brief:$('textarea[name="brief"]').val(),
                            uri:$('input[name="url"]').val(),
                            cover_picture:$('input[name="cover"]').val(),
                        };
                    }else{
                        url = '${ctx}/wechat_material/save_material';
                        list = {
                            title:$('input[name="title"]').val(),
                            wechat_id:$.cookie('platFormId'),
                            brief:$('textarea[name="brief"]').val(),
                            uri:$('input[name="url"]').val(),
                            type:'4',
                            cover_picture:$('input[name="cover"]').val(),
                        };
                    }
                    $.post(url,list,function (data) {
                        typeof data != 'object'&& (data = JSON.parse(data));
                        if(data.code=='10000'){
                            window.location.reload();
                        }else{
                            layer.open({
                                content:data.msg,
                            })
                        }
                    })
                }
            });
            $('input[name="title"]').keyup(function () {
                if($(this).val()){
                    $('.title').html($(this).val());
                }else{
                    $('.title').html('标题');
                }
            });
            $('textarea[name="brief"]').keyup(function () {
                if($(this).val()){
                    $('.desc').html($(this).val());
                }else{
                    $('.desc').html('摘要');
                }
            })
        }
        $('.creat-btn').click(function () {
            initInputChange();
        });
        initEvent();
    })
</script>
</body>
</html>
