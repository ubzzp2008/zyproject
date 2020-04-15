package com.fl.web.model.store;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcerp-service
 * @类名称：Warehouse
 * @类描述：
 * @创建人：justin
 * @创建时间：2020/2/5 11:22
 */
@Getter
@Setter
public class Warehouse extends PageModel {
    private String code;
    private String name;
}
