package com.xiaxia.mallchat.common.user.service.Impl;

import com.xiaxia.mallchat.common.common.annotation.RedissonLock;
import com.xiaxia.mallchat.common.common.domain.enums.YesOrNoEnum;
import com.xiaxia.mallchat.common.common.service.LockService;
import com.xiaxia.mallchat.common.user.dao.UserBackpackDao;
import com.xiaxia.mallchat.common.user.domain.entity.ItemConfig;
import com.xiaxia.mallchat.common.user.domain.entity.UserBackpack;
import com.xiaxia.mallchat.common.user.domain.enums.IdempotentEnum;
import com.xiaxia.mallchat.common.user.domain.enums.ItemTypeEnum;
import com.xiaxia.mallchat.common.user.service.IUserBackpackService;
import com.xiaxia.mallchat.common.user.service.cache.ItemCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xfl
 * @date 2024/1/28 16:32
 */
@Service
public class IUserBackpackServiceImpl implements IUserBackpackService {

    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;

    @Override
    @RedissonLock(key = "#uid", waitTime = 5, unit = TimeUnit.SECONDS)
    @Transactional(rollbackFor = Exception.class)
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        //幂等校验
        String idempotent = getIdempotentEnum(itemId, idempotentEnum, businessId);
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {
            return ;
        }
        //业务的检查
        ItemConfig itemConfig = itemCache.getById(itemId);
        if(ItemTypeEnum.BADGE.getType().equals(itemConfig.getType())){
            Integer countByValidItemId = userBackpackDao.getCountByValidItemId(uid, itemId);
            //确保只有一个
            if (countByValidItemId > 0) {
                return ;
            }
        }
        //发放物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();

        userBackpackDao.save(insert);
    }

    private String getIdempotentEnum(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format(("%d_%d_%s"), itemId, idempotentEnum.getType(), businessId);
    }
}
