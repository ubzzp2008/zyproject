package com.fl.web.dao.shop;

import com.fl.web.entity.shop.TOrderInfo;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IGoodsDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:34
 */
public interface IOrderInfoDao {

    void saveOrderInfoBatch(List<TOrderInfo> infoList);

    @Select("select distinct desk_code deskCode,desk_name deskName from order_info ")
    @ResultType(TOrderInfo.class)
    List<TOrderInfo> getOrderDeskList();
}
