package com.fl.web.model.shop;

import com.fl.web.model.base.PageModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @version V0.1
 * @项目名称：demo-service
 * @类名称：Goods
 * @类描述：
 * @创建人：justin
 * @创建时间：2020-04-16 10:40
 */
@Getter
@Setter
public class Goods extends PageModel {
    private String goodsCode;
    private String goodsName;
}
