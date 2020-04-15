package com.fl.web.model.system;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：UserPosition
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-19 15:47
 */
@Getter
@Setter
public class UserPosition extends PageModel {
    private String userName;
    private String realName;
    private String posCode;
    private String posName;

}
