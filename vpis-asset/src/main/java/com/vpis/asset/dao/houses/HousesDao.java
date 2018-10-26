package com.vpis.asset.dao.houses;


import com.vpis.asset.bean.vo.HouseVo;
import com.vpis.common.entity.house.Houses;
import com.vpis.common.page.PageableResponse;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HousesDao
 *  @package: com.vpis.asset.dao.houses
 *  @Date: Created in 2018/10/26 下午4:01
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Component
public class HousesDao {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PageableResponse<HouseVo> near(BigDecimal longitudeX, BigDecimal latitudeY, Integer pageNumber, Integer pageSize){

        PageableResponse<HouseVo> response = new PageableResponse<>();

        Map<String ,Object> params = new HashMap<>(7);
        params.put("lonX",longitudeX);
        params.put("latY",latitudeY);

        String sqlCount = "select count(1) from (SELECT (st_distance (point (longitude_x, latitude_y),point(:lonX,:latY) ) *111195) distance FROM  houses s where s.deleted = 0 HAVING distance < 50) m;";
        Long count = jdbcTemplate.queryForObject(sqlCount,params,Long.class);

        if(Preconditions.isNotBlank(count) && count > 0){
            if(pageNumber < 0){
                pageNumber = 0;
            }
            params.put("pageNumber", pageNumber * pageSize);
            params.put("pageSize",pageSize);
            String sqlPage = "SELECT  s.house_name,s.average_price,s.advice_num,s.banner_url,s.heat,(st_distance (point (longitude_x, latitude_y),point(:lonX,:latY) ) *111195) AS distance  FROM  houses s where s.deleted = 0  HAVING distance < 50 ORDER BY distance limit :pageNumber, :pageSize;";
            List<HouseVo>  list = jdbcTemplate.query(sqlPage,params, new BeanPropertyRowMapper<>(HouseVo.class));
            for(HouseVo houseVo : list){
                houseVo.setDistance(houseVo.getDistance().setScale(1, BigDecimal.ROUND_HALF_DOWN));
            }
            response.setList(list);

        }
        response.setTotalCount(Preconditions.isBlank(count) ? 0 : count);
        response.setTotalPages(pageNumber);
        return response;
    }
}
