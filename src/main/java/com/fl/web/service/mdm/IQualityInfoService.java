package com.fl.web.service.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.entity.mdm.TQualityInfo;
import com.fl.web.model.mdm.QualityInfo;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IQualityInfoService
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 10:29
 */
public interface IQualityInfoService {

    /**
     * @description：分页查询
     * @author：justin
     * @date：2020-01-09 10:25
     */
    public PageInfo<TQualityInfo> queryQualityInfoList(QualityInfo info);

    /**
     * @description：保存
     * @author：justin
     * @date：2020-01-09 10:25
     */
    public void saveQualityInfo(TQualityInfo info);

    /**
     * @description：修改
     * @author：justin
     * @date：2020-01-09 10:25
     */
    public void updateQualityInfo(TQualityInfo info);

    /**
     * @description：根据id删除
     * @author：justin
     * @date：2020-01-09 10:27
     */
    public void deleteQualityInfoById(String id);

    /**
     * @description：获取所有质量类别
     * @author：justin
     * @date：2020-01-09 10:41
     */
    public List<TQualityInfo> getAllQualityInfo();

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2020-01-09 10:46
     */
    public TQualityInfo getQualityInfoById(String id);

    /**
     * @description：根据code获取数据
     * @author：justin
     * @date：2020-01-09 10:46
     */
    public TQualityInfo getQualityInfoByCode(String code);
}
