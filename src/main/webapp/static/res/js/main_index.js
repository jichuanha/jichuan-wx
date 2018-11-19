    $(function(){
        var blockType = $("#block-type");
        var addBlock = $(".add-block");
        var btnAddBlock = $("#add-block");
        var btnSavePage = $(".save-page");
        var categoryList =  $("#categoryList1");
        var subCategoryList =  $("#sub-category-list");
        var btnPublishPage =  $("#publish-page");
        var pageList = $("#page-list");
        var btnDelete = $("#delete-page");
        var pageName = $("input[name='page_name']");
        var $pageOrder =  $("input[name='page_order']");
        var $blockPadding =  $("input[name='block-padding']");
        var btnAddPage = $("#add-page");
        var DEFAULT_PAGE_ORDER = '99' ;
        var fn = {
            addPage:function(){
                fn.resetCategory();
                pageList.parent().find(".select2-container").hide();
                pageName.show();
                $(window).removeData("pageId");
                btnDelete.attr("disabled","");
                btnDelete.addClass("disabled");
                $pageOrder.val(DEFAULT_PAGE_ORDER);
                fn.clean();
                fn.enabledBtn();
                fn.disablePublishBtn();
                fn.enableSelect();
            }
            ,resetCategory:function(){

                categoryList.prop("disabled",false);
                subCategoryList.prop("disabled",false);
                $pageOrder.val('99');
                $blockPadding.val('20');
                categoryList.val('0').trigger("change");
                subCategoryList.find("option:not(:eq(0))").remove();
                subCategoryList.val('0').trigger("change");
                pageList.val('').trigger("change");

            }
            ,disabledSelect:function(){
                categoryList.prop("disabled",true);
                subCategoryList.prop("disabled",true);
            },enableSelect:function(){
                categoryList.prop("disabled",false);
                subCategoryList.prop("disabled",false);
            }
            ,enabledBtn:function(){
                btnSavePage.removeClass("disabled");
                btnAddBlock.removeClass("disabled");
                btnSavePage.removeAttr("disabled");
                btnAddBlock.removeAttr("disabled");
            },enablePublishBtn:function(){
                btnPublishPage.removeClass("disabled");
                btnPublishPage.removeAttr("disabled");
            }
            ,disabledBtn :function(){
                btnAddBlock.addClass("disabled");
                btnSavePage.addClass("disabled");
                btnPublishPage.addClass("disabled");
                btnAddBlock.attr("disabled","");
                btnSavePage.attr("disabled","");
                btnPublishPage.attr("disabled","");
            },disablePublishBtn:function(){
                btnPublishPage.addClass("disabled");
                btnPublishPage.attr("disabled","");
            },enabledDeleteBtn:function(){
                btnDelete.removeAttr("disabled");
                btnDelete.removeClass("disabled");
            }
            ,clean:function(){
                $(".goods-form,.tms-block,.div-image").remove();
            },confirm:function(fun){
                $.jBox.confirm("确定删除吗？","提示",function (a) {
                    if(a =='cancel'){
                        return;
                    }
                    fun();
                })
            },alert:function(info){
                $.jBox.alert(info,"警告");
            },check:function(that){
                var text = $(that).text();
                var goods = $(that).attr("data-info");
                goods =  JSON.parse(  decodeURIComponent( goods));
                if(text == '选择'){
                    $(that).css({color:'#fff',background:'#5cb85c',border:'#5cb85c'}).text("取消");
                    var goodsList = $(window).data('goodsList') || [];
                    goodsList.push(goods);
                    $(window).data('goodsList',goodsList);
                }else{
                    $(that).removeAttr("style");
                    $(that).text("选择");
                    var array =$.grep($(window).data('goodsList'),function(n,i){
                         return n.id != goods.id ;
                    })
                    $(window).data('goodsList',array);
                }

             },addBlock:function(){
                var blockTypeVal = blockType.val();
                switch(blockTypeVal){
                    case '1':
                        addBlock.before(doT.template( mainTpl.image)({}));
                        break;
                    case '2':
                        var obj = {};
                        if( parentCategoryId != '' ){
                            obj.categoryList =
                                $(window).data("categoryMap")[parseInt(parentCategoryId)]['sub_categorys'];
                        }
                        var obj = $(doT.template( mainTpl.goods)(obj));
                        if(subCategoryList.val() != '0'){
                           obj.find("select").attr("disabled","");
                           obj.find("select").val(subCategoryList.val())
                        }
                        addBlock.before(obj);
                        break;
                    case '3':

                        addBlock.before(doT.template( mainTpl.tms)({}));
                        break;

                }
            }
            ,savePage:function(){
                var imageList = $(".div-image");
                var goodsList = $(".goods-form");
                var tmsList = $(".tms-block");
                var pageNameVal = pageName.val();
                var isInvalid = false ;
                var pageId  = $(window).data("pageId")||null ;
                if( pageId == null && pageNameVal == ''  ){
                    fn.alert("请填写页面名称");
                    return ;
                }

                var pageOrder = $pageOrder.val();
                if(pageOrder == '' || /\D+/.test(pageOrder) ){
                    fn.alert("请填写合法的顺序！");
                    return ;
                }
                var blockPadding = $blockPadding.val();
                var categoryId = categoryList.val() ;
                var subCategoryId = subCategoryList.val();
                var blocks =[];
                $.each(imageList,function(i,n){
                    if(isInvalid){
                        return ;
                    }
                    var height = $(n).find("input[name='height']").val();
                    var blockOrder = $(n).find("input[name='block_order']").val();
                    if(     blockOrder == '' || /\D+/.test(blockOrder)  ){
                        fn.alert("请填写合法的轮播块顺序！");
                        isInvalid = true;
                        return ;
                    }
                    var imageObj = {height:height,block_order:blockOrder,block_type:1};
                    var elements = [];
                    if($(n).find(".ul-image").size() == 0){
                        fn.alert("请增加轮播图片！");
                        isInvalid = true;
                        return ;
                    }
                    $.each($(n).find(".ul-image"),function(i,c){
                        var sortIndex =  $(c).find("input[name='sort_index']").val();
                        var imgUri = $(c).find("img").attr("src");
                        if(typeof(imgUri) == 'undefined' ||  imgUri == ''){
                            fn.alert("请上传轮播图片！");
                            isInvalid = true;
                            return ;
                        }
                        var target  =$(c).find("input[name='target']").val();
                        if(target == ''){
                            fn.alert("请填写图片链接！");
                            isInvalid = true;
                            return ;
                        }
                        if( sortIndex == '' || /\D+/.test(sortIndex)  ){
                            fn.alert("请填写合法的图片顺序！");
                            isInvalid = true;
                            return ;
                        }
                        var imageObj = {sort_index:sortIndex,image_url:imgUri,target_url:target};
                        /*if(imageObj.sort_index == ''){
                            delete imageObj.sort_index;
                        }*/

                        elements.push(imageObj);
                    })
                    imageObj.elements = JSON.stringify(elements);
                    blocks.push(imageObj);
                });
                $.each(goodsList,function(i,n){
                    if(isInvalid){
                        return ;
                    }
                    var categoryTitle = $(n).find("input[name='category_title']").val();
                    var subCategoryTitle = $(n).find("input[name='sub_category_title']").val();
                    var height = $(n).find("input[name='height']").val();
                    var subCategoryId  = $(n).find("select[name='sub_category_list']").val();
                    var blockOrder = $(n).find("input[name='block_order']").val();
                    if(blockOrder == '' || /\D+/.test(blockOrder) ){
                        fn.alert("请填写合法的商品分类块顺序！");
                        isInvalid = true;
                        return ;
                    }
                    var category ={category_title:categoryTitle,sub_category_title:subCategoryTitle
                    };
                    var goodsObj = {height:height,block_order:blockOrder,block_type:2,sub_category_id:subCategoryId};
                    var productList = [];
                    var box =  $(n).find("tr:not(:eq(0))");
                    if(box.size() == 0){
                        fn.alert("需要选择商品！");
                        isInvalid = true;
                        return ;
                    }
                    $.each($(n).find("tr:not(:eq(0))"),function(i,c){
                        var sellerId = $(c).attr("seller_id");
                        var itemId =  $(c).attr("item_id");
                        var itemOrder =  $(c).find("input[name='item_order']").val();
                        if(itemOrder == '' || /\D+/.test(itemOrder) ){
                            fn.alert("请填写合法的商品顺序！");
                            isInvalid = true;
                            return ;
                        }
                        var eleObj = {item_id:itemId,seller_id:sellerId,item_order:itemOrder};
                        productList.push(eleObj);
                    })
                    category.product_list_list = productList;
                    goodsObj.elements = JSON.stringify([category]);
                    blocks.push(goodsObj);
                })


                $.each(tmsList,function(i,n){
                    if(isInvalid){
                        return ;
                    }
                    var height = $(n).find("input[name='height']").val();
                    var blockOrder = $(n).find("input[name='block_order']").val();
                    var tmsName =  $(n).find("input[name='tms_name']").val();
                    if(blockOrder == '' || /\D+/.test(blockOrder) ){
                        fn.alert("请填写合法的tms块顺序！");
                        isInvalid = true;
                        return ;
                    }
                    if(tmsName == ''){
                        fn.alert("请填写tms名称！");
                        isInvalid = true;
                        return ;
                    }
                    var tmsObj = {block_order:blockOrder,height:height,
                        tms_name:tmsName,block_type:3};
                    if(tmsObj.block_order == ''){
                        delete tmsObj.block_order;
                    }
                    blocks.push(tmsObj);
                });

                if(isInvalid){
                    return ;
                }
                var data = $(".div-image,.goods-form,.tms-block");
                if(data.size() == 0){
                    fn.alert("请添加页面块！");
                    return ;
                }
                var isAdd = !$(window).data("pageId");
                $.post(ctx+"/mainweb/page/save.do",
                    {name:pageNameVal,category_id:categoryId,page_order:pageOrder
                        ,block_padding:blockPadding,id:pageId,sub_category_id:subCategoryId
                        ,blocks:JSON.stringify(blocks)},function(resp){
                        if(resp.code == 10000){
                            var tpl = doT.template(mainTpl.options);
                            if(isAdd){
                                pageList.append(tpl([{id:resp.data,name:pageNameVal}]));
                                pageList.parent().find(".select2-container").show();
                                pageName.hide();
                            }
                            fn.resetCategory();
                            fn.clean();
                            fn.disabledBtn();
                            $.jBox.info("保存页面成功！","提示");
                        }else{
                            $.jBox.alert("保存页面失败！","警告");
                        }
                    })
            }
            ,getPage:function(resp){
                resp = JSON.parse(resp);
                if(resp.code != 10000){
                    $.jBox.alert("查询页面异常",resp.msg);
                    return ;
                }
                fn.enabledBtn();
                fn.enablePublishBtn();
                fn.enabledDeleteBtn();
                fn.disabledSelect();


                var item = resp.data;
                $(window).data("pageId",item.id);
                $pageOrder.val(item.page_order);

                $blockPadding.val(item.block_padding);
                window.setSubCategory = function(id){

                }
                $.each(item.all_page_block_list,function(i,n){
                    switch(n.block_type){
                        case 1:
                            addBlock.before(doT.template( mainTpl.image)(n));
                            break ;
                        case 2:
                            var $tmp = $(doT.template( mainTpl.goods)(n)) ;
                            addBlock.before($tmp);
                            break ;
                        case 3:
                            addBlock.before(doT.template( mainTpl.tms)(n));
                            break;
                    }

                })
                if(item.category_id){
                    categoryList.val(item.category_id).trigger("change");
                }
                if(item.sub_category_id){
                    subCategoryList.val(item.sub_category_id).trigger("change");
                }
                var categoryIndex = 0;
                $.each(item.all_page_block_list,function(i,n){
                    switch(n.block_type){
                        case 2:
                            var ele = $("select[name='sub_category_list']:eq("+categoryIndex+")");
                            ele.addClass("disabled").attr("disabled","");
                            ele.find("option[value='"+n.sub_category_id+"']").attr("selected","");
                            categoryIndex++;
                            break ;

                    }

                })
            }
        }

        btnPublishPage.click(function(){

            var data = $(".div-image,.goods-form,.tms-block");
            if(data == 0){
                fn.alert("请添加页面块！");
                return ;
            }
            $.jBox.confirm("确定发布页面吗？","提示",function(a){

                if(a == 'cancel'){
                    return ;
                }
                $.post(ctx+"/mainweb/page/publish.do",{pageId:$(window).data("pageId")}
                    ,function(resp){
                        if(resp.code != 10000){
                            $.jBox.alert(resp.msg);
                        }else{
                            $.jBox.info('发布成功');
                        }
                    })
            })

        })



       $.post(ctx+"/item/category/zj/query.do",function(resp){
           var data = resp.data;
           var categoryMap = {};
           $.each(data,function(i,n){
               categoryMap[n.id]= n
           })
           $(window).data("categoryMap",categoryMap);
           var tpl = doT.template(mainTpl.cateOptions);
           categoryList.append(tpl(data));
       })
        categoryList.change(function(){
            var val = $(this).val();
            var allSubCategoryList = $("select[name='sub_category_list']");
            if(val == '0'){
                subCategoryList.find("option:not(:eq(0))").remove();
                allSubCategoryList.find("option:not(:eq(0))").remove();
                return ;
            }

            //设置一级类目
            parentCategoryId = val ;
            var obj =$(window).data("categoryMap")[parseInt(val)];
            var tpl = doT.template(mainTpl.cateOptions);
            allSubCategoryList.removeAttr("disabled");
            subCategoryList.find("option:not(:eq(0))").remove();
            subCategoryList.val("0").trigger("change");
            subCategoryList.append(tpl(obj['sub_categorys']));

            allSubCategoryList.find("option:not(:eq(0))").remove();
            allSubCategoryList.val("0").trigger("change");
            allSubCategoryList.append(tpl(obj['sub_categorys']));
        })
        subCategoryList.change(function(){
            var val = $(this).val();
            var allSubCategoryList = $("select[name='sub_category_list']");

            if(val == '0'){
                allSubCategoryList.removeClass("selected-disabled").removeAttr("disabled","")
                return ;
            }
            categoryId =  val ;
            allSubCategoryList.val(categoryId);
            allSubCategoryList.addClass("selected-disabled").attr("disabled","")
        })

        $.post(ctx+"/mainweb/page/names.do",function(resp){
            var data = JSON.parse(resp).data;
            var tpl = doT.template(mainTpl.options);
            $(window).data("pageNames",data);
            pageList.append(tpl(data));

        })
        pageList.change(function(){
            var val = $(this).val();

            if( val != ''){
                btnDelete.removeClass("disabled");
                btnPublishPage.removeClass("disabled");
                btnAddBlock.removeClass("disabled");
                btnSavePage.removeClass("disabled");
                fn.clean();
                $.post(ctx+"/mainweb/page/get.do",{id:val},function(resp){
                    fn.getPage(resp);
                })
            }

        })


        btnAddPage.click(function(){
           fn.addPage();
        })
        btnDelete.click(function(){
            btnDelete.addClass("disabled");
            var id = $(window).data("pageId");
            if(typeof(id) == 'undefined' || id == '' ){
                fn.alert("删除页面的id不能为空！");
                return ;
            }
            $.jBox.confirm("删除页面后将导致前台不能展示","确认删除此页面吗？",
             function(a){
                 if(a== 'cancel'){
                     return ;
                 }
                 $.post(ctx+"/mainweb/page/delete.do",{id:id},function(resp){
                     if(resp.code != 10000){
                         $.jBox.alert(resp.msg,"警告");
                         btnDelete.removeClass("disabled");
                         return ;
                     }
                     $.jBox.info("删除成功","提示");
                     
                     fn.resetCategory();
                     fn.clean();
                     var data =$.grep($(window).data("pageNames"),function(n,i){
                         return n.id != id;
                     })
                     $(window).data("pageNames",data);
                     pageList.find("option:not(:eq(0))").remove();
                     pageList.append(doT.template( mainTpl.options)(data));
                 })
             })

        })

        btnAddBlock.click(function(){
            fn.addBlock();
        })
        //image-delete
        $(document).on('click', '.block-delete', function() {
            var that = this;
            fn.confirm(function(){
                $(that).parents(".form-search").remove();
            })

        })
        $(document).on('click', '.btn-add-image', function() {
            $(this).parents(".block-image").append(mainTpl.imageItem)
        })
        $(document).on('click', '.image-delete', function() {
            var that = this;
            fn.confirm(function(){
                $(that).parents("ul").remove();
            })

        })
        var  loadCategory = "";
        //加载分类
        var loadCategoryAjax =  $.ajax({url:loadCategory});
        $.when(loadCategoryAjax).done(function(data){
            var tpl =doT.template(mainTpl.options);
            $("#category-list").append(tpl(data));
        });
        // 获取用户输入的信息
        $(".save-page").click(function(){
           fn.savePage();
        });
        var totalCount = 0;
        var pageSize = 10;
        var totalPage = 0;
        var keyword = '';
        var parentCategoryId = '';
        var categoryId ='';
        var arr_selected = [] ;
        var goodsData = null;  // 用来存获取的数据对象
        var jgoodsBody =$(".j-goods-tbody");
        function getGoods(currentPage) {

            $.post(ctx+'/item/query.do',
                {current_page:currentPage,page_size:pageSize,category_id:categoryId == '0'?'':categoryId,
                    parent_category_id:parentCategoryId== '0'?'':parentCategoryId,key:keyword} ,
                function(resp){
                    goodsData = resp.data;
                    totalCount = goodsData.total_count;
                    totalPage = Math.ceil(totalCount/pageSize);
                    var data = doT.template(mainTpl.goodsBox)(goodsData);
                    if(currentPage == 1){
                        $(".tcdPageCode").createPage({
                            pageCount:totalPage,
                            current:1,
                            backFn:function(p){

                                getGoods(p);
                            }
                        });
                    }

                    jgoodsBody.empty();
                    jgoodsBody.append(data);
                    //document.getElementById('j-goods-box').innerHTML = doT.template(tmpl)(goodsData);
                }
            )

        }
        $('.content').on('click', '.btn-add-item', function() {
            //getGoods(1,10);
            var parent = $(this).parents(".goods-category-whole");
            categoryId = parent.find("select[name='sub_category_list']").val();             
            window.goodsParent = parent;
             
        });
        $(".goods-submit").on('click',function(){
            var container = window.goodsParent ;
            var goodsTable = container.find(".goods-table");
            var tpl = doT.template(mainTpl.itemTr);
            var goodsList = $(window).data('goodsList');
            if(!goodsList || goodsList.length == 0){
                $.jBox.alert("请选择一个商品！");
                return ;
            }
            goodsTable.append(tpl(goodsList));
            $('#myModal').modal('hide');
            $(window).removeData('goodsList');
            window.goodsParent = undefined;
            $(".j-goods-tbody").empty();
        })
        $('#myModal').on('click', '.select-group-goods', function() {
               fn.check(this)
        });

        $(document).on('click','.delete-goods',function(){
            var that = this;
            fn.confirm(function () {
                $(that).parents("tr").remove();  
            })
            
        })
        $('#myModal').on('show',function(){

            getGoods(1,10);
        })

        $("#j-search").click(function(){
            keyword = $("#keyword").val();
            getGoods(1);
        })

        // totalPage = totalCount / count;
        // modal的分页插件


        var currImgSelectBtn = null;

        function getImgList(currentPage,pageSize) {
            $.ajax({
                type: 'POST',
                url: 'http://media.leaf.com/user_file.php' ,
                data: {
                    biz_code: 'hanshu',
                    user_id: 1,
                    path_id: 0,
                    keyword: undefined,
                    order: 'desc',
                    page: currentPage,
                    num: pageSize
                },
                success: function(data){
                    var data = JSON.parse(data);
                    totalCount = data.data.total_num;
                    count = data.data.list.lenth;
                    var tmpl = document.getElementById('img-select-template').innerHTML;
                    document.getElementById('j-img-select-box').innerHTML = doT.template(tmpl)(data.data.list);
                }
            });
        }
        $(".mySelectModalPage").createPage({
            pageCount: 5,
            current: 1,
            backFn: function(nextPage){
                getImgList(nextPage, 5);
            }
        });
        $('body').delegate('.imgItemUpload', 'click', function () {
            currImgSelectBtn = $(this);
            $('#mySelectModal').modal('show');
            getImgList(1,5);
        });

        $('body').delegate('.img-select', 'click', function () {
            var  imgSrc = $(this).closest('.j-goods-box').find('td').eq(0).find('img').attr('src');
            var imgElement = '<img src="'+ imgSrc + '" style="width: 50px;height: 50px;">';
            $('#mySelectModal').modal('hide');
            currImgSelectBtn.before(imgElement).hide();
        });
        $('#uploadNewImg').click(function () {
            $('#mySelectModal').modal('hide');
            $('#newImgModal').modal('show');
            initUploader();
        });

        $('body').delegate('.img-block-delete', 'click', function () {
            $(this).closest('.ul-image-select').remove();
        });
        // 优化retina, 在retina下这个值是2
        var ratio = window.devicePixelRatio || 1,
        // 缩略图大小
            thumbnailWidth = 100 * ratio,
            thumbnailHeight = 100 * ratio,
        // Web Uploader实例
            uploader;
        function initUploader() {
            $('#uploader-container').html('<div id="fileList"></div><div id="filePicker">选择图片</div>');
            // 初始化Web Uploader
            uploader = WebUploader.create({
                // 自动上传。
                auto: false,
                // swf文件路径
                //swf: BASE_URL + '/js/Uploader.swf',
                // 文件接收服务端。
                server: 'http://media.leaf.com/upload.php',
                formData:{
                    user_id: 1,
                    biz_code: 'hanshu'
                },
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: {
                    id: '#filePicker',
                    multiple: false
                },
                // 只允许选择文件，可选。
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                }
            });
            $("#filePicker").mouseenter(function(){
                if(uploader){
                    uploader.refresh();
                }
            });
            // 当有文件添加进来的时候
            uploader.on( 'fileQueued', function( file ) {
                var $li = $(
                        '<div id="' + file.id + '" class="file-item thumbnail">' +
                        '<img>' +
                        '<div class="info">' + file.name + '</div>' +
                        '</div>'
                    ),
                    $img = $li.find('img');
                $('#fileList').append( $li );
                // 创建缩略图
                uploader.makeThumb( file, function( error, src ) {
                    if ( error ) {
                        $img.replaceWith('<span>不能预览</span>');
                        return;
                    }
                    $img.attr( 'src', src );
                }, thumbnailWidth, thumbnailHeight );
            });
            // 文件上传过程中创建进度条实时显示。
            uploader.on( 'uploadProgress', function( file, percentage ) {
                var $li = $( '#'+file.id ),
                    $percent = $li.find('.progress span');

                // 避免重复创建
                if ( !$percent.length ) {
                    $percent = $('<p class="progress"><span></span></p>')
                        .appendTo( $li )
                        .find('span');
                }
                $percent.css( 'width', percentage * 100 + '%' );
            });
            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            uploader.on( 'uploadSuccess', function( file, res ) {
                $( '#'+file.id ).addClass('upload-state-done');
                var  imgSrc = res.data.url;
                var imgElement = '<img src="'+ imgSrc + '" style="width: 50px;height: 50px;">';
                currImgSelectBtn.before(imgElement).hide();
                uploader = null;
                $('#newImgModal').modal('hide');
            });
            // 文件上传失败，现实上传出错。
            uploader.on( 'uploadError', function( file ) {
                var $li = $( '#'+file.id ),
                    $error = $li.find('div.error');
                // 避免重复创建
                if ( !$error.length ) {
                    $error = $('<div class="error"></div>').appendTo( $li );
                }
                $error.text('上传失败');
            });
            // 完成上传完了，成功或者失败，先删除进度条。
            uploader.on( 'uploadComplete', function( file ) {
                $( '#'+file.id ).find('.progress').remove();
            });
            $('#confirm-upload').click(function () {
                //console.log("上传...");
                if(uploader) {
                    uploader.upload();
                }
            });
        }
    });
