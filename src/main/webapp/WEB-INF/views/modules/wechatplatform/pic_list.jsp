
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
    <title>图片</title>
    <style>
        #list-wrap,#creat-wrap{padding: 50px;}
        a:hover, a:focus{text-decoration: none;color: inherit;}
        header{width: 100%;height: 55px;border-bottom: 2px solid #eee;}
        header ul{text-decoration: none;list-style: none}
        header ul li{font-size: 16px;line-height: 50px;display: inline-block;margin-right: 70px;box-sizing: border-box;}
        header ul li a{color:#000; text-decoration: none;list-style: none;}
        .actived{border-bottom: 5px solid #3f5185;}
        .creat-wrap{margin-bottom: 20px;}
        .creat-wrap .btn{font-size: 20px;display: inline-block;}
        .creat-wrap span{font-size: 20px;margin-left: 20px;}
        #list-block,#creat-block{padding: 50px 0;overflow: hidden;}
        .item{width: 200px;margin-right: 20px;margin-bottom:20px;float: left;border:1px solid #eee;position: relative;box-sizing: border-box;height: 400px;overflow: hidden;line-height: 400px;}
        #creat-block .item{height: 667px;}
        .creat-btn{padding: 3px 30px;}
        .creat-btn .webuploader-pick{font-size: 20px;display: inline;}
        .item img{display: inline;width: 100%;}
        .floatL{float: left} .floatR{float: right;}.desc{border-bottom: 1px solid #eee;padding-bottom: 20px;}
        .opration{display: none;position: absolute;top:0;left: 0;right: 0;bottom: 0;background: rgba(255,255,255,0.8);padding: 20px;line-height: normal;}
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
        <li><a href="${ctx}/wechat_material/link_article_list">高级图文</a></li>
        <li class="actived"><a href="${ctx}/wechat_material/link_pic_list">图片</a></li>
        <li><a href="${ctx}/wechat_material/link_sound_list">语音</a></li>
    </ul>
</header>
<div id="list-wrap" class="init-block">
    <div class="creat-wrap"><button class="btn btn-primary creat-btn">上传</button><span class="total">共同0条</span></div>
    <div id="list-block">
        <div class="item">
        <div class="opration">
            <i class="icon-remove-sign"></i>
        </div>
         <img>
        </div>
    </div>
    <div id="pagenation"></div>
</div>

<script>
    $(function(){
        var params = {
            current_page:1,
            page_size:50,
            type:1,
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
                            '            <div class="opration">\n' +
                            '                <i class="icon-remove-sign" data-index="'+ index +'"></i>\n' +
                            '            </div>\n' +
                            '            <img src="//yiyezi.yyzws.com/ex/'+ item.cover_picture+'">\n' +
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
                    })
                })
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
                    id: '.creat-btn', // 按钮元素
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
                        window.location.reload();
                    }else{
                        layer.open({
                            content:data.msg,
                        })
                    }
                })
            });
        }

        initEvent();
    })
</script>
</body>
</html>
