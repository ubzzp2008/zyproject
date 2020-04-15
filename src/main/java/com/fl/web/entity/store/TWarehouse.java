package com.fl.web.entity.store;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TWarehouse
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-05 12:38
 */
@Getter
@Setter
public class TWarehouse extends BaseEntity {
    private String companyCode;//公司编码
    private String company;//公司
    private String code;//仓库编码
    private String name;//仓库名称
    private String addr;//仓库地址
    private String contact;//管理人员
    private String phone;//联系方式
    private BigDecimal safeNum;//安全库存
    private BigDecimal num;//现有库存
    private String note;//备注

}
