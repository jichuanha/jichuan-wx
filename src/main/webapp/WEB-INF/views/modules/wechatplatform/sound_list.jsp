
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
    <title>语音</title>
    <style>
        #list-wrap,#creat-wrap{padding: 50px;}
        header{width: 100%;height: 55px;border-bottom: 2px solid #eee;}
        header ul{text-decoration: none;list-style: none}
        header ul li{font-size: 20px;line-height: 50px;display: inline-block;margin-right: 50px;box-sizing: border-box;}
        header ul li a{color:#000; text-decoration: none;list-style: none;}
        .actived{border-bottom: 5px solid #3f5185;}
        .creat-wrap{margin-bottom: 20px;}
        .creat-wrap .btn{font-size: 20px;display: inline-block;}
        .creat-wrap span{font-size: 20px;margin-left: 20px;}
        #creat-block .item{height: 667px;}
        .creat-btn{padding: 10px 30px;}
        .creat-btn .webuploader-pick{font-size: 20px;display: inline;}
        .item img{display: block;width: 100%;}
        .floatL{float: left} .floatR{float: right;}.desc{border-bottom: 1px solid #eee;padding-bottom: 20px;}
        .opration{display: none;position: absolute;top:0;left: 0;right: 0;bottom: 0;background: rgba(255,255,255,0.8);padding: 20px;}
        .opration i{font-size: 30px;margin-right: 10px;cursor: pointer}
        #input-block form>div{padding: 10px 20px;}
        textarea{min-height: 80px;}.back{cursor: pointer;margin-right: 0px;width: 30px;}
        .table-head,#list-block  .item{width: 100%;height: 50px;background: #eee;line-height: 50px;text-align: center;}
        .table-head .title,#list-block .item>div{width: 25%;float: left;}
        #list-block .item{background: #fff;border:1px solid #eee;margin-top: 20px;}
        #list-block .item>div{border-right: 1px solid #eee;box-sizing: border-box;}
        #list-block .item .delete {border-right: none;cursor: pointer}
    </style>
</head>
<body>
<header>
    <ul class="init-block">
        <li><a href="${ctx}/wechat_material/link_article_list">高级图文</a></li>
        <li><a href="${ctx}/wechat_material/link_pic_list">图片</a></li>
        <li class="actived"><a href="${ctx}/wechat_material/link_sound_list">语音</a></li>
    </ul>
</header>
<div id="list-wrap" class="init-block">
    <div class="creat-wrap"><button class="btn btn-primary creat-btn" data-toggle="modal" data-target="#myModal">新建</button><span class="total">共同0条</span></div>
    <div class="table-head">
        <div class="title">语音名称</div>
        <div class="title">文件名称</div>
        <div class="title">创建时间</div>
        <div class="title">操作</div>
    </div>
    <div id="list-block">
        <div class="item">
            <div>测试</div>
            <div>00.00.00</div>
            <div>2018-08-29 12：00：00</div>
            <div class="delete">删除</div>
        </div>
    </div>
    <div id="pagenation"></div>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" id="myModalLabel">
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
</div>

<script>
    $(function(){
        var params = {
            current_page:1,
            page_size:50,
            type:3,
            wechat_id:$.cookie('platFormId'),
        };
        var listArr = [];
        var count = 0;
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
                                '<div>'+ item.title+'</div>' +
                            '<div>'+ item.cover_picture+'</div>' +
                            '<div>'+ item.create_date+'</div>' +
                            '<div class="delete" data-index='+index+'>删除</div>' +
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
                            window.location.reload();
                        }else{
                            layer.open({
                                content:data.msg,
                            })
                        }
                    })
                }
            });
        });
        function initEvent() {
            $('.delete').live('click',function () {
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
                    })
                })
            });
        }

        initEvent();
    })
</script>
</body>
</html>
