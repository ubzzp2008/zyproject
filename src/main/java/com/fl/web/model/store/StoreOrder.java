package com.fl.web.model.store;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：StoreOrder
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-02-13 13:38
 */
@Getter
@Setter
public class StoreOrder extends PageModel {
    private String companyCode;
    private String projectCode;
    private String storeNo;
    private String state;
    private String optType;
    private String storeType;//正常0，冲销1
}
