package com.fl.web.entity.saleServer;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TRepairOrderLog
 * @类描述：维修单审批日志
 * @创建人：justin
 * @创建时间：2019-12-10 17:59
 */
@Getter
@Setter
public class TRepairOrderLog {
    private String id;//主键ID
    private String orderNo;//单据号
    private String orderStatus;//单据状态
    private String description;//描述
    private String posCode;//岗位编码
    private String posName;//岗位名称
    private String reason;//审批意见
    private String createBy;//新增人
    private String realname;//真实名
    private Date createDate;//新增时间
}