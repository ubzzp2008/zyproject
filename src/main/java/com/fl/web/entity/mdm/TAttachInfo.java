package com.fl.web.entity.mdm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TAttachInfo
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-05 16:51
 */
@Getter
@Setter
public class TAttachInfo extends BaseEntity {
    private String tableId;//表主键ID
    private String tableName;//表名
    private String orderNo;//单据号
    private String fileName;//新文件名
    private String fileType;//文件类型
    private Integer fileSize;//文件大小
    private String filePath;//文件路径
    private String oldName;//原始文件名
    private String flag;//附件标识：0新增维修单时附件，1维修单待提交时附件

}
