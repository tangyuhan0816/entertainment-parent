package com.vpis.asset.bean.house;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import com.vpis.common.page.PageableRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HouseBean
 *  @package: com.vpis.asset.bean.house
 *  @Date: Created in 2018/10/26 下午4:33
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
@ApiModel(value = "楼盘请求对象")
public class HouseBean extends BaseBean{

    @ApiModelProperty(value = "0 热度查询 1距离查询 2 价格查询")
    @JSONField(name = "type")
    @JsonProperty("type")
    private Integer type;

    @ApiModelProperty(value = "经度")
    @JSONField(name = "longitude_x")
    @JsonProperty("longitude_x")
    private BigDecimal longitudeX;

    @ApiModelProperty(value = "纬度")
    @JSONField(name = "latitude_y")
    @JsonProperty("latitude_y")
    private BigDecimal latitudeY;

    @ApiModelProperty(value = "分页")
    @JSONField(name = "page")
    @JsonProperty("page")
    private PageableRequest pageableRequest;
}
