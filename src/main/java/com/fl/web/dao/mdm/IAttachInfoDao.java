package com.fl.web.dao.mdm;

import com.fl.web.entity.mdm.TAttachInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IAttachInfoDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-05 17:04
 */
public interface IAttachInfoDao {
    /**
     * @description：批量保存附件信息
     * @author：justin
     * @date：2019-12-05 17:12
     */
    public void saveAttachInfoBatch(List<TAttachInfo> list);

    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-06 14:34
     */
    public void saveAttachInfo(TAttachInfo info);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-06 15:39
     */
    @Select("select id,order_no orderNo,table_id tableId,table_name tableName,file_name fileName,file_type fileType," +
            "file_size fileSize,file_path filePath,old_name oldName,flag from attach_info where id=#{id}")
    @ResultType(TAttachInfo.class)
    public TAttachInfo getAttachInfoById(@Param("id") String id);

    /**
     * @description：根据id删除数据
     * @author：justin
     * @date：2019-12-06 15:40
     */
    @Delete("delete from attach_info where id=#{id}")
    public void deleteAttachInfoById(@Param("id") String id);


    /**
     * @description：根据单据号获取数据
     * @author：justin
     * @date：2019-12-06 15:39
     */
    @Select("select id,order_no orderNo,table_id tableId,table_name tableName,file_name fileName,file_type fileType," +
            "file_size fileSize,file_path filePath,old_name oldName,flag from attach_info where order_no=#{orderNo}")
    @ResultType(TAttachInfo.class)
    public List<TAttachInfo> getAttachInfoByOrderNo(@Param("orderNo") String orderNo);

    /**
     * @description：根据tableId获取数据
     * @author：justin
     * @date：2019-12-06 15:39
     */
    @Select("select id,order_no orderNo,table_id tableId,table_name tableName,file_name fileName,file_type fileType," +
            "file_size fileSize,file_path filePath,old_name oldName,flag from attach_info where table_id=#{tableId}")
    @ResultType(TAttachInfo.class)
    public List<TAttachInfo> getAttachInfoByTableId(@Param("tableId") String tableId);

    /**
     * @description：根据tableId和flag检查数据是够存在
     * @author：justin
     * @date：2019-12-10 14:41
     */
    @Select("select count(1) from attach_info where table_id=#{tableId} and flag=#{flag}")
    @ResultType(Integer.class)
    public Integer checkAttachInfoByTableId(@Param("tableId") String tableId, @Param("flag") String flag);
}
