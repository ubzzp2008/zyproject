package com.fl.web.model.qm;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MaraCensor
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:53
 */
@Getter
@Setter
public class MaraCensor extends PageModel {
    private String orderNo;
    private String matnr;
    private String maktx;
    private String norms;
    private String state;
}
