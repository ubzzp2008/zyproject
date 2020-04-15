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
public class WarehousePos extends PageModel {
    private String wareCode;//仓库编码
    private String code;//仓位编码
    private String name;//仓位名称
}
