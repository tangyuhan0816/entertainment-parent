package com.vpis.asset.service.sys;

import com.vpis.asset.repository.sys.MouldsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: MouldsService
 *  @package: com.vpis.asset.service.sys
 *  @Date: Created in 2018/10/30 下午7:16
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Service
public class MouldsService {

    @Autowired
    private MouldsRepository mouldsRepository;

    public void queryCommonMoulds(String agentId){

    }


}
