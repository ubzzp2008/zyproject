package com.fl.web.service.store;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.store.TStoreInfo;
import com.fl.web.model.store.StoreInfo;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：IStoreInfoService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:33
 */
public interface IStoreInfoService {
    /**
     * @description：根据物料获取物料库存信息
     * @author：justin
     * @date：2020-02-14 15:30
     */
    public TStoreInfo getStoreInfoByMatnr(String matnr, String wareCode, String warePosCode);

    /**
     * @description：修改库存信息
     * @author：justin
     * @date：2020-02-14 15:32
     */
    public void updateStoreInfo(TStoreInfo storeInfo);

    /**
     * @description：保存库存信息
     * @author：justin
     * @date：2020-02-14 15:32
     */
    public void saveStoreInfo(TStoreInfo storeInfo);

    /**
     * @description：分页查询库存信息
     * @author：justin
     * @date：2020-02-17 09:32
     */
    public PageInfo<TStoreInfo> queryStoreInfoList(StoreInfo info);

    /**
     * @description：检查库存数是否足够
     * @author：justin
     * @date：2020-02-19 11:22
     */
    public Integer checkStoreInfoNum(String matnr, String wareCode, String warePosCode, BigDecimal usableNum);
}
