package com.fl.web.model.sd;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：OrderBack
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 13:24
 */
@Getter
@Setter
public class OrderBack extends PageModel {
    private String state;
    private String orderNo;
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
}
