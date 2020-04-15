package com.fl.web.dao.store;

import com.fl.web.entity.store.TStoreInfo;
import com.fl.web.model.store.StoreInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：IStoreInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:28
 */
public interface IStoreInfoDao {
    /**
     * @description：根据物料获取物料库存信息
     * @author：justin
     * @date：2020-02-14 15:30
     */
    public TStoreInfo getStoreInfoByMatnr(@Param("matnr") String matnr, @Param("wareCode") String wareCode, @Param("warePosCode") String warePosCode);

    /**
     * @description：根据id修改库存信息
     * @author：justin
     * @date：2020-02-14 15:32
     */
    public void updateStoreInfo(TStoreInfo storeInfo);

    /**
     * @description：根据物料批量更新库存
     * @author：justin
     * @date：2020-02-14 15:32
     */
    public void updateStoreInfoBatch(List<TStoreInfo> storeInfoList);

    /**
     * @description：保存库存信息
     * @author：justin
     * @date：2020-02-14 15:32
     */
    public void saveStoreInfo(TStoreInfo storeInfo);

    /**
     * @description：分页查询库存信息
     * @author：justin
     * @date：2020-02-17 09:35
     */
    public List<TStoreInfo> queryStoreInfoList(StoreInfo info);

    /**
     * @description：检查库存数是否足够
     * @author：justin
     * @date：2020-02-19 11:22
     */
    @Select("select count(1) from store_info where matnr=#{matnr} and ware_code=#{wareCode} and ware_pos_code=#{warePosCode} and usable_num>=#{usableNum} and status!='003'")
    @ResultType(Integer.class)
    public Integer checkStoreInfoNum(String matnr, String wareCode, String warePosCode, BigDecimal usableNum);
}
