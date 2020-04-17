package com.fl.web.dao.shop;

import com.fl.web.entity.shop.TOrderReport;
import com.fl.web.model.shop.OrderReport;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IGoodsDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:34
 */
public interface IOrderReportDao {

    void saveOrderReportBatch(List<TOrderReport> infoList);

    List<TOrderReport> queryOrderReportList(OrderReport report);

}
