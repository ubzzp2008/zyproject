package com.fl.web.entity.store;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：T
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:37
 */
@Getter
@Setter
public class TStoreOrder extends BaseEntity {
    private String storeNo;//单据号
    private String companyCode;//公司
    private String company;//公司
    private String projectCode;//项目类别
    private String projectName;//项目类别
    private String optDept;//部门
    private String optName;//操作人
    private String optDate;//日期
    private String note;//备注
    private String state;//状态
    private String optType;//出入库类型：入库IN，出库OUT
    private String storeType;//单据标识：正常：0，冲销：1

    private List<TStoreItem> itemList;

}
