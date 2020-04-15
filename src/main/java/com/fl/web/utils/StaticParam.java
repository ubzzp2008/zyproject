package com.fl.web.utils;

/**
 * @version V0.1
 * @项目名称：hcproject
 * @类名称：StaticParam
 * @类描述：
 * @创建人：justin
 * @创建时间：2018-12-28 19:25
 */
public class StaticParam {
    /**
     * 组织根的数据库ID值
     */
    public final static String ROOT_ORG = "99999";
    /**
     * 岗位根的数据库ID值
     */
    public final static String ROOT_POS = "10000";
    /**
     * 主岗位标识-M
     */
    public final static String POS_M = "M";
    /**
     * 子岗位标识-S
     */
    public final static String POS_S = "S";
    /**
     * 维修员岗位编码-repairPos
     */
    public final static String REPAIRPOS = "servicePerson";
    public final static String AUTHALL = "0";//所有数据权限
    public final static String AUTHDEPT = "1";//当前部门数据权限
    public final static String AUTHONLY = "2";//仅自己数据权限


    /*******************************数据状态***********************************************/
    /**
     * 数据状态：新增---ADD--009
     */
    public final static String ADD = "009";
    /**
     * 数据状态：修改---MOD--004
     */
    public final static String MOD = "004";
    /**
     * 数据状态：删除---DEL--003
     */
    public final static String DEL = "003";
    /*******************************数据字典类型编码***********************************************/
    /**
     * 装机类型字典编码 --- HC001
     */
    public final static String HCZJFS = "HC001";
    /**
     * 维修单状态字典编码 --- HC002
     */
    public final static String HCROS = "HC002";
    /**
     * 维修类型字典编码 --- HC003
     */
    public final static String HCDT = "HC003";
    /**
     * 维修部件状态字典编码 --- HC004
     */
    public final static String HCBJS = "HC004";
    /**
     * 快递方式数据字典编码 --- HC005
     */
    public final static String HCEXP = "HC005";
    /**
     * 单位数据字典编码 --- HC006
     */
    public final static String HCUNIT = "HC006";
    /**
     * 菜单图标数据字典编码 --- HC007
     */
    public final static String HCICON = "HC007";
    /**
     * 采购订单单据类型数据字典编码 --- HC008
     */
    public final static String HCORDTYPE = "HC008";
    /**
     * 采购订单状态数据字典编码 --- HC009
     */
    public final static String HCREQ = "HC009";
    /**
     * 采购申请单状态数据字典编码 --- HC010
     */
    public final static String HCORD = "HC010";

    /*******************************流水号类型***********************************************/
    /**
     * 流水号类型：维修单单号 --- W
     */
    public final static String SN_W = "W";
    /**
     * 流水号类型：送检单号 --- JY
     */
    public final static String SN_JY = "JY";
    /**
     * 流水号类型：送检单批号 --- HL
     */
    public final static String SN_HL = "HL";
    /**
     * 流水号类型：采购申请单号 --- REQ
     */
    public final static String SN_REQ = "REQ";
    /**
     * 流水号类型：采购订单号 --- PO
     */
    public final static String SN_PO = "PO";
    /**
     * 流水号类型：入库单 --- WIN
     */
    public final static String WIN = "WIN";
    public final static String IN = "IN";
    /**
     * 流水号类型：出库单 --- WOUT
     */
    public final static String WOUT = "WOUT";
    public final static String OUT = "OUT";

    /**
     * 流水号类型：流水号4位数 --- 0001
     */
    public final static String SN_4 = "0001";
    /**
     * 流水号类型：流水号3位数 --- 001
     */
    public final static String SN_3 = "001";

