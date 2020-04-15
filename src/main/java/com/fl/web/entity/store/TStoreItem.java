package com.fl.web.entity.store;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TStoreItem
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:37
 */
@Getter
@Setter
public class TStoreItem extends BaseEntity {
    private String storeNo;
    private Integer itemNo;
    private String matnr;
    private String maktx;
    private String norms;
    private String unit;
    private String manufacturer;
    private String qualityType;
    private String maktl;
    private String maktlName;
    private String wareCode;//仓库
    private String wareName;//仓库
    private String companyCode;//公司
    private String company;//公司
    private String warePosCode;//仓位
    private String warePosName;//仓位
    private String projectCode;//项目类别
    private String projectName;//项目类别
    private BigDecimal optNum;//数量
    private String deliveryId;
    private String censorId;
    private String orderItemId;
}
