package com.fl.web.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：PositionTree
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-11-19 09:37
 */
@Getter
@Setter
public class BaseTree {
    private String value;
    private String label;
    private List<BaseTree> children;

}
