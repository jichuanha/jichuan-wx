/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(6);


/***/ },
/* 1 */,
/* 2 */,
/* 3 */
/***/ function(module, exports) {

	module.exports = {
		"api_path": "/jeesite/a",
		"preview_path":"http://m.leaf.com/"}

/***/ },
/* 4 */
/***/ function(module, exports) {

	module.exports = {
		parse : function(obj) {
			if(typeof obj != 'object') {
	            obj = JSON.parse(obj);
	        }
	        return obj;
		}
	}

/***/ },
/* 5 */,
/* 6 */
/***/ function(module, exports, __webpack_require__) {

	// var mainTpl = require('../module/recommend/mainweb-tpl.js');
	var api_path = __webpack_require__(3).api_path;
	var httpUrl = __webpack_require__(7);
	var gridFactory = __webpack_require__(8);
	var accessCtrl = __webpack_require__(13);
	var jsonstring2object = __webpack_require__(4);
	$(function() {
	    var pageTypeSelect = $('#page-type');//pageTypeSelect.select2();
	    var blockType = $("#block-type");
	    var addBlock = $(".add-block");
	    var btnAddBlock = $("#add-block");
	    var btnSavePage = $("#save-page-btn");
	    var categoryList = $("#cate_key");
	    var subCategoryList = $("#category_two");
	    // var btnPublishPage = $("#publish-page");
	    var tabList = $("#tab-list");tabList.select2();
	    // var tabListLi = $('#tab-list-li');
	    var btnDelete = $("#delete-page");
	    var pageName = $("input[name='page_name']");
	    var tabName = $("input[name='tab_name']");
	    var tabNameLi = $('#tab-name-li');
	    var pageNameLi = $('#page-name-li');
	    var $pageOrder = $("input[name='page_order']");
	    var $blockPadding = $("input[name='block-padding']");
	    var btnAddPage = $("#add-page");
	    var DEFAULT_PAGE_ORDER = '99';
	    // gridFactory.init();
	    var fn = {
	        addPage: function() {
	            // fn.resetCategory();
	            // tabList.parent().find(".select2-container").hide();
	            // tabNameLi.show();
	            if(pageTypeSelect.val() === '1'){
	                tabNameLi.show();
	            }
	            $(window).removeData("pageId");
	            btnDelete.attr("disabled", "");
	            btnDelete.addClass("disabled");
	            $pageOrder.val(DEFAULT_PAGE_ORDER);
	            fn.clean();
	            fn.enabledBtn();
	            // fn.disablePublishBtn();
	            fn.enableSelect();
	        },
	        resetCategory: function() {
	            // categoryList.prop("disabled", false);
	            // subCategoryList.prop("disabled", false);
	            $pageOrder.val('99');
	            $blockPadding.val('20');
	            // categoryList.val('0').trigger("change");
	            // subCategoryList.find("option:not(:eq(0))").remove();
	            // subCategoryList.val('0').trigger("change");
	            tabList.val('').trigger("change");

	        },
	        disabledSelect: function() {
	            // categoryList.prop("disabled", true);
	            // subCategoryList.prop("disabled", true);
	        },
	        enableSelect: function() {
	            // categoryList.prop("disabled", false);
	            // subCategoryList.prop("disabled", false);
	        },
	        enabledBtn: function() {
	            btnSavePage.removeClass("disabled");
	            btnAddBlock.removeClass("disabled");
	            btnSavePage.removeAttr("disabled");
	            btnAddBlock.removeAttr("disabled");
	        },
	        enablePublishBtn: function() {
	            // btnPublishPage.removeClass("disabled");
	            // btnPublishPage.removeAttr("disabled");
	        },
	        disabledBtn: function() {
	            btnAddBlock.addClass("disabled");
	            btnSavePage.addClass("disabled");
	            // btnPublishPage.addClass("disabled");
	            btnAddBlock.attr("disabled", "");
	            btnSavePage.attr("disabled", "");
	            // btnPublishPage.attr("disabled", "");
	        },
	        disablePublishBtn: function() {
	            // btnPublishPage.addClass("disabled");
	            // btnPublishPage.attr("disabled", "");
	        },
	        enabledDeleteBtn: function() {
	            btnDelete.removeAttr("disabled");
	            btnDelete.removeClass("disabled");
	        },
	        clean: function() {
	            $(".goods-form,.tms-block,.div-image,.div-single-img,.block-grid-container").remove();
	        },
	        confirm: function(fun) {
	            layer.confirm('确定删除吗？', {
	                btn: ['确定','取消'] //按钮
	            }, function(){
	                fun();
	                layer.msg('已删除');
	            }, function(){
	                return;
	            });
	            // $.jBox.confirm("确定删除吗？", "提示", function(a) {
	            //     if (a == 'cancel') {
	            //         return;
	            //     }
	            //     fun();
	            // })
	        },
	        alert: function(info) {
	            // $.jBox.alert(info, "警告");
	            layer.msg(info);
	        },
	        check: function(that) {
	            var text = $(that).text();
	            var goods = $(that).attr("data-info");
	            goods = JSON.parse(decodeURIComponent(goods));
	            if (text == '选择') {
	                $(that).css({ color: '#fff', background: '#5cb85c', border: '#5cb85c' }).text("取消");
	                var goodsList = $(window).data('goodsList') || [];
	                goodsList.push(goods);
	                $(window).data('goodsList', goodsList);
	            } else {
	                $(that).removeAttr("style");
	                $(that).text("选择");
	                var array = $.grep($(window).data('goodsList'), function(n, i) {
	                    return n.id != goods.id;
	                })
	                $(window).data('goodsList', array);
	            }

	        },
	        addBlock: function() {
	            var blockTypeVal = blockType.val();
	            switch (blockTypeVal) {
	                case '1':
	                    addBlock.before(doT.template(mainTpl.image)({}));
	                    break;
	                case '2':
	                    var obj = {};
	                    if (parentCategoryId != '') {
	                        obj.categoryList =
	                            $(window).data("categoryMap")[parseInt(parentCategoryId)]['sub_categorys'];
	                    }
	                    var obj = $(doT.template(mainTpl.goods)(obj));
	                    // if (subCategoryList.val() != '0') {
	                    //     obj.find("select").attr("disabled", "");
	                    //     obj.find("select").val(subCategoryList.val())
	                    // }
	                    addBlock.before(obj);
	                    break;
	                case '3':

	                    addBlock.before(doT.template(mainTpl.tms)({}));
	                    break;
	                case '5':
	                    // var girdBlockID = 
	                    addBlock.before(doT.template(mainTpl.grid)({}));
	                    gridFactory.init();
	                    break;
	                case '6':
	                    addBlock.before(doT.template(mainTpl.singleImgNew)({}));
	                    break;

	            }
	        },
	        savePage: function() {
	            var imageList = $(".div-image");
	            var singleImgList = $('.div-single-img');
	            var goodsList = $(".goods-form");
	            var tmsList = $(".tms-block");
	            var gridList = $('.block-grid-container');
	            var tabNameVal = tabName.val();
	            var pageNameVal = pageName.val();
	            var isInvalid = false;
	            // var pageId = $(window).data("pageId") || null;
	            var pageType = null;
	            var pageId = null;
	            if(accessCtrl.isNew()) {
	                pageType = pageTypeSelect.val();
	            } else {
	                pageType = httpUrl.getQueryString('pagetype');
	                pageId = httpUrl.getQueryString('pageid');
	            }
	            
	            if (pageNameVal == '') {
	                layer.msg('请填写页面名称');
	                return;
	            }
	            //只有界面类型为主页时才判断页签名称是否为空
	            if (pageTypeSelect.val() === '1' && pageId == null && tabNameVal == '') {
	                layer.msg('请填写页签名称');
	                return;
	            }

	            var pageOrder = $pageOrder.val();
	            if (pageOrder == '' || /\D+/.test(pageOrder)) {
	                layer.msg('请填写合法的顺序！');
	                return;
	            }
	            var blockPadding = $blockPadding.val();
	            var categoryId = categoryList.val();
	            var subCategoryId = subCategoryList.val();
	            var blocks = [];
	            $.each(imageList, function(i, n) {
	                if (isInvalid) {
	                    return;
	                }
	                var height = $(n).find("input[name='height']").val();
	                var blockOrder = $(n).find("input[name='block_order']").val();
	                if (blockOrder == '' || /\D+/.test(blockOrder)) {
	                    layer.msg("请填写合法的轮播块顺序！");
	                    isInvalid = true;
	                    return;
	                }
	                var imageObj = { height: height, block_order: blockOrder, block_type: 1 };
	                var elements = [];
	                if ($(n).find(".ul-image").size() == 0) {
	                    layer.msg("请增加轮播图片！");
	                    isInvalid = true;
	                    return;
	                }
	                $.each($(n).find(".ul-image"), function(i, c) {
	                    var sortIndex = $(c).find("input[name='sort_index']").val();
	                    var imgUri = $(c).find("img").attr("src");
	                    if (typeof(imgUri) == 'undefined' || imgUri == '') {
	                        layer.msg("请上传轮播图片！");
	                        isInvalid = true;
	                        return;
	                    }
	                    var target = $(c).find("input[name='target']").val();
	                    if (target == '') {
	                        layer.msg("请填写轮播块的图片链接！");
	                        isInvalid = true;
	                        return;
	                    }
	                    if (sortIndex == '' || /\D+/.test(sortIndex)) {
	                        layer.msg("请填写合法的图片顺序！");
	                        isInvalid = true;
	                        return;
	                    }
	                    var imageObj = { sort_index: sortIndex, image_url: imgUri, target_url: target };
	                    /*if(imageObj.sort_index == ''){
	                        delete imageObj.sort_index;
	                    }*/

	                    elements.push(imageObj);
	                })
	                imageObj.elements = JSON.stringify(elements);
	                blocks.push(imageObj);
	            });
	            $.each(singleImgList, function(i, n) {
	                if (isInvalid) {
	                    return;
	                }
	                var height = $(n).find("input[name='height']").val();
	                var blockOrder = $(n).find("input[name='block_order']").val();
	                var imageObj = { height: height, block_order: blockOrder, block_type: 6 };
	                var elements = [];
	                if ($(n).find(".ul-image").size() == 0) {
	                    layer.msg("请增加图片块图片！");
	                    isInvalid = true;
	                    return;
	                }
	                $.each($(n).find(".ul-image"), function(i, c) {
	                    // var sortIndex = $(c).find("input[name='sort_index']").val();
	                    var imgUri = $(c).find("img").attr("src");
	                    if (typeof(imgUri) == 'undefined' || imgUri == '') {
	                        layer.msg("请上传图片块图片！");
	                        isInvalid = true;
	                        return;
	                    }
	                    var target = $(c).find("input[name='target']").val();
	                    // if (target == '') {
	                    //     layer.msg("请填写图片链接！");
	                    //     isInvalid = true;
	                    //     return;
	                    // }
	                    // if (sortIndex == '' || /\D+/.test(sortIndex)) {
	                    //     layer.msg("请填写合法的图片顺序！");
	                    //     isInvalid = true;
	                    //     return;
	                    // }
	                    var imgWidth = $(c).closest('.ul-image').find('[name="width-px"]').val();
	                    var imgHeight = $(c).closest('.ul-image').find('[name="height-px"]').val();
	                    var imageObj = {image_url: imgUri, target_url: target, width: imgWidth, height: imgHeight };
	                    /*if(imageObj.sort_index == ''){
	                        delete imageObj.sort_index;
	                    }*/

	                    elements.push(imageObj);
	                })
	                imageObj.elements = JSON.stringify(elements);
	                blocks.push(imageObj);
	            });
	            $.each(goodsList, function(i, n) {
	                if (isInvalid) {
	                    return;
	                }
	                var categoryTitle = $(n).find("input[name='category_title']").val();
	                var subCategoryTitle = $(n).find("input[name='sub_category_title']").val();
	                var height = $(n).find("input[name='height']").val();
	                var subCategoryId = $(n).find("select[name='sub_category_list']").val();
	                var blockOrder = $(n).find("input[name='block_order']").val();
	                if (blockOrder == '' || /\D+/.test(blockOrder)) {
	                    layer.msg("请填写合法的商品分类块顺序！");
	                    isInvalid = true;
	                    return;
	                }
	                var category = {
	                    category_title: categoryTitle,
	                    sub_category_title: subCategoryTitle
	                };
	                var goodsObj = { height: height, block_order: blockOrder, block_type: 2, sub_category_id: subCategoryId };
	                var productList = [];
	                var box = $(n).find("tr:not(:eq(0))");
	                if (box.size() == 0) {
	                    layer.msg("需要选择商品！");
	                    isInvalid = true;
	                    return;
	                }
	                $.each($(n).find("tr:not(:eq(0))"), function(i, c) {
	                    var sellerId = $(c).attr("seller_id");
	                    var itemId = $(c).attr("item_id");
	                    var itemOrder = $(c).find("input[name='item_order']").val();
	                    if (itemOrder == '' || /\D+/.test(itemOrder)) {
	                        layer.msg("请填写合法的商品顺序！");
	                        isInvalid = true;
	                        return;
	                    }
	                    var eleObj = { item_id: itemId, seller_id: sellerId, item_order: itemOrder };
	                    productList.push(eleObj);
	                })
	                category.product_list_list = productList;
	                goodsObj.elements = JSON.stringify([category]);
	                blocks.push(goodsObj);
	            })


	            $.each(tmsList, function(i, n) {
	                if (isInvalid) {
	                    return;
	                }
	                var height = $(n).find("input[name='height']").val();
	                var blockOrder = $(n).find("input[name='block_order']").val();
	                var tmsName = $(n).find("input[name='tms_name']").val();
	                if (blockOrder == '' || /\D+/.test(blockOrder)) {
	                    layer.msg("请填写合法的tms块顺序！");
	                    isInvalid = true;
	                    return;
	                }
	                if (tmsName == '') {
	                    layer.msg("请填写tms名称！");
	                    isInvalid = true;
	                    return;
	                }
	                var tmsObj = {
	                    block_order: blockOrder,
	                    height: height,
	                    tms_name: tmsName,
	                    block_type: 3
	                };
	                if (tmsObj.block_order == '') {
	                    delete tmsObj.block_order;
	                }
	                blocks.push(tmsObj);
	            });


	            $.each(gridList, function(i,n){
	                if(!gridFactory.validateGrid(n)) {
	                    isInvalid = true;
	                    return false;
	                } else {
	                    var gridData = gridFactory.saveGrid();
	                    var blockOrder = $(n).find("input[name='block_order']").val();
	                    var gridObj = {
	                        block_order: blockOrder,
	                        block_type: 5,
	                        elements: JSON.stringify([gridData])
	                    };
	                    blocks.push(gridObj);

	                }
	            });
	            
	            if (isInvalid) {
	                return;
	            }
	            var data = null;
	            if(pageTypeSelect.val() === '1') {
	                data = $(".div-image,.div-single-img,.goods-form,.tms-block,.block-grid-container");
	            } else {
	                data = $(".div-image,.div-single-img,.goods-form,.block-grid-container");
	                tabNameVal = pageNameVal;
	            }
	            
	            if (data.size() == 0) {
	                layer.msg("请添加页面块！");
	                return;
	            }
	            // var isAdd = !$(window).data("pageId");
	            $.post(api_path + "/mainweb/page/save.do", {
	                type: pageType,
	                id: pageId,
	                // page_name: pageNameVal,
	                // tab_name: tabNameVal,
	                name: tabNameVal,
	                category_id: '0',
	                page_order: pageOrder,
	                block_padding: blockPadding,
	                // id: pageId,
	                sub_category_id: '0',
	                blocks: JSON.stringify(blocks)
	            }, function(resp) {
	                if (resp.code == 10000) {
	                    // var tpl = doT.template(mainTpl.options);
	                    // if (isAdd) {
	                    //     tabList.append(tpl([{ id: resp.data, name: tabNameVal }]));
	                    //     tabList.parent().find(".select2-container").show();
	                    //     tabNameLi.hide();
	                    // }
	                    // fn.resetCategory();
	                    // fn.clean();
	                    fn.disabledBtn();
	                    // $.jBox.info("保存页面成功！", "提示");
	                    layer.msg("保存页面成功！");
	                    location.href = 'mainweb-list.html';
	                } else {
	                    // $.jBox.alert("保存页面失败！", "警告");
	                    layer.msg("保存页面失败！");
	                }
	            })
	        },
	        getPage: function(resp) {
	            resp = jsonstring2object.parse(resp);
	            // resp = JSON.parse(resp);
	            if (resp.code != 10000) {
	                // $.jBox.alert("查询页面异常", resp.msg);
	                layer.msg(resp.msg);
	                return;
	            }
	            fn.enabledBtn();
	            // fn.enablePublishBtn();
	            fn.enabledDeleteBtn();
	            fn.disabledSelect();


	            var item = resp.data;
	            // $(window).data("pageId", item.id);
	            if(httpUrl.getQueryString('pagetype') != '1') {
	                pageName.val(item.name);
	                pageName.removeAttr('disabled');
	                tabNameLi.hide();
	            } else {
	                tabName.val(item.name);
	            }
	            // pageName.val(item.name);
	            $pageOrder.val(item.page_order);
	            $blockPadding.val(item.block_padding);
	            window.setSubCategory = function(id) {
	            }
	            $.each(item.all_page_block_list, function(i, n) {
	                switch (n.block_type) {
	                    case 1:
	                        addBlock.before(doT.template(mainTpl.image)(n));
	                        break;
	                    case 2:
	                        var $tmp = $(doT.template(mainTpl.goods)(n));
	                        addBlock.before($tmp);
	                        break;
	                    case 3:
	                        addBlock.before(doT.template(mainTpl.tms)(n));
	                        break;
	                    case 5:
	                        var virtualGridInfo = {
	                            row: n.element_list[0].grid_row,
	                            column: n.element_list[0].grid_column
	                        };
	                        blockInfo = [];
	                        temp = null;
	                        var id = 1;
	                        for(var i=0;i<virtualGridInfo.row;i++){
	                            for(var j=0;j<virtualGridInfo.column;j++) {
	                                temp = {
	                                    ids:[id],
	                                    left_top_x: j,
	                                    left_top_y: i,
	                                    bottom_right_x: j+1,
	                                    bottom_right_y: i+1
	                                };
	                                blockInfo.push(temp);
	                                id++;
	                                temp = null;
	                            }
	                        }
	                        virtualGridInfo.blockInfo = blockInfo;
	                        n.gridInfo = n.element_list[0];
	                        n.virtualGridInfo = virtualGridInfo;
	                        addBlock.before(doT.template(mainTpl.editBlockTpl)(n));
	                        gridFactory.bindEvent();
	                        break;
	                    case 6:
	                        addBlock.before(doT.template(mainTpl.singleImg)(n));
	                        break;
	                }
	            });
	            if (item.category_id) {
	                categoryList.val(item.category_id).trigger("change");
	            }
	            if (item.sub_category_id) {
	                subCategoryList.val(item.sub_category_id).trigger("change");
	            }
	            var categoryIndex = 0;
	            $.each(item.all_page_block_list, function(i, n) {
	                switch (n.block_type) {
	                    case 2:
	                        var ele = $("select[name='sub_category_list']:eq(" + categoryIndex + ")");
	                        ele.addClass("disabled").attr("disabled", "");
	                        ele.find("option[value='" + n.sub_category_id + "']").attr("selected", "");
	                        categoryIndex++;
	                        break;
	                }
	            });
	        }
	    }
	    accessCtrl.init();
	    
	    // btnPublishPage.click(function() {
	    //     var data = $(".div-image,.goods-form,.tms-block");
	    //     if (data == 0) {
	    //         layer.msg("请添加页面块！");
	    //         return;
	    //     }
	    //     $.jBox.confirm("确定发布页面吗？", "提示", function(a) {

	    //         if (a == 'cancel') {
	    //             return;
	    //         }
	    //         $.post(api_path + "/mainweb/page/publish.do", { pageId: $(window).data("pageId") }, function(resp) {
	    //             if (resp.code != 10000) {
	    //                 $.jBox.alert(resp.msg);
	    //             } else {
	    //                 $.jBox.info('发布成功');
	    //             }
	    //         })
	    //     })
	    // })

	    //pageTypeSelect
	    pageTypeSelect.change(function(){
	        // if($(this).val())
	        //当前选择的页面类型
	        var selectedPageType = $(this).val();
	        switch(selectedPageType) {
	            case '1'://主页
	                fn.clean();
	                // btnAddPage.show();
	                // pageName.val('主页').attr('disabled','disabled');
	                pageNameLi.hide();
	                // tabList.parent().find(".select2-container").show();
	                // tabListLi.show();
	                // $('#page-tab-li').show();
	                // tabList.parent().find(".select2-container").show();
	                tabNameLi.show();
	                $('.tab-order-li,.tab-order-gap').show();
	                $('#block-type option').eq(2).show();
	                break;
	            case '2'://活动页
	                fn.clean();
	                pageName.val('').removeAttr('disabled');
	                // btnAddPage.hide();
	                // tabList.hide();
	                pageNameLi.show();
	                tabNameLi.hide();
	                // tabListLi.hide();
	                // $('#page-tab-li').hide();
	                // tabList.parent().find(".select2-container").hide();
	                $('.tab-order-li,.tab-order-gap').hide();
	                $('#block-type option').eq(2).hide();
	                break;
	        }
	    });


	    // $.post(api_path + "/item/category/zj/query.do", function(resp) {
	    //     var data = resp.data;
	    //     var categoryMap = {};
	    //     $.each(data, function(i, n) {
	    //         categoryMap[n.id] = n
	    //     })
	    //     $(window).data("categoryMap", categoryMap);
	    //     var tpl = doT.template(mainTpl.cateOptions);
	    //     // categoryList.append(tpl(data));
	    // });
	    // categoryList.change(function() {
	    //     var val = $(this).val();
	    //     var allSubCategoryList = $("select[name='sub_category_list']");
	    //     if (val == '0') {
	    //         subCategoryList.find("option:not(:eq(0))").remove();
	    //         allSubCategoryList.find("option:not(:eq(0))").remove();
	    //         return;
	    //     }

	    //     //设置一级类目
	    //     parentCategoryId = val;
	    //     var obj = $(window).data("categoryMap")[parseInt(val)];
	    //     var tpl = doT.template(mainTpl.cateOptions);
	    //     allSubCategoryList.removeAttr("disabled");
	    //     subCategoryList.find("option:not(:eq(0))").remove();
	    //     subCategoryList.val("0").trigger("change");
	    //     subCategoryList.append(tpl(obj['sub_categorys']));

	    //     allSubCategoryList.find("option:not(:eq(0))").remove();
	    //     allSubCategoryList.val("0").trigger("change");
	    //     allSubCategoryList.append(tpl(obj['sub_categorys']));
	    // })
	    // subCategoryList.change(function() {
	    //     var val = $(this).val();
	    //     var allSubCategoryList = $("select[name='sub_category_list']");

	    //     if (val == '0') {
	    //         allSubCategoryList.removeClass("selected-disabled").removeAttr("disabled", "")
	    //         return;
	    //     }
	    //     categoryId = val;
	    //     allSubCategoryList.val(categoryId);
	    //     allSubCategoryList.addClass("selected-disabled").attr("disabled", "")
	    // })

	    // function initTabList(){
	    //     $.post(api_path + "/mainweb/page/names.do", function(resp) {
	    //         var data = jsonstring2object.parse(resp).data;
	    //         // var data = JSON.parse(resp).data;
	    //         var tpl = doT.template(mainTpl.options);
	    //         $(window).data("pageNames", data);
	    //         tabList.append(tpl(data));
	    //     });
	    // }
	    // if(accessCtrl.isNew()) {
	    //     initTabList();
	    // } else {
	    //     if(httpUrl.getQueryString('pagetype') === '1') {
	    //         initTabList();
	    //     }
	    // }

	    if(!accessCtrl.isNew()) {
	        // tabList.change(function() {
	        //     var val = $(this).val();
	        //     if (val != '') {
	        //         btnDelete.removeClass("disabled");
	        //         btnPublishPage.removeClass("disabled");
	        //         btnAddBlock.removeClass("disabled");
	        //         btnSavePage.removeClass("disabled");
	        //         fn.clean();
	        //         $.post(api_path + "/mainweb/page/get.do", { id: val }, function(resp) {
	        //             fn.getPage(resp);
	        //         })
	        //     }
	        // }); && httpUrl.getQueryString('pagetype') === '1'
	        var pagetype = httpUrl.getQueryString('pagetype'),
	            pageid = httpUrl.getQueryString('pageid');
	        // btnDelete.removeClass("disabled");
	        // btnPublishPage.removeClass("disabled");
	        // btnAddBlock.removeClass("disabled");
	        // btnSavePage.removeClass("disabled");
	        // fn.clean();
	        $.post(api_path + "/mainweb/page/get.do", { type: pagetype, id: pageid }, function(resp) {
	            fn.getPage(resp);
	        });
	    }


	    btnAddPage.click(function(e) {
	        e.preventDefault();
	        fn.addPage();
	    })
	    btnDelete.click(function() {
	        btnDelete.addClass("disabled");
	        var id = $(window).data("pageId");
	        if (typeof(id) == 'undefined' || id == '') {
	            layer.msg("删除页面的id不能为空！");
	            return;
	        }
	        layer.confirm('删除页面后将导致前台不能展示,确认删除此页面吗？', {
	            btn: ['确定','取消'] //按钮
	        }, function(){
	            $.post(api_path + "/mainweb/page/delete.do", { id: id }, function(resp) {
	                if (resp.code != 10000) {
	                    // $.jBox.alert(resp.msg, "警告");
	                    layer.msg(resp.msg);
	                    btnDelete.removeClass("disabled");
	                    return;
	                }
	                // $.jBox.info("删除成功", "提示");
	                layer.msg("删除成功");
	                fn.resetCategory();
	                fn.clean();
	                var data = $.grep($(window).data("pageNames"), function(n, i) {
	                    return n.id != id;
	                })
	                $(window).data("pageNames", data);
	                tabList.find("option:not(:eq(0))").remove();
	                tabList.append(doT.template(mainTpl.options)(data));
	            });
	        }, function(){
	            // layer.msg('也可以这样', {
	            //     time: 20000, //20s后自动关闭
	            //     btn: ['明白了', '知道了']
	            // });
	        });
	        // $.jBox.confirm("删除页面后将导致前台不能展示", "确认删除此页面吗？",
	        //     function(a) {
	        //         if (a == 'cancel') {
	        //             return;
	        //         }
	        //         $.post(api_path + "/mainweb/page/delete.do", { id: id }, function(resp) {
	        //             if (resp.code != 10000) {
	        //                 $.jBox.alert(resp.msg, "警告");
	        //                 btnDelete.removeClass("disabled");
	        //                 return;
	        //             }
	        //             $.jBox.info("删除成功", "提示");

	        //             fn.resetCategory();
	        //             fn.clean();
	        //             var data = $.grep($(window).data("pageNames"), function(n, i) {
	        //                 return n.id != id;
	        //             })
	        //             $(window).data("pageNames", data);
	        //             tabList.find("option:not(:eq(0))").remove();
	        //             tabList.append(doT.template(mainTpl.options)(data));
	        //         })
	        //     })
	    })

	    btnAddBlock.click(function() {
	        fn.addBlock();
	    })
	        //image-delete
	    $(document).on('click', '.block-delete', function() {
	        var that = this;
	        fn.confirm(function() {
	            $(that).parents(".form-search").remove();
	        })
	    })
	    $(document).on('click', '.btn-add-image', function() {
	        $(this).parents(".block-image").append(mainTpl.imageItem)
	    })
	    $(document).on('click', '.btn-add-single-image', function() {
	        // if(!$(this).hasClass('active') && $(this).parents(".block-image").find('.ul-image-select').find('li').eq(0).find('img').length != 0) {
	        //     $(this).parents(".block-image").append(mainTpl.singleImageItem)
	        // } else {
	        //     layer.msg('只能添加一块');
	        // }
	        // $(this).addClass('active');
	        $(this).parents(".block-image").append(mainTpl.singleImageItem);
	        $(this).hide();
	    })
	    $(document).on('click', '.image-delete', function() {
	        var that = this;
	        fn.confirm(function() {
	            $(that).parents("ul").remove();
	        })
	    });
	    $(document).on('click', '.single-image-delete', function() {
	        var that = this;
	        fn.confirm(function() {
	            $(that).closest(".block-image").append(mainTpl.singleImageItem);
	            $(that).parents("ul").remove();
	        })
	    })
	    var loadCategory = "";
	    //加载分类
	    var loadCategoryAjax = $.ajax({ url: loadCategory });
	    $.when(loadCategoryAjax).done(function(data) {
	        var tpl = doT.template(mainTpl.options);
	        $("#category-list").append(tpl(data));
	    });
	    // 获取用户输入的信息
	    btnSavePage.click(function() {
	        fn.savePage();
	    });
	    var totalCount = 0;
	    var pageSize = 10;
	    var totalPage = 0;
	    var keyword = '';
	    var parentCategoryId = '';
	    var categoryId = '';
	    var arr_selected = [];
	    var goodsData = null; // 用来存获取的数据对象
	    var jgoodsBody = $(".j-goods-tbody");
	    var getGoodsPageIndex = 0;
	    var isOnSale = '';
	    function getGoods(currentPage) {
	        $(".tcdPageCode").css({
	            visibility: 'hidden'
	        });
	        $.post(api_path + '/item/query.do', {
	                current_page: currentPage,
	                page_size: pageSize,
	                category_id: subCategoryList.val(),
	                parent_category_id: categoryList.val(),
	                key: keyword,
	                brand_key: $('#brand_key').val(),
	                item_status: isOnSale
	            },
	            function(resp) {
	                goodsData = resp.data;
	                totalCount = goodsData.total_count;
	                totalPage = Math.ceil(totalCount / pageSize);
	                var data = doT.template(mainTpl.goodsBox)(goodsData);
	                if (getGoodsPageIndex == 0) {
	                    getGoodsPageIndex++;
	                    var pageConfig = {
	                        pageCount: totalPage,
	                        current: currentPage,
	                        backFn: function(p) {
	                            getGoods(p);
	                        }
	                    };
	                    $(".tcdPageCode").createPage(pageConfig);
	                    $(".tcdPageCode").css({visibility:'visible'});
	                } else {
	                    var pageConfig = {
	                        pageCount: totalPage,
	                        current: currentPage
	                    };
	                    $(".tcdPageCode").createPage(pageConfig);
	                    $(".tcdPageCode").css({visibility:'visible'});
	                }
	                jgoodsBody.empty();
	                jgoodsBody.append(data);
	                //document.getElementById('j-goods-box').innerHTML = doT.template(tmpl)(goodsData);
	            }
	        )
	    }
	    $('.content').on('click', '.btn-add-item', function() {
	        $('#myModal').modal({
	            show: true
	        });
	        //getGoods(1,10);
	        var parent = $(this).parents(".goods-category-whole");
	        // categoryId = parent.find("select[name='sub_category_list']").val();
	        window.goodsParent = parent;
	        getGoods(1);
	        //商品类目
	        getCate();
	        //品牌
	        getBrand();
	    });
	    $('#isOnSale').change(function() {
	        // getGoodsPageIndex = 0;
	        isOnSale = isOnSale === '4' ? "" : '4';
	        getGoods(1);
	    });

	    categoryList.change(function() {
	        var val = $(this).val();
	        if (val == '0') {
	            subCategoryList.find("option:not(:eq(0))").remove();
	            return;
	        }

	        //设置一级类目
	        // parentCategoryId = val;
	        var obj = $(window).data("categoryMap")[parseInt(val)];
	        var tpl = doT.template(mainTpl.cate2Options);
	        subCategoryList.find("option:not(:eq(0))").remove();
	        subCategoryList.val("0").trigger("change");
	        subCategoryList.html(tpl(obj['sub_categorys'])).show();
	    });
	    // $('#brand_key').change(function() {
	    //     goodsFilter.brand_key = $(this).val();
	    // });

	    function getCate() {
	        $.get(api_path + "/item/category/zj/query.do", { parent_id: 0 }, function(resp) {
	            var data = resp.data;
	            var categoryMap = {};
	            $.each(data, function(i, n) {
	                categoryMap[n.id] = n
	            });
	            $(window).data("categoryMap", categoryMap);
	            var tpl = doT.template(mainTpl.cateOptions);
	            categoryList.html(tpl(data));
	        });
	    }

	    // function getTwoCate(id) {
	    //     $.get('/bossmanager/category/query2.do', { parent_id: id }, function(data) {
	    //         $('#category_two').html(doT.template(self_tpl.j_category)({
	    //             items: data.data,
	    //             category_value: 2
	    //         }));
	    //     });
	    // };

	    function getBrand() {
	        $.get(api_path + '/item/brand/query.do', {}, function(data) {
	            $('#brand_key').html(doT.template(mainTpl.brand_key)(data.data));
	        });
	    }
	    $(".goods-submit").on('click', function() {
	        var container = window.goodsParent;
	        var goodsTable = container.find(".goods-table");
	        var tpl = doT.template(mainTpl.itemTr);
	        var goodsList = $(window).data('goodsList');
	        if (!goodsList || goodsList.length == 0) {
	            // $.jBox.alert("请选择一个商品！");
	            layer.msg("请选择一个商品！");
	            return;
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
	    $(document).on('click', '.delete-goods', function() {
	        var that = this;
	        fn.confirm(function() {
	            $(that).parents("tr").remove();
	        })
	    })


	    $("#j-search").click(function() {
	        keyword = $("#keyword").val();
	        // getGoodsPageIndex = 0;
	        getGoods(1);
	    })

	    // totalPage = totalCount / count;
	    // modal的分页插件


	    var currImgSelectBtn = null;

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
	    $(".mySelectModalPage").createPage({
	        pageCount: 5,
	        current: 1,
	        backFn: function(nextPage) {
	            getImgList(nextPage, 5);
	        }
	    });
	    $('body').delegate('.imgItemUpload', 'click', function() {
	        currImgSelectBtn = $(this);
	        $('#mySelectModal').modal('show');
	        getImgList(1, 5);
	    });

	    $('body').delegate('.img-select', 'click', function() {
	        var imgSrc = $(this).closest('.j-goods-box').find('td').eq(0).find('img').attr('src');
	        var imgElement = '<img src="' + imgSrc + '" style="width: 50px;height: 50px;">';
	        $('#mySelectModal').modal('hide');
	        currImgSelectBtn.before(imgElement).hide();
	        var img = new Image();
	        img.src = currImgSelectBtn.siblings('img').attr('src');
	        currImgSelectBtn.closest('.ul-image-select').find('[name="width-px"]').val(img.width);
	        currImgSelectBtn.closest('.ul-image-select').find('[name="height-px"]').val(img.height);
	    });
	    $('#mySelectModal').delegate('#uploadNewImg','click',function(){
	        $('#mySelectModal').modal('hide');
	        $('#newImgModal').modal('show');
	        initUploader();
	    });


	    $('body').delegate('.img-block-delete', 'click', function() {
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
	            formData: {
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
	            var imgElement = '<img src="' + imgSrc + '" style="width: 50px;height: 50px;">';
	            currImgSelectBtn.before(imgElement).hide();
	            uploader = null;
	            $('#newImgModal').modal('hide');
	            setTimeout(function(){
	                var img = new Image();
	                img.src = currImgSelectBtn.siblings('img').attr('src');
	                currImgSelectBtn.closest('.ul-image-select').find('[name="width-px"]').val(img.width);
	                currImgSelectBtn.closest('.ul-image-select').find('[name="height-px"]').val(img.height);
	            },500);
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
	});


/***/ },
/* 7 */
/***/ function(module, exports) {

	function getQueryString(name) {
	    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null) {
	        return unescape(r[2]);
	    }
	    return null;
	}
	exports.getQueryString = getQueryString;

/***/ },
/* 8 */
/***/ function(module, exports, __webpack_require__) {

	var self_tpl = __webpack_require__(9);
	var localStorageTool = __webpack_require__(10);
	var imgSelect = __webpack_require__(11);


	function initBlankGrid(target, row, column) {
	    var gridInfo = {
	        row: row,
	        column: column,
	        blockWidth:100
	    };
	    blockInfo = [];
	    temp = null;
	    var id = 1;
	    for(var i=0;i<gridInfo.row;i++){
	        for(var j=0;j<gridInfo.column;j++) {
	            temp = {
	                id:[id],
	                left_top_x: j,
	                left_top_y: i,
	                bottom_right_x: j+1,
	                bottom_right_y: i+1
	            };
	            blockInfo.push(temp);
	            id++;
	            temp = null;
	        }
	    }
	    gridInfo.blockInfo = blockInfo;
	    target.html('').html(doT.template(self_tpl.blockTpl)(gridInfo))
	                     .css({
	                        width: gridInfo.column*100 + 'px',
	                        height: gridInfo.row*100 + 'px'
	                     });
	    gridFactory.gridBlock.find('.virtual-grid-container,.grid-container').attr('data-row', gridInfo.row).attr('data-column', gridInfo.column);
	    // if(!$('#grid-container').data('row'))
	    // localStorageTool.setItem("gridInfo", JSON.stringify(gridInfo));  
	    // localStorageTool.setItem("gridInfo", JSON.stringify(getReadyData()));  
	}

	function initGrid(target) {
	    $.get('/getGrid.do',{id: ''},function(data){
	        var temp = data;
	        target.html(doT.template(self_tpl.editBlockTpl)(data.data))
	        // .css({
	        //                     width: data.data.column*100 + 'px',
	        //                     height: data.data.row*100 + 'px'
	        //                  });
	        // initBlankGrid($('#virtual-grid-container'), data.data.row, data.data.column);
	        // localStorageTool.setItem("gridInfo", JSON.stringify(data.data));  
	    });
	}


	function mergeGrid() {
	    //当前选中的格子信息
	    var selected_grid = [];
	    //当前选中的格子id
	    var str = '';
	    gridFactory.gridBlock.find('.grid-container span.active').each(function(index){
	        selected_grid.push({id: $(this).data('id')});
	        if(index != gridFactory.gridBlock.find('.grid-container span.active').length -1) {
	            str += $(this).data('id') + ',';
	        } else {
	            str += $(this).data('id');
	        }
	    });
	    var gridArray = str.split(',');
	    //设置虚拟格子的active
	    gridFactory.gridBlock.find('.virtual-grid-container span').removeClass('active');
	    gridArray.forEach(function(item,i){
	        gridFactory.gridBlock.find('.virtual-grid-container span[data-id="' + item + '"]').addClass('active');
	    });
	    // selected_grid.forEach(function(item,i){
	    //     $('#virtual-grid-container span[data-id="' + item.id + '"]').addClass('active');
	    // });


	    //当前格子的n*m
	    var row = gridFactory.gridBlock.find('.grid-container').attr('data-row');
	    var column = gridFactory.gridBlock.find('.grid-container').attr('data-column');
	    //most_left_top -----begin -----
	    var most_left_top_grid = {};
	    for(var i=1;i<=column;i++) {
	        gridFactory.gridBlock.find('.virtual-grid-container .col' + i).each(function(){
	            if($(this).hasClass('active')) {
	                most_left_top_grid.id = $(this).data('id');
	                most_left_top_grid.left_top_x = $(this).data('left_top_x');
	                most_left_top_grid.left_top_y = $(this).data('left_top_y');
	                // most_left_top_grid.bottom_right_x = $(this).data('bottom_right_x');
	                // most_left_top_grid.bottom_right_y = $(this).data('bottom_right_y');
	                return false;
	            }
	        });
	        if(most_left_top_grid.id != null) {
	            break;
	        }
	    }

	    //most_left_top -----end -----


	    //most_bottom_right ----- begin -----
	    
	    var most_bottom_right_grid = {};
	    var temp = null;
	    for(var i=row*column;i>0;i--){
	        temp = gridFactory.gridBlock.find('.virtual-grid-container span[data-id="' + i + '"]');
	        if(temp.hasClass('active')) {
	            most_bottom_right_grid.id = temp.data('id');
	            most_bottom_right_grid.left_top_x = temp.data('left_top_x');
	            most_bottom_right_grid.left_top_y = temp.data('left_top_y');
	            most_bottom_right_grid.bottom_right_x = temp.data('bottom_right_x');
	            most_bottom_right_grid.bottom_right_y = temp.data('bottom_right_y');
	            break;
	        }
	    }
	    //most_bottom_right ----- end -----
	    if(most_left_top_grid.left_top_x > most_bottom_right_grid.left_top_x) {
	        layer.msg("当前选择无法合并");
	        return;
	    } else {
	        if(most_left_top_grid.left_top_x === most_bottom_right_grid.left_top_x && most_left_top_grid.left_top_y === most_bottom_right_grid.left_top_y) {
	            layer.msg("当前选择无法合并");
	        }
	        //开始合并-------->>>
	        //有效区域内的格子数
	        var total_grid_sum = Math.abs(most_bottom_right_grid.bottom_right_x - most_left_top_grid.left_top_x)*Math.abs(most_bottom_right_grid.bottom_right_y - most_left_top_grid.left_top_y);
	        //有效区域内active的格子数
	        var active_grid_sum = 0;
	        var temp = null;
	        gridFactory.gridBlock.find('.virtual-grid-container span.active').each(function(){
	            var this_left_top_x = $(this).data('left_top_x');
	            var this_left_top_y = $(this).data('left_top_y');
	            if(this_left_top_x >= most_left_top_grid.left_top_x && this_left_top_x <= most_bottom_right_grid.left_top_x && this_left_top_y >= most_left_top_grid.left_top_y && this_left_top_y <= most_bottom_right_grid.left_top_y) {
	                active_grid_sum++;
	            }
	        });
	        
	        if(total_grid_sum === active_grid_sum && total_grid_sum === gridFactory.gridBlock.find('.virtual-grid-container span.active').length) {
	            //开始合并
	            //取得可以合并的格子id
	            var avail_grid_id = [];
	            gridFactory.gridBlock.find('.virtual-grid-container span').each(function(){
	                var this_left_top_x = $(this).data('left_top_x');
	                var this_left_top_y = $(this).data('left_top_y');
	                if(this_left_top_x >= most_left_top_grid.left_top_x && this_left_top_x <= most_bottom_right_grid.left_top_x && this_left_top_y >= most_left_top_grid.left_top_y && this_left_top_y <= most_bottom_right_grid.left_top_y) {
	                    avail_grid_id.push($(this).data('id'));
	                }
	            });
	            // avail_grid_id.forEach(function(item,i){
	            //     $('#grid-container span[data-id="' + item + '"]').remove();
	            // });
	            // $('#grid-container span[data-id="' + gridArray.join(",") + '"]').remove();
	            selected_grid.forEach(function(item,i){
	                gridFactory.gridBlock.find('.grid-container span[data-id="' + item.id + '"]').remove();
	            });
	            // $('#grid-container span[data-id="' + gridArray.join(",") + '"]').remove();
	            //构造新格子
	            var merge_grid = {
	                id: avail_grid_id,
	                left_top_x: gridFactory.gridBlock.find('.virtual-grid-container span[data-id="' + avail_grid_id[0] + '"]').data('left_top_x'),
	                left_top_y: gridFactory.gridBlock.find('.virtual-grid-container span[data-id="' + avail_grid_id[0] + '"]').data('left_top_y'),
	                bottom_right_x: gridFactory.gridBlock.find('.virtual-grid-container span[data-id="' + avail_grid_id[avail_grid_id.length-1] + '"]').data('bottom_right_x'),
	                bottom_right_y: gridFactory.gridBlock.find('.virtual-grid-container span[data-id="' + avail_grid_id[avail_grid_id.length-1] + '"]').data('bottom_right_y'),
	                blockWidth: 100
	            };
	            gridFactory.gridBlock.find('.grid-container').append(doT.template(self_tpl.singleBlockTpl)(merge_grid));
	            // initBlankGrid($('#virtual-grid-container'), $('#virtual-grid-container').data('row'), $('#virtual-grid-container').data('column'));
	            //把当前格子的排列信息放到缓存
	            localStorageTool.setItem("gridInfo", JSON.stringify(getReadyData()));  
	        } else {
	            // initBlankGrid($('#virtual-grid-container'), $('#virtual-grid-container').data('row'), $('#virtual-grid-container').data('column'));
	            layer.msg("当前选择无法合并");
	        }
	    }
	}
	/** 判断item是否存在于数组中
	 *  item:元素
	 *  itemArray: 元素数组
	***/
	function inArray(item, itemArray) {
	    var isExist = false;
	    for(var i=0;i<itemArray.length;i++) {
	        if(item === itemArray[i]) {
	            isExist = true;
	            break;
	        }
	    }
	    return isExist;
	}

	//当前格子的排列信息
	function getReadyData() {
	    var saveGridInfo = {};
	    //格子序列
	    var grid_queue = [];
	    //按照1到row*column
	    //已经计算过的id
	    var ready_id = [];
	    //当前格子的n*m
	    var row = gridFactory.gridBlock.find('.grid-container').attr('data-row');
	    var column = gridFactory.gridBlock.find('.grid-container').attr('data-column');
	    for(var i=1;i<=row*column;i++){
	        gridFactory.gridBlock.find('.grid-container span').each(function(){
	            var curr_block = $(this);
	            //当前块的id
	            var curr_id = (curr_block.data('id')+'').split(",");
	            if(!inArray(i+'',ready_id) && inArray(i+'',curr_id)) {
	                for(var j=0;j<curr_id.length;j++) {
	                    ready_id.push(curr_id[j]);
	                }
	                var singleBlock = {
	                    ids: curr_id,
	                    left_top_x: curr_block.data('left_top_x'),
	                    left_top_y: curr_block.data('left_top_y'),
	                    bottom_right_x: curr_block.data('bottom_right_x'),
	                    bottom_right_y: curr_block.data('bottom_right_y'),
	                    image_url: curr_block.find('img').attr('src'),
	                    target_url: curr_block.attr('imghref')
	                };
	                grid_queue.push(singleBlock);
	                // private Long gridId;
	                // private Integer leftTopX;
	                // private Integer leftTopY;
	                // private Integer bottomRightX;
	                // private Integer bottomRightY;
	                // private String imageUrl;
	                // private String targetUrl;
	                // private Date gmtCreated;
	                // private Date gmtModified;
	                // private Integer deleteMark;
	                // private List<Integer> ids;
	            }
	        });
	    }
	    saveGridInfo.grid_row = row;
	    saveGridInfo.grid_column = column;
	    saveGridInfo.grid_list = grid_queue;
	    return saveGridInfo;
	}
	function validateGrid(n) {
	    gridFactory.gridBlock = $(n);
	    //首先判断每个格子中是否已配置图片
	    if(gridFactory.gridBlock.find('.grid-container span img').length === 0 || gridFactory.gridBlock.find('.grid-container span').length != gridFactory.gridBlock.find('.grid-container span img').length) {
	        layer.msg("请先配置格子块的图片");
	        return false;
	    }
	    return true;
	}
	function saveGrid() {
	    var saveGridInfo = getReadyData();
	    return saveGridInfo;
	}

	function bindEvent() {
	    // $('body').undelegate('.grid-container span','click').delegate('.grid-container span','click',function(){
	    //     $(this).toggleClass('active');
	    // });
	    // $('.grid-container').undelegate('span','click').delegate('span','click',function(){
	    //     $(this).toggleClass('active');
	    // });
	    //生成格子
	    //produceGrid();
	    // $('body').undelegate('.produce-grid-btn','click').delegate('.produce-grid-btn','click',function(){
	    //     //当前操作的grid块
	    //     gridFactory.gridBlock = $(this).closest('.block-grid-container');
	    //     initBlankGrid(gridFactory.gridBlock.find('#grid-container'), gridFactory.gridBlock.find('input[name=row-num]').val(), gridFactory.gridBlock.find('input[name=column-num]').val());
	    //     initBlankGrid(gridFactory.gridBlock.find('#virtual-grid-container'), gridFactory.gridBlock.find('input[name=row-num]').val(), gridFactory.gridBlock.find('input[name=column-num]').val());
	    //     gridFactory.gridBlock.find('.btn-container').show(); 
	    // });
	    $('.produce-grid-btn').unbind('click').click(function(){
	        //当前操作的grid块
	        gridFactory.gridBlock = $(this).closest('.block-grid-container');
	        initBlankGrid(gridFactory.gridBlock.find('.grid-container'), gridFactory.gridBlock.find('input[name=row-num]').val(), gridFactory.gridBlock.find('input[name=column-num]').val());
	        initBlankGrid(gridFactory.gridBlock.find('.virtual-grid-container'), gridFactory.gridBlock.find('input[name=row-num]').val(), gridFactory.gridBlock.find('input[name=column-num]').val());
	        gridFactory.gridBlock.find('.btn-container').show(); 
	    });
	    //合并格子
	    // $('#merge-grid-btn').click(function(){
	    //     if($('#grid-container span.active').length < 2) {
	    //         layer.msg("至少选择两个格子才能合并");
	    //         return false;
	    //     }
	    //     mergeGrid();
	    // });
	    $('.merge-grid-btn').unbind('click').click(function(){
	        gridFactory.gridBlock = $(this).closest('.block-grid-container');
	        if(gridFactory.gridBlock.find('.grid-container span.active').length < 2) {
	            layer.msg("至少选择两个格子才能合并");
	            return false;
	        }
	        mergeGrid();
	    });
	    //全选
	    $('.select-all-grid-btn').unbind('click').click(function(){
	        gridFactory.gridBlock = $(this).closest('.block-grid-container');
	        gridFactory.gridBlock.find('.grid-container span').removeClass('active').addClass('active');
	    });
	    
	    //重置格子
	    $('.reset-grid-btn').unbind('click').click(function(){
	        gridFactory.gridBlock = $(this).closest('.block-grid-container');
	        initBlankGrid(gridFactory.gridBlock.find('.grid-container'), gridFactory.gridBlock.find('input[name=row-num]').val(), gridFactory.gridBlock.find('input[name=column-num]').val());
	        initBlankGrid(gridFactory.gridBlock.find('.virtual-grid-container'), gridFactory.gridBlock.find('input[name=row-num]').val(), gridFactory.gridBlock.find('input[name=column-num]').val());
	    }); 

	    //保存格子
	    // $('#save-grid-btn').click(function(){
	    //     saveGrid();
	    // });

	    //预览格子
	    // $('#preview-grid-btn').click(function(){
	    //     //iframe层
	    //     layer.open({
	    //       type: 2,
	    //       title: '预览',
	    //       shadeClose: true,
	    //       shade: 0.8,
	    //       area: ['414px', '736px'],
	    //       content: 'http://192.168.8.169:3000/html/recommend/preview.html' //iframe的url
	    //     }); 
	    // });

	    //上传图片
	    imgSelect.initModal();
	    $('.grid-container').undelegate('span','click').delegate('span','click',function(){
	        $(this).toggleClass('active');
	        gridFactory.gridBlock = $(this).closest('.block-grid-container');
	        if(gridFactory.gridBlock.find('.upload-img-switch').hasClass('active')){
	            $(this).siblings('.active').removeClass('active');
	            // imgSelect.initModal();
	            if($(this).hasClass('active')) {
	                imgSelect.show(gridFactory.gridBlock);
	            }
	        }
	        
	        // if(gridFactory.gridBlock.find('.upload-img-switch').hasClass('active') && $(this).hasClass('active')) {
	        //     imgSelect.show();
	        // }
	    });

	    // $('#uploadNewImg').click(function() {
	    //     $('#mySelectModal').modal('hide');
	    //     $('#newImgModal').modal('show');
	    //     //initUploader();
	    // });
	}
	function init() {
	    bindEvent();
	    //type=edit
	    // if(location.href.substr(location.href.indexOf("type=")+5) === "edit") {
	    //     initGrid($('#grid-container'));
	    // }
	}
	var gridFactory = {
	    init: init,
	    validateGrid: validateGrid,
	    saveGrid: saveGrid,
	    bindEvent: bindEvent
	}
	module.exports = gridFactory;

/***/ },
/* 9 */
/***/ function(module, exports) {

	var self_tpl = {
		'blockTpl': '{{~ it.blockInfo:item:index }}\
						<span class="col{{? (index+1)%it.column!=0 }}{{= (index+1)%it.column }}{{??}}{{= it.column }}{{?}}" data-id="{{= item.id.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*it.blockWidth }}px;top:{{= item.left_top_y*it.blockWidth }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*it.blockWidth }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*it.blockWidth }}px;">\
						</span>\
					{{~}}',
		'editBlockTpl': '{{~ it.blockInfo:item:index }}\
						<span data-id="{{= item.id.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*it.blockWidth }}px;top:{{= item.left_top_y*it.blockWidth }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*it.blockWidth }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*it.blockWidth }}px;">\
						</span>\
					{{~}}',
		'singleBlockTpl':'<span data-id="{{= it.id.join(",") }}" data-left_top_x="{{= it.left_top_x }}" data-left_top_y="{{= it.left_top_y }}" data-bottom_right_x="{{= it.bottom_right_x }}" data-bottom_right_y="{{= it.bottom_right_y }}" style="left:{{= it.left_top_x*it.blockWidth }}px;top:{{= it.left_top_y*it.blockWidth }}px;width:{{= Math.abs(it.left_top_x-it.bottom_right_x)*it.blockWidth }}px;height:{{= Math.abs(it.left_top_y-it.bottom_right_y)*it.blockWidth }}px;">\
						 </span>'
	};
	module.exports = self_tpl;

/***/ },
/* 10 */
/***/ function(module, exports) {

	function setItem(item, obj) {
		localStorage.setItem(item, obj);
	}

	function getItem(item) {
		return localStorage.getItem(item);
	}

	function removeItem(item){
		localStorage.removeItem(item);
	}

	exports.setItem = setItem;
	exports.removeItem = removeItem;
	exports.getItem = getItem;

/***/ },
/* 11 */
/***/ function(module, exports, __webpack_require__) {

	var gridImgUpload = __webpack_require__(12);
	var self_tpl = {
	    img_select_modal:   '<div class="modal fade img-select-modal" id="img-select-modal">\
	                          <div class="modal-dialog">\
	                            <div class="modal-content">\
	                              <div class="modal-header">\
	                                图片库\
	                                <a class="btn btn-sm btn-default" id="uploadNewImgGrid">新图片</a>\
	                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
	                              </div>\
	                              <div class="modal-body">\
	                                <div class="img-container" id="j-img-select-box-grid">\
	                                </div>\
	                                <div class="selected-img-container" id="j-img-selected-box-grid">\
	                                </div>\
	                                <div class="href-input">\
	                                    <span>链接：</span><input type="text">\
	                                    <button type="button" class="btn btn-success confirm-select" id="confirm-select">确定选择</button>\
	                                </div>\
	                                <div class="modal-page img-select-pager" id="img-select-pager"></div>\
	                              </div>\
	                            </div>\
	                          </div>\
	                        </div>',
	    img_select_template:'{{ for(var i=0; i < it.length; i++) { }}\
	                                <span>\
	                                    <img src="{{=it[i].file_url}}">\
	                                </span>\
	                        {{ } }}',
	    img_selected_tpl:   '当前选择：<span class="active">\
	                            <img src="{{= it.file_url}}">\
	                        </span>'
	};

	/**
	 *  图片选择弹框父亲页
	 *  1：格子
	 *  2：
	**/
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
	            $('#j-img-select-box-grid').html('');
	            $('#j-img-selected-box-grid').html('');
	            var obj = {
	                file_url: imgSelect.gridBlock.find('.grid-container span.active img').attr('src'),
	                img_href: imgSelect.gridBlock.find('.grid-container span.active').attr('imghref')
	            }
	            if(obj.file_url) {
	                $('#j-img-selected-box-grid').append(doT.template(self_tpl.img_selected_tpl)(obj));
	            }
	            if(obj.img_href) {
	                $('#img-select-modal .href-input input').val(obj.img_href);
	            }else {
	                $('#img-select-modal .href-input input').val('');
	            }
	            $('#j-img-select-box-grid').append(doT.template(self_tpl.img_select_template)(data.data.list));
	        }
	    });
	}





	function initModal() {
	    $('#img-select-modal-container').html(self_tpl.img_select_modal);
	    bindEvent();
	    gridImgUpload.initModal();
	}

	function bindEvent() {
	    $('#img-select-modal').undelegate('.img-select', 'click').delegate('.img-select', 'click', function () {
	        $('#img-select-modal .img-select').removeClass('active');
	        $(this).toggleClass('active');
	        var  imgSrc = $(this).closest('.j-goods-box').find('td').eq(0).find('img').attr('src');
	        // var imgElement = '<span class="item-img" data-imgsrc="'+ imgSrc +'"><img src="' + imgSrc + '"><a href="javascript:;" class="cancel"></a></span>';
	        // $('#img-select-modal').modal('hide');
	        // $('#goods-pic-add-icon').before(imgElement);
	        // if(imgSelect.entry === 1) {
	        //     $('#grid-container span.active').html('<img src="' + imgSrc + '">');
	        // }
	    });
	    $('#img-select-modal').undelegate('.confirm-select', 'click').delegate('.confirm-select', 'click', function () {
	        var  imgSrc = $('#img-select-modal .img-container span.active').find('img').attr('src') || $('#img-select-modal .selected-img-container span.active').find('img').attr('src');
	        // var imgElement = '<span class="item-img" data-imgsrc="'+ imgSrc +'"><img src="' + imgSrc + '"><a href="javascript:;" class="cancel"></a></span>';
	        // $('#img-select-modal').modal('hide');
	        // $('#goods-pic-add-icon').before(imgElement);
	        var imgHref = $('#img-select-modal .href-input input').val().trim();
	        var activeGridContainer = imgSelect.gridBlock.find('.grid-container span.active');
	        if(imgSrc) {
	            if(imgHref != ''){
	                 activeGridContainer.attr('imghref', imgHref);
	                 activeGridContainer.addClass('imghref');
	                 activeGridContainer.html('<img src="' + imgSrc + '">');
	            } else {
	                 activeGridContainer.attr('imghref', '');
	                 activeGridContainer.removeClass('imghref');
	                 activeGridContainer.html('<img src="' + imgSrc + '">');
	            }
	        } else {
	            activeGridContainer.html('');
	        }
	        imgSelect.gridBlock.find('.grid-container span').removeClass('active');
	        $('#img-select-modal').modal('hide');
	    });

	    $('.upload-img-switch').unbind('click').click(function(){
	        imgSelect.gridBlock = $(this).closest('.block-grid-container');
	        $(this).toggleClass('active');
	        if($(this).hasClass('active')){
	            imgSelect.gridBlock.find('.grid-container span').removeClass('active');
	            $(this).html('合并模式');
	            imgSelect.gridBlock.find('.merge-grid-btn').hide();
	            imgSelect.gridBlock.find('.select-all-grid-btn').hide();
	        } else {
	            $(this).html('上传模式');
	            imgSelect.gridBlock.find('.merge-grid-btn').show();
	            imgSelect.gridBlock.find('.select-all-grid-btn').show();
	        }
	    });

	    $('#img-select-modal-container .img-container').undelegate('span', 'click').delegate('span', 'click', function(){
	        // $('#img-select-modal-container .img-container span').removeClass('active');
	        $('#img-select-modal-container .selected-img-container span').removeClass('active');
	        $(this).siblings('span').removeClass('active');
	        $(this).toggleClass('active');
	    });
	    $('#img-select-modal-container .selected-img-container').undelegate('span', 'click').delegate('span', 'click', function(){
	        $('#img-select-modal-container .img-container span').removeClass('active');
	        $(this).toggleClass('active');
	    });
	}
	function show(currGridBlock) {
	    imgSelect.gridBlock = currGridBlock;
	    gridImgUpload.initGridBlock(currGridBlock);
	    $('#img-select-modal').modal('show');
	    getImgList(1,16);
	    pager();
	}

	function pager() {
	    $("#img-select-pager").createPage({
	        pageCount: 5,
	        current: 1,
	        backFn: function(nextPage) {
	            getImgList(nextPage, 16);
	        }
	    });
	}
	var imgSelect = {
	    initModal: initModal,
	    show: show
	}
	module.exports = imgSelect;

/***/ },
/* 12 */
/***/ function(module, exports) {

	var self_tpl = {
	    newImgModal:   '<div class="modal fade" id="newGridImgModal">\
	                      <div class="modal-dialog">\
	                        <div class="modal-content">\
	                          <div class="modal-header">\
	                            新图片<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
	                          </div>\
	                          <div class="modal-body">\
	                            <div id="grid-uploader-container">\
	                            </div>\
	                            <div class="href-input">\
	                                <span>链接：</span><input type="text">\
	                            </div>\
	                          </div>\
	                          <div class="modal-footer">\
	                            <button class="btn btn-primary"  id="grid-confirm-upload">上传</button>\
	                          </div>\
	                        </div>\
	                      </div>\
	                    </div>'
	}



	// 优化retina, 在retina下这个值是2
	var ratio = window.devicePixelRatio || 1,
	    // 缩略图大小
	    thumbnailWidth = 100 * ratio,
	    thumbnailHeight = 100 * ratio,
	    // Web Uploader实例
	    uploader;

	function initUploader() {
	    $('#grid-uploader-container').html('<div id="gridFileList"></div><div id="gridFilePicker">选择图片</div>');
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
	            id: '#gridFilePicker',
	            multiple: false
	        },
	        // 只允许选择文件，可选。
	        accept: {
	            title: 'Images',
	            extensions: 'gif,jpg,jpeg,bmp,png',
	            mimeTypes: 'image/*'
	        }
	    });
	    $("#gridFilePicker").mouseenter(function() {
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
	        $('#gridFileList').append($li);
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
	        uploader = null;
	        var imgHref = $('#newGridImgModal .href-input input').val().trim();
	        if(imgSrc) {
	            if(imgHref != ''){
	                gridImgUpload.gridBlock.find('.grid-container span.active').attr('imghref', imgHref);
	                gridImgUpload.gridBlock.find('.grid-container span.active').html('<img src="' + imgSrc + '">');
	            } else {
	                gridImgUpload.gridBlock.find('.grid-container span.active').attr('imghref', '');
	                gridImgUpload.gridBlock.find('.grid-container span.active').html('<img src="' + imgSrc + '">');
	            }
	        } else {
	            gridImgUpload.gridBlock.find('.grid-container span.active').html('');
	        }
	        gridImgUpload.gridBlock.find('.grid-container span').removeClass('active');
	        $('#newGridImgModal').modal('hide');
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
	    $('#grid-confirm-upload').click(function() {
	        //console.log("上传...");
	        if (uploader) {
	            uploader.upload();
	        }
	    });
	}

	function bindEvent() {
	    $('#uploadNewImgGrid').click(function() {
	        $('#img-select-modal').modal('hide');
	        $('#newGridImgModal').modal('show');
	        var obj = {
	            file_url: gridImgUpload.gridBlock.find('.grid-container span.active img').attr('src'),
	            img_href: gridImgUpload.gridBlock.find('.grid-container span.active').attr('imghref')
	        }
	        // if(obj.file_url) {
	        //     $('#j-img-selected-box-grid').html('').append(doT.template(self_tpl.img_selected_tpl)(obj));
	        // }
	        if(obj.img_href) {
	            $('#newGridImgModal .href-input input').val(obj.img_href);
	        }else {
	            $('#newGridImgModal .href-input input').val('');
	        }
	        initUploader();
	    });
	}

	function initModal() {
	    $('#new-grid-img-upload-modal-container').html(self_tpl.newImgModal);
	    bindEvent();
	}

	function initGridBlock(gridBlock) {
	    gridImgUpload.gridBlock = gridBlock;
	}
	var gridImgUpload = {
	    initModal: initModal,
	    initGridBlock: initGridBlock
	}
	// function gridImgUpload() {
	//     initModal();
	//     bindEvent()
	// }
	module.exports = gridImgUpload;

/***/ },
/* 13 */
/***/ function(module, exports, __webpack_require__) {

	/**
	 *	控制页面元素的可见性及相关逻辑
	 *
	**/
	var httpUrl = __webpack_require__(7);
	var self_tpl = {
		pageTypeTpl: '{{~ it.data:item:index }}\
							<option value="{{= item.id }}" {{? it.pagetype && it.pagetype === item.id }} selected {{?}}>{{= item.name }}</option>\
					   {{~}}'
	}
	function initPageType() {
		var pageType = {
			data: [
				{
					id: 1,
					name: "主页"
				},
				{
					id: 2,
					name: "推广页"
				}
			]
		}
		if(isNew()){
			$('#page-name-li').hide();
			pageType.pagetype = pageType.data[0].id;
			$('#page-title').html('界面绘制-新增')
		} else {
			if(httpUrl.getQueryString('pagetype') === '1'){
				$('#page-name-li').hide();
			}
			$('#page-title').html('界面绘制-编辑')
			pageType.pagetype = parseInt(httpUrl.getQueryString('pagetype'));
			$('#page-type').attr('disabled','disabled');
		}
		$('#page-type').html(doT.template(self_tpl.pageTypeTpl)(pageType));
	}
	function initPageData() {
		if(httpUrl.getQueryString('pagetype') && httpUrl.getQueryString('pagetype') != '1') {
			$('.tab-order-li,.tab-order-gap').hide();
		}
	}
	//是否是新增
	function isNew() {
		if(httpUrl.getQueryString('pagetype') && httpUrl.getQueryString('pageid')) {
			return false;
		}
		return true;
	}
	function init() {
		initPageType();
		initPageData();
		if(!httpUrl.getQueryString('pagetype')) {
	        $("#add-page").show();
	    }
	    if(httpUrl.getQueryString('pagetype')) {
	    	$("#add-page").hide();
	    	if(httpUrl.getQueryString('pagetype') != 1) {
	    		$('#block-type option').eq(2).hide();
	    	}
	    }
	}

	var accessCtrl = {
		init: init,
		isNew: isNew
	}
	module.exports = accessCtrl;

/***/ }
/******/ ]);