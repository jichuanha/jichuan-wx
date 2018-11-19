/******/ (function(modules) { // webpackBootstrap
/******/ 	// install a JSONP callback for chunk loading
/******/ 	var parentJsonpFunction = window["webpackJsonp"];
/******/ 	window["webpackJsonp"] = function webpackJsonpCallback(chunkIds, moreModules) {
/******/ 		// add "moreModules" to the modules object,
/******/ 		// then flag all "chunkIds" as loaded and fire callback
/******/ 		var moduleId, chunkId, i = 0, callbacks = [];
/******/ 		for(;i < chunkIds.length; i++) {
/******/ 			chunkId = chunkIds[i];
/******/ 			if(installedChunks[chunkId])
/******/ 				callbacks.push.apply(callbacks, installedChunks[chunkId]);
/******/ 			installedChunks[chunkId] = 0;
/******/ 		}
/******/ 		for(moduleId in moreModules) {
/******/ 			modules[moduleId] = moreModules[moduleId];
/******/ 		}
/******/ 		if(parentJsonpFunction) parentJsonpFunction(chunkIds, moreModules);
/******/ 		while(callbacks.length)
/******/ 			callbacks.shift().call(null, __webpack_require__);

/******/ 	};

/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// object to store loaded and loading chunks
/******/ 	// "0" means "already loaded"
/******/ 	// Array means "loading", array contains callbacks
/******/ 	var installedChunks = {
/******/ 		1:0
/******/ 	};

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

/******/ 	// This file contains only the entry chunk.
/******/ 	// The chunk loading function for additional chunks
/******/ 	__webpack_require__.e = function requireEnsure(chunkId, callback) {
/******/ 		// "0" is the signal for "already loaded"
/******/ 		if(installedChunks[chunkId] === 0)
/******/ 			return callback.call(null, __webpack_require__);

/******/ 		// an array means "currently loading".
/******/ 		if(installedChunks[chunkId] !== undefined) {
/******/ 			installedChunks[chunkId].push(callback);
/******/ 		} else {
/******/ 			// start chunk loading
/******/ 			installedChunks[chunkId] = [callback];
/******/ 			var head = document.getElementsByTagName('head')[0];
/******/ 			var script = document.createElement('script');
/******/ 			script.type = 'text/javascript';
/******/ 			script.charset = 'utf-8';
/******/ 			script.async = true;

/******/ 			script.src = __webpack_require__.p + "" + chunkId + "." + ({}[chunkId]||chunkId) + ".js";
/******/ 			head.appendChild(script);
/******/ 		}
/******/ 	};

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

	module.exports = __webpack_require__(5);


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
/* 5 */
/***/ function(module, exports, __webpack_require__) {

	// var self_tpl = require('../module/demo/tpl-amd.js');
	var api_path = __webpack_require__(3).api_path;
	var jsonstring2object = __webpack_require__(4);
	__webpack_require__.e/* nsure */(2, function(require){
	    var self_tpl = __webpack_require__(2);
	    $(function() {
	        // var self_tpl = require('../module/sec-kill-tpl.js');
	        //商品弹窗......
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
	        var timepickerCfg = {
	            minInterval: (1000 * 60),
	            showSecond: true,
	            timeFormat: 'HH:mm:ss',
	            start: {
	                minDate: new Date()
	            },
	            end: {}
	        };
	        var errMsg = null; // 保存输入框不为空时的提示信息
	        var picIsExit = null; // 保存每个子场是否有推广图片
	        var iconBtn = null; // 图片角标添加的位置
	        var relativeGoodsList = null; // 保存每个子场活动的关联商品的skuid，在每次添加商品时就会刷新。
	        var goodInfo = {};
	        $('#publish-time').datetimepicker();
	        $.timepicker.datetimeRange(
	            $('#campaign-start-time'),
	            $('#campaign-end-time'),
	            timepickerCfg
	        );
	        $(".form-horizontal").on("click", "#add-goods-btn", (function() {
	            relativeGoodsList = null;
	            relativeGoodsList = new Array();
	            trDoms = $(this).closest(".per-session").find("tbody tr");
	            for (var i = 0; i < trDoms.length; i++) {
	                relativeGoodsList.push($(trDoms[i]).attr("data-item_id"));
	            };
	            // 负责显表格
	            $(".form-horizontal .parent-table").css("display", "table");
	            addTarget = $(this).closest(".per-session").find(".relative-goods");
	            $('#select-goods-modal').modal('show');
	            //商品类目
	            getCate();
	            //品牌
	            getBrand();
	            getGoodsList();
	        }));

	        function getCate() {
	            $.get(api_path + '/bossmanager/category/query.do', { parent_id: 0 }, function(data) {
	                $('#cate_key').html(doT.template(self_tpl.cate_key)(data));
	                // bindEvent();
	            });
	        };

	        function getTwoCate(id) {
	            $.get(api_path + '/bossmanager/category/query2.do', { parent_id: id }, function(data) {
	                var template = doT.template($('#j-template-category').html());
	                $('#category_two').html(template({
	                    items: data.data,
	                    category_value: 2
	                }));
	            });
	        };

	        function getBrand() {
	            $.get(api_path + '/bossmanager/item/brand/query.do', {}, function(data) {
	                $('#brand_key').html(doT.template(self_tpl.brand_key)(data.data));
	            });
	        };

	        function getGoodsList() {
	            $.get(api_path + '/bossmanager/item/query.do', {
	                    key: goodsFilter.key,
	                    brand_key: goodsFilter.brand_key,
	                    category_id: goodsFilter.category_id,
	                    item_status: goodsFilter.item_status,
	                    current_page: goodsFilter.current_page,
	                    page_size: goodsFilter.pageSize
	                },
	                function(data) {
	                    var $render = $('#j-render-goods');
	                    $(data.data.data).each(function(k, value) {
	                        if (typeof value.create_time == "undefined") {
	                            value.create_time = '';
	                        };
	                    });
	                    var template = doT.template($('#j-template-goods').html());
	                    $render.html(template({
	                        items: data.data.data,
	                        type: 0 //1
	                    }));
	                    pageCtrl($("#goods-list-pager"), goodsFilter.current_page, Math.ceil(data.data.total_count / goodsFilter.pageSize));
	                }
	            );
	        };

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
	                }
	            });
	        };
	        //获取角标
	        function getMark() {
	            $.get(api_path + '/bossmanager/item/icon/query.do', {
	                    icon_name: iconFilter.icon_name,
	                    page_size: iconFilter.page_size,
	                    current_page: iconFilter.current_page,
	                },
	                function(data) {
	                    $('#mark-icon-list').html(doT.template(self_tpl.markTpl)(data));
	                    pageCtrl($('#icon-list-pager'), goodsFilter.current_page, Math.ceil(data.data.total_count / iconFilter.page_size));
	                }
	            );
	        };

	        function renderSelectedGoods(goodsID_array) {
	            $.get(api_path + '/bossmanager/goods/query.do', {
	                    goodsID: goodsID_array
	                },
	                function(data) {
	                    $('#select-goods-modal').modal('hide');
	                    addTarget.html(doT.template(self_tpl.goodsTpl)(data.data.data)).closest('.selected-goods').show();

	                }
	            );
	        };
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
	                    };
	                };
	                $('#select-goods-modal').modal('hide');
	                $(this).toggleClass('active');
	            });
	            // 获取展开SKU商品
	            function rendExtendTbody(spuId, goodId) {
	                $.get(api_path + '/bossmanager/item/sku/queryx.do', {
	                        goodsID: spuId
	                    },
	                    function(data) {
	                        var template = doT.template($('#j-template-sku').html());
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
	            $('#icon-confirm-select').click(function() {
	                var selectedIcon = $('#mark-icon-list .img.active');
	                if (selectedIcon.length != 0) {
	                    iconBtn.html('<img src="' + selectedIcon.data('url') + '" class="img-circle" data-iconID="' + selectedIcon.data('id') + '">');
	                    $('#select-icon-modal').modal('hide');
	                }
	            });

	            $('.add-icon-btn').click(function() {
	                $('#select-icon-modal').modal('show');
	                getMark();
	            });
	            $('.whole #session-wrap').on('click', '.img-container', function() {
	                iconBtn = null;
	                iconBtn = $(this).closest(".per-session").find(".add-icon-btn")
	                $('#select-icon-modal').modal('show');
	                getMark();
	            });

	            $('#icon-seach-bth').click(function() {
	                iconFilter.icon_name = $('#keyword-icon-input').val().trim();
	                getMark();
	            });

	            $('#mark-icon-list').delegate('.img', 'click', function() {
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
	                seckill_name: $('#campaign-name').val().trim(),
	                start_time: $('#campaign-start-time').val().trim(),
	                end_time: $('#campaign-end-time').val().trim(),
	                sub_seckill_special_list: []
	            };
	            var tempSpuInfo = null;

	            $('#session-wrap .per-session').each(function() {
	                tempSpuInfo = {};
	                tempSpuInfo.sub_seckill_name = $('.per-session .sub-active-name').val().trim();
	                tempSpuInfo.start_time = $('.per-session .sub-active-start-time').val().trim();
	                tempSpuInfo.end_time = $('.per-session .sub-active-end-time').val().trim();
	                tempSpuInfo.image_url = $('.per-session .img-container img').val();
	                tempSpuInfo.target_url = $('.per-session .sub-active-img-target').val().trim();
	                tempSpuInfo.user_sku_limit_number = 1;
	                tempSpuInfo.seckill_item_list = [];

	                var skuItem = null;
	                $(this).find('tbody tr').each(function() {
	                    skuItem = {};
	                    skuItem.item_id = $(this).attr('spu-id');
	                    skuItem.item_name = $(this).find(".name p").eq(0).text();
	                    skuItem.item_sku_sn = $(this).attr('item_sku_sn');
	                    skuItem.sku_code = $(this).find(".name p").eq(1).text();
	                    skuItem.seller_id = $(this).attr("sellerid")
	                    skuItem.seckill_price = $(this).find("input").eq(0).val().trim();
	                    skuItem.market_price = $(this).find(".price p span").text();
	                    skuItem.number = $(this).find(".num p input").val().trim();
	                    skuItem.item_sku_id = $(this).attr('sku-id');

	                    tempSpuInfo.seckill_item_list.push(skuItem);
	                });
	                campaignInfo.sub_seckill_special_list.push(tempSpuInfo)
	            });
	            return campaignInfo;
	        };
	        // 检查表单是否为空
	        function checkDataIsProper() {
	            picIsExit = null;
	            errMsg = null;
	            var priceLen = $(".current-price").length;
	            for (var i = 0; i < priceLen; i++) {
	                if ($(".current-price").eq(i).val().trim().length == 0) {
	                    errMsg = "秒杀价格不能为空";
	                    break;
	                };
	            };
	            var numLen = $(".limit-number").length;
	            for (var i = 0; i < numLen; i++) {
	                if ($(".limit-number").eq(i).val().trim().length == 0) {
	                    errMsg = "秒杀数量不能为空";
	                    break;
	                };
	            };
	            if ($(".orderIsBlank").val().trim().length == 0) {
	                errMsg = "活动顺序不能为空"
	            };
	            var timeLen = $(".ctrTime").length;
	            for (var i = 0; i < timeLen; i++) {
	                if ($(".ctrTime").eq(i).val().trim().length == 0) {
	                    errMsg = "活动时间不能为空";
	                    break;
	                };
	            };
	            var nameLen = $(".ctr-name").length;
	            for (var i = 0; i < nameLen; i++) {
	                if ($(".ctr-name").eq(i).val().trim().length == 0) {
	                    errMsg = "活动名称不能为空";
	                    break;
	                };
	            };
	            var imgLen = $(".img-container").length;
	            for (var i = 0; i < imgLen; i++) {
	                if ($(".img-container").eq(i).has("img").length == 0 && $(".sub-active-img-target").eq(i).has("img").length == 0) {
	                    picIsExit = "请选择推广图片";
	                    break;
	                };
	            };
	        };
	        $("#add-session").on('click', function() {
	            var interText = doT.template($("#j-template-session").text());
	            $("#session-wrap").append(interText());
	            $.timepicker.datetimeRange(
	                $('.session-campaign-start-time'),
	                $('.session-campaign-end-time'),
	                timepickerCfg
	            );
	        });

	        // 删除子场
	        $(".form-horizontal").on('click', ".btn-dele", function(event) {
	            $(event.target).parents('.per-session').remove();
	        });
	        $("#campaign-name").on("blur", function() {
	            if ($("#campaign-name").val().length == 0) {
	                layer.tips('请填写活动名称', '#campaign-name');
	            } else if ($("#campaign-name").val().length >= 10) {
	                layer.tips('最长10个字符', '#campaign-name');
	            };
	        });
	        $("#input-in-order").on("keyup", function() {
	            this.value = this.value.replace(/[^\d]/g, '');
	        });
	        //限购数量、价格规则
	        function limitNumberRule() {
	            $('#session-wrap').delegate('.limit-number', 'keyup', function() {
	                var maxNumber = $(this).attr('stock_num');
	                var currNumber = $(this).val().trim();
	                var newNumber = currNumber;
	                newNumber = currNumber.replace(/\D/g, '');
	                if (currNumber.length > 1) {
	                    newNumber = newNumber.replace(/^0/, '');
	                }
	                if (parseInt(newNumber) > maxNumber) {
	                    newNumber = 0;
	                }
	                $(this).val(newNumber);
	            });
	            $('#session-wrap').delegate('.limit-price', 'keyup', function() {
	                var currPrice = $(this).val().trim();
	                var newPrice = currPrice;
	                //只能输入一个小数点 禁止...
	                if (currPrice.split('.').length > 2) {
	                    $(this).val('')
	                    return;
	                }
	                //禁止000
	                if (currPrice.length > 1 && currPrice.substr(1, 1) === "0") {
	                    newPrice = newPrice.replace(/^0/, '');
	                }
	                newPrice = newPrice.replace(/^\./g, '').replace(/[^\d.]/g, '');
	                $(this).val(newPrice);
	            });
	        };
	        limitNumberRule();
	        // 保存
	        $('#save-selected-btn').click(function() {

	            checkDataIsProper();
	            if (errMsg != null) {
	                layer.msg(errMsg);
	            } else {
	                var parm = collectData();
	                $.post('/seckill/add.do', {
	                        parent_id: parm
	                    },
	                    function() {
	                        layer.msg("保存成功！");
	                    }
	                );
	            };
	        });
	        $('#publish-selected-btn').click(function() {
	            $.post(api_path + 'marketing/limtedPurchase/startLimtedPurchase.do', { id: $('#campaign-name').data('campaign_id') }, function(data) {
	                location.href = "/time-purchase-list.html";
	            });
	        });
	        $("#publish-selected-btn").on("click", function() {
	            //询问框
	            //询问框
	            layer.confirm('是否创建推广模块', {
	                    btn: ['是', '否'] //按钮
	                },
	                function() {
	                    if (picIsExit == null) {
	                        layer.msg("请选择推广图片");
	                    } else {
	                        $.post(api_path + 'marketing/limtedPurchase/startLimtedPurchase.do', { id: $('#campaign-name').data('campaign_id') }, function(data) {
	                            location.href = "/time-purchase-list.html";
	                        });
	                    };
	                },
	                function() {
	                    $.post(api_path + 'marketing/limtedPurchase/startLimtedPurchase.do', { id: $('#campaign-name').data('campaign_id') }, function(data) {
	                        location.href = "/time-purchase-list.html";
	                    });
	                });
	        });

	        function getGoodsData(pageIndex) {
	            $.ajax({
	                type : "post",
	                url : api_path + '/supplier/itemRepo/query.do',
	                data : {
	                    current_page: pageIndex,
	                    page_size: 20,
	                    key: $('#keywords').val()
	                },
	                success : function(result) {
	                    result = jsonstring2object.parse(result);
	                    var interText = doT.template($("#act-template").text());
	                    if(result.data.data) {
	                        $("#interpolation").html(interText(result.data.data));
	                    } else {
	                        $("#interpolation").html([]);
	                    }

	                    if(count === 1) {
	                        pager(1, Math.ceil(result.data.total_count/20));
	                        count++;
	                    }
	                }
	            });
	        };
	        $('#test-btn').click(function(){
	            getGoodsData(1);
	        });
	    });
	});




/***/ }
/******/ ]);