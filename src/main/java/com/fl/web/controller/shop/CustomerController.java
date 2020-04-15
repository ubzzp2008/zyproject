package com.fl.web.controller.shop;

import com.fl.web.controller.base.BaseController;
import com.fl.web.entity.shop.TCustomer;
import com.fl.web.model.shop.Customer;
import com.fl.web.service.shop.ICustomerService;
import com.fl.web.utils.AjaxJson;
import com.fl.web.utils.StringUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/shop/cust")
public class CustomerController extends BaseController {
    @Autowired
    private ICustomerService customerService;
    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 09:31
     */
    @PostMapping(value = "/queryCustomerList")
    public void queryCustomerList(@RequestBody Customer info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            String msg = validatePageParam(info.getPageNum(), info.getPageSize());
            if (StringUtil.isEmpty(msg)) {
                PageInfo<TCustomer> list = customerService.queryCustomerList(info);
                json.setSuccess(true);
                json.setObj(list);
            } else {
                json.setMsg(msg);
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            logger.error("系统异常", e);
            json.setMsg("系统异常" + e.getMessage());
        }
        writeJson(json, response);
    }
    /**
     * @description：检查编码是否存在
     * @author：justin
     * @date：2019-12-13 10:04
     */
    @GetMapping(value = "/checkPhoneExist")
    public void checkPhoneExist(@RequestParam(name = "phone") String phone, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            phone = phone.trim();
            TCustomer t = customerService.checkPhoneExist(phone);
            if (t != null) {
                json.setSuccess(false);
                json.setMsg("此手机号已经是会员，无需新增！");
            } else {
                json.setSuccess(true);
                json.setMsg("此手机号验证通过，可使用");
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
     * @description：保存数据
     * @author：justin
     * @date：2019-11-18 17:43
     */
    @PostMapping(value = "/saveCustomer")
    public void saveCustomer(@RequestBody TCustomer info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getPhone()) && StringUtil.isNotEmpty(info.getUserName())) {
                String phone = info.getPhone().trim();
                TCustomer t = customerService.checkPhoneExist(phone);
                if (t != null) {
                    json.setSuccess(false);
                    json.setMsg("保存失败！手机号：" + phone + "已经是会员！");
                } else {
                    customerService.saveCustomer(info);
                    json.setSuccess(true);
                    json.setMsg("保存成功");
                }
            } else {
                json.setSuccess(false);
                json.setMsg("保存失败！会员姓名和手机号不能为空！");
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
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:06
     */
    @GetMapping(value = "/getCustomerById")
    public void getCustomerById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            TCustomer p = customerService.getCustomerById(id);
            json.setSuccess(true);
            json.setObj(p);
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg("程序异常,请联系管理员!" + e.getMessage());
            e.printStackTrace();
            logger.error("系统异常", e);
        }
        writeJson(json, response);
    }

    /**
     * @description：修改组织信息
     * @author：justin
     * @date：2019-12-13 09:58
     */
    @PostMapping(value = "/updateCustomer")
    public void updateCustomer(@RequestBody TCustomer info, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            if (StringUtil.isNotEmpty(info.getId()) && StringUtil.isNotEmpty(info.getUserName()) && StringUtil.isNotEmpty(info.getPhone())) {
                customerService.updateCustomer(info);
                json.setSuccess(true);
                json.setMsg("修改成功");
            } else {
                json.setMsg("操作失败！id和会员姓名、手机号不能为空！");
                json.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-13 10:14
     */
    @PostMapping(value = "/deleteOrgById")
    public void deleteOrgById(@RequestParam(name = "id") String id, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
            customerService.deleteCustomer(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统异常", e);
            json.setSuccess(false);
            json.setMsg("系统异常：" + e.getMessage());
        }
        writeJson(json, response);
    }

}
