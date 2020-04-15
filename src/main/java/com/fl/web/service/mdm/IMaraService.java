package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TMara;
import com.fl.web.model.mdm.Mara;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IMaraService
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 12:45
 */
public interface IMaraService {
    /**
     * @description：分页查询物料数据
     * @author：justin
     * @date：2019-10-10 11:18
     */
    public PageInfo<TMara> queryMaraList(Mara mara);

    /**
     * @desc：保存物料基础信息
     * @author：justin
     * @date：2019-01-09 16:13
     */
    public void saveMara(TMara mara);

    /**
     * @description：系统根据采购单自动建立物料
     * @author：justin
     * @date：2019-12-25 10:48
     */
    public String autoSaveMara(TMara mara);

    /**
     * @desc：根据物料编码获取物料信息
     * @author：justin
     * @date：2019-01-10 12:57
     */
    public TMara getMaraByMatnr(String matnr);

    /**
     * @description：根据id获取物料信息
     * @author：justin
     * @date：2019-10-10 16:18
     */
    public TMara getMaraById(String id);

    /**
     * @description：根据id删除物料
     * @author：justin
     * @date：2019-10-10 16:23
     */
    public void deleteMaraById(String id);

    /**
     * @description：修改物料
     * @author：justin
     * @date：2019-10-10 16:24
     */
    public void updateMara(TMara mara);

    /**
     * @description：前端自动匹配带入查询物料数据
     * @author：justin
     * @date：2019-10-15 15:33
     */
    public List<TMara> searchMaraList(String info);

    /**
     * @description：批量保存数据
     * @author：justin
     * @date：2019-11-26 11:46
     */
    public void saveMaraBatch(List<TMara> maraList);

    /**
     * @description：导出excel
     * @author：justin
     * @date：2019-11-27 11:05
     */
    public List<TMara> exportMaraList(Mara mara);

    /**
     * @description：分页查询金蝶物料数据
     * @author：justin
     * @date：2019-12-12 09:31
     */
    public PageInfo<TMara> searchKdMaraList(Mara mara);

    /**
     * @description：修改物料参考价格
     * @author：justin
     * @date：2020-01-08 17:03
     */
    public void updateMaraPrice(String matnr, BigDecimal price);
}
