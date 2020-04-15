package com.fl.web.model.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：BaseModel
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 10:59
 */
@Getter
@Setter
public class PageModel implements Serializable {
    protected int pageNum;
    protected int pageSize;

}
