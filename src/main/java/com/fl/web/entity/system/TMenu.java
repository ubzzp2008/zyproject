package com.fl.web.entity.system;

import com.fl.web.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：TMenu
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-10-09 11:06
 */
@Getter
@Setter
public class TMenu extends BaseEntity {
    private String id;
    private String name;// 菜单名称
    private Integer seq;// 显示顺序
    private String lockFlag;// 状态：0 启用; 1：禁用
    private String url;// 菜单地址
    private Integer levele;//层级
    private String pid;// 父级id
    private String pname;// 父级菜单
    private String icon;//图标名称
    private String description;// 描述
    private List<TMenu> childMenuList;// 下级菜单集合
    private boolean isChecked;// 是否选中
    private String checkFlag;
    private boolean open;// 是否展开

}
