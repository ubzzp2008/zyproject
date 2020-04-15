package com.fl.web.utils;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：AuthorEnum
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-16 15:20
 */
public enum AuthorEnum {
    //    private static String REPAIRPOS = "repairPos";//维修员岗位编码-repairPos
//    private static String BIOLOGYPOS = "biologyPos";//分子生物验证员岗位编码-repairPos
//    val==1部门领导，获取所有下级岗位的数据，val==2只能获取本人相关数据
    BIOLOGYLEADERPOS("biologyLeaderPos", "1"),
    SERVICEPERSON("servicePerson", "2"),
    BIOLOGYPERSON("biologyPerson", "2");
    // 成员变量
    private String code;
    private String val;

    // 构造方法
    private AuthorEnum(String code, String val) {
        this.code = code;
        this.val = val;
    }

    // 普通方法
    public static String getVal(String code) {
        for (AuthorEnum c : AuthorEnum.values()) {
            if (StringUtil.equals(c.getCode(), code)) {
                return c.val;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }

}
