package com.hhhhhh.order.servant;

import com.hhhhhh.common.consts.Const;
import com.hhhhhh.common.pojo.JsonResult;
import com.hhhhhh.mapper.TbOrderItemMapper;
import com.hhhhhh.mapper.TbOrderMapper;
import com.hhhhhh.order.client.OrderService;
import com.hhhhhh.pojo.*;
import com.hhhhhh.service.RedisService;
import com.hhhhhh.common.utils.FastJsonConvert;
import com.hhhhhh.common.utils.IDUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单Service
 */
@Service(version = Const.HHHHHH_ORDER_VERSION)
@Transactional
public class OrderServant implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServant.class);

    @Resource
    private RedisService redisService;

    @Resource
    private TbOrderItemMapper orderItemMapper;

    @Resource
    private TbOrderMapper orderMapper;

    @Value("${redisKey.prefix.cart_order_info_profix}")
    private String CART_ORDER_INFO_PROFIX;
    @Value("${redisKey.prefix.cart_order_index_profix}")
    private String CART_ORDER_INDEX_PROFIX;
    @Value("${redisKey.prefix.cart_info_profix}")
    private String CART_INFO_PROFIX;

    @Override
    public JsonResult generateOrder(String userCookieValue, String cartCookieValue, Integer addrId, Integer noAnnoyance, Integer paymentType, String orderId, String shippingName) {

//        JsonResult result = userService.token(userCookieValue, "");
//        if (result.getData() == null) {
//            logger.error("用户没有登录!");
//            return JsonResult.build(400, "系统错误!");
//        }

//        String data = (String) result.getData();
//        TbUser user = FastJsonConvert.convertJSONToObject(data, TbUser.class);

//        String userId = user.getId() + "";
        String userId = "";
        userId = "0000" + userId;
        userId = userId.substring(userId.length() - 4, userId.length());

        String key1 = CART_ORDER_INFO_PROFIX + orderId;
        String key2 = CART_ORDER_INDEX_PROFIX + orderId;
        String key3 = CART_INFO_PROFIX + cartCookieValue;

        orderId = paymentType.toString() + orderId + userId;

        final TbOrder order = new TbOrder();
        //设置订单id
        order.setOrderId(orderId);
        //设置用户id
        order.setUserId(1L);
        //设置地址id
        order.setAddrId(Long.valueOf(addrId));
        //设置支付类型
        order.setPaymentType(paymentType);
        //设置邮费
        order.setPostFee("0");
        //设置状态
        order.setStatus(Const.NON_PAYMENT);
        //设置物流名称
        order.setShippingName(shippingName);
        //设置退换无忧
        order.setNoAnnoyance(noAnnoyance + "");
        //设置服务费
        order.setServicePrice("0");
        //设置返现
        order.setReturnPrice("0");
        //设置没有评价
        order.setBuyerRate(Const.EVALUATE_NO);
        //设置订单创建时间
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        Long payment = 0L;
        List<CartInfo> cartInfos = null;
        List<CartInfo> cartInfoAll = null;
        String[] split = null;

        try {

            String cartInfo = redisService.get(key1);
            String cartIndex = redisService.get(key2);
            String cartInfoListString = redisService.get(key3);

            if (StringUtils.isBlank(cartInfo) || StringUtils.isBlank(cartIndex) || StringUtils.isBlank(cartInfoListString)) {
                return JsonResult.build(400, "系统错误!");
            }

            cartInfos = FastJsonConvert.convertJSONToArray(cartInfo, CartInfo.class);
            cartInfoAll = FastJsonConvert.convertJSONToArray(cartInfoListString, CartInfo.class);
            split = cartIndex.split(",");

        } catch (Exception e) {
            logger.error("Redis 服务出错!", e);
        }

        // 保存订单项️
        if (cartInfos.size() > 0) {
            for (int i = 0; i < cartInfos.size(); i++) {
                CartInfo cartInfo = cartInfos.get(i);

                String orderItemId = IDUtils.genOrderItemId();
                TbOrderItem orderItem = new TbOrderItem();
                orderItem.setId(orderItemId);
                orderItem.setOrderId(orderId);
                orderItem.setItemId(cartInfo.getId() + "");
                orderItem.setTitle(cartInfo.getName());
                orderItem.setNum(cartInfo.getNum());
                orderItem.setPicPath(cartInfo.getImageUrl());
                orderItem.setPrice(cartInfo.getPrice());
                orderItem.setTotalFee(cartInfo.getSum());
                orderItem.setWeights(cartInfo.getWeight() + "");
                // 记录日志
                orderItemMapper.insert(orderItem);

                logger.info("保存订单项,订单:" + orderItem.toString());
                payment += cartInfo.getSum();
            }
        }

        //设置总金额
        order.setPayment(payment + noAnnoyance + "");
        //设置总重
        //order.setTotalWeight();
        // 保存订单
        orderMapper.insert(order);


        // 移除购物车选中商品️
        if (cartInfoAll.size() >= split.length) {
            for (int i = split.length - 1; i >= 0; i--) {
                cartInfoAll.remove(Integer.parseInt(split[i]));
            }
            logger.debug("移除购物车购买商品！数量:" + split.length);
        } else {
            logger.error("订单项数量小于和index数量");
            return JsonResult.build(400, "系统错误!");
        }

        try {

            redisService.set(key3, FastJsonConvert.convertObjectToJSON(cartInfoAll));

        } catch (Exception e) {
            logger.error("Redis 服务出错!", e);
        }

        final String orderString = FastJsonConvert.convertObjectToJSON(order);
        // 发送消息 topic
        //jmsTemplate.send(destination, new MessageCreator() {
        //    @Override
        //    public Message createMessage(Session session) throws JMSException {
        //
        //        TextMessage message = session.createTextMessage(orderString);
        //
        //        logger.info("发送JMS消息,消息为:" + orderString);
        //
        //        return message;
        //    }
        //});

        return null;
    }
}
