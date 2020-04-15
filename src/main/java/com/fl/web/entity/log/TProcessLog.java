package com.fl.web.entity.log;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrderLog
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-23 15:49
 */
@Getter
@Setter
public class TProcessLog {
    private String id;//主键ID
    private String orderNo;//单据号
    private String orderStatus;//单据状态
    private String description;//描述
    private String reason;//审批意见
    private String createBy;//新增人
    private String realname;//真实名
    private Date createDate;//新增时间

    public TProcessLog() {
    }

    public TProcessLog(String orderNo, String orderStatus, String description, String reason) {
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.description = description;
        this.reason = reason;
    }

}
