package com.fl.web.service.shop;

import com.fl.web.entity.shop.TOrderReport;
import com.fl.web.model.shop.OrderReport;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：IOrderInfoSerive
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:37
 */
public interface IOrderReportService {
    void saveOrderReportBatch(List<TOrderReport> infoList);

    PageInfo<TOrderReport> queryOrderReportList(OrderReport report);
}
