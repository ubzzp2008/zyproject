package com.fl.web.entity.system;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TPosition
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-18 17:02
 */
@Getter
@Setter
public class TPosition extends BaseEntity {
    private String posCode;
    private String posName;
    private String pid;
    private String pCode;
    private String pName;
    private String note;
    private String orgId;

}