    /*******************************采购申请单状态***********************************************/
    /**
     * 采购申请单标识：旧物料流程---OLD
     */
    public final static String REQ_OLD = "OLD";
    /**
     * 采购申请单标识：新物料流程---NEW
     */
    public final static String REQ_NEW = "NEW";
    /**
     * 采购申请单状态：待提交（已保存）---0
     */
    public final static String REQ_0 = "0";
    public final static String REQ_1 = "1";//新物料待上级领导确认
    public final static String REQ_2 = "2";//新物料待质管部确认
    public final static String REQ_3 = "3";//新物料询价待领取
    public final static String REQ_4 = "4";//新物料待询价
    public final static String REQ_5 = "5";
    /**
     * 采购申请单状态：待部门领导审核（已提交）---10
     */
    public final static String REQ_10 = "10";
    /**
     * 采购申请单状态：待采购部确认（待采购部审批）---20
     */
    public final static String REQ_20 = "20";
    /**
     * 采购申请单状态：待下单（待采购下单）---30
     */
    public final static String REQ_30 = "30";
    /**
     * 采购申请单状态：已下单（已生成正式订单）---50
     */
    public final static String REQ_50 = "50";
    /*******************************采购订单状态***********************************************/
    /**
     * 采购订单状态：待提交（已保存）---0
     */
    public final static String ORD_5 = "5";
    /**
     * 采购订单状态：待部门领导审核（已提交）---10
     */
    public final static String ORD_10 = "10";
    /**
     * 采购订单状态：待收货（采购部领导审批通过）---20
     */
    public final static String ORD_20 = "20";
    /**
     * 采购订单状态：部分收货---21
     */
    public final static String ORD_21 = "21";
    /**
     * 采购订单状态：差异收货 ---22
     */
    public final static String ORD_22 = "22";
    /**
     * 采购订单状态：完全收货 ---30
     */
    public final static String ORD_30 = "30";
    /**
     * 采购订单状态：待下单（待采购下单）---30
     */
//    public final static String REQ_30 = "30";
    /**
     * 采购订单状态：已下单（已生成正式订单）---50
     */
//    public final static String REQ_50 = "50";

    /*******************************维修单状态***********************************************/
    /**
     * 维修单状态：待确认（已上报待确认）--- 0
     */
    public final static String ROS_0 = "0";
    /**
     * 维修单状态：待审批（已审批待领取）--- 5
     */
    public final static String ROS_5 = "5";
    /**
     * 维修单状态：待处理（待填写维修方案）--- 10
     */
    public final static String ROS_10 = "10";
    /**
     * 维修单状态：维修方案待审批--- 15
     */
    public final static String ROS_15 = "15";
    /**
     * 维修单状态：维修中--- 20
     */
    public final static String ROS_20 = "20";
    /**
     * 维修单状态：待部门领导确认--- 25
     */
    public final static String ROS_25 = "25";
    /**
     * 维修单状态：待质管部确认--- 30
     */
    public final static String ROS_30 = "30";
    /**
     * 维修单状态：已完成--- 50
     */
    public final static String ROS_50 = "50";
    /**
     * 维修单状态：已作废--- 99
     */
    public final static String ROS_99 = "99";


    /*******************************维修类型***********************************************/
    /**
     * 维修类型：现场维修--- DT01
     */
    public final static String DT01 = "DT01";
    /**
     * 维修类型：移除报废--- DT02
     */
    public final static String DT02 = "DT02";
    /**
     * 维修类型：移除退坏--- DT03
     */
    public final static String DT03 = "DT03";
    /**
     * 维修类型：新增部件--- DT04
     */
    public final static String DT04 = "DT04";
    /**
     * 维修类型：报废换新--- DT05
     */
    public final static String DT05 = "DT05";
    /**
     * 维修类型：退坏换新--- DT06
     */
    public final static String DT06 = "DT06";
    /**
     * 维修类型：维护保养--- DT07
     */
    public final static String DT07 = "DT07";

    /*******************************维修部件流程***********************************************/
    /**
     * 维修部件流程：退坏流程--- OLD
     */
    public final static String MF_OLD = "OLD";
    /**
     * 维修部件流程：换新流程--- NEW
     */
    public final static String MF_NEW = "NEW";

    /*******************************维修单行项目状态***********************************************/
    /**
     * 维修单行项目状态：待审批--- 0
     */
    public final static String RIS00 = "0";
    /**
     * 维修单行项目状态：退坏中--- 5
     */
    public final static String RIS05 = "5";
    /**
     * 维修单行项目状态：换新中--- 10
     */
    public final static String RIS10 = "10";
    /**
     * 维修单行项目状态：已完成--- 20
     */
    public final static String RIS20 = "20";

