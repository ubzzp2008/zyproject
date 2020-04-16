package com.fl.web.dao.shop;

import com.fl.web.entity.shop.TGoods;
import com.fl.web.model.shop.Goods;
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
public interface IGoodsDao {
    /**
     * @description：保存数据
     * @author：justin
     * @date：2019-12-13 09:42
     */
    void saveGoods(TGoods goods);

    /**
     * @description：修改数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    void updateGoods(TGoods goods);

    /**
     * @description：删除数据
     * @author：justin
     * @date：2019-12-13 09:43
     */
    @Delete("delete from goods_info  where id=#{id}")
    public void deleteGoods(@Param("id") String id);

    /**
     * @description：根据编码获取数据
     * @author：justin
     * @date：2019-12-13 09:53
     */
    @Select("select id,goods_code goodsCode,goods_name goodsName,unit,price,dis_price disPrice,sortnum from goods_info where goods_code=#{code}")
    @ResultType(TGoods.class)
    public TGoods checkCodeExist(@Param("code") String code);

    /**
     * @description：根据id获取数据
     * @author：justin
     * @date：2019-12-13 10:09
     */
    @Select("select id,goods_code goodsCode,goods_name goodsName,unit,price,dis_price disPrice,sortnum from goods_info where id=#{id}")
    @ResultType(TGoods.class)
    public TGoods getGoodsById(@Param("id") String id);

    /**
     * @description：分页查询
     * @author：justin
     * @date：2019-10-10 09:43
     */
    public List<TGoods> queryGoodsList(Goods goods);
}
