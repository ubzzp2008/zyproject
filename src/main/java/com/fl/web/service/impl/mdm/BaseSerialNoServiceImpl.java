package com.fl.web.service.impl.mdm;

import com.fl.web.dao.mdm.IBaseSerialNoDao;
import com.fl.web.entity.mdm.TBaseSerialNo;
import com.fl.web.service.mdm.IBaseSerialNoService;
import com.fl.web.utils.StringUtil;
import com.fl.web.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：BaseSerialNoSeriveImpl
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-01-15 09:02
 */
@Service
@Transactional
public class BaseSerialNoServiceImpl implements IBaseSerialNoService {
    @Autowired
    private IBaseSerialNoDao baseSerialNoDao;

    @Override
    public synchronized String getSerialNoByType(String type, String format) {
        // 查询最新流水号
        TBaseSerialNo baseDto = baseSerialNoDao.findSerialNoByType(type);
        String code = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String currDate = formatter.format(date);
        if (baseDto == null) {
            code = currDate + format;
            baseDto = new TBaseSerialNo();
            baseDto.setId(UUIDUtil.get32UUID());
            baseDto.setType(type);
            baseDto.setCode(code);
            baseDto.setName("流水号");
            baseSerialNoDao.saveSerialNo(baseDto);
        } else {
            String nowNo = baseDto.getCode().substring(0, 8);
            //当前日期与流水码日期不等，则流水码从0001开始，若相等，则流水码+1
            if (StringUtil.equals(nowNo, currDate)) {
                code = String.valueOf(Long.parseLong(baseDto.getCode()) + 1);
            } else {
                code = currDate + format;
            }
            baseSerialNoDao.updateSerialNo(code,baseDto.getId());
        }
        return type + code;
    }
}
