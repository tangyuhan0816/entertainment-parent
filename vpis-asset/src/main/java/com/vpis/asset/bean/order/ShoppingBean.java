package com.vpis.asset.bean.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import com.vpis.common.entity.house.House;
import lombok.Data;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ShoppingBean
 *  @package: com.vpis.asset.bean.order
 *  @Date: Created in 2018/11/19 下午1:25
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 购物车bean
 */
@Data
public class ShoppingBean extends BaseBean{

    @JSONField(name = "house_id")
    @JsonProperty("house_id")
    private Long houseId;

    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    private String houseName;

    @JSONField(name = "price")
    @JsonProperty("price")
    private BigDecimal price;

    @JSONField(name = "product_count")
    @JsonProperty("product_count")
    private Integer productCount;

    @JSONField(name = "person_number")
    @JsonProperty("person_number")
    private Integer personNumber;

    @JSONField(name = "banner_url")
    @JsonProperty("banner_url")
    private String bannerUrl;

    @JSONField(name = "create_date")
    @JsonProperty("create_date")
    private Date createDate;

//    @Override
//    public int hashCode(){
//        return this.getHouseId().hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return (this.getHouseId().equals(((ShoppingBean)obj).getHouseId()));
//    }
}
