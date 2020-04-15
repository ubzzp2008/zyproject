package com.fl.web.entity.qm;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TMatnrCensor
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-01-09 16:51
 */
@Getter
@Setter
public class TCensorShipItem extends BaseEntity {
    private String orderNo;//采购订单号
    private String censorNo;//送检单号
    private Integer itemNo;//行号
    private String maktl;//大类编码
    private String maktlName;//大类编码
    private String matnr;//物料编码
    private String maktx;//物料名称
    private String norms;//型号规格
    private String unit;//单位
    private BigDecimal num;//送检数量
    private BigDecimal unqualifiedNum;//不合格数量
    private BigDecimal qualifiedNum;//合格数量
    private String qualityType;//质量类别
    private String batchNo;//批次号
    private String generationDate;//生产日期
    private String validityDate;//保质期
    private String manufacturer;//制造商
    private String censorId;//送检表id
    private String orderItemId;//订单行id
}
