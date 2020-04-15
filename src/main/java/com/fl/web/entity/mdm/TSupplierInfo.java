package com.fl.web.entity.mdm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TSupplierInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-16 11:55
 */
@Getter
@Setter
public class TSupplierInfo extends BaseEntity {
    private String supplierCode;//供应商编码
    private String supplierName;//供应商名称
    private String supplierType;//供应商类型
    private String address;//地址
    private String contact;//联系人
    private String contactTel;//联系电话
    private String telphone;//联系手机
    private String bankType;//开户银行
    private String bankAccount;//银行账号

}