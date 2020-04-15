package com.fl.web.entity.mdm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TKunnrInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 12:48
 */
@Getter
@Setter
public class TKunnrInfo extends BaseEntity {

    private String kunnr;//客户编码
    private String kunnrName;//客户名称
    private String address;//地址
    private String contactInfo;//联系人
    private String contactTel;//联系电话
}
