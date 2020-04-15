package com.fl.web.entity.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @version V0.1
 * @项目名称：springBootDemo
 * @类名称：BaseEntity
 * @类描述：
 * @创建人：justin
 * @创建时间：2018-05-03 16:19
 */
@Getter
@Setter
public class BaseEntity implements Serializable {
    /**
     * 主键ID  32位UUID
     */
    private String id;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 最后修改人
     */
    private String updateBy;
    /**
     * 最后修改时间
     */
    private Date updateDate;
    /**
     * 数据状态 009新增，004修改，003删除
     */
    private String status;

}
