package com.hhhhhh.cart.servant;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhhhhh.cart.client.CartService;
import com.hhhhhh.common.consts.Const;
import com.hhhhhh.common.pojo.JsonResult;
import com.hhhhhh.common.utils.FastJsonConvert;
import com.hhhhhh.mapper.TbItemMapper;
import com.hhhhhh.pojo.CartInfo;
import com.hhhhhh.pojo.TbItem;
import com.hhhhhh.pojo.TbItemExample;
import com.hhhhhh.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(version = Const.HHHHHH_CART_VERSION)
public class CartServant implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServant.class);

    @Value("${redisKey.prefix.cart_info_profix}")
    private String CART_INFO_PROFIX;
    @Value("${redisKey.prefix.redis_cart_expire_time}")
    private Integer REDIS_CART_EXPIRE_TIME;
    @Value("${redisKey.prefix.item_info_profix}")
    private String ITEM_INFO_PROFIX;
    @Value("${redisKey.prefix.item_info_base_suffix}")
    private String ITEM_INFO_BASE_SUFFIX;

    @Resource
    private RedisService redisService;

    @Resource
    private TbItemMapper itemMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult addCart(Long pid, Integer pcount, String uuid) {


        String key = CART_INFO_PROFIX + uuid;
        String cartInfoString = null;
        try {
            cartInfoString = redisService.get(key);
        } catch (Exception e) {
            logger.error("Redis出错!", e);
        }

        TbItem item = null;

        try {
            String redisItem = redisService.get(ITEM_INFO_PROFIX + pid + ITEM_INFO_BASE_SUFFIX);

            if (StringUtils.isNotBlank(redisItem)) {
                item = FastJsonConvert.convertJSONToObject(redisItem, TbItem.class);

            } else {
                TbItemExample example = new TbItemExample();
                TbItemExample.Criteria criteria = example.createCriteria();

                criteria.andIdEqualTo(pid);

                List<TbItem> itemList = itemMapper.selectByExample(example);

                if (itemList != null && itemList.size() > 0) {
                    item = itemList.get(0);
                } else {
                    return JsonResult.build(500, "商品查询不到!");
                }
            }

        } catch (Exception e) {
            logger.error("Redis出错!", e);
        }

        CartInfo cartInfo = new CartInfo();

        cartInfo.setId(item.getId());
        cartInfo.setName(item.getTitle());
        String[] split = item.getImage().split(",");
        cartInfo.setImageUrl(split[0]);
        cartInfo.setColour("黑色");
        cartInfo.setNum(pcount);
        cartInfo.setPrice(item.getPrice());
        cartInfo.setSize("32GB");

        if (StringUtils.isBlank(cartInfoString)) {

            ArrayList<CartInfo> cartInfos = new ArrayList<>();

            cartInfos.add(cartInfo);

            logger.debug("第一次保存商品到Redis uuid:" + uuid);

            try {
                redisService.set(key, FastJsonConvert.convertObjectToJSON(cartInfos));
                redisService.expire(key, REDIS_CART_EXPIRE_TIME);
            } catch (Exception e) {
                logger.error("Redis出错!", e);
            }

            return JsonResult.build(200, "ok", cartInfo);

        } else {
            List<CartInfo> list = FastJsonConvert.convertJSONToArray(cartInfoString, CartInfo.class);
            if (list != null && list.size() > 0) {
                boolean flag = true;
                for (int i = 0; i < list.size(); i++) {
                    CartInfo Info = list.get(i);
                    if (Info.getId().equals(item.getId())) {
                        Info.setNum(Info.getNum() + pcount);
                        list.remove(i);
                        list.add(Info);
                        flag = false;

                        logger.debug("商品已经存在数量加" + pcount + "个 uuid:" + uuid);
                    }
                }
                if (flag) {
                    list.add(cartInfo);
                    logger.debug("商品不存在数量为" + pcount + "个 uuid:" + uuid);
                }
            }

            logger.debug("商品添加完成 购物车" + list.size() + "件商品 uuid:" + uuid);

            try {
                redisService.set(key, FastJsonConvert.convertObjectToJSON(list));
                redisService.expire(key, REDIS_CART_EXPIRE_TIME);
            } catch (Exception e) {
                logger.error("Redis出错!", e);
            }

            return JsonResult.build(200, "ok", cartInfo);
        }
    }

    @Override
    public List<CartInfo> getCartInfoListByCookiesId(String cookieUUID) {

        String cartInfoString = redisService.get(CART_INFO_PROFIX + cookieUUID);

        if (StringUtils.isNotBlank(cartInfoString)) {
            return FastJsonConvert.convertJSONToArray(cartInfoString, CartInfo.class);
        }

        return null;
    }

    /**
     *
     * 根据商品id和数量对购物车增加商品或减少商品
     *
     * @param pid       商品id
     * @param pcount    增加数量
     * @param type      1 增加 2 减少
     * @param index     商品位置   ps:用于直接定位商品 不用遍历整个购物车
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult decreOrIncre(Long pid, Integer pcount, Integer type, Integer index, String cookieUUID) {

        String key = CART_INFO_PROFIX + cookieUUID;

        List<CartInfo> cartInfoList = getCartInfoListByCookiesId(cookieUUID);
        if (cartInfoList == null || cartInfoList.size() == 0) {
            return JsonResult.build(400, "购物车没有此商品 请不要非法操作!");
        }

        CartInfo cartInfo = cartInfoList.get(index);

        if (type == 1) {
            cartInfo.setNum(cartInfo.getNum() + pcount);
        } else {
            cartInfo.setNum(cartInfo.getNum() - pcount);
        }
        //cartInfoList.remove(index);
        //cartInfoList.add(index, cartInfo);
        redisService.set(key, FastJsonConvert.convertObjectToJSON(cartInfoList));
        redisService.expire(key,REDIS_CART_EXPIRE_TIME);

        return JsonResult.ok();
    }
}
