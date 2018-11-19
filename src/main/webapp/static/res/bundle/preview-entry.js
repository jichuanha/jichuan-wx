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
/******/ ({

/***/ 0:
/***/ function(module, exports, __webpack_require__) {

	module.exports = __webpack_require__(16);


/***/ },

/***/ 10:
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

/***/ 16:
/***/ function(module, exports, __webpack_require__) {

	var localStorageTool = __webpack_require__(10);
	$(function(){
		// var self_tpl = {
		// 	'blockTpl': '{{~ it.blockInfo:item:index }}\
		// 					<span data-id="{{= item.id.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*it.blockWidth*it.windowWidth }}px;top:{{= item.left_top_y*it.blockWidth*it.windowWidth }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*it.blockWidth*it.windowWidth }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*it.blockWidth*it.windowWidth }}px;border:1px solid blue">\
		// 						<i class="icon-plus"></i>\
		// 					</span>\
		// 				{{~}}'
		// };
		var self_tpl = {
			'blockTpl': '{{~ it.blockInfo:item:index }}\
							<span data-id="{{= item.id.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*it.blockWidth*it.windowWidth }}px;top:{{= item.left_top_y*it.blockWidth*it.windowWidth }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*it.blockWidth*it.windowWidth }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*it.blockWidth*it.windowWidth }}px;border:1px solid blue">\
								<i class="icon-plus"></i>\
							</span>\
						{{~}}'
		};
		function initGrid(target) {
		    var gridInfo = JSON.parse(localStorageTool.getItem('gridInfo'));
		    //每个小块宽度的百分比
		    var single_block_width = 1/gridInfo.column;
		    gridInfo.blockWidth = single_block_width;
		    gridInfo.windowWidth = $(window).width();
		        target.html(doT.template(self_tpl.blockTpl)(gridInfo));
		}
		initGrid($('#preview-container'));
	});



/***/ }

/******/ });