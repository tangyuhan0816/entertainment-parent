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

    @ApiModelProperty(value = "是否按热度查询")
    @JSONField(name = "is_heat")
    @JsonProperty("is_heat")
    private Boolean isHeat;

    @ApiModelProperty(value = "是否按距离查询")
    @JSONField(name = "is_near")
    @JsonProperty("is_near")
    private Boolean isNear;

    @ApiModelProperty(value = "是否按价格查询")
    @JSONField(name = "is_price")
    @JsonProperty("is_price")
    private Boolean isPrice;

    @ApiModelProperty(value = "经度")
    @JSONField(name = "longitude_x")
    @JsonProperty("longitude_x")
    private BigDecimal longitudeX;

    @ApiModelProperty(value = "纬度")
    @JSONField(name = "latitude_y")
    @JsonProperty("latitude_y")
    private BigDecimal latitudeY;

    @ApiModelProperty(value = "区域编码")
    @JSONField(name = "area_code")
    @JsonProperty("area_code")
    private String areaCode;

    @ApiModelProperty(value = "分页")
    @JSONField(name = "page")
    @JsonProperty("page")
    private PageableRequest pageableRequest;
}
