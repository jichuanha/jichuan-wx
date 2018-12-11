
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <meta name="decorator" content="default"/>
    <%--<link rel="stylesheet" href="${ctxStatic}/webuploader-0.1.5/webuploader.css">--%>
    <%--<script src="${ctxStatic}/webuploader-0.1.5/webuploader.js"></script>--%>
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
        .item{width: 294px;margin-right: 20px;margin-bottom:20px;float: left;border:1px solid #eee;padding: 20px;position: relative;box-sizing: border-box;}
        #creat-block .item{height: 580px;}
        .item img{display: block;width: 100%;height: 200px;margin: 20px 0;}
        .floatL{float: left} .floatR{float: right;}.desc{border-bottom: 1px solid #eee;padding-bottom: 20px;word-break: break-all;}
        .opration{display: none;position: absolute;top:0;left: 0;right: 0;bottom: 0;background: rgba(255,255,255,0.8);padding: 20px;}
        .opration i{font-size: 30px;margin-right: 10px;cursor: pointer}
        .platName{height: 50px;line-height: 50px;width: 100%;font-size: 17px;text-align: center;background: url('${ctxStatic}/images/bg_mobile.png');color:#fff;position: absolute;
            top: 0;left: 0;background-size: cover;line-height: 65px;}
        .info{margin-top: 50px;}
        #input-block{border:1px solid #eee;width: 600px;float: left;margin-left: 20px;height: 580px;}
        #input-block form>div{padding: 10px 20px;}
        .input-title{border-bottom: 1px solid #eee;}
        #save{margin:0 auto;display: block;width: 200px;padding: 10px 0;}
        textarea{min-height: 80px;}.back{cursor: pointer;margin-right: 0px;width: 30px;}
        .menu-wrap{height: 50px;width: 100%;position: absolute;bottom: 0;left: 0;line-height: 50px;text-align: center;border-top: 1px solid #eee;}
        .menu-wrap>div{border-left: 1px solid #eee;float: left;cursor: pointer;overflow: hidden;box-sizing: border-box;}
        .menu-wrap>div:first-child{border-left:none;}
        #input-block input{margin-bottom: 0;}.edit-block>div{margin-bottom: 20px;}
    </style>
</head>
<body>

<div id="creat-wrap" class="creat-block">
    <div>由于微信接口延迟，菜单修改后需点击“提交发布”，最长可能需要30分钟才会更新至公众号。</div>
    <div>点击“提交发布”前的操作仅在当前页面生效。如需公众号菜单即时生效，可先取消关注，再重新关注。</div>
    <div id="creat-block">
        <div class="item">
            <div class="platName"></div>
            <div class="menu-wrap">
                <%--<div></div>--%>
                <%--<div id="add-menu-btn">+添加菜单</div>--%>
            </div>
        </div>
        <div id="input-block">
            <form id="article-form">
                <div class="input-title">编辑菜单</div>
                <div class="edit-block">
                </div>
                <%--<button class="btn btn-primary" id="save">保存</button>--%>
            </form>
        </div>
    </div>
    <div><button class="btn btn-primary" style="margin-left: 294px;margin-top: -50px;" id="syMenu">同步到微信</button></div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    编辑菜单
                </h4>
            </div>
            <form id="edit-form">
                <div class="modal-body">
                    <div><label>菜单类型：</label>
                        <input type="radio" name="type" value="1" id="menu"><label for="menu">菜单</label>
                        <input type="radio" name="type" value="2" id="button"><label for="button">按钮</label>
                        <input type="radio" name="type" value="3" id="link"><label for="link">链接</label>
                    </div>
                    <div><label>排序：</label><input class="input-large" name="sort" type="number" required></div>
                    <div class="kw-wrap"><label>关键字：</label><input class="input-large" name="keyword" type="text" required></div>
                    <div class="url-wrap"><label>链接：</label><input class="input-large" name="url" type="url" id="url" required></div>
                </div>
                <div class="modal-footer">
                    <div type="button" class="btn btn-default" data-dismiss="modal">关闭</div>
                    <button class="btn btn-primary" id="save-menu-content">保存</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script>
    $(function(){
        var params = {
            wechat_id:$.cookie('platFormId'),
        };
        var listArr = [];
        var count = 0;
        var btnType = '';
        var editIndex = 0;
        var hasInit = false;
        $('#myModal').on("show.bs.modal",function(){
            $('label.error').remove();
            $("#edit-form").validate({
                submitHandler:function(form){
                    console.log(btnType);
                    var params = {
                        wechat_id:$.cookie('platFormId'),
                        id:editIndex.data.id,
                        type:btnType||editIndex.data.type,
                        sort:$('input[name=sort]').val(),
                    };
                    if(params.type==2){
                        params.keywords = $('input[name=keyword]').val();
                    }else if(params.type==3){
                        params.uri = $('input[name=url]').val();
                    }
                    $.post('${ctx}/wechat_menu/modify_menu',params,function (res) {
                        typeof res != 'object'&& (res = JSON.parse(res));
                        if(res.code==10000){
                            location.reload();
                        }
                    })
                }
            });
        });
        function getData(){
            $.ajax({
                url:'${ctx}/wechat_menu/custom_menu_list',
                type:'POST',
                data:params,
                success:function(data){
                    typeof data != 'object'&& (data = JSON.parse(data));
                    data.data.forEach(function(item,index){
                        if(item.menu_level==1){
                            item.sub_menu = [];
                            listArr.push(item);
                        }else{
                            var _index = listArr.length-1;
                            listArr[_index].sub_menu.push(item);
                        }
                    });
                    var _html = [];
                    var length = 0;
                    length= listArr.length<3?listArr.length+1:3;
                    var width = Math.floor(100/length);
                    listArr.forEach(function(item){
                        _html.push('<div data-id='+ item.id +' style="width:'+ width +'%" class="menu1">'+ item.name + '</div>');
                    });
                    if(listArr.length==0){
                        _html.push('<div id="add-menu-btn" style="width:'+ width +'%">+添加菜单</div>');
                    }else if(listArr.length<3){
                        _html.push('<div id="add-menu-btn" style="width:'+ width +'%">+</div>');
                    }
                    $('.menu-wrap').html(_html.join(''));
                    $('.menu-wrap .menu1[data-id='+ $.cookie('menuIndex')+']').click();
                }
            })
        }
        getData();
        function initEvent() {
            $('#syMenu').live('click',function () {
                $.post('${ctx}/toWechat/syMenu',{wechat_id:params.wechat_id},function (res) {
                    typeof res != 'object'&& (res = JSON.parse(res));
                    if(res.code==10000){
                        layer.open({content:'同步成功'});
                    }else{
                        layer.open({content:res.msg});
                    }
                })
            });

            $('input[type=radio]').live('click',function(){
                var type = parseInt($(this).val());
                btnType = type;
                if(type===1){
                    $('.kw-wrap').hide();
                    $('.url-wrap').hide();
                }else if(type===2){
                    $('.kw-wrap').show();
                    $('.url-wrap').hide();
                }else if(type===3){
                    $('.kw-wrap').hide();
                    $('.url-wrap').show();
                }
            });

            // $('#save-menu-content').live('click',function () {
            //    // $('#myModal').modal('hide');
            // });

            $('.menu-wrap .menu1').live('click',function(){
                var index = $(this).index();
                var _html = [];
                _html.push('<div class="menu1"><label>一级菜单：</label>' +
                    '<input class="input-large" name="title" type="text" required ' +
                    'placeholder="字数不超过4个汉字或8个字母" value="'+ listArr[index].name +'"  data-index="'+ index +'">\n' +
                    '                    <div class="btn btn-primary edit"  data-index="'+ index +'">编辑</div>\n' +
                    '                    <div class="btn btn-danger delete"  data-index="'+ index +'">删除</div>\n' +
                    '                </div>');
                listArr[index].sub_menu.forEach(function(item,_index){
                    _html.push('<div><label>二级菜单：</label>' +
                        '<input class="input-large" name="title" type="text" required ' +
                        'placeholder="字数不超过4个汉字或8个字母" value="'+ item.name +'"  data-index="'+ _index +'" data-pindex="'+ index +'">\n' +
                        '                    <div class="btn btn-primary edit" data-index="'+ _index +'" data-pindex="'+ index +'">编辑</div>\n' +
                        '                    <div class="btn btn-danger delete"  data-index="'+ _index +'" data-pindex="'+ index +'">删除</div>\n' +
                        '                </div>');
                });
                if(listArr[index].sub_menu.length<5){
                    _html.push('<div><div class="btn btn-primary add-menu2-btn" data-index="'+ index +'">增加二级菜单</div></div>');
                }
                $('.edit-block').html(_html.join(''));
                $.cookie('menuIndex',$(this).data('id'));
                changeStatus(index);
            });

            $('.edit-block input').live('change',function(){
                var _data = getListData($(this));
                var data = _data.data;
                var _pindex = _data.pindex;
                if($(this).val()===''){
                    $(this).val(data.name);
                    return;
                }
                var params = {
                    wechat_id:$.cookie('platFormId'),
                    id:data.id,
                    name:$(this).val(),
                };
                $.post('${ctx}/wechat_menu/modify_menu',params,function (res) {
                    typeof res != 'object'&& (res = JSON.parse(res));
                    if(res.code==10000){
                        if(_data.level===1){
                            $('.menu-wrap .menu1').eq(_pindex).html(params.name);
                        }
                        data.name = params.name;
                    }
                })
            });

            $('#add-menu-btn').live('click',function(){
                var params = {
                    wechat_id:$.cookie('platFormId'),
                    sort:listArr.length+1,
                    type:1,
                    name:'菜单名称',
                };
                $.post('${ctx}/wechat_menu/save_menu',params,function (data) {
                    typeof data != 'object'&& (data = JSON.parse(data));
                    if(data.code==10000){
                        window.location.reload();
                    }else{
                        layer.open({content:data.msg});
                    }
                })
            });

            $('.edit-block .delete').live('click',function(){
                var data = getListData($(this)).data;
                var confirmL=layer.confirm('确认删除此菜单？',{
                    btn:['确定','取消']
                },function(){
                    layer.close(confirmL);
                    $.post('${ctx}/wechat_menu/remove_menu',{id:data.id,wechat_id:params.wechat_id},function (data) {
                        typeof data != 'object'&& (data = JSON.parse(data));
                        if(data.code==10000){
                            layer.close(confirmL);
                            window.location.reload();
                        }else{
                            layer.open({content:data.msg});
                        }
                    })
                })
            });
            $('.edit-block .edit').live('click',function(){
                if($(this)[0].hasAttribute('disabled')){
                    return;
                }
                var data = getListData($(this));
                editIndex = data;
                $('#myModal').modal();
                initInputChange(data.data);
            });

            $('.add-menu2-btn').live('click',function () {
               var index = $(this).data('index');
                var params = {
                    parent_id:listArr[index].id,
                    wechat_id:$.cookie('platFormId'),
                    sort:listArr[index].sub_menu.length+1,
                    type:1,
                    name:'菜单名称',
                };
                $.post('${ctx}/wechat_menu/save_menu',params,function (data) {
                    typeof data != 'object'&& (data = JSON.parse(data));
                    if(data.code==10000){
                        window.location.reload();
                    }else{
                        layer.open({content:data.msg});
                    }
                })
            });

            $('.platName').html($.cookie('platFormName'));
        }

        function getListData(element){
            var _pindex = '';
            var data = '';
            var level = 1;
            if(element.data('pindex')===undefined){
                data = listArr[element.data('index')];
                _pindex = element.data('index');
            }else{
                data = listArr[element.data('pindex')].sub_menu[element.data('index')];
                _pindex = element.data('pindex');
                level=2;
            }
            return {
                data:data,
                pindex:_pindex,
                level:level,
            }
        }

        function changeStatus(index){
            if(listArr[index].sub_menu.length!==0){
                $('.edit-block .menu1 .edit').attr('disabled','disabled');
            }else {
                $('.edit-block .menu1 .edit').removeAttr('disabled');
            }
        }
        function initInputChange(data){
            $('label.error').remove();
            if(data.type===1){
                $('.kw-wrap').hide();
                $('.url-wrap').hide();
                $('#menu').attr('checked','checked');
            }else if(data.type===2){
                $('.kw-wrap').show();
                $('.url-wrap').hide();
                $('#button').attr('checked','checked');
                $('input[name=keyword]').val(data.keywords);
            }else if(data.type===3){
                $('.kw-wrap').hide();
                $('.url-wrap').show();
                $('#link').attr('checked','checked');
                $('input[name=url]').val(data.uri);
            }
            $('input[name=sort]').val(data.sort);
        }
        initEvent();
    })
</script>
</body>
</html>
