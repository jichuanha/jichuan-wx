package com.hzkans.crm.common.constant;

public enum ResponseEnum {
    /**
     * 请求处理成功
     */
    REQUEST_SUCESS(10000, 0, "请求处理成功"),

    /**
     * 参数类异常
     */
    P_E_PARAM_ISNULL(20001, 0, "参数不能为空"),
    P_E_PARAM_INVALID(20002, 0, "参数非法"),
    P_E_USER_NOT_LOGIN(20003, 0, "user_session_key is null,you need login."),
    P_E_ACTIVITY_TIME_INVALID(20004, 0, "活动时间不合法"),
    P_E_SIZE_OF_GIFT_ITEM_OUT_OF_LIMIT(20005, 0, "赠品只能添加一个"),
    P_E_PASSWORD_CONFIRM_ERROR(20006, 0, "密码验证错误"),
    P_E_OLD_PASSWORD_ERROR(20007, 0, "旧密码错误"),
    P_E_DUMPLICATE_RULE(20008, 0, "重复的签到规则"),
    P_E_SIZE_OF_COUPON_OUT_OF_LIMIT(20005, 0, "优惠券不能超过 10 张"),

	
	
    /**
     * 系统类异常
     */
    S_E_SERVICE_ERROR(30001, 0, "系统异常，请稍后重试"),

    S_E_OSS_FILE_ERROR(30002, 0, "文件下载异常，请稍后重试"),

    S_E_OSS_FILE_NOT_EXISTS_ERROR(30003, 0, "文件不存在"),

    S_E_DEPENDENT_SERVICE_EXCEPTION(30004,0,"底层依赖的服务异常"),


