package com.vpis.common.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SortField
 *  @package: com.entertainment.common.page
 *  @Date: Created in 2018/7/20 下午5:08
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class SortField extends BaseBean {

    public SortField() {
    }

    public SortField(String fieldName) {
        this(SortDirection.ASC, fieldName);
    }

    public SortField(SortDirection direction, String fieldName) {
        this.direction = direction;
        this.fieldName = fieldName;
    }


    @JsonProperty("direction")
    @JSONField(name = "direction")
    private SortDirection direction;

    @JsonProperty("field_name")
    @JSONField(name = "field_name")
    private String fieldName;

}
