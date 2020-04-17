package com.fl.web.service.impl.shop;

import com.fl.web.dao.shop.IOrderReportDao;
import com.fl.web.entity.shop.TOrderReport;
import com.fl.web.model.shop.OrderReport;
import com.fl.web.service.shop.IOrderReportService;
import com.fl.web.utils.DateUtils;
import com.fl.web.utils.StaticParam;
import com.fl.web.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：OrderInfoServiceImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-17 16:37
 */
@Service
@Transactional
public class OrderReportServiceImpl implements IOrderReportService {
    @Autowired
    private IOrderReportDao orderReportDao;

    @Override
    public void saveOrderReportBatch(List<TOrderReport> infoList) {
        for (TOrderReport report : infoList) {
            report.setId(UUIDUtil.get32UUID());
            report.setOrderDate(DateUtils.formatDate());
            report.setStatus(StaticParam.ADD);
            report.setCreateDate(new Date());
        }
        orderReportDao.saveOrderReportBatch(infoList);
    }

    @Override
    public PageInfo<TOrderReport> queryOrderReportList(OrderReport report) {
        PageHelper.startPage(report.getPageNum(), report.getPageSize());
        List<TOrderReport> list = orderReportDao.queryOrderReportList(report);
        PageInfo result = new PageInfo(list);
        return result;
    }
}