    /**
     * 业务类异常
     */
    B_E_SESSION_TIMEOUT(40000, 0, "登录已过期，请重新登录"),
    B_E_PASSWORD_INVALID(40001, 0, "用户名或是密码错误"),
    B_E_RECORD_NOT_EXIST(41000, 0, "请求记录不存在"),
    B_E_VERYFY_CODE_TIMEOUT(40002, 0, "验证码已失效"),
    B_E_VERYFY_CODE_INVALID(40003, 0, "验证码错误"),
    B_E_DOMAIN_NAME_INVALID(40004, 0, "域名无效"),
    B_E_API_LOGIN_NOT_SUPPORT(40005, 0, "不支持该第三方登入方式"),
    B_E_ACTIVITY_ITEM_NOT_UNIQUE(40006, 0, "同一个商品不能同时参与多个优惠活动"),
    B_E_OVERLAPPING_ACTIVITIES_OUT_OF_LIMITATION(40007, 0, "同时进行的优惠活动不能超过三个"),
    B_E_USER_AUTH_INFO_IS_EXIST(40008, 0, "用户认证信息已经存在"),
    B_E_ID_CARD_NO_HAS_BEEN_AUTHED(40009, 0, "身份证号码已经被注册过了"),
    B_E_MOBILE_ALREADY_EXISTS(40010, 0, "手机号已经被注册过"),
    B_E_ID_CARD_ERROR(40011, 0, "身份证信息非法"),
    B_E_PERMISSION_REJECT(40012, 0, "没有访问权限"),
    B_E_MOBILE_FORMAT_ERROR(40013, 0, "手机号格式错误"),
    B_E_SHOP_NAME_EXISTS(40014, 0, "店铺名称已经存在"),
    B_E_PASSWORD_LENGTH_ERROR(40015, 0, "密码长度错误"),
    B_E_SHOP_IS_FREEZEN(40016, 0, "您的店铺已经被冻结，请联系商场管理员"),
    B_E_SHOP_CREATE_ERROR(40017, 0, "您的店铺创建失败"),
    B_E_THERE_ARE_SUB_CATE_IN_THIS_CATE(40018, 0, "该类目下存在子类目，不允许直接删除"),
    B_E_THERE_ARE_ITEM_IN_THIS_CATE(40019, 0, "该类目下存在商品，不允许直接删除"),
    B_E_CONFIG_NOT_ALLOWED(40020,0,"当前配置不允许这样的操作"),
    B_E_MOBILE_VERIFY_CODE_AGAIN(40021, 0, "验证码频繁发送"),
    B_E_APP_INFO_NOT_EXISTS(40022, 0, "AppInfo 不能存在"),
    B_E_UPOLAD_FILE_ERROR(40023,0,"文件上传失败"),
    B_E_RESULT_IS_NULL(40024,0,"结果集为空"),
    B_E_VERYFY_CODE_IS_NULL(40025,0,"请输入验证码"),
    B_E_USER_AUTH_INFO_NOT_EXIST(40026,0,"该登录名还没有注册"),
    B_E_VERIFY_LINK_NOT_NEW(40027,0,"不是最新的验证链接"),
    B_E_VERIFY_LINK_EXPIRES(40028,0,"链接已经过期,请重新申请找回密码"),
    B_E_VERIFY_LINK_IS_ERROE(40029,0,"链接不正确"),
    B_E_PASSWORD_IS_NULL(40030,0,"密码为空"),
    B_E_SEND_MAIL_ERROR(40031,0,"发送邮件失败"),
    B_E_MESSAGE_IS_NULL(40032,0,"审核信息不能为空"),
    B_E_NOT_FIND_ACT(40032,0,"未找到对应的活动"),
    B_E_NOT_FIND_JOIN(40032,0,"未找到对应的参加信息"),
    B_E_TYPE_ERROR(40033,0,"只能导入 xls,xlsx 结尾的表格"),
    B_E_TYPE_DATA_ERROR(40034,0,"导入内容格式有误"),
    B_E_TABLE_NOT_EXIST(40034,0,"表格不存在"),
    B_E_TABLE_STATUS_ERROR(40035,0,"表格不在待发布状态"),
    B_E_FILE_NOT_EXIST(40035,0,"表格不在待发布状态"),
    B_E_DOWNLOAD_ERROR(40036,0,"下载异常"),
    B_E_MENU_EMPTY(40037,0,"菜单列表为空"),
    B_E_FIRST_MENU_ERROR(40038,0,"一级菜单超出3个限制数"),
    CONTENT_IS_NULL(40039, 0, "文本内容不能为空"),
    B_E_SECOND_MENU_ERROR(40038,0,"二级菜单超出5个限制数"),
    B_E_MATE_TYPE_ERROR(40039,0,"传递的类型异常"),
    /**
     * 添加失败
     */
    B_E_FAILED_TO_ADD(41001, 0, "创建失败"),
    B_E_FAILED_TO_DELETE(41002, 0, "删除失败"),
    B_E_ONLY_ONE_RULE_MODULE_BE_ON(41003, 32001, "同时只能开启一个发放规则"),
    B_E_THE_GRANT_RULE_DOES_NOT_EXIST(41004, 32004, "财富发放规则不存在"),
    B_E_EXCHANGE_RATE_CANNOT_BE_ZERO(41005, 0, "兑换比率不能为0"),
    COMPONENT_NOT_EXIST(41006, 0,"component not exist"),
    DATE_ALREADY_EXIST(41007, 0,"数据已经存在,请勿重复提交!"),
    DATEBASE_SAVE_ERROR(41008,0,"数据库保存失败"),
    B_E_MODIFY_ERROR(41009, 0, "修改失败"),
    B_E_ALERADY_EXIST(41010, 0, "微信公众号名称已存在"),
    DATEBASE_QUERY_ERROR(41011,0,"数据库查询失败"),
    B_E_FAILED_TO_GET(41012, 0, "活动查询失败"),
    B_E_REQUIRED_NOT_FILLED(41013, 0, "有必填选项未填"),
    B_E_ACTIVITY_EXIST(41014, 0, "活动已存在"),
    B_E_UPDATE_STATUS_FAIL(41015, 0, "更改状态失败"),
    B_E_SHOP_EXIST(41016, 0, "店铺已存在"),
    B_E_PER_MUST_LOWER_TOTAL(41017, 0, "单笔金额不能大于返利总额"),
    B_E_ACTIVITY_TYPE_EXIST(41018, 0, "活动类型已存在"),
    B_E_BING_EXIST(41019, 0, "此活动与此公众号已绑定"),
    B_E_RATE_IS_BIGGER(41020, 0, "比例总和超过1，请重新设置"),
    B_E_RATE_IS_SMALLER(41021, 0, "比例总和不到1，请重新设置"),

    /**
     * 商品模板文件类异常
     */
    B_E_SHEET_NOT_EXIST(42001, 0, "页面不存在"),
    B_E_SKU_CAN_NOT_BE_NULL(42002, 0, "sku不能为空"),
    B_E_TITLE_ILEGAL(42003, 0, "标题不合规"),
    B_E_MARKET_PRICE_LESS_THAN_ZERO(42004, 0, "市场价小于0"),
    B_E_PROMOTION_PRICE_LESS_THAN_ZERO(42005, 0, "促销价小于0"),
    B_E_STOCK_NUM_LESS_THAN_ZERO(42006, 0, "库存量小于0"),
    B_E_BRAND_NOT_EXIST(42007, 0, "品牌不存在"),
    B_E_SKU_VALUE_ERROR(42008, 0, "sku值错误"),
    B_E_BASE_PROPERTY_VALUE_NOT_SAME(42009, 0, "基本属性不一致"),
    B_E_STOCK_NUM_NOT_LONG(42010, 0, "库存量不是整数"),
    B_E_PRICE_FORMAT_ERROR(42011, 0, "金额格式错误"),
    B_E_BASE_PROPERTY_ERROR(42012,0 ,"基本属性值错误" );


    private int code;
    private int compareCode;
    private String msg;

    private ResponseEnum(int code, int compareCode, String msg) {
        this.code = code;
        this.compareCode = compareCode;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCompareCode() {
        return compareCode;
    }
}
