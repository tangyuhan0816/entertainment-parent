//package com.vpis.asset.entity.sys;
//
//
//import com.alibaba.fastjson.annotation.JSONField;
//import com.vpis.common.base.BaseEntity;
//import com.vpis.common.type.EmailType;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.persistence.*;
//
///**
// *  @Author: Yuhan.Tang
// *  @ClassName: EmailTemplate
// *  @package: com.entertainment.asset.entity.sys
// *  @Date: Created in 2018/8/3 下午4:35
// *  @email yuhan.tang@magicwindow.cn
// *  @Description:
// */
//@Data
//@Entity
//@Table(name = "email_template")
//public class EmailTemplate extends BaseEntity{
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @ApiModelProperty(value = "主键")
//    private Long id;
//
//    @JSONField(name = "content")
//    @JsonProperty("content")
//    @Column(name = "content")
//    private String content;
//
//    @JSONField(name = "type")
//    @JsonProperty("type")
//    @Column(name = "type")
//    private EmailType type;
//}
