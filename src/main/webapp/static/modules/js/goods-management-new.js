$(function() {
    _.templateSettings = {
        interpolate : /\{\{(.+?)\}\}/g
    };
    // 富文本编辑器
    var fat_editor = UE.getEditor('editor');
    //商品弹窗
    var goodsFilter = {
        brand_key: '',
        category_id: '',
        current_page: 1,
        item_status: undefined,
        key: '', //搜索框
        page_size: 10
    };
    // 点击添加关联商品所在的tbody
    var addTarget = null;
    //角标弹窗
    var iconFilter = {
        icon_name: '',
        page_size: 20,
        current_page: 1
    };
    var labelFilter = {
        icon_name: '',
        page_size: 20,
        current_page: 1
    };
    var isImgServer = null; // 判断是角标还是服务标签
    var errMsg = null; // 保存输入框不为空时的提示信息
    var relativeGoodsList = null; // 保存每个子场活动的关联商品的skuid，在每次添加商品时就会刷新。
    var goodInfo = {};
    $(".form-horizontal").on("click", "#add-goods-btn", (function() {
        relativeGoodsList = null;
        relativeGoodsList = new Array();
        trDoms = $(this).closest(".per-session").find("tbody tr");
        for (var i = 0; i < trDoms.length; i++) {
            relativeGoodsList.push($(trDoms[i]).attr("data-item_id"));
        }
        // 负责显表格
        $(".form-horizontal .parent-table").css("display", "table");
        addTarget = $(this).closest(".per-session").find(".relative-goods");
        $('#select-goods-modal').modal('show');
        //商品类目
        getCate();
       // 品牌
        //getBrand();
        getGoodsList();
    }));

    function getCate() {
        $.get(ctx+ '/item/category/query.do', { parent_id: 0 }, function(data) {
            $('#cate_key').html(doT.template(self_tpl.cate_key)(data));
            // bindEvent();
        });
    }

    function getTwoCate(id) {
        $.get(ctx + '/item/category/query.do',
            {
                parent_id: id
            },
            function(data) {
            $('#category_two').append(doT.template($('#j-template-category'))({
                items: data.data,
                category_value: 2
            }));
            // var template = _.template($('#j-template-category').html());
            // $('#category_two').html(template({
            //     items: data.data,
            //     category_value: 2
            // }));
        });
    }
    getBrand();
    function getBrand() {
        $.get(ctx + '/item/brand/query.do', {}, function(data) {
            $('#brand_key,#brand').html(doT.template(self_tpl.brand_key)(data.data));
        });
    }

    function getGoodsList() {
        $.get(ctx + '/item/query.do', {
                key: goodsFilter.key,
                brand_id: goodsFilter.brand_key,
                category_id: goodsFilter.category_id,
                item_status: goodsFilter.item_status,
                current_page: goodsFilter.current_page,
                page_size: goodsFilter.page_size
            },
            function(data) {
                var $render = $('#j-render-goods');
                $(data.data.data).each(function(k, value) {
                    if (typeof value.create_time == "undefined") {
                        value.create_time = '';
                    }
                });
                var template = _.template($('#j-template-goods').html());
                $render.html(template({
                    items: data.data.data,
                    type: 0 //1
                }));
                pageCtrl($("#goods-list-pager"), goodsFilter.current_page, Math.ceil(data.data.total_count / goodsFilter.pageSize));
            }
        );
    }

    function pageCtrl(pager, pageIndex, pageCount) {
        pager.createPage({
            pageCount: pageCount,
            current: pageIndex,
            backFn: function(currentPage) {
                if (pager.data('pagertype') === 'goods') {
                    goodsFilter.current_page = currentPage;
                    getGoodsList();
                };
                if (pager.data('pagertype') === 'mark') {
                    iconFilter.current_page = currentPage;
                    getMark();
                };
                if (pager.data('pagertype') === 'label') {
                    iconFilter.current_page = currentPage;
                    getLable();
                };
            }
        });
    }
    //获取角标
    function getMark() {
        $.get(ctx+'/item/icon/query.do',
            {
                page_size: iconFilter.page_size,
                current_page: iconFilter.current_page
            },
            function(data) {
                $('#mark-icon-list').html(doT.template(self_tpl.markTpl)(data));
                pageCtrl($('#icon-list-pager'), goodsFilter.current_page, Math.ceil(data.data.total_count / iconFilter.page_size));
            }
        );
    };
    // 获取标签
    function getLable() {
        $.get(ctx+ '/item/label/query.do', {
                page_size: iconFilter.page_size,
                current_page: iconFilter.current_page
            },
            function(data) {
                var objData = jQuery.parseJSON(data);
                $('#mark-lable-list').html(doT.template(self_tpl.lableTpl)(objData));
                pageCtrl($('#label-list-pager'), goodsFilter.current_page, Math.ceil(objData.data.total_count / iconFilter.page_size));
            }
        );
    };

    function renderSelectedGoods(goodsID_array) {
        $.get('/bossmanager/goods/query.do', {
                goodsID: goodsID_array
            },
            function(data) {
                $('#select-goods-modal').modal('hide');
                addTarget.html(doT.template(self_tpl.goodsTpl)(data.data.data)).closest('.selected-goods').show();
            }
        );
    }
    // 从获取到的数据中取出需要展示
    function bindEvent() {
        $('#isOnSale').change(function() {
            // goodsFilter.item_status === '4' ? goodsFilter.item_status = undefined : goodsFilter.item_status = '4';
            goodsFilter.item_status = goodsFilter.item_status === '4' ? undefined : '4';
            getGoodsList();
        });
        $('#cate_key').change(function() {
            goodsFilter.category_id = $(this).val();
            $('#category_two').closest('.form-group').show();
            getTwoCate($(this).val());
        });
        $('#brand_key').change(function() {
            goodsFilter.brand_key = $(this).val();
        });
        $('#seach-bth').click(function() {
            goodsFilter.key = $('#keyword-input').val().trim();
            getGoodsList();
        });
        // 点击展开扩展和隐藏
        $('#j-render-goods').delegate('.select-goods', 'click', function() {
            //$(this).toggleClass('active');
            $(this).parents("tr").next().toggleClass("show-or-hidden");
            if (!$(this).parents("tr").next().hasClass("show-or-hidden")) {
                $(this).html("隐藏");
            } else {
                $(this).html("展开合并");
            }
            var goodId = $(this).parents("tr").next().find("tbody").attr("id");
            spuId = goodId.substring(4);
            rendExtendTbody(spuId, goodId);
        });
        $('#j-render-goods').delegate('.j-s-sku', 'click', function() {
            goodInfo = {};
            goodInfo.img = $(this).closest("tr").find(".tc").attr("good-img");
            goodInfo.name = $(this).closest('tbody').attr("good-name");
            goodInfo.attribute = $(this).closest("tr").find(".attrbute").text();
            goodInfo.oriPrice = $(this).closest("tr").find(".ori-price").text();
            goodInfo.price = $(this).closest("tr").find(".price").text();
            goodInfo.num = $(this).closest("tr").find(".num").text();
            goodInfo.skuId = $(this).closest("tr").find(".sku-id").text();
            goodInfo.spuId = $(this).closest('tbody').attr("spu-id");
            goodInfo.sellerId = $(this).closest('tbody').attr("seller-id");
            goodInfo.item_sku_sn = $(this).closest("tr").find(".item_sku_sn").text();
            var goodSku = $(this).closest("tr").find(".sku-id").text();
            if (relativeGoodsList.length == 0) {
                addTarget.append(doT.template(self_tpl.goodsTpl)(goodInfo)).closest('.selected-goods').show();
            }
            for (var i = 0; i < relativeGoodsList.length; i++) {
                if (relativeGoodsList[i] != goodSku && i == (relativeGoodsList.length - 1)) {
                    addTarget.append(doT.template(self_tpl.goodsTpl)(goodInfo)).closest('.selected-goods').show();
                } else {
                    break;
                }
            }
            $('#select-goods-modal').modal('hide');

            $(this).toggleClass('active');
        });
        // 获取展开SKU商品
        function rendExtendTbody(spuId, goodId) {
            $.get('/bossmanager/item/sku/queryx.do', {
                    goodsID: spuId
                },
                function(data) {
                    var template = _.template($('#j-template-sku').html());
                    $("#" + goodId).html(template({
                        items: data.data.skus
                    }));
                }
            );
        }
        $('#confirm-select').click(function() {
            //选择的商品id数组
            var goodsID_array = [];
            $('.ui-table .active').each(function() {
                goodsID_array.push($(this).data('id'));
            });
            //渲染已选择的商品列表
            goodsID_array.length != 0 ? renderSelectedGoods(goodsID_array) : undefined;
        });
        $('.icon-confirm-select').click(function() {
            var selectedIcon = $('#mark-icon-list .img.active');
            var selectedLable = $('#mark-lable-list .img.active');
            if (selectedLable.length != 0) {
                if (isImgServer == 1) {
                    $(".add-icon-btn").html('<img src="' + selectedLable.data('url') + '" class="img-circle" data-iconID="' + selectedLable.data('id') + '">');
                } else {
                    $(".img-server").prepend('<div><img src="' + selectedLable.data('url') + '" class="img-circle" data-iconID="' + selectedLable.data('id') + '"><i class="icon-remove-circle dele" position="absolute"></div>');
                }
                $('.select-icon-modal').modal('hide');
            };
            if (selectedIcon.length != 0) {
                $(".add-icon-btn").html('<img src="' + selectedIcon.data('url') + '" class="img-circle" data-iconID="' + selectedIcon.data('id') + '">');
                $('.select-icon-modal').modal('hide');
            }
        });
        $('.add-icon-btn').click(function() {
            isImgServer = 1;
            $('#select-icon-modal').modal('show');
            getMark();
        });
        $('.img-server').click(function() {
            isImgServer = 2;
            $('#select-lable-modal').modal('show');
            getLable();
        });
        $(".img-server").on("click", '.dele', function() {
            $(this).parent().remove();
            return false;
        })
        $('#icon-seach-bth').click(function() {
            iconFilter.icon_name = $('#keyword-icon-input').val().trim();
            getMark();
        });
        $('#mark-icon-list').delegate('.img', 'click', function() {
            // $('#mark-icon-list .img').removeClass('active');
            $(this).closest('li').siblings().find('.img').removeClass('active');
            $(this).toggleClass('active');
        });
        $('#mark-lable-list').delegate('.img', 'click', function() {
            // $('#mark-icon-list .img').removeClass('active');
            $(this).closest('li').siblings().find('.img').removeClass('active');
            $(this).toggleClass('active');
        });
        $('#session-wrap').on('click', '.delete-icon', function() {
            $(this).closest('tr').remove();
            var id = $(this).closest('tr').attr("data-item_id");
        });
        $('#cancel-selected-btn').click(function() {
            location.href = "/time-purchase-list.html";
        });
    }
    bindEvent();
    // 收集数据
    function collectData() {
        var campaignInfo = {
            cate_name: $('.whole .content .cateName span').eq(0).val().trim(),
            good_name: $('.form-horizontal .goodName').eq(0).val().trim(),
            sale_info: $('.form-horizontal .sale-info').eq(0).val().trim(),
            brand_name: $('.form-horizontal .brandName').eq(0).val().trim(),
            icon_img: {},
            label_img: [],
            band_goods: [],
            main_pics:[],
            good_details:""
        };
        campaignInfo.icon_img.link = $('.whole .content .add-icon-btn img').eq(0).attr("src");
        campaignInfo.icon_img.icon_id = $('.whole .content .add-icon-btn img').eq(0).attr("data-iconid");
        var labelImgLen = $('.whole .content .img-server img').length;
        for (var i = 0; i < labelImgLen; i++) {
            var item = {};
            item.link = $('.whole .content .img-server img').eq(i).attr("src");
            item.icon_id = $('.whole .content .img-server img').eq(i).attr("data-iconid");
            campaignInfo.label_img.push(item);
        };
        var mainPicLen = $("#goods-pic img").length;
        for(var i = 0; i<mainPicLen; i++){
            var item = {};
            item.link = $("#goods-pic img").eq(i).attr("src");
            campaignInfo.main_pics.push(item);
        };
        campaignInfo.good_details = $("iframe body.view").html();
        var skuItem = null;
        $('.per-session tbody tr').each(function() {
            var skuItem = {};
            skuItem.item_id = $(this).attr('spu-id');
            skuItem.item_name = $(this).find(".name p").eq(0).text();
            skuItem.item_sku_sn = $(this).attr('item_sku_sn');
            skuItem.sku_code = $(this).find(".name p").eq(1).text();
            skuItem.seller_id = $(this).attr("sellerid")
            skuItem.market_price = $(this).find(".price p span").text();
            skuItem.item_sku_id = $(this).attr('sku-id');
            campaignInfo.band_goods.push(skuItem);
        });
        return campaignInfo;
    }
    // 检查表单是否为空
    function checkDataIsProper() {
        errMsg = null;
        if ($(".form-horizontal .per-session tbody tr").length == 0) {
            errMsg = "捆绑商品不能为空";
        };
        if ($(".form-horizontal .brandName").eq(0).val() == "请选择品牌") {
            errMsg = "商品品牌不能为空";
        };
        if ($(".form-horizontal .goodName").eq(0).val().trim().length == 0) {
            errMsg = "商品名称不能为空";
        };
        if ($(".whole .content .cateName span").eq(0).text().trim().length == 0) {
            errMsg = "商品类目不能为空";
        };
    }
    
    // 删除子场
    $(".form-horizontal").on('click', ".btn-dele", function(event) {
        $(event.target).parents('.per-session').remove();
    });
    $("#campaign-name").on("blur", function() {
        if ($("#campaign-name").val().length == 0) {
            layer.tips('请填写活动名称', '#campaign-name');
        } else if ($("#campaign-name").val().length >= 10) {
            layer.tips('最长10个字符', '#campaign-name');
        }
    });
    $("#input-in-order").on("keyup", function() {
        this.value = this.value.replace(/[^\d]/g, '');
    });
    // 保存
    $('#save-selected-btn').click(function() {
        checkDataIsProper();
        if (errMsg != null) {
            layer.msg(errMsg);
        } else {
            var parm = collectData();
            cosole.log(parm)
            $.post('/seckill/add.do', {
                    parent_id: parm
                },
                function() {
                    layer.msg("保存成功！");
                }
            );
        }
    });
   
	// 检查表单是否为空
	function checkDataIsProper(){
		errMsg = null;
		if($(".form-horizontal .per-session tbody tr").length == 0){
			errMsg = "捆绑商品不能为空";
		};
		if($(".form-horizontal .brandName").eq(0).val() =="请选择品牌"){
			errMsg = "商品品牌不能为空";
		};
		if($(".form-horizontal .sale-info").eq(0).val().trim().length == 0){
			errMsg = "促销信息不能为空";
		};
		if($(".form-horizontal .goodName").eq(0).val().trim().length == 0){
			errMsg = "商品名称不能为空";
		};
		if($(".whole .content .cateName span").eq(0).text().trim().length == 0){
			errMsg = "商品类目不能为空";
		};
        if($("#goods-pic .item-img").length == 0){
            errMsg = "商品主图不能为空";
        };
        if (fat_editor.getContent() == '') {
            errMsg = "商品详情不能为空！";
        }
	}
	var interText = doT.template($("#j-template-session").text());
	$("#session-wrap").append(interText());
	// 删除子场
	$(".form-horizontal").on('click',".btn-dele",function(event){
		$(event.target).parents('.per-session').remove();
	});
	$("#campaign-name").on("blur",function(){
		if($("#campaign-name").val().length==0){
			layer.tips('请填写活动名称', '#campaign-name');
		}else if ($("#campaign-name").val().length >= 10) {
			layer.tips('最长10个字符', '#campaign-name');
		}
	});
	$("#input-in-order").on("keyup",function(){
		this.value=this.value.replace(/[^\d]/g,'');
	});
	// 保存
	$('#save-selected-btn').click(function(){
		checkDataIsProper();
		if(errMsg != null){
			layer.msg(errMsg);
		}else{
			var parm = collectData();
				console.log(parm)
			$.post('/seckill/add.do', 
				{
					parent_id: parm
				},
				function(){
					layer.msg("保存成功！");
				}
			);
		}
	});
    function collectData() {
        function getLabes() {
            var label_icon = [];
            $('#goods_labes img').each(function () {
                label_icon.push($(this).data('iconid'));
            });
            return label_icon.join(',');
        }
        var gallery =[];
        $('#goods-pic .item-img').each(function () {
            var temp = {
                id: '',
                color: '',
                img: $(this).data('imgsrc')
            };
            gallery.push(temp);
        });
        var composites = [];
        // sku-id="21685" spu-id="3576" item_sku_sn="HJIIHIJH"
        $('#relative-goods tr').each(function () {
            var temp = {
                sku_id: $(this).attr('sku-id'),
                item_id: $(this).attr('spu-id'),
                num: $(this).find('.sku-sum').val().trim()
            };
            composites.push(temp);
        });
        var skus = [];
        return {
            name: $('#goods_name').val().trim(),
            cate_id: $('#goods_category_id').attr('category_id'),
            brief: $('#goods_brief').val().trim(),
            description: fat_editor.getContent(),
            brand_id: $('#brand option:checked').val(),
            corner_icon: $('#goods_corner_icon img').data('iconid'),
            labels: getLabes(),
            gallery: JSON.stringify(gallery),
            composites: composites
        }
    }
	$("#choose-categary").on("click",function(){
		$('#select-goods-categary').modal();
		$.get(ctx+'/item/category/query.do',
			{

			},
			function(data){
                console.log(data)
                var interText = doT.template($("#j-template-categary").text());
                $("#select-goods-categary .left").html(interText(data.data));
                $("#select-goods-categary .right").html(interText(data.data));
                $("#select-goods-categary .left p").first().addClass("active");
                var attr = $("#select-goods-categary .left p").attr("data-id");
                $("#select-goods-categary .right p[data-parent_id =" + attr + "]").css("display", "block");
                $("#select-goods-categary .right p").first().addClass("active");
            });
    });
    $("#select-goods-categary .left").on("click", "p", function() {
        $("#select-goods-categary .left p").removeClass("active");
        $("#select-goods-categary .right p").removeClass("active");
        $(this).addClass("active");
        $("#select-goods-categary .right p").css("display", "none");
        var attr = $(this).attr("data-id");
        $("#select-goods-categary .right p[data-parent_id =" + attr + "]").css("display", "block");
    });
    $("#select-goods-categary .right").on("click", "p", function() {
        if ($("#select-goods-categary .left p").hasClass("active")) {
            $("#select-goods-categary .right p").removeClass("active");
            $(this).addClass("active");
        }
    });
    var cateLeftName = null;
    var cateRightName = null;
    $("#confirm-categary").on("click", function() {
        var cateLeft = $("#select-goods-categary .modal-body .left .active");
        var cateRight = $("#select-goods-categary .modal-body .right .active");
        var category_id = $("#select-goods-categary .modal-body .right .active").attr("data-id");
        cateLeftName = cateLeft.text();
        cateRightName = cateRight.text();
        $(".content .cateName span").text(cateLeftName + "-" + cateRightName);
        $(".content .cateName span").attr("category_id",category_id);
        if ($("#select-goods-categary .left p").hasClass("active") && $("#select-goods-categary .right p").hasClass("active")) {
            $('#select-goods-categary').modal('hide');
        };
        //init(category_id,cateRightName);
    });

    // function init(categaryId, name) {
    //     $.get(ctx + '/item/brand/query.do',
    //         {
    //             category_id: categaryId
    //         },
    //         function(data) {
    //             for (var i = 0; i < data.data.data.length; i++) {
    //                 // $(".brand select").append('<option>' + data.data.data[i].brand_name + '</option>');
    //             }
    //         }
    //     );
    // };
    // init();


    function getImgList(currentPage, pageSize) {
        $.ajax({
            type: 'POST',
            url: 'http://media.leaf.com/user_file.php',
            data: {
                biz_code: 'hanshu',
                user_id: 1,
                path_id: 0,
                keyword: undefined,
                order: 'desc',
                page: currentPage,
                num: pageSize
            },
            success: function(data) {
                var data = JSON.parse(data);
                totalCount = data.data.total_num;
                count = data.data.list.lenth;
                var tmpl = document.getElementById('img-select-template').innerHTML;
                document.getElementById('j-img-select-box').innerHTML = doT.template(tmpl)(data.data.list);
            }
        });
    }
    $('#goods-pic-add-icon').click(function() {
        $('#mySelectModal').modal('show');
        getImgList(1, 5);
    });
    $(".mySelectModalPage").createPage({
        pageCount: 5,
        current: 1,
        backFn: function(nextPage) {
            getImgList(nextPage, 5);
        }
    });
    $('#uploadNewImg').click(function() {
        $('#mySelectModal').modal('hide');
        $('#newImgModal').modal('show');
        initUploader();
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
            formData: {
                user_id: 1,
                biz_code: 'hanshu'
            },
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: {
                id: '#filePicker',
                multiple: true
            },
            // 只允许选择文件，可选。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        $("#filePicker").mouseenter(function() {
            if (uploader) {
                uploader.refresh();
            }
        });
        // 当有文件添加进来的时候
        uploader.on('fileQueued', function(file) {
            var $li = $(
                    '<div id="' + file.id + '" class="file-item thumbnail">' +
                    '<img>' +
                    '<div class="info">' + file.name + '</div>' +
                    '</div>'
                ),
                $img = $li.find('img');
            $('#fileList').append($li);
            // 创建缩略图
            uploader.makeThumb(file, function(error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }
                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function(file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo($li)
                    .find('span');
            }
            $percent.css('width', percentage * 100 + '%');
        });
        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function(file, res) {
            $('#' + file.id).addClass('upload-state-done');
            var imgSrc = res.data.url;
            var imgElement = '<span class="item-img" data-imgsrc="'+ imgSrc +'"><img src="' + imgSrc + '"><a href="javascript:;" class="cancel"></a></span>';
            $('#goods-pic-add-icon').before(imgElement);
            uploader = null;
            $('#newImgModal').modal('hide');
        });
        // 文件上传失败，现实上传出错。
        uploader.on('uploadError', function(file) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');
            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }
            $error.text('上传失败');
        });
        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function(file) {
            $('#' + file.id).find('.progress').remove();
        });
        $('#confirm-upload').click(function() {
            //console.log("上传...");
            if (uploader) {
                uploader.upload();
            }
        });
    }
    $('body').delegate('.img-select', 'click', function () {
        var  imgSrc = $(this).closest('.j-goods-box').find('td').eq(0).find('img').attr('src');
        var imgElement = '<span class="item-img" data-imgsrc="'+ imgSrc +'"><img src="' + imgSrc + '"><a href="javascript:;" class="cancel"></a></span>';
        $('#mySelectModal').modal('hide');
        $('#goods-pic-add-icon').before(imgElement);
    });
    $('#goods-pic').delegate('.cancel','click',function(){
        $(this).closest('.item-img').remove();
    });
});
