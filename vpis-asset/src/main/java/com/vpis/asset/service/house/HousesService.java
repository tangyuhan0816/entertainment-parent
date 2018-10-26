package com.vpis.asset.service.house;

import com.vpis.asset.bean.house.HouseBean;
import com.vpis.asset.bean.vo.HouseVo;
import com.vpis.asset.controller.sys.LoginController;
import com.vpis.asset.dao.houses.HousesDao;
import com.vpis.asset.repository.house.HousesRepository;
import com.vpis.common.entity.house.Houses;
import com.vpis.common.exception.BusinessException;
import com.vpis.common.exception.STException;
import com.vpis.common.page.PageableResponse;
import com.vpis.common.page.SortDirection;
import com.vpis.common.page.SortField;
import com.vpis.common.utils.PageableConverter;
import com.vpis.common.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HousesService
 *  @package: com.vpis.asset.service.house
 *  @Date: Created in 2018/10/26 上午11:57
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Service
public class HousesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HousesRepository housesRepository;

    @Autowired
    private HousesDao housesDao;

    public PageableResponse<HouseVo> page(HouseBean bean) {

        Page<Houses> page = null;

        if (Preconditions.isBlank(bean.getPageableRequest())) {
            throw new BusinessException("page is null");
        }

        PageableResponse<HouseVo> response = new PageableResponse<>();

        List<SortField> sortFields = new ArrayList<>();

        SortField sortField = new SortField();

        sortField.setDirection(SortDirection.DESC);

        sortFields.add(sortField);

        bean.getPageableRequest().setSortFields(sortFields);

        if (bean.getIsHeat()) {

            sortField.setFieldName("heat");

            PageRequest pageRequest = PageableConverter.toPageRequest(bean.getPageableRequest());

            page = housesRepository.findByDeletedIsFalse(pageRequest);

        } else if (bean.getIsPrice()) {

            sortField.setFieldName("averagePrice");

            PageRequest pageRequest = PageableConverter.toPageRequest(bean.getPageableRequest());

            page = housesRepository.findByDeletedIsFalse(pageRequest);

        } else if (bean.getIsNear()) {

            return housesDao.near(bean.getLongitudeX(), bean.getLatitudeY(), bean.getPageableRequest().getPageNumber(), bean.getPageableRequest().getPageSize());

        } else {

            throw new STException("error");
        }

        List<HouseVo> list = new ArrayList<>();

        for (Houses houses : page.getContent()) {

            HouseVo houseVo = new HouseVo();

            BeanUtils.copyProperties(houses, houseVo);

            list.add(houseVo);
        }

        response.setTotalPages(page.getTotalPages());

        response.setTotalCount(page.getTotalElements());

        response.setList(list);

        return response;
    }

}
