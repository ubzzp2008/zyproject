package com.fl.web.entity.system;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TUserPosition
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:02
 */
@Getter
@Setter
public class TUserPosition extends BaseEntity {
    private String userId;
    private String userName;
    private String realName;
    private String empCode;
    private String posId;
    private String posCode;
    private String posName;
    private String posType;
    private String startDate;
    private String endDate;
    private String orgId;
    private String orgCode;
    private String orgName;

}
