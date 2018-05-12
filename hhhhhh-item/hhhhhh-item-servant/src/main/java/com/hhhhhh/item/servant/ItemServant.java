package com.hhhhhh.item.servant;

import com.alibaba.dubbo.config.annotation.Service;
import com.hhhhhh.common.consts.Const;
import com.hhhhhh.common.utils.FastJsonConvert;
import com.hhhhhh.item.client.ItemService;
import com.hhhhhh.mapper.TbItemDescMapper;
import com.hhhhhh.mapper.TbItemMapper;
import com.hhhhhh.pojo.TbItem;
import com.hhhhhh.pojo.TbItemDesc;
import com.hhhhhh.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;


/**
 * 商品 Service 实现
 */
@Service(version = Const.HHHHHH_ITEM_VERSION)
public class ItemServant implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServant.class);

    @Resource
    private TbItemMapper itemMapper;

    @Resource
    private TbItemDescMapper itemDescMapper;

    @Resource
    private RedisService redisService;

    @Value("${redisKey.prefix.item_info_profix}")
    private String ITEM_INFO_PROFIX;

    @Value("${redisKey.suffix.item_info_base_suffix}")
    private String  ITEM_INFO_BASE_SUFFIX;

    @Value("${redisKey.suffix.item_info_desc_suffix}")
    private String ITEM_INFO_DESC_SUFFIX;

    @Value("${redisKey.expire_time}")
    private Integer REDIS_EXPIRE_TIME;

    @Override
    public TbItem getItemById(Long itemId) {

        String key = ITEM_INFO_PROFIX + itemId + ITEM_INFO_BASE_SUFFIX;

        try {
            String jsonItem = redisService.get(key);

            if (StringUtils.isNotBlank(jsonItem)) {

                logger.info("Redis 查询 商品信息 商品ID:" + itemId);

                return FastJsonConvert.convertJSONToObject(jsonItem, TbItem.class);

            } else {
                logger.error("Redis 查询不到 key:" + key);
            }
        } catch (Exception e) {
            logger.error("商品信息 获取缓存报错",e);
        }

        logger.info("根据商品ID"+itemId+"查询商品！");
        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        try {
            redisService.set(key, FastJsonConvert.convertObjectToJSON(item));

            redisService.expire(key, REDIS_EXPIRE_TIME);

            logger.info("Redis 缓存商品信息 key:" + key);

        } catch (Exception e) {
            logger.error("缓存错误商品ID:" + itemId, e);
        }

        return item;

    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {

        String key = ITEM_INFO_PROFIX + itemId + ITEM_INFO_DESC_SUFFIX;

        try {
            String jsonItem = redisService.get(key);

            if (StringUtils.isNotBlank(jsonItem)) {

                logger.info("Redis 查询 商品详情 商品ID:" + itemId);

                return FastJsonConvert.convertJSONToObject(jsonItem, TbItemDesc.class);

            } else {
                logger.error("Redis 查询不到 key:" + key);
            }
        } catch (Exception e) {
            logger.error("商品详情 获取缓存报错",e);
        }
        logger.info("根据商品ID"+itemId+"查询商品详情！");

        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
        try {
            redisService.set(key, FastJsonConvert.convertObjectToJSON(itemDesc));

            redisService.expire(key, REDIS_EXPIRE_TIME);

            logger.info("Redis 缓存 商品详情 key:" + key);

        } catch (Exception e) {
            logger.error("缓存错误商品ID:" + itemId, e);
        }
        return itemDesc;
    }
}
