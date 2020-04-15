package com.fl.web.entity.mdm;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TMara
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 11:47
 */
@Getter
@Setter
public class TMara extends BaseEntity {
    /**
     * 物料编码
     */
    @Excel(name = "物料编码", orderNum = "1")
    private String matnr;
    /**
     * 物料名称
     */
    @Excel(name = "物料名称", orderNum = "2")
    private String maktx;
    /**
     * 物料大类
     */
    @Excel(name = "大类编码", orderNum = "3")
    private String maktl;
    /**
     * 物料大类
     */
    @Excel(name = "大类名称", orderNum = "4")
    private String maktlName;
    /**
     * 物料规格
     */
    @Excel(name = "规格型号", orderNum = "5")
    private String norms;
    /**
     * 物料单位
     */
    @Excel(name = "单位", orderNum = "6")
    private String unit;
    @Excel(name = "质量类别", orderNum = "7")
    private String qualityType;//质量类别
    @Excel(name = "制造商", orderNum = "8")
    private String manufacturer;//制造商

    private BigDecimal price;//参考单价
}
