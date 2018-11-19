var mainTpl =
{image:
    '<div class="form-search breadcrumb div-image">\
		<div><div class="block-control ">\
				<ul class="ul-form-3 ">\
					<li>块类型:轮播<label></label></li>\
					<li><label>顺序：</label><input type="text" onkeyup="return value=mainTpl.getNumberValue(this.value)"  value="{{=it.block_order ||99}}"   class="input-mini" name="block_order"></li>\
					<li>\
						<label>高度：</label>\
						<input type="text" disabled onkeyup="return value=mainTpl.getNumberValue(this.value)"  class="input-disabled input-mini"  value="{{=it.height || "360"}}" name="height" >\
					</li>\
					<li  style="float: right">\
						<button class="btn block-delete"  >删除</button>\
					</li>\
				</ul>\
			</div>\
			<div class="block-image">\
				<button class="btn btn-add-image" type="button" style="float: right;">增一张</button>\
				{{? it.element_list}}\
				  {{~it.element_list || {} :i:index}}\
				<ul class="ul-form-4 ul-image">\
					<li>\
						<label>图片:</label>\
						{{? typeof(i.image_url) != "undefined" }}\
						<img src="{{=i.image_url}}" style="width:50px;height: 50px;"/> \
						{{?}}\
						{{? !i.image_url  }}\
						<button class="btn" style="width: 30px;height: 30px;"></button>\
						{{?}}\
					</li>\
					<li>\
						<label>顺序:</label>\
						<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index"  value="{{=i.sort_index ||"99" }}"  >\
					</li>\
					<li>\
						<label>链接:</label>\
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
,imageItem:'<ul class="ul-form-4 ul-image ul-image-select">\
					<li>\
						<label>图片:</label>\
						<input type="hidden" name="img_uri" />\
						<button class="btn imgItemUpload">...</button>\
					</li>\
					<li>\
						<label>顺序:</label>\
						<input type="text"  class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="sort_index" value="99"   class="input-mini">\
					</li>\
					<li>\
						<label>链接:</label>\
						<input type="text" name="target"  class="input-xxlarge">\
					</li>\
					<li>\
						<button class="btn image-delete right" >删除</button>\
					</li>\
				</ul>'
    ,goods:'<div class=" form-search breadcrumb goods-form" >\
		<div>\
			<div class="block-control goods-category">\
				<ul class="ul-form-5">\
					<li>\
						块类型:商品分类\
						<label></label>\
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
						<button class="btn block-delete " >删除</button>\
					</li>\
				</ul>\
			</div>\
			<div class="goods-category-whole">\
				<ul class="ul-form-6">\
					<li>\
						<label>标题:</label>\
						<input type="text" name="category_title" maxlength="8" value="{{=it.element_list?it.element_list[0].category_title:""}}">\
					</li>\
					<li>\
						<label>副标题:</label>\
						<input type="text" name="sub_category_title" maxlength="28" value="{{=it.element_list?it.element_list[0].sub_category_title:""}}">\
					</li>\
					<li>\
						<label>商品品类:</label>\
						<select name="sub_category_list"><option value="0">请选择品类</option>\
						{{~it.categoryList :value:index}}\
                            {{? typeof(value) != "undefined"}}\
					        <option value="{{=value.id}}"  >{{=value.cate_name}}</option>\
					        {{?}}\
			            {{~}}\
						</select>\
					</li>\
					<li>\
						<button class="btn btn-add-item" type="button" data-toggle="modal" data-target="#myModal">增加商品</button>\
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
    ,tms:'<div class="form-search breadcrumb tms-block">\
		<div>\
			<div class="block-control goods-category">\
				<ul class="ul-form-5">\
					<li>\
						块类型:TMS\
						<label></label>\
					</li>\
					<li>\
						<label>顺序：</label>\
						<input type="text" class="input-mini" onkeyup="return value=mainTpl.getNumberValue(this.value)"  name="block_order" value="{{=it.block_order||"99"}}">\
					</li>\
					<li>\
						<label>高度：</label>\
						<input type="text" onkeyup="return value=mainTpl.getNumberValue(this.value)"  class="disabled  input-mini input-disabled" disabled  name="height" value="{{=it.height||"360"}}">\
					</li>\
					<li style="float: right">\
						<button class="btn block-delete" >删除</button>\
					</li>\
				</ul>\
			</div>\
			<div class="goods-category-whole">\
				<ul class="ul-form-6">\
					<li>\
						<label>TMS:</label>\
						<input type="text" name="tms_name"  required value="{{=it.tms_name||""}}">\
					</li>\
				</ul>\
			</div>\
	</div>\
		</div>'
	,options:'{{~it :value:index}}\
					<option value="{{=value.id}}">{{=value.name}}------{{=mainTpl.getPublishStatus(value.publish_status)}}</option>\
			{{~}}'
    ,cateOptions:'{{~it :value:index}}\
    {{? typeof(value) != "undefined"}}\
					<option value="{{=value.id}}">{{=value.cate_name}}</option>\
					{{?}}\
			{{~}}'
    ,goodsBox:'   {{~it.data :it:index}}\
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
    ,itemTr:'{{~it :it:index}}<tr item_id="{{=it.id}}" seller_id="{{=it.seller_id}}">\
<td><img src="{{=it.icon_url}}" width="150" height="150" /></td>\
<td>{{=it.item_name}}</td>\
<td>{{=it.market_price/100}}元</td>\
<td><input type="text" value="99" onkeyup="return value=mainTpl.getNumberValue(this.value)" name="item_order" class="input-mini" maxlength="3"> </td>\
<td><a href="javascript:;" data-id="{{=it.id}}" class="delete-goods">删除</a> </td>\
</tr>{{~}}'


    ,getItemStatus :function(status){

        switch (status){
            case 4:
                return '上架';
            case 5:
                return '下架';

        }
    },getItemType:function(type){
       switch(type){
           case 1:
               return '普通商品';
       }
    },getItemInfo:function(info){
        info.item_desc = null;
        return  encodeURIComponent(JSON.stringify(info))
    },getPublishStatus:function(status){
            return status == 1?'已发布':'未发布'
},getNumberValue:function(val){
    return val.replace(/\D/g,'');
}


}