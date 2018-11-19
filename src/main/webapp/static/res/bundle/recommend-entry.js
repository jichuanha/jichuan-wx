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

	module.exports = __webpack_require__(17);


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
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
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
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */
/***/ function(module, exports, __webpack_require__) {

	
	var gridFactory = __webpack_require__(18);
	var api_path = __webpack_require__(3).api_path;
	var jsonstring2object = __webpack_require__(4);
	gridFactory.init();
	// $('#producegridbtn').click(function(){
	//     alert('ok');
	// });




/***/ },
/* 18 */
/***/ function(module, exports, __webpack_require__) {

	var self_tpl = __webpack_require__(19);
	var localStorageTool = __webpack_require__(10);
	var imgSelect = __webpack_require__(20);
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
	        $('#virtual-grid-container,#grid-container').attr('data-row', gridInfo.row).attr('data-column', gridInfo.column);
	        // if(!$('#grid-container').data('row'))
	        // localStorageTool.setItem("gridInfo", JSON.stringify(gridInfo));  
	        localStorageTool.setItem("gridInfo", JSON.stringify(getReadyData()));  
	}

	function initGrid(target) {
	    $.get('/getGrid.do',{id: ''},function(data){
	        var temp = data;
	        target.html(doT.template(self_tpl.editBlockTpl)(data.data)).css({
	                            width: data.data.column*100 + 'px',
	                            height: data.data.row*100 + 'px'
	                         });
	        initBlankGrid($('#virtual-grid-container'), data.data.row, data.data.column);
	        localStorageTool.setItem("gridInfo", JSON.stringify(data.data));  
	    });
	}


	function mergeGrid() {
	    //当前选中的格子信息
	    var selected_grid = [];
	    //当前选中的格子id
	    var str = '';
	    $('#grid-container span.active').each(function(index){
	        selected_grid.push({id: $(this).data('id')});
	        if(index != $('#grid-container span.active').length -1) {
	            str += $(this).data('id') + ',';
	        } else {
	            str += $(this).data('id');
	        }
	    });
	    var gridArray = str.split(',');
	    //设置虚拟格子的active
	    $('#virtual-grid-container span').removeClass('active');
	    gridArray.forEach(function(item,i){
	        $('#virtual-grid-container span[data-id="' + item + '"]').addClass('active');
	    });
	    // selected_grid.forEach(function(item,i){
	    //     $('#virtual-grid-container span[data-id="' + item.id + '"]').addClass('active');
	    // });


	    //当前格子的n*m
	    var row = $('#grid-container').attr('data-row');
	    var column = $('#grid-container').attr('data-column');
	    //most_left_top -----begin -----
	    var most_left_top_grid = {};
	    for(var i=1;i<=column;i++) {
	        $('#virtual-grid-container .col' + i).each(function(){
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
	        temp = $('#virtual-grid-container span[data-id="' + i + '"]');
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
	        $('#virtual-grid-container span.active').each(function(){
	            var this_left_top_x = $(this).data('left_top_x');
	            var this_left_top_y = $(this).data('left_top_y');
	            if(this_left_top_x >= most_left_top_grid.left_top_x && this_left_top_x <= most_bottom_right_grid.left_top_x && this_left_top_y >= most_left_top_grid.left_top_y && this_left_top_y <= most_bottom_right_grid.left_top_y) {
	                active_grid_sum++;
	            }
	        });
	        
	        if(total_grid_sum === active_grid_sum && total_grid_sum === $('#virtual-grid-container span.active').length) {
	            //开始合并
	            //取得可以合并的格子id
	            var avail_grid_id = [];
	            $('#virtual-grid-container span').each(function(){
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
	                $('#grid-container span[data-id="' + item.id + '"]').remove();
	            });
	            // $('#grid-container span[data-id="' + gridArray.join(",") + '"]').remove();
	            //构造新格子
	            var merge_grid = {
	                id: avail_grid_id,
	                left_top_x: $('#virtual-grid-container span[data-id="' + avail_grid_id[0] + '"]').data('left_top_x'),
	                left_top_y: $('#virtual-grid-container span[data-id="' + avail_grid_id[0] + '"]').data('left_top_y'),
	                bottom_right_x: $('#virtual-grid-container span[data-id="' + avail_grid_id[avail_grid_id.length-1] + '"]').data('bottom_right_x'),
	                bottom_right_y: $('#virtual-grid-container span[data-id="' + avail_grid_id[avail_grid_id.length-1] + '"]').data('bottom_right_y'),
	                blockWidth: 100
	            };
	            $('#grid-container').append(doT.template(self_tpl.singleBlockTpl)(merge_grid));
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
	    var row = $('#grid-container').attr('data-row');
	    var column = $('#grid-container').attr('data-column');
	    for(var i=1;i<=row*column;i++){
	        $('#grid-container span').each(function(){
	            var curr_block = $(this);
	            //当前块的id
	            var curr_id = (curr_block.data('id')+'').split(",");
	            if(!inArray(i+'',ready_id) && inArray(i+'',curr_id)) {
	                for(var j=0;j<curr_id.length;j++) {
	                    ready_id.push(curr_id[j]);
	                }
	                var singleBlock = {
	                    id: curr_id,
	                    left_top_x: curr_block.data('left_top_x'),
	                    left_top_y: curr_block.data('left_top_y'),
	                    bottom_right_x: curr_block.data('bottom_right_x'),
	                    bottom_right_y: curr_block.data('bottom_right_y')
	                };
	                grid_queue.push(singleBlock);
	            }
	        });
	    }
	    saveGridInfo.row = row;
	    saveGridInfo.column = column;
	    saveGridInfo.blockInfo = grid_queue;
	    return saveGridInfo;
	}
	function saveGrid() {
	    var saveGridInfo = getReadyData();
	}

	function bindEvent() {
	    $('#grid-container').delegate('span','click',function(){
	        $(this).toggleClass('active');
	    });
	    //生成格子
	    //produceGrid();
	    $('#produce-grid-btn').click(function(){
	        initBlankGrid($('#grid-container'), $('input[name=row-num]').val(), $('input[name=column-num]').val());
	        initBlankGrid($('#virtual-grid-container'), $('input[name=row-num]').val(), $('input[name=column-num]').val());
	        // localStorageTool.setItem("gridInfo", JSON.stringify(getReadyData()));  
	    });
	    //合并格子
	    $('#merge-grid-btn').click(function(){
	        if($('#grid-container span.active').length < 2) {
	            layer.msg("至少选择两个格子才能合并");
	            return;
	        }
	        mergeGrid();
	    });
	    
	    //重置格子
	    $('#reset-grid-btn').click(function(){
	        initBlankGrid($('#grid-container'), $('input[name=row-num]').val(), $('input[name=column-num]').val());
	        initBlankGrid($('#virtual-grid-container'), $('input[name=row-num]').val(), $('input[name=column-num]').val());
	    }); 

	    //保存格子
	    $('#save-grid-btn').click(function(){
	        saveGrid();
	    });

	    //预览格子
	    $('#preview-grid-btn').click(function(){
	        //iframe层
	        layer.open({
	          type: 2,
	          title: '预览',
	          shadeClose: true,
	          shade: 0.8,
	          area: ['414px', '736px'],
	          content: 'http://192.168.8.169:3000/html/recommend/preview.html' //iframe的url
	        }); 
	    });

	    //上传图片
	    imgSelect.initModal(1);
	    $('#grid-container').delegate('span','click',function(){
	        if($('#upload-img-switch').hasClass('active') && $(this).hasClass('active')) {
	            imgSelect.show();
	        }
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
	    if(location.href.substr(location.href.indexOf("type=")+5) === "edit") {
	        initGrid($('#grid-container'));
	    }
	}
	var gridFactory = {
	    init: init
	}
	module.exports = gridFactory;

/***/ },
/* 19 */
/***/ function(module, exports) {

	var self_tpl = {
		'blockTpl': '{{~ it.blockInfo:item:index }}\
						<span class="col{{? (index+1)%it.column!=0 }}{{= (index+1)%it.column }}{{??}}{{= it.column }}{{?}}" data-id="{{= item.id.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*it.blockWidth }}px;top:{{= item.left_top_y*it.blockWidth }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*it.blockWidth }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*it.blockWidth }}px;border:1px solid blue">\
						</span>\
					{{~}}',
		'editBlockTpl': '{{~ it.blockInfo:item:index }}\
						<span data-id="{{= item.id.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*it.blockWidth }}px;top:{{= item.left_top_y*it.blockWidth }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*it.blockWidth }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*it.blockWidth }}px;border:1px solid blue">\
						</span>\
					{{~}}',
		'singleBlockTpl':'<span data-id="{{= it.id.join(",") }}" data-left_top_x="{{= it.left_top_x }}" data-left_top_y="{{= it.left_top_y }}" data-bottom_right_x="{{= it.bottom_right_x }}" data-bottom_right_y="{{= it.bottom_right_y }}" style="left:{{= it.left_top_x*it.blockWidth }}px;top:{{= it.left_top_y*it.blockWidth }}px;width:{{= Math.abs(it.left_top_x-it.bottom_right_x)*it.blockWidth }}px;height:{{= Math.abs(it.left_top_y-it.bottom_right_y)*it.blockWidth }}px;border:1px solid blue">\
						 </span>'
	};
	module.exports = self_tpl;

/***/ },
/* 20 */
/***/ function(module, exports) {

	var self_tpl = {
	    img_select_modal:   '<div class="modal fade img-select-modal" id="img-select-modal">\
	                          <div class="modal-dialog">\
	                            <div class="modal-content">\
	                              <div class="modal-header">\
	                                图片库\
	                                <a class="btn btn-sm btn-default" id="uploadNewImg">新图片</a>\
	                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>\
	                              </div>\
	                              <div class="modal-body">\
	                                <table class="ui-table">\
	                                    <thead>\
	                                    <tr>\
	                                        <th class="tc" style="width: 20%;">图片</th>\
	                                        <th class="tc" style="width: 30%;">名称</th>\
	                                        <th class="tc" style="width: 30%;">时间</th>\
	                                        <th class="tc" style="width: 20%;">操作</th>\
	                                    </tr>\
	                                    </thead>\
	                                    <tbody id="j-img-select-box" class="j-goods-tbody">\
	                                    </tbody>\
	                                </table>\
	                                <div class="href-input">链接：<input type="text" /></div>\
	                                <button type="button" class="btn btn-success confirm-select" id="confirm-select">确定选择</button>\
	                                <div class="modal-page img-select-pager" id="img-select-pager"></div>\
	                              </div>\
	                            </div>\
	                          </div>\
	                        </div>',
	    img_select_template:'{{ for(var i=0; i < it.length; i++) { }}\
	                                <tr class="j-goods-box">\
	                                    <td class="tc"><span><img src="{{=it[i].file_url}}" style="width: 50px;height: 50px;"></span></td>\
	                                    <td class="tc"><span class="popup-g-name">{{=it[i].file_name}}</span></td>\
	                                    <td class="tc"><span>{{=it[i].create_time}}</span></td>\
	                                    <td class="tc"><a src="javascript:void(0);" class="img-select">选择</a></td>\
	                                </tr>\
	                        {{ } }}',
	    img_selected_tpl:   '<tr class="j-goods-box">\
	                            <td class="tc"><span><a href="{{= it.src }}"><img src="{{= it.file_url }}" style="width: 50px;height: 50px;"></a></span></td>\
	                            <td class="tc"><span class="popup-g-name"></span></td>\
	                            <td class="tc"><span></span></td>\
	                            <td class="tc"><a src="javascript:void(0);" class="img-select active">已选择</a></td>\
	                        </tr>'
	};

	/**
	 *  图片选择弹框父亲页
	 *  1：格子
	 *  2：
	**/
	var entry = null;
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
	            $('#j-img-select-box').html('');
	            if($('#grid-container span.active img')) {
	                var obj = {
	                    file_url: $('#grid-container span.active img').attr('src'),
	                    src: $('#grid-container span.active src').attr('href')
	                }
	                $('#j-img-select-box').append(doT.template(self_tpl.img_selected_tpl)(obj));
	            }
	            $('#j-img-select-box').append(doT.template(self_tpl.img_select_template)(data.data.list));
	        }
	    });
	}





	function initModal(entry) {
	    imgSelect.entry = entry;
	    $('#img-select-modal-container').html(self_tpl.img_select_modal);
	    bindEvent();
	}

	function bindEvent() {
	    $('#img-select-modal').delegate('.img-select', 'click', function () {
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
	    $('#img-select-modal').delegate('.confirm-select', 'click', function () {
	        var  imgSrc = $('#img-select-modal .img-select.active').eq(0).closest('.j-goods-box').find('td').eq(0).find('img').attr('src');
	        // var imgElement = '<span class="item-img" data-imgsrc="'+ imgSrc +'"><img src="' + imgSrc + '"><a href="javascript:;" class="cancel"></a></span>';
	        // $('#img-select-modal').modal('hide');
	        // $('#goods-pic-add-icon').before(imgElement);
	        var imgHref = $('#img-select-modal .href-input input').val().trim();
	        if(imgSelect.entry === 1) {
	            if(imgSrc) {
	                if(imgHref != ''){
	                    $('#grid-container span.active').html('<a href="' + imgHref + '"><img src="' + imgSrc + '"></a>');
	                } else {
	                    $('#grid-container span.active').html('<a href="javascript:void(0);"><img src="' + imgSrc + '"></a>');
	                }
	            } else {
	                $('#grid-container span.active').html('');
	            }
	            $('#img-select-modal').modal('hide');
	        }
	    });

	    $('#upload-img-switch').click(function(){
	        $(this).toggleClass('active');
	        if($(this).hasClass('active')){
	            $('#grid-container span').removeClass('active');
	            $(this).html('合并模式');
	            $('#merge-grid-btn').hide();
	        } else {
	            $(this).html('上传模式');
	            $('#merge-grid-btn').show();
	        }
	    });
	}
	function show() {
	    $('#img-select-modal').modal('show');
	    getImgList(1,5);
	    pager();
	}

	function pager() {
	    $("#img-select-pager").createPage({
	        pageCount: 5,
	        current: 1,
	        backFn: function(nextPage) {
	            getImgList(nextPage, 5);
	        }
	    });
	}
	var imgSelect = {
	    initModal: initModal,
	    show: show
	}
	module.exports = imgSelect;

/***/ }
/******/ ]);