package com.hhhhhh.common.consts;

public interface Const {

    /** 判断代码：是 */
    String TRUE = "1";

    /** 判断代码：否 */
    String FALSE = "0";

    /** 通用字符集编码 */
    String CHARSET_UTF8 = "UTF-8";

    /** 中文字符集编码 */
    String CHARSET_CHINESE = "GBK";

    /** 英文字符集编码 */
    String CHARSET_LATIN = "ISO-8859-1";

    /** 根节点ID */
    String ROOT_ID = "root";

    /** NULL字符串 */
    String NULL = "null";

    /** 日期格式 */
    String FORMAT_DATE = "yyyy-MM-dd";

    /** 日期时间格式 */
    String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    /** 时间戳格式 */
    String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
    
    /** JSON成功标记 */
    String JSON_SUCCESS = "success";

    /** JSON数据 */
    String JSON_DATA = "data";

    /** JSON数据列表 */
    String JSON_ROWS = "rows";
    
    /** JSON总数 */
    String JSON_TOTAL = "total";

    /** JSON消息文本 */
    String JSON_MESSAGE = "message";

    // 订单相关
    /** 货到付款 */
    Integer CASH_ON_DELIVERY = 1;
    /** 在线支付 */
    Integer ONLINE_PAYMENT = 2;
    /** 微信支付 */
    Integer WEIXIN_PAYMENT = 3;
    /** 支付宝支付 */
    Integer ALIPAY_PAYMENT = 4;

    /** 未付款 */
    Integer NON_PAYMENT = 1;
    /** 已付款 */
    Integer PAYMENT = 2;
    /** 未发货*/
    Integer NON_SHIPMENTS = 3;
    /** 已发货 */
    Integer SHIPMENTS = 4;
    /** 交易成功 */
    Integer SUCCESSFUL = 5;
    /** 交易关闭 */
    Integer CLOSE = 6;

    /**未评价*/
    Integer EVALUATE_NO = 7;
    /**已评价*/
    Integer EVALUATE_YES = 8;
    /**cookie 用户登录token*/
    String TOKEN_LOGIN = "_xlg";
    /**cookie 购物车 key*/
    String CART_KEY = "_xca";

    // Dubbox版本
    String HHHHHH_REDIS_VERSION = "1.0.0";
    String HHHHHH_SSO_VERSION = "1.0.0";
    String HHHHHH_NOTIFY_VERSION = "1.0.0";
    String HHHHHH_ADMIN_VERSION = "1.0.0";
    String HHHHHH_SEARCH_VERSION = "1.0.0";
    String HHHHHH_PORTAL_VERSION = "1.0.0";
    String HHHHHH_ORDER_VERSION = "1.0.0";
    String HHHHHH_CART_VERSION = "1.0.0";
    String HHHHHH_ITEM_VERSION = "1.0.0";
}
