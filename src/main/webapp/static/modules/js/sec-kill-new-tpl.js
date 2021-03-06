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
	'goodsTpl': '<tr class="parent-tr" sku-id="{{= it.skuId }}"  spu-id="{{= it.spuId}}" item_sku_sn="{{= it.item_sku_sn}}" sellerId="{{= it.sellerId}}">\
					<td>\
						<div class="name">\
							<img src="{{= it.img }}">\
							<p>{{= it.name }}</p>\
							<p>{{= it.attribute }}</p>\
						</div>\
					</td>\
					<td class="price">\
						<p>￥&nbsp;<input class="current-num current-price" type="text"/></p>\
						<p>现价：￥<span>{{? it.price }}{{= it.price}}{{??}}-{{?}}</span></p>\
					</td>\
					<td class="num">\
						<p>&nbsp;<input class="current-num store-num" stoer-num="{{= it.num}}" type="text"/></p>\
						<p>库存件数：<span>{{? it.num }}{{= it.num}}{{??}}-{{?}}</span></p>\
					</td>\
					<td><i class="icon-remove icon-large delete-icon"></i></td>\
				</tr>',
	'editTpl':  '{{~ it:item:index }}\
				<div class="head-tips">\
					<span>创建的限时购，买家购买相应商品时，满足条件自动享受活动价格。</span>\
					<span class="remove"><i class="icon-remove"></i></span>\
				</div>\
				<div class="content">\
					<form class="form-horizontal" role="form">\
						<div class="form-group">\
							<label for="campaign-name" class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动名称：</label>\
							<div class="col-xs-5">\
								<input type="text" class="form-control" id="campaign-name" placeholder="" maxlength="50" data-campaign_id="{{= item.id }}">\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动时间：</label>\
							<div class="col-xs-3">\
								<input type="text" class="form-control"  placeholder="" id="campaign-start-time" value="{{= item.start_time }}">\
							</div>\
							<div class="col-xs-1 col-to">\
								<span class="form-control to" >-</span>\
							</div>\
							<div class="col-xs-3">\
								<input type="text" class="form-control" placeholder="" id="campaign-end-time" value="{{= item.end_time }}">\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>发布时间：</label>\
							<div class="col-xs-3">\
								<input type="text" class="form-control"  placeholder="" id="publish-time" value="{{= item.publish_time }}">\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动角标：</label>\
							<div class="col-xs-3">\
								<div class="img-container" id="add-icon-btn">\
									<img src="{{= item.activity_tag }}" class="img-circle">\
								</div>\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label">与优惠券叠加：</label>\
							<div class="col-xs-1">\
								<div class="radio">\
									<label>\
										<input type="radio" name="coupon-radio" value="0" {{? item.isCoupon}} checked {{?}}>是\
									</label>\
								</div>\
							</div>\
							<div class="col-xs-1">\
								<div class="radio">\
									<label>\
										<input type="radio" name="coupon-radio"  value="1" {{? !item.isCoupon}} checked {{?}}>否\
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
						<div class="form-group selected-goods" id="selected-goods">\
							<table class="table parent-table">\
								<thead>\
									<tr>\
										<th>名称</th>\
										<th>限购</th>\
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
				{{~}}'
}