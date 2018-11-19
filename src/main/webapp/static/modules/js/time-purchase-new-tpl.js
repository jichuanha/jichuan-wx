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
	'goodsTpl': '{{~ it:item:index }}\
					<tr class="parent-tr" data-item_id="{{= item.id }}">\
						<td>\
							<div class="name" style="margin-top: {{= (item.skus.length*80+20-134)/2 }}px;">\
								<img src="{{= item.icon_url }}">\
								<p>{{= item.item_name }}</p>\
								<p>规格型号：{{= item.skuCode }}</p>\
								<p>贸易类型：{{? item.higo_mark == 0 }}国内商品{{?? item.higo_mark == 1 }}跨境商品{{?}}</p>\
								{{? item.item_status == 4 }}\
									<p>当前状态：下架</p>\
								{{?? item.item_status == 5 }}\
									<p>当前状态：上架,预售</p>\
								{{?? item.item_status == 7 }}\
									<p>当前状态：上架</p>\
								{{?}}\
							</div>\
						</td>\
						<td>\
							<div style="margin-top: {{= (item.skus.length*80+20-46)/2 }}px;">\
								<input type="text" class="limit-number" data-stock_num="{{= item.stock_num }}"/>\
								<p>0为不限</p>\
								<p>当前库存:{{= item.stock_num }}</p>\
							</div>\
						</td>\
						<td colspan="1">\
							<table class="child-table">\
								<thead>\
									<tr>\
										<th class="tc">规格</th>\
										<th class="tc">价格</th>\
									</tr>\
								</thead>\
								<tbody>\
									{{~ item.skus:skuItem }}\
										<tr class="child-tr" data-sku_id="{{= skuItem.sku_id }}">\
											<td>{{= skuItem.sku_code }}</td>\
											<td>\
												<input type="text" class="limit-price"/>\
												<p>现售价：</p>\
												<p>￥{{= skuItem.price }}</p>\
											</td>\
										</tr>\
									{{~}}\
								</tbody>\
							</table>\
						</td>\
						<td><i class="icon-remove icon-large delete-icon" style="margin-top: {{= (item.skus.length*80+20-20)/2 }}px;"></i></td>\
					</tr>\
				{{~}}',
	'goodsEditTpl': '{{~ it:item:index }}\
						<tr class="parent-tr" data-item_id="{{= item.id }}">\
							<td>\
								<div class="name" style="margin-top: {{= (item.list.length*80+20-134)/2 }}px;">\
									<img src="{{= item.icon_url }}">\
									<p>{{= item.item_name }}</p>\
									<p>规格型号：{{= item.skuCode }}</p>\
									<p>贸易类型：{{? item.higo_mark == 0 }}国内商品{{?? item.higo_mark == 1 }}跨境商品{{?}}</p>\
									{{? item.item_status == 4 }}\
										<p>当前状态：下架</p>\
									{{?? item.item_status == 5 }}\
										<p>当前状态：上架,预售</p>\
									{{?? item.item_status == 7 }}\
										<p>当前状态：上架</p>\
									{{?}}\
								</div>\
							</td>\
							<td>\
								<div style="margin-top: {{= (item.list.length*80+20-46)/2 }}px;">\
									<input type="text" class="limit-number" value="{{= item.goods_quantity }}" data-stock_num="{{= item.stock_num }}"/>\
									<p>0为不限</p>\
									<p>当前库存:{{= item.stock_num }}</p>\
								</div>\
							</td>\
							<td colspan="1">\
								<table class="child-table">\
									<thead>\
										<tr>\
											<th class="tc">规格</th>\
											<th class="tc">价格</th>\
										</tr>\
									</thead>\
									<tbody>\
										{{~ item.list:skuItem }}\
											<tr class="child-tr" data-sku_id="{{= skuItem.sku_id }}">\
												<td>{{= skuItem.sku_code }}</td>\
												<td>\
													<input type="text" value="{{= skuItem.goods_price }}" class="limit-price"/>\
													<p>现售价：</p>\
													<p>￥{{= skuItem.price }}</p>\
												</td>\
											</tr>\
										{{~}}\
									</tbody>\
								</table>\
							</td>\
							<td><i class="icon-remove icon-large delete-icon" style="margin-top: {{= (item.list.length*80+20-20)/2 }}px;"></i></td>\
						</tr>\
					{{~}}',
	'editTpl':  '<div class="head-tips">\
					<span>创建的限时购，买家购买相应商品时，满足条件自动享受活动价格。</span>\
					<span class="remove"><i class="icon-remove"></i></span>\
				</div>\
				<div class="content">\
					<form class="form-horizontal" role="form">\
						<div class="form-group">\
							<label for="campaign-name" class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动名称：</label>\
							<div class="col-xs-5">\
								<input type="text" class="form-control" id="campaign-name" placeholder="" maxlength="50" data-campaign_id="{{= it.id }}" value="{{= it.activity_name }}">\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动时间：</label>\
							<div class="col-xs-3">\
								<input type="text" class="form-control"  placeholder="" id="campaign-start-time" value="{{= it.start_time }}">\
							</div>\
							<div class="col-xs-1 col-to">\
								<span class="form-control to" >-</span>\
							</div>\
							<div class="col-xs-3">\
								<input type="text" class="form-control" placeholder="" id="campaign-end-time" value="{{= it.end_time }}">\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>发布时间：</label>\
							<div class="col-xs-3">\
								<input type="text" class="form-control"  placeholder="" id="publish-time" value="{{= it.publish_time }}">\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label"><i class="must">*&nbsp;</i>活动角标：</label>\
							<div class="col-xs-3">\
								<div class="img-container" id="add-icon-btn">\
									<img src="{{= it.activity_tag }}" class="img-circle">\
								</div>\
							</div>\
						</div>\
						<div class="form-group">\
							<label class="col-xs-2 control-label">与优惠券叠加：</label>\
							<div class="col-xs-1">\
								<div class="radio">\
									<label>\
										<input type="radio" name="coupon-radio" value="0" {{? it.isCoupon}} checked {{?}}>是\
									</label>\
								</div>\
							</div>\
							<div class="col-xs-1">\
								<div class="radio">\
									<label>\
										<input type="radio" name="coupon-radio"  value="1" {{? !it.isCoupon}} checked {{?}}>否\
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
				</div>'
}