var mainTpl = {
	image:  '<div class="form-search breadcrumb div-image">\
				<div><div class="block-control ">\
						<ul class="ul-form-3 ">\
							<li><span class="block-title">块类型：轮播</span></li>\
							<li><label>顺序：</label><input type="text" onkeyup="return value=mainTpl.getNumberValue(this.value)"  value="{{=it.block_order ||99}}"   class="input-mini" name="block_order"></li>\
							<li>\
								<label>高度：</label>\
								<input type="text" disabled onkeyup="return value=mainTpl.getNumberValue(this.value)"  class="input-disabled input-mini"  value="{{=it.height || "360"}}" name="height" >\
							</li>\
							<li  style="float: right">\
								<button class="btn block-invalid" style="display:none;">使失效</button>\
							</li>\
							<li  style="float: right">\
								<button class="btn block-delete">删除</button>\
							</li>\
						</ul>\
					</div>\
					<div class="block-image">\
						<button class="btn btn-add-image" type="button" style="float: right;">增一张</button>\
						{{? it.element_list}}\
						  {{~it.element_list || {} :i:index}}\
						<ul class="ul-form-4 ul-image">\
							<li>\
								<label>图片：</label>\
								{{? typeof(i.image_url) != "undefined" }}\
								<img src="{{=i.image_url}}" style="width:50px;height: 50px;"/> \
								{{?}}\
								{{? !i.image_url  }}\
								<button class="btn" style="width: 30px;height: 30px;"></button>\
								{{?}}\
							</li>\
							<li>\
								<label>顺序：</label>\
								<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index"  value="{{=i.sort_index ||"99" }}"  >\
							</li>\
							<li>\
								<label>链接：</label>\
								<input type="text" name="target"  class="input-xxlarge" value="{{=i.target_url ||""}}">\
							</li>\
							<li>\
								<button class="btn image-delete right" type="button" data-id="{{=i.id }}"  >删除</button>\
							</li>\
						</ul>\
						{{~}}\
						{{?}}\
					</div>\
				</div>\
			</div>'
	,singleImg:  '<div class="form-search breadcrumb div-single-img">\
				<div><div class="block-control ">\
						<ul class="ul-form-3 ">\
							<li><span class="block-title">块类型：图片块</span></li>\
							<li><label>顺序：</label><input type="text" onkeyup="return value=mainTpl.getNumberValue(this.value)"  value="{{=it.block_order ||99}}"   class="input-mini" name="block_order"></li>\
							<li>\
								<label>高度：</label>\
								<input type="text" disabled onkeyup="return value=mainTpl.getNumberValue(this.value)"  class="input-disabled input-mini"  value="{{=it.height || "360"}}" name="height" >\
							</li>\
							<li  style="float: right">\
								<button class="btn block-invalid" style="display:none;">使失效</button>\
							</li>\
							<li  style="float: right;">\
								<button class="btn block-delete">删除</button>\
							</li>\
						</ul>\
					</div>\
					<div class="block-image">\
						{{? it.element_list}}\
						  {{~it.element_list || {} :i:index}}\
							<ul class="ul-form-4 ul-image">\
								<li>\
									<label>图片：</label>\
									{{? typeof(i.image_url) != "undefined" }}\
									<img src="{{=i.image_url}}" style="width:50px;height: 50px;"/> \
									{{?}}\
									{{? !i.image_url  }}\
									<button class="btn" style="width: 30px;height: 30px;"></button>\
									{{?}}\
								</li>\
								<li>\
									<label>宽度：</label>\
									<input name="width-px" style="width:50px;" value="{{=i.width}}" disabled/>\
								</li>\
								<li>\
									<label>高度：</label>\
									<input name="height-px" style="width:50px;" value="{{=i.height}}" disabled/>\
								</li>\
								<li style="display:none;">\
									<label>顺序：</label>\
									<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index"  value="{{=i.sort_index ||"99" }}"  >\
								</li>\
								<li>\
									<label>链接：</label>\
									<input type="text" name="target"  class="input-xxlarge" value="{{=i.target_url ||""}}">\
								</li>\
								<li>\
									<button class="btn single-image-delete right" type="button" data-id="{{=i.id }}"  >删除</button>\
								</li>\
							</ul>\
						  {{~}}\
						{{?}}\
					</div>\
				</div>\
			</div>'
	,singleImgNew:  '<div class="form-search breadcrumb div-single-img">\
			<div><div class="block-control ">\
					<ul class="ul-form-3 ">\
						<li><span class="block-title">块类型：图片块</span></li>\
						<li><label>顺序：</label><input type="text" onkeyup="return value=mainTpl.getNumberValue(this.value)"  value="{{=it.block_order ||99}}"   class="input-mini" name="block_order"></li>\
						<li>\
							<label>高度：</label>\
							<input type="text" disabled onkeyup="return value=mainTpl.getNumberValue(this.value)"  class="input-disabled input-mini"  value="{{=it.height || "360"}}" name="height" >\
						</li>\
						<li  style="float: right">\
							<button class="btn block-invalid" style="display:none;">使失效</button>\
						</li>\
						<li  style="float: right;">\
							<button class="btn block-delete">删除</button>\
						</li>\
					</ul>\
				</div>\
				<div class="block-image">\
					<ul class="ul-form-4 ul-image ul-image-select">\
						<li>\
							<label>图片：</label>\
							<input type="hidden" name="img_uri" />\
							<button class="btn imgItemUpload">...</button>\
						</li>\
						<li>\
							<label>宽度：</label>\
							<input name="width-px" style="width:50px;" disabled/>\
						</li>\
						<li>\
							<label>高度：</label>\
							<input name="height-px" style="width:50px;" disabled/>\
						</li>\
						<li style="display:none;">\
							<label>顺序：</label>\
							<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index" value="99"   class="input-mini">\
						</li>\
						<li>\
							<label>链接：</label>\
							<input type="text" name="target"  class="input-xxlarge">\
						</li>\
						<li>\
							<button class="btn single-image-delete right" type="button""  >删除</button>\
						</li>\
					</ul>\
				</div>\
			</div>\
		</div>'
	,imageItem:'<ul class="ul-form-4 ul-image ul-image-select">\
					<li>\
						<label>图片：</label>\
						<input type="hidden" name="img_uri" />\
						<button class="btn imgItemUpload">...</button>\
					</li>\
					<li>\
						<label>顺序：</label>\
						<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index" value="99"   class="input-mini">\
					</li>\
					<li>\
						<label>链接：</label>\
						<input type="text" name="target"  class="input-xxlarge">\
					</li>\
					<li>\
						<button class="btn image-delete right" >删除</button>\
					</li>\
				</ul>'
	,singleImageItem:'<ul class="ul-form-4 ul-image ul-image-select">\
					<li>\
						<label>图片：</label>\
						<input type="hidden" name="img_uri" />\
						<button class="btn imgItemUpload">...</button>\
					</li>\
					<li>\
						<label>宽度：</label>\
						<input name="width-px" style="width:50px;" disabled/>\
					</li>\
					<li>\
						<label>高度：</label>\
						<input name="height-px" style="width:50px;" disabled/>\
					</li>\
					<li style="display:none;">\
						<label>顺序：</label>\
						<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index" value="99"   class="input-mini">\
					</li>\
					<li>\
						<label>链接：</label>\
						<input type="text" name="target"  class="input-xxlarge">\
					</li>\
					<li>\
						<button class="btn single-image-delete right" >删除</button>\
					</li>\
				</ul>'
	,goods:'<div class=" form-search breadcrumb goods-form" >\
				<div>\
					<div class="block-control goods-category">\
						<ul class="ul-form-5">\
							<li>\
								<span class="block-title">块类型：商品分类</span>\
							</li>\
							<li>\
								<label>顺序：</label>\
								<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)" name="block_order" value="{{=it.block_order || "99" }}">\
							</li>\
							<li>\
								<label>高度：</label>\
								<input type="text" disabled class="input-mini input-disabled" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="height" value="{{=it.height||"360"}}">\
							</li>\
							<li style="float: right">\
								<button class="btn block-invalid" style="display:none;">使失效</button>\
							</li>\
							<li style="float: right">\
								<button class="btn block-delete">删除</button>\
							</li>\
						</ul>\
					</div>\
					<div class="goods-category-whole">\
						<ul class="ul-form-6" style="margin:5px;">\
							<li>\
								<label>标题：</label>\
								<input type="text" name="category_title" maxlength="8" value="{{=it.element_list?it.element_list[0].category_title:""}}">\
							</li>\
							<li>\
								<label>副标题：</label>\
								<input type="text" name="sub_category_title" maxlength="28" value="{{=it.element_list?it.element_list[0].sub_category_title:""}}">\
							</li>\
							<li>\
								<form class="form-inline form-new" role="form">\
								  <div class="form-group">\
								  </div>\
								</form>\
							</li>\
							<li>\
								<button class="btn btn-add-item" type="button">增加商品</button>\
							</li>\
						</ul>\
						<div class="goods-category-table">\
							<table class="goods-table">\
								<tr>\
									<th>图片</th>\
									<th>名称</th>\
									<th>价格</th>\
									<th>顺序</th>\
									<th>操作</th>\
								</tr>\
								{{?it.element_list}}\
								{{~it.element_list[0].product_list_list :i:index}}\
								<tr item_id="{{=i.item_id}}" seller_id="{{=i.seller_id}}">\
								<td> <img src="{{=i.icon_url}}" height="150" width="150"/></td>\
								<td>{{=i.item_name}}</td>\
								<td>{{=i.market_price/100}}元</td>\
								<td><input type="text" name="item_order" class="input-mini" value="{{=i.item_order || "99" }}"</td>\
								<td><a href="javascript:;" data-id="{{=i.item_id}}" class="delete-goods">删除</a> </td>\
								</tr>\
								{{~}}\
								{{?}}\
							</table>\
						</div>\
					</div>\
				</div>\
			</div>'
	,tms:   '<div class="form-search breadcrumb tms-block">\
				<div>\
					<div class="block-control goods-category">\
						<ul class="ul-form-5">\
							<li>\
								<span class="block-title">块类型：TMS</span>\
							</li>\
							<li>\
								<label>顺序：</label>\
								<input type="text" class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="block_order" value="{{=it.block_order||"99"}}">\
							</li>\
							<li>\
								<label>高度：</label>\
								<input type="text" onkeyup="return value=mainTpl.getNumberValue(this.value)"  class="disabled  input-mini input-disabled" disabled  name="height" value="{{=it.height||"360"}}">\
							</li>\
							<li  style="float: right">\
								<button class="btn block-invalid" style="display:none;">使失效</button>\
							</li>\
							<li style="float: right">\
								<button class="btn block-delete" >删除</button>\
							</li>\
						</ul>\
					</div>\
					<div class="goods-category-whole">\
						<ul class="ul-form-6">\
							<li>\
								<label>TMS：</label>\
								<input type="text" name="tms_name"  required value="{{=it.tms_name||""}}">\
							</li>\
						</ul>\
					</div>\
				</div>\
			</div>'
	,grid:   '<div class="block-grid-container form-search">\
				<div class="block-info">\
					<ul class="ul-form-5">\
						<li>\
							<span class="block-title">块类型：格子</span>\
						</li>\
						<li>\
							<label>顺序：</label>\
							<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)" name="block_order" value="{{=it.block_order || "99" }}">\
						</li>\
						<li>\
							<label>高度：</label>\
							<input type="text" disabled class="input-mini input-disabled" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="height" value="{{=it.height||"360"}}">\
						</li>\
						<li  style="float: right">\
								<button class="btn block-invalid" style="display:none;">使失效</button>\
							</li>\
						<li style="float: right">\
							<button class="btn block-delete">删除</button>\
						</li>\
					</ul>\
				</div>\
				<div class="">\
					<div class="grid-row-column">\
						<div class="row-column-info">\
							<span>行数:</span><input type="text" value="3" name="row-num" id="row-num" onkeyup="return value=mainTpl.getNumberValue(this.value)">\
							<span>列数:</span><input type="text" value="3" name="column-num" onkeyup="return value=mainTpl.getNumberValue(this.value)">\
							<button type="button" class="btn btn-success produce-grid-btn" id="produce-grid-btn">生成格子</button>\
						</div>\
					</div>\
				</div>\
				<div>\
					<div class="content grid-container">\
					</div>\
					<div class="content virtual-grid-container" style="display: none;">\
					</div>\
				</div>\
				<div class="btn-container" style="display:none;">\
					<button type="button" class="btn btn-default upload-img-switch" id="upload-img-switch">上传模式</button>\
					<button type="button" class="btn btn-success merge-grid-btn" id="merge-grid-btn">合并</button>\
					<button type="button" class="btn btn-default select-all-grid-btn" id="select-all-grid-btn">全选</button>\
					<button type="button" class="btn btn-primary reset-grid-btn" id="reset-grid-btn">重置</button>\
					<button type="button" class="btn btn-primary" id="save-grid-btn" style="display:none;">保存</button>\
				</div>\
			</div>'
	,editBlockTpl:   '<div class="block-grid-container form-search">\
						<div class="block-info">\
							<ul class="ul-form-5">\
								<li>\
									<span class="block-title">块类型：格子</span>\
								</li>\
								<li>\
									<label>顺序：</label>\
									<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)" name="block_order" value="{{=it.block_order || "99" }}">\
								</li>\
								<li>\
									<label>高度：</label>\
									<input type="text" disabled class="input-mini input-disabled" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="height" value="{{=it.height||"360"}}">\
								</li>\
								<li  style="float: right">\
										<button class="btn block-invalid" style="display:none;">使失效</button>\
									</li>\
								<li style="float: right">\
									<button class="btn block-delete">删除</button>\
								</li>\
							</ul>\
						</div>\
						<div class="">\
							<div class="grid-row-column">\
								<div class="row-column-info">\
									<span>行数:</span><input type="text" value="{{=it.gridInfo.grid_row }}" name="row-num" id="row-num" onkeyup="return value=mainTpl.getNumberValue(this.value)">\
									<span>列数:</span><input type="text" value="{{=it.gridInfo.grid_column }}" name="column-num" onkeyup="return value=mainTpl.getNumberValue(this.value)">\
									<button type="button" class="btn btn-success produce-grid-btn" id="produce-grid-btn">生成格子</button>\
								</div>\
							</div>\
						</div>\
						<div>\
							<div class="content grid-container" data-row="{{=it.gridInfo.grid_row }}" data-column="{{=it.gridInfo.grid_column }}" style="width:{{= it.gridInfo.grid_column*100 }}px;height:{{= it.gridInfo.grid_row*100 }}px;">\
								{{~ it.gridInfo.grid_list:item:index }}\
									<span data-id="{{= item.ids.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*100 }}px;top:{{= item.left_top_y*100 }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*100 }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*100 }}px;" class="{{? item.target_url }}imghref{{?}}" imghref="{{? item.target_url }}{{= item.target_url }}{{?}}">\
										<img src="{{= item.image_url }}">\
									</span>\
								{{~}}\
							</div>\
							<div class="content virtual-grid-container" data-row="{{=it.virtualGridInfo.row }}" data-column="{{=it.virtualGridInfo.column }}" style="display: none;width:{{= it.virtualGridInfo.column*100 }}px;height:{{= it.virtualGridInfo.row*100 }}px;">\
								{{~ it.virtualGridInfo.blockInfo:item:index }}\
									<span class="col{{? (index+1)%it.virtualGridInfo.column!=0 }}{{= (index+1)%it.virtualGridInfo.column }}{{??}}{{= it.virtualGridInfo.column }}{{?}}" data-id="{{= item.ids.join(",") }}" data-left_top_x="{{= item.left_top_x }}" data-left_top_y="{{= item.left_top_y }}" data-bottom_right_x="{{= item.bottom_right_x }}" data-bottom_right_y="{{= item.bottom_right_y }}" style="left:{{= item.left_top_x*100 }}px;top:{{= item.left_top_y*100 }}px;width:{{= Math.abs(item.left_top_x-item.bottom_right_x)*100 }}px;height:{{= Math.abs(item.left_top_y-item.bottom_right_y)*100 }}px;">\
									</span>\
								{{~}}\
							</div>\
						</div>\
						<div class="btn-container">\
							<button type="button" class="btn btn-default upload-img-switch" id="upload-img-switch">上传模式</button>\
							<button type="button" class="btn btn-success merge-grid-btn" id="merge-grid-btn">合并</button>\
							<button type="button" class="btn btn-default select-all-grid-btn">全选</button>\
							<button type="button" class="btn btn-primary reset-grid-btn" id="reset-grid-btn">重置</button>\
							<button type="button" class="btn btn-primary" id="save-grid-btn" style="display:none;">保存</button>\
						</div>\
					</div>'
	,options: '{{~it :value:index}}\
					<option value="{{=value.id}}">{{=value.name}}------{{=mainTpl.getPublishStatus(value.publish_status)}}</option>\
			  {{~}}'
	,cateOptions:  '<option value="">请选择类目</option>{{~it :value:index}}\
						{{? typeof(value) != "undefined"}}\
						<option value="{{=value.id}}">{{=value.cate_name}}</option>\
						{{?}}\
					{{~}}'
	,cate2Options:  '<option value="">请选择二级类目</option>{{~it :value:index}}\
		{{? typeof(value) != "undefined"}}\
		<option value="{{=value.id}}">{{=value.cate_name}}</option>\
		{{?}}\
	{{~}}'
	,brand_key: '<option value="">请选择品牌</option>{{~it.data:val:inx}}<option value="{{=val.id}}">{{=val.brand_name}}</option>{{~}}'
	,goodsBox:' {{~it.data :it:index}}\
					<tr class="j-goods-box">\
						<td class="tc"><span><img src="{{=it.icon_url}}" style="width: 50px;height: 50px;"></span></td>\
						<td><span class="popup-g-name">{{=it.item_name}}</span></td>\
						<td class="tc"><span>{{=it.gmt_created}}</span></td>\
						<td class="tc">\
							<span class="green">{{=mainTpl.getItemStatus(it.item_status)}}</span>\
						</td>\
						<td class="tc">{{=mainTpl.getItemType(it.item_type)}}</td>\
						<td><a class="select-goods select-group-goods j-select-g" data-info="{{=mainTpl.getItemInfo(it)}}" data-status="{{=mainTpl.getItemStatus(it.item_status)}}" data-id="{{=it.id}}"  >选择</a></td>\
					</tr>\
				{{~}}'
	,itemTr:'{{~it :it:index}}\
				<tr item_id="{{=it.id}}" seller_id="{{=it.seller_id}}">\
					<td><img src="{{=it.icon_url}}" width="150" height="150" /></td>\
					<td>{{=it.item_name}}</td>\
					<td>{{=it.market_price/100}}元</td>\
					<td><input type="text" value="99" onkeyup="return value=mainTpl.getNumberValue(this.value)" name="item_order" class="input-mini" maxlength="3"> </td>\
					<td><a href="javascript:;" data-id="{{=it.id}}" class="delete-goods">删除</a> </td>\
				</tr>\
			{{~}}'
	,getItemStatus: function(status){
		switch (status){
			case 1:
				return '待审核';
			case 4:
				return '上架';
			case 5:
				return '下架';
		}
	},getItemType: function(type){
	   switch(type){
		   case 1:
			   return '普通商品';
		   case 22:
			   return '组合商品';
	   }
	},getItemInfo: function(info){
		info.item_desc = null;
		return  encodeURIComponent(JSON.stringify(info))
	},getPublishStatus: function(status){
			return status == 1?'已发布':'未发布'
	},getNumberValue: function(val){
		return val.replace(/\D/g,'');
	}
}
// module.exports = mainTpl;