package com.vpis.asset.bean.house;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HouseBean
 *  @package: com.vpis.asset.bean.house
 *  @Date: Created in 2018/10/26 下午4:33
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
@ApiModel(value = "楼盘详情")
public class HouseDetailBean extends BaseBean {

    @ApiModelProperty(value = "是否按热度查询")
    @JSONField(name = "is_heat")
    @JsonProperty("is_heat")
    private Boolean isHeat;
}
