package com.vpis.common.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: PageableResponse
 *  @package: com.entertainment.common.page
 *  @Date: Created in 2018/7/20 下午5:08
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class PageableResponse<T> extends BaseBean {


    @JSONField(name = "total_count")
    @JsonProperty("total_count")
    private long totalCount;

    @JSONField(name = "total_pages")
    @JsonProperty("total_pages")
    private int totalPages;

    @JSONField(name = "list")
    @JsonProperty("list")
    @JsonDeserialize(using = JsonDeserializer.class)
    private List<T> list;
}