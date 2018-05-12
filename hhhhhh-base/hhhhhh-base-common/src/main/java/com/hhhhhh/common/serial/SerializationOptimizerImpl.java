package com.hhhhhh.common.serial;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;
import com.alibaba.fastjson.JSONObject;
import com.beust.jcommander.internal.Lists;
import com.hhhhhh.common.pojo.EuiResult;
import com.hhhhhh.common.pojo.EuiTreeNode;
import com.hhhhhh.common.pojo.JsonResult;
import com.hhhhhh.pojo.*;

import java.util.Collection;
import java.util.List;

public class SerializationOptimizerImpl implements SerializationOptimizer {

    @Override
    public Collection<Class> getSerializableClasses() {
        List<Class> classes = Lists.newLinkedList();
        //这里可以把所有需要进行序列化的类进行添加
        classes.add(JSONObject.class);
        classes.add(TbItem.class);
        classes.add(TbContentCategory.class);
        classes.add(TbContent.class);
        classes.add(TbItemDesc.class);
        classes.add(TbItemParam.class);
        classes.add(TbOrder.class);
        classes.add(TbOrderItem.class);
        classes.add(TbUser.class);
        classes.add(TbCategory.class);
        classes.add(TbCategorySecondary.class);
        classes.add(TbUserAddr.class);
        classes.add(TbTransactionMessage.class);
        classes.add(EuiResult.class);
        classes.add(EuiTreeNode.class);
        classes.add(JsonResult.class);
        classes.add(EsItem.class);
        classes.add(SearchResult.class);
        classes.add(CartInfo.class);
        return classes;
    }
}
