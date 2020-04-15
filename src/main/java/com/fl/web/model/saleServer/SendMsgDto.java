package com.fl.web.model.saleServer;

import com.fl.web.entity.saleServer.TRepairOrderHead;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：SendMsgDto
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-20 11:42
 */
@Getter
@Setter
public class SendMsgDto {

    private List<TRepairOrderHead> dataList;
    private String reason;
    private String flag;

}
