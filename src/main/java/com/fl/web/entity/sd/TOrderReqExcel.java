package com.fl.web.entity.sd;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fl.web.utils.StringUtil;

import java.math.BigDecimal;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TOrderReqExcel
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-26 14:31
 */
public class TOrderReqExcel {
    private String matnr;//物料编码
    @Excel(name = "物料名称", width = 20)
    private String maktx;//物料名称
    @Excel(name = "规格型号", width = 20)
    private String norms;//型号规格
    @Excel(name = "大类编码")
    private String maktl;//物料大类编码
    @Excel(name = "单位", width = 8)
    private String unit;//单位
    @Excel(name = "数量")
    private BigDecimal num;//数量
    @Excel(name = "制造商")
    private String manufacturer;//制造商
    @Excel(name = "质量类别")
    private String qualityType;//质量类别
    @Excel(name = "建议供应商", width = 15)
    private String suggestSupplier;//议供应商名称
    @Excel(name = "预计要货日期", width = 15)
    private String wishDate;//预计要货日期
    @Excel(name = "备注", width = 30)
    private String reqNote;//备注

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = StringUtil.isNotEmpty(matnr) ? matnr.trim() : null;
    }

    public String getMaktx() {
        return maktx;
    }

    public void setMaktx(String maktx) {
        this.maktx = StringUtil.isNotEmpty(maktx) ? maktx.trim() : null;
    }

    public String getNorms() {
        return norms;
    }

    public void setNorms(String norms) {
        this.norms = StringUtil.isNotEmpty(norms) ? norms.trim() : null;
    }

    public String getMaktl() {
        return maktl;
    }

    public void setMaktl(String maktl) {
        this.maktl = StringUtil.isNotEmpty(maktl) ? maktl.trim() : null;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = StringUtil.isNotEmpty(unit) ? unit.trim() : null;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = StringUtil.isNotEmpty(manufacturer) ? manufacturer.trim() : null;
    }

    public String getQualityType() {
        return qualityType;
    }

    public void setQualityType(String qualityType) {
        this.qualityType = StringUtil.isNotEmpty(qualityType) ? qualityType.trim() : null;
    }

    public String getSuggestSupplier() {
        return suggestSupplier;
    }

    public void setSuggestSupplier(String suggestSupplier) {
        this.suggestSupplier = StringUtil.isNotEmpty(suggestSupplier) ? suggestSupplier.trim() : null;
    }

    public String getWishDate() {
        return wishDate;
    }

    public void setWishDate(String wishDate) {
        this.wishDate = StringUtil.isNotEmpty(wishDate) ? wishDate.trim() : null;
    }

    public String getReqNote() {
        return reqNote;
    }

    public void setReqNote(String reqNote) {
        this.reqNote = reqNote;
    }
}
