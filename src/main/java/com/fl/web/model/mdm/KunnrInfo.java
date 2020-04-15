package com.fl.web.model.mdm;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：KunnrInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-11 12:48
 */
@Getter
@Setter
public class KunnrInfo extends PageModel {
    private String kunnr;//客户编码
    private String kunnrName;//客户名称
    private String address;//地址

}
