package com.fl.web.service.mdm;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：IBaseSerialNoSerive
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-15 09:03
 */
public interface IBaseSerialNoService {
    /**
     * @desc：根据类型获取编码
     * @author：justin
     * @param：type 单号前缀字母
     * @param：format 流水码位数 如：0001,001
     * @date：2019-01-15 09:04
     */
    public String getSerialNoByType(String type, String format);

}
