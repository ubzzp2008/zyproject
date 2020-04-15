package com.fl.web.entity.sd;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TMaraBack
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-14 13:23
 */
@Setter
@Getter
public class TOrderBack extends BaseEntity {
    private String orderNo;
    private String itemId;//采购订单行id
    private Integer itemNo;//行号
    private String maktl;//物料大类编码
    private String maktlName;
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String unit;//单位
    private String manufacturer;//制造商
    private String qualityType;//质量类别
    private BigDecimal backNum;//不合格退回数量
    private String state;
    private String reason;//不合格备注

}
