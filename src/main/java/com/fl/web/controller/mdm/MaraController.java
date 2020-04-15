package com.fl.web.controller.mdm;

import com.github.pagehelper.PageInfo;
import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.mdm.TMara;
import com.fl.web.model.mdm.Mara;
import com.fl.web.service.mdm.IMaraService;
import com.fl.web.utils.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：MaraController
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-08 11:24
 */
@RestController
@RequestMapping("/mdm/mara")
public class MaraController extends BaseController {
    @Autowired
    private IMaraService maraService;


    /**
     * @description：分页查询物料数据
     * @author：justin
     * @date：2019-10-08 11:34
     */
    @RequestMapping(value = "/queryMaraList", method = RequestMethod.POST)
    public void queryMaraList(@RequestBody Mara mara, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(mara.getPageNum(), mara.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TMara> list = maraService.queryMaraList(mara);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取物料信息异常", e);
            json.setMsg("获取物料信息异常" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：检查物料编码是否已经存在
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getMaraByMatnr")
    public void getMaraByMatnr(@RequestParam(name = "matnr") String matnr, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            matnr = matnr.trim();
            TMara m = maraService.getMaraByMatnr(matnr);
            if (m != null) {
                json.setSuccess(false);
                json.setMsg("此编码已经被使用，请更换编码！");
            } else {
                json.setSuccess(true);
                json.setMsg("此编码验证通过，可使用");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id获取物料信息
     * @author：justin
     * @date：2019-10-10 15:25
     */
    @GetMapping(value = "/getMaraById")
    public void getMaraById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TMara mara = maraService.getMaraById(id);
            json.setSuccess(true);
            json.setObj(mara);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }


    /**
     * @description：保存物料信息
     * @author：justin
     * @date：2019-10-08 11:31
     */
    @PostMapping(value = "/saveMara")
    public void saveMara(@RequestBody TMara mara, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String matnr = mara.getMatnr().trim();
            TMara m = maraService.getMaraByMatnr(matnr);
            if (m != null) {
                json.setSuccess(false);
                json.setMsg("保存失败！编码：" + matnr + "已经存在！");
            } else {
                maraService.saveMara(mara);
                json.setSuccess(true);
                json.setMsg("保存成功");
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存物料异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改物料信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/updateMara")
    public void updateMara(@RequestBody TMara mara, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            maraService.updateMara(mara);
            json.setSuccess(true);
            json.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改物料失败", e);
            json.setSuccess(false);
            json.setMsg("修改物料失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：删除物料信息
     * @author：justin
     * @date：2019-10-10 15:32
     */
    @PostMapping(value = "/deleteMaraById")
    public void deleteMaraById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            maraService.deleteMaraById(id);
            json.setSuccess(true);
            json.setMsg("物料删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("物料删除失败", e);
            json.setSuccess(false);
            json.setMsg("物料删除失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据物料编码或名称查询物料
     * @author：justin
     * @date：2019-10-15 15:29
     */
    @GetMapping(value = "/searchMaraList")
    public void searchMaraList(@RequestParam(name = "info", required = false) String info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            List<TMara> list = maraService.searchMaraList("%" + (StringUtil.isEmpty(info) ? "" : info) + "%");
            json.setObj(list);
            json.setSuccess(true);
            json.setMsg("获取数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据失败", e);
            json.setSuccess(false);
            json.setMsg("获取数据失败" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：导出excel
     * @author：justin
     * @date：2019-11-27 12:28
     */
    @RequestMapping("exportMara")
    public void exportMara(@RequestBody Mara mara, HttpServletResponse response) {
        Long t1 = System.currentTimeMillis();
        List<TMara> maraList = maraService.exportMaraList(mara);
        Long t2 = System.currentTimeMillis();
        logger.info("查询耗时{}秒", (t2 - t1) / 1000);
        Long t3 = System.currentTimeMillis();
        EasyPoiUtil.exportExcelXlsx(maraList, null, null, TMara.class, "物料基础数据", response);
        Long t4 = System.currentTimeMillis();
        logger.info("写入excel耗时{}秒", (t4 - t3) / 1000);
        logger.info("导出总耗时{}秒", (t4 - t1) / 1000);
    }

    /**
     * @description：测试批量保存物料信息
     * @author：justin
     * @date：2019-11-26 11:56
     */
    @GetMapping(value = "/initMara")
    public void initMara(HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            //构建大量测试数据
            List<TMara> maraList = new ArrayList<>();
//            for (int i = 1001000001; i <= 1001500000; i++) {
            int count = 1;
            for (int i = 1001500001; i <= 1001999999; i++) {
                TMara tm = new TMara();
                tm.setId(UUIDUtil.get32UUID());
                tm.setMatnr(String.valueOf(i));
                tm.setMaktx("测试物料" + i);
                tm.setMaktl("1001");
                tm.setUnit("个");
                tm.setNorms("测试规格");
                tm.setQualityType("Al类");
                tm.setManufacturer("测试制造商");
                tm.setPrice(new BigDecimal(0));
                tm.setCreateDate(new Date());
                tm.setStatus(StaticParam.ADD);
                maraList.add(tm);
                if (maraList.size() % 1000 == 0) {
                    logger.info(String.valueOf(maraList.size()));
                    maraService.saveMaraBatch(maraList);
                    logger.info("保存第{}个1000条数据", count);
                    count++;
                    maraList.clear();
                }
            }
            if (CollectionUtils.isNotEmpty(maraList)) {
                maraService.saveMaraBatch(maraList);
            }
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("保存物料异常", e);
        }
        writeJson(json, response);
    }


    /**
     * @description：分页查询金蝶系统物料
     * @author：justin
     * @date：2019-12-12 09:31
     */
    @RequestMapping(value = "/searchKdMaraList", method = RequestMethod.POST)
    public void searchKdMaraList(@RequestBody Mara mara, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(mara.getPageNum(), mara.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TMara> list = maraService.searchKdMaraList(mara);
                json.setObj(list);
                json.setSuccess(true);
            } else {
                json.setSuccess(false);
                json.setMsg(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("获取物料信息异常", e);
            json.setMsg("获取物料信息异常" + e.getMessage());
        }
        writeJson(json, response);
    }


}
