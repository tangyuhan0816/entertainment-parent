package com.vpis.asset.bean;

import com.vpis.asset.bean.base.BaseBean;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SessionUser
 *  @package: com.vpis.asset.bean
 *  @Date: Created in 2018/10/30 下午3:34
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class SessionUser extends BaseBean{

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 代理区域编码
     */
    private String agentArea;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 手机号码
     */
    private String email;

}
