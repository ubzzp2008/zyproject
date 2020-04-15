package com.fl.web.model.base;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * 类名称：com.yunhan.scc.trading.web.model.system.moduleTree
 * 类描述：用于功能权限树形菜单展示
 * 创建人：swb
 * 创建时间：2015-6-25 下午4:36:38
 * 修改人：
 * 修改时间：
 * 修改备注：
 * @version V0.1
 */
@Getter
@Setter
public class ModuleTree {
	private String id;
	private String code;
	private String name;
	private String description;
	private List<ModuleTree> childrenList;
	private boolean open;
	private boolean checked;
	private String pid;
	private String iconCls; //图标
	private String url;

}