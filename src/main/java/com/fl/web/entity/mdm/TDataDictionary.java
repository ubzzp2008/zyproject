package com.fl.web.entity.mdm;

import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：TDataDictionary
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-17 14:43
 */
@Getter
@Setter
public class TDataDictionary {
    /**
     * 主键ID
     */
    private String id;
    /**
     * 组编码
     */
    private String groupCode;
    private String groupName;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 备注
     */
    private String note;
    /**
     * 排序号
     */
    private Integer sortnum;

}
