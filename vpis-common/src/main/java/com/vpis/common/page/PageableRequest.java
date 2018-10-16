package com.vpis.common.page;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseBean;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: PageableRequest
 *  @package: com.entertainment.common.page
 *  @Date: Created in 2018/7/20 下午5:08
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class PageableRequest extends BaseBean {

    public PageableRequest() {
    }

    public PageableRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }


    @JsonProperty("page_number")
    @JSONField(name = "page_number")
    private int pageNumber;

    @JsonProperty("page_size")
    @JSONField(name = "page_size")
    private int pageSize;


    @JsonProperty("sort_fields")
    @JSONField(name = "sort_fields")
    private List<SortField> sortFields;

}