    /*******************************维修部件状态***********************************************/
    /**
     * 维修部件状态：坏件待邮寄--- 5
     */
    public final static String BJS05 = "5";
    /**
     * 维修部件状态：待部门领导审批--- 10
     */
    public final static String BJS10 = "10";
    /**
     * 维修部件状态：待发货审批--- 20
     */
    public final static String BJS20 = "20";
    /**
     * 维修部件状态：待收货--- 30
     */
    public final static String BJS30 = "30";
    /**
     * 维修部件状态：已完成（已收货）--- 50
     */
    public final static String BJS50 = "50";


    /*******************************物料送检状态***********************************************/
    /**
     * 送检状态：待质管部收货---0
     */
    public final static String SJ_0 = "0";
    /**
     * 送检状态：待检测结果录入---10
     */
    public final static String SJ_10 = "10";
    /**
     * 送检状态：质检不合格待确认---15
     */
    public final static String SJ_15 = "15";
    /**
     * 送检状态：待生成质检单---20
     */
    public final static String SJ_20 = "20";
    /**
     * 送检状态：质检完成待转入库--30
     */
    public final static String SJ_30 = "30";
    /**
     * 送检状态：已转待入库--50
     */
    public final static String SJ_50 = "50";
    /**
     * 送检状态：已入库--60
     */
    public final static String SJ_60 = "60";

    /*******************************物料退换货状态***********************************************/
    /**
     * 物料退换货状态：质检退回---0
     */
    public final static String MB_0 = "0";
    /**
     * 物料退换货状态：质检不合格待处理---5
     */
    public final static String MB_5 = "5";
    /**
     * 物料退换货状态：已换货--30
     */
    public final static String MB_30 = "30";
    /**
     * 物料退换货状态：已退货---50
     */
    public final static String MB_50 = "50";
    /**
     * 物料退换货状态：已报废---99
     */
    public final static String MB_99 = "99";

    /*******************************收货数据状态***********************************************/
    /**
     * 收货数据状态：待确认送检或转入库---0
     */
    public final static String DELIV_0 = "0";
    /**
     * 收货数据状态：已送质检部---10
     */
    public final static String DELIV_10 = "10";
    /**
     * 收货数据状态：质检部收货退回---11
     */
    public final static String DELIV_11 = "11";
    /**
     * 收货数据状态：免检待入库（已送仓库）---20
     */
    public final static String DELIV_20 = "20";
    /**
     * 收货数据状态：质检待入库（已送仓库）---30
     */
    public final static String DELIV_30 = "30";
    /**
     * 收货数据状态：已入库---50
     */
    public final static String DELIV_50 = "50";
    /**
     * 收货数据状态：仓库退回---21
     */
//    public final static String DELIV_21 = "21";
    /**
     * 收货数据状态：差异收货数据---99
     */
    public final static String DELIV_99 = "99";
    /**
     * 收货数据状态：质检不合格待处理---5
     */
//    public final static String MB_5 = "5";
    /**
     * 收货数据状态：已换货--30
     */
//    public final static String MB_30 = "30";
    /**
     * 收货数据状态：已退货---50
     */
//    public final static String MB_50 = "50";
    /**
     * 收货数据状态：已报废---99
     */
//    public final static String MB_99 = "99";

    /*******************************物料领用申请单数据状态***********************************************/
    /**
     * 物料领用申请单数据状态：待提交---0
     */
    public final static String ST_0 = "0";
    /**
     * 物料领用申请单数据状态：待部门领导审批---10
     */
    public final static String ST_10 = "10";
    /**
     * 物料领用申请单数据状态：待仓库确认---20
     */
    public final static String ST_20 = "20";
    /**
     * 物料领用申请单数据状态：已完成（已生成出库单）---50
     */
    public final static String ST_50 = "50";
    /**
     * 物料领用申请单数据状态：已作废---99
     */
    public final static String ST_99 = "99";
}
