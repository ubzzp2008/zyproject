package com.fl.web.model.mdm;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：SupplierInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 11:53
 */
@Getter
@Setter
public class SupplierInfo extends PageModel {
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String supplierType;//供应商类型

}
