package com.fl.web.entity.saleServer;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TRepairUser
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-14 15:54
 */
@Getter
@Setter
public class TRepairUser extends BaseEntity {

    private String userName;
    private String realName;
    private String telphone;
    private String empCode;
    private String note;

}
