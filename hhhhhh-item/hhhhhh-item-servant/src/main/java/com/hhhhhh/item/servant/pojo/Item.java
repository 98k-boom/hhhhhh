package com.hhhhhh.item.servant.pojo;

import com.hhhhhh.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;

/**
 * TbItem 扩展pojo
 */
public class Item extends TbItem {

    private String[] images;

    public Item(TbItem tbItem) {
        //初始化属性
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages() {

        if (StringUtils.isNotBlank(this.getImage())) {

            String[] split = this.getImage().split(",");

            return split;
        } else {
            return null;
        }

    }
}
