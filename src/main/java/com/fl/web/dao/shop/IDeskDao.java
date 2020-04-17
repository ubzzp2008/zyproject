package com.fl.web.dao.shop;

import com.fl.web.entity.shop.TDesk;
import com.fl.web.model.shop.Desk;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @version V0.1
 * @项目名称：hcmanager
 * @类名称：IGoodsDao
 * @类描述：
 * @创建人：justin
 * @创建时间：2019-12-13 09:34
 */
public interface IDeskDao {
    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-13 09:42
     */
    void saveDesk(TDesk desk);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    void updateDesk(TDesk desk);

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    @Delete("delete from desk_info  where id=#{id}")
    public void deleteDesk(@Param("id") String id);

    /**
     * @description：根据编码获取数据
     * @author：justin
     * @date：2019-12-13 09:53
     */
    @Select("select id,desk_code deskCode,desk_name deskName,sortnum from desk_info where desk_code=#{code}")
    @ResultType(TDesk.class)
    public TDesk checkCodeExist(@Param("code") String code);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:09
     */
    @Select("select id,desk_code deskCode,desk_name deskName,sortnum from desk_info where id=#{id}")
    @ResultType(TDesk.class)
    public TDesk getDeskById(@Param("id") String id);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 09:43
     */
    public List<TDesk> queryDeskList(Desk desk);

    /**
     * @description：获取可用桌号
     * @author：justin
     * @date：2019-10-10 09:43
     */
    public List<TDesk> getUseableDeskList();
}
