package com.fl.web.entity.system;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrg
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:33
 */
@Getter
@Setter
public class TOrg extends BaseEntity {
    private String orgCode;
    private String orgName;
    private String pid;
    private String note;

}
