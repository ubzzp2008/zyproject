package com.fl.web.model.mdm;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：Mara
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-10 11:15
 */
@Getter
@Setter
public class Mara extends PageModel {
    /**
     * 物料编码
     */
    private String matnr;
    /**
     * 物料名称
     */
    private String maktx;
    /**
     * 物料大类
     */
    private String maktl;
    /**
     * 物料规格
     */
    private String norms;


}
