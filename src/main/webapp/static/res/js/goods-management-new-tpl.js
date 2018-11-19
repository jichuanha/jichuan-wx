var self_tpl = {
	 //品牌列表
	'brand_key': '<option value="">请选择品牌</option>{{~it.data:val:inx}}<option value="{{=val.id}}">{{=val.brand_name}}</option>{{~}}',
	//类目列表
	'cate_key': '<option value="">请选择类目</option>{{~it.data:val:inx}}<option value="{{=val.id}}">{{=val.cate_name}}</option>{{~}}',
	//角标
	'markTpl': '{{~ it.data.data:item }}\
		<li>\
			<div class="img" data-id="{{= item.id }}" data-url="{{= item.icon_url }}"><img src="{{= item.icon_url }}"></div>\
			<p class="name">{{= item.icon_name }}</p>\
		</li>\
		{{~}}',
	'lableTpl': '{{~ it.data.data:item }}\
		<li>\
			<div class="img" data-id="{{= item.id }}" data-url="{{= item.icon_url }}"><img src="{{= item.icon_url }}"></div>\
			<p class="name">{{= item.label_name }}</p>\
		</li>\
		{{~}}',
	'goodsTpl': '<tr class="parent-tr" sku-id="{{= it.skuId }}"  spu-id="{{= it.spuId}}" item_sku_sn="{{= it.item_sku_sn}}" sellerId="{{= it.sellerId}}">\
					<td>\
						<div class="name">\
							<img src="{{= it.img }}">\
							<p>{{= it.name }}</p>\
						</div>\
					</td>\
					<td class="price">\
						<p>现价：￥<span>{{? it.price }}{{= it.price}}{{??}}-{{?}}</span></p>\
						<p>原价：￥<span>{{? it.oriPrice }}{{= it.oriPrice}}{{??}}-{{?}}</span></p>\
					</td>\
					<td class="num">\
						<p>{{? it.attribute }}{{= it.attribute}}{{??}}-{{?}}</p>\
					</td>\
					<td><i class="icon-remove icon-large delete-icon"></i></td>\
				</tr>',
	'editTpl': '<div class="container whole">\
					<div class="head-tips">\
						<span>创建的限时购，买家购买相应商品时，满足条件自动享受活动价格。</span>\
						<span class="remove"><i class="icon-remove"></i></span>\
					</div>\
					<div class="content">\
						<form class="form-horizontal" role="form">\
							<div class="form-group">\
								<label for="campaign-name" class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动名称：</label>\
								<div class="col-xs-5">\
									<input type="text" class="form-control" id="campaign-name" placeholder="" maxlength="50" data-campaign_id="">\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动时间：</label>\
								<div class="col-xs-3">\
									<input type="text" class="form-control"  placeholder="" id="campaign-start-time">\
								</div>\
								<div class="col-xs-1 col-to">\
									<span class="form-control to" >-</span>\
								</div>\
								<div class="col-xs-3">\
									<input type="text" class="form-control" placeholder="" id="campaign-end-time">\
								</div>\
							</div>\
							<div class="form-group" style="display: none;">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>发布时间：</label>\
								<div class="col-xs-3">\
									<input type="text" class="form-control"  placeholder="" id="publish-time">\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动角标：</label>\
								<div class="col-xs-3">\
									<div class="img-container" id="add-icon-btn">\
										<i class="icon-plus"></i>\
									</div>\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label">与优惠券叠加：</label>\
								<div class="col-xs-1">\
									<div class="radio">\
										<label>\
											<input type="radio" name="coupon-radio" value="0" checked>是\
										</label>\
									</div>\
								</div>\
								<div class="col-xs-1">\
									<div class="radio">\
										<label>\
											<input type="radio" name="coupon-radio"  value="1">否\
										</label>\
									</div>\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动商品：</label>\
								<div class="col-xs-2 add-link-goods-col">\
									<div class="form-control" id="add-goods-btn">\
										<i class="icon-plus"></i>添加关联商品\
									</div>\
								</div>\
							</div>\
							<div class="form-group">\
								<label class="col-xs-2">&nbsp;</label>\
								<div class="col-xs-3">\
									<button type="button" class="btn btn-success" id="save-selected-btn">保存</button>\
									<button type="button" class="btn btn-default" id="cancel-selected-btn">取消</button>\
									<button type="button" class="btn btn-success" id="publish-selected-btn" disabled="">发布</button>\
								</div>\
							</div>\
							<div class="form-group selected-goods" id="selected-goods" >\
								<table class="table parent-table">\
									<thead>\
										<tr>\
											<th>名称</th>\
											<th>SKU</th>\
											<th>操作</th>\
										</tr>\
									</thead>\
									<tbody id="goods-info-body">\
									</tbody>\
								</table>\
							</div>\
						</form>\
					</div>\
				</div>',
	'img_select_template': '{{ for(var i=0; i < it.length; i++) { }}\
							    <tr class="j-goods-box">\
							        <td class="tc"><span><img src="{{=it[i].file_url}}" style="width: 50px;height: 50px;"></span></td>\
							        <td class="tc"><span class="popup-g-name">{{=it[i].file_name}}</span></td>\
							        <td class="tc"><span>{{=it[i].create_time}}</span></td>\
							        <td class="tc"><a src="javascript:void(0);" class="img-select">选择</a></td>\
							    </tr>\
							{{ } }}',
	'j_category': '{{ if( it.category_value == 1 ){ }}\
						<option>--请选择一级类目--</option>\
						{{ }else{ }}\
						<option>--请选择二级类目--</option>\
						{{ } }}\
						{{~it.items:item}}\
						<option value="{{= item.id }}" data-name="{{= item.cate_name }}">{{= item.cate_name }}</option>\
						{{~}}',
	'j_template_session':'<div class="per-session">\
							<table class="table table-striped table-bordered table-hover parent-table">\
								<thead>\
									<tr>\
										<th>捆绑商品</th>\
										<th>价格</th>\
										<th>属性</th>\
										<th>操作</th>\
									</tr>\
								</thead>\
								<tbody class="relative-goods">\
								</tbody>\
							</table>\
							<div class="form-group">\
								<div class="col-xs-2 add-link-goods-col">\
									<div class="form-control" id="add-goods-btn">\
										<i class="icon-plus"></i>添加关联商品\
									</div>\
								</div>\
							</div>\
						</div>',
	'j_template_categary':'{{ for(var i=0; i < it.length; i++) { }}\
						    	<p data-parent_id="{{= it[i].parent_id }}" data-id="{{= it[i].id }}" >{{= it[i].cate_name}}</p>\
						    {{ } }}',
	'j_template_sku':'{{~it:item:index}}\
						<tr>\
							<td class="tc" good-img="{{= item.image_url }}"><img src="{{= item.image_url }}"></td>\
							<td class="item_sku_sn"><span>{{= item.item_sku_sn }}</span></td>\
							<td class="sku-id"><span>{{= item.sku_id }}</span></td>\
							<td class="attrbute"><span>{{? item.sku_code }}{{= item.sku_code}}{{??}}-{{?}}</span></td>\
							<td class="ori-price"><span>{{= Number(item.origin_price/100).toFixed(2) }}</span></td>\
							<td class="price"><span>{{= Number(item.price/100).toFixed(2) }}</span></td>\
							<td class="num"><span>{{= item.num }}</span></td>\
							<td><a href="javascript:;" class="j-s-sku" data-info="{{= encodeURIComponent(JSON.stringify(item)) }}">选择</a></td>\
						</tr>\
					{{~}}',
	'j_template_goods':'{{~it.items:item:index}}\
							<tr class="j-goods-box" >\
								<td class="tc"><span><img src="{{= item.icon_url }}" style="width: 50px;height: 50px;"></span></td>\
								<td><span class="popup-g-name">{{= item.item_name }}</span></td>\
								<td class="tc"><span>{{= item.gmt_created }}</span></td>\
								<td class="tc">\
									{{ if( item.item_status == 4 ){ }}\
										<span class="green">{{? item.status_name }}{{= item.status_name}}{{??}}-{{?}}</span>\
									{{ }else if( item.item_status == 1 ){ }}\
										<span class="red">{{? item.status_name }}{{= item.status_name}}{{??}}-{{?}}</span>\
									{{ }else{ }}\
										<span class="black">{{? item.status_name }}{{= item.status_name}}{{??}}-{{?}}</span>\
									{{ } }}\
								</td>\
								<td class="tc">{{? item.higo_mark == 0 }}国内{{??}}跨境{{?}}</td>\
								{{ if( it.type && it.type == 1 ){ }}\
								<td><a class="j-open-sku" data-id="{{= item.id }}" data-name="{{= item.item_name }}" data-sid="{{= item.seller_id }}" style="cursor: pointer">展开并添加</a></td>\
								{{ }else{ }}\
								<td><a class="select-goods select-group-goods j-select-g" data-status="0" data-id="{{= item.id }}" data-info="{{= encodeURIComponent(JSON.stringify(item)) }}">展开并添加</a></td>\
								{{ } }}\
							</tr>\
							<tr class="j-sku-box show-or-hidden">\
								<td colspan="6">\
									<table class="ui-table table table-striped table-bordered table-hover">\
										<thead>\
										<tr>\
											<th>图片</th>\
											<th>商品编码</th>\
											<th>sku_id</th>\
											<th class="attrbute">sku属性</th>\
											<th class="ori-price">原价</th>\
											<th>现价</th>\
											<th>库存</th>\
											<th>操作</th>\
										</tr>\
										</thead>\
										<tbody id="box-{{= item.id }}" good-name="{{= item.item_name }}" spu-id="{{= item.id }}" seller-id="{{= item.seller_id }}">\
										</tbody>\
									</table>\
								</td>\
							</tr>\
						{{~}}'
}