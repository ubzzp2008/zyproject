package com.fl.web.dao.saleServer;

import com.fl.web.entity.saleServer.TRepairMara;
import com.fl.web.model.saleServer.RepairMara;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IRepairMaraDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-30 15:00
 */
public interface IRepairMaraDao {
    /**
     * @description：批量保存
     * @author：justin
     * @date：2019-10-30 17:04
     */
    public void saveRepairMaraBatch(List<TRepairMara> reMaraList);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-31 14:06
     */
    public List<TRepairMara> queryRepairMaraList(RepairMara info);

    /**
     * @description：更新数据
     * @author：justin
     * @date：2019-11-01 10:48
     */
    public void updateRepairMara(Map<String, Object> param);

    /**
     * @description：检查单据行换新和退坏流程是否已经完成
     * @author：justin
     * @date：2019-11-01 13:45
     */
    @Select("select count(1) from repair_mara where item_id=#{itemId} and status!='003' and state!=#{state}")
    @ResultType(Integer.class)
    public Integer findRepairMaraByItemId(@Param("itemId") String itemId, @Param("state") String state);

    /**
     * @description：根据单号集合获取数据
     * @author：justin
     * @date：2019-12-23 13:05
     */
    public List<TRepairMara> getRepairMaraByOrderNoList(List<String> orderNoList);
}
