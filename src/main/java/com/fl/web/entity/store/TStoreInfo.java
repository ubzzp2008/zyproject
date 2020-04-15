package com.fl.web.entity.store;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TStoreInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-05 12:38
 */
@Getter
@Setter
public class TStoreInfo extends BaseEntity {
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//规格型号
    private String unit;//单位
    private String manufacturer;//制造商
    private String qualityType;//质量类别
    private String maktl;//大类编码
    private String maktlName;//大类名称
    private String wareCode;//仓库编码
    private String wareName;//仓库名称
    private String companyCode;//公司编码
    private String company;//公司名称
    private String warePosCode;//仓位编码
    private String warePosName;//仓位名称
    private String projectCode;//项目类别编码
    private String projectName;//项目类别名称
    private BigDecimal storeNum;//现有库存
    private BigDecimal frozenNum;//冻结库存
    private BigDecimal usableNum;//可用库存
}
