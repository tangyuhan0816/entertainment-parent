package com.vpis.asset.service.sys;

import com.vpis.asset.bean.sys.MouldsBean;
import com.vpis.asset.repository.sys.MouldsRepository;
import com.vpis.common.entity.sys.Moulds;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Moulds> findCommonMoulds(Long agentId){
        return mouldsRepository.findByUserIdAndDeletedIsFalse(agentId);
    }

    public List<Moulds> findMyMoulds(Long userId){
        return mouldsRepository.findByUserIdAndDeletedIsFalse(userId);
    }

    public Moulds saveMould(MouldsBean bean,Long userId){
        Moulds mould = new Moulds();
        if(Preconditions.isNotBlank(bean.getId())){
            Moulds m = mouldsRepository.findByIdAndUserIdAndDeletedIsFalse(bean.getId(),userId);
            BeanUtils.copyProperties(m,mould);
        } else {
            //用户自定义模版
            mould.setMouldType(1);
            mould.setUserId(userId);
        }
        BeanUtils.copyProperties(bean,mould);
        mould = mouldsRepository.save(mould);
        return mould;
    }

    public void del(Long id, Long userId){
        Moulds mould = mouldsRepository.findByIdAndUserIdAndDeletedIsFalse(id,userId);
        if(Preconditions.isBlank(mould)){
            throw new STException("mould not found");
        }
        mould.setDeleted(Boolean.TRUE);
        mouldsRepository.save(mould);
    }

    public Moulds findByIdAndUserId(Long id, Long userId){
        return mouldsRepository.findByIdAndUserIdAndDeletedIsFalse(id,userId);
    }

}
