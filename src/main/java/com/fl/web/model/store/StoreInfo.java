package com.fl.web.model.store;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：StoreInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:23
 */
@Getter
@Setter
public class StoreInfo extends PageModel {
    private String matnr;
    private String maktx;
    private String norms;
    private String companyCode;
    private String wareCode;
    private String warePosCode;
    private String projectCode;//项目类别编码
}
