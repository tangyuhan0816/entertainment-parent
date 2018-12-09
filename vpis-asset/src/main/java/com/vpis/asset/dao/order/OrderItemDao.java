package com.vpis.asset.dao.order;

import com.vpis.asset.bean.vo.OrderItemHouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderItemDao
 *  @package: com.vpis.asset.dao.order
 *  @Date: Created in 2018/12/9 下午3:55
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Component
public class OrderItemDao {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<OrderItemHouseVo> findPage(Long orderId){
        Map<String, String> params = new HashMap<>();
        params.put("orderId", String.valueOf(orderId));
        String sql = "select h.house_name,h.banner_url,om.product_count,om.product_amount_total from order_item om  left join house h \n" +
                " on h.id = om.product_id where om.order_id = :orderId and om.deleted = 0 and h.deleted = 0";
        return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(OrderItemHouseVo.class));
    }
}
