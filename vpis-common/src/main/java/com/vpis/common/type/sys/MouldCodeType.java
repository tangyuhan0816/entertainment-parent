package com.vpis.common.type.sys;

import lombok.Getter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: MouldCodeType
 *  @package: com.vpis.common.type.sys
 *  @Date: Created in 2018/11/22 下午8:32
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 模版投放区域位置
 */
@Getter
public enum MouldCodeType {

    /**
     * 模版code
     */
    CODE_101(101, "图片"),
    CODE_102(102, "视频"),
    CODE_201(201, "2:1上图片下视频"),
    CODE_202(202, "2:1上视频下图片"),
    CODE_203(203, "2:1上图片下文字"),
    CODE_204(204, "2:1上视频下文字"),
    CODE_301(301, "1:1:1上文字中图片下视频"),
    CODE_302(302, "1:1:1上文字中视频下图片"),
    CODE_303(303, "1:1:1上视频中图片下文字"),
    CODE_304(304, "1:1:1上图片中文字下视频");

    private Integer code;
    private String text;

    MouldCodeType(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public static MouldCodeType getTypeByCode(Integer code) {

        for (MouldCodeType mouldCodeType : MouldCodeType.values()) {
            if (mouldCodeType.getCode().equals(code)) {
                return mouldCodeType;
            }
        }
        return null;
    }
}
