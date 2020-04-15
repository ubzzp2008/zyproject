package com.fl.web.entity.mdm;

import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：TBaseSerialNo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-15 09:06
 */
@Getter
@Setter
public class TBaseSerialNo   {
    /**
     * 32位uuid
     */
    private String id;
    private String code;
    private String name;
    /**
     * 类型分类
     */
    private String type;

}
