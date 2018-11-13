package com.vpis.asset.dao.order;

import com.vpis.asset.bean.vo.OrderVo;
import com.vpis.common.page.PageableResponse;
import com.vpis.common.type.order.OrderStatus;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDao {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Long countOrder(Map<String, Object> params){
        StringBuilder sql = new StringBuilder();
        sql.append("select count(1) from `order` o where o.user_id = :userId ");

        if(Preconditions.isNotBlank(params.get("orderStatus"))){
            sql.append(" and o.order_status = :orderStatus ");
        }

        return jdbcTemplate.queryForObject(sql.toString(), params, Long.class);
    }

    public PageableResponse<OrderVo> findOrderList(Long userId, Integer pageNumber, Integer pageSize, Integer orderStatus){

        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);

        StringBuilder sql = new StringBuilder();
        sql.append("select o.id, h.house_name,om.product_count,o.order_amount_total from `order` o left join order_item om \n" +
                "on o.id = om.order_id left join house h \n" +
                "on h.id = om.product_id where o.user_id = :userId ");

        if(Preconditions.isNotBlank(orderStatus)){
            sql.append(" and o.order_status = :orderStatus ");
            params.put("orderStatus",orderStatus);
        }

        Long count = countOrder(params);

        List<OrderVo> list = new ArrayList<>();
        if(Preconditions.isNotBlank(count) && count > 0){

            if(pageNumber < 0){
                pageNumber = 0;
            }
            params.put("pageNumber", pageNumber * pageSize);
            params.put("pageSize", pageSize);

            sql.append(" order by o.create_date_time desc ");
            sql.append(" limit ");
            sql.append(" :pageNumber ");
            sql.append(",");
            sql.append(" :pageSize ");
            list = jdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(OrderVo.class));
        }

        PageableResponse<OrderVo> response = new PageableResponse<>();
        response.setTotalCount(count == null ? 0 : count);
        response.setTotalPages(pageNumber);
        response.setList(list);
        return response;
    }
}
