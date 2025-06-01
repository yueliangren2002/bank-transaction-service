package com.bank.service.impl;

import com.bank.entity.vo.TransactionVo;
import com.bank.service.CaffinService;
import com.github.benmanes.caffeine.cache.Cache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class CaffinServiceImpl implements CaffinService {

    //缓存key前缀
    private final static String CACHE_KEY_PRE = "TRANSACTION_SERVICE:CACHE:";

    //cafine缓存对象
    @Resource(name = "transanction")
    private Cache<String, TransactionVo> transanctionCache;

    /**
     * id、交易信息存缓存
     *
     * @param id
     * @param value
     */
    @Override
    public void put(Long id, TransactionVo value) {
        transanctionCache.put(generateKeyById(id), value);
    }

    /**
     * 根据id查询缓存
     *
     * @param id
     * @return
     */
    @Override
    public Optional<TransactionVo> get(Long id) {
        TransactionVo vo = transanctionCache.getIfPresent(generateKeyById(id));
        if (null == vo) {
            return Optional.empty();
        }
        return Optional.of(vo);
    }

    /**
     * 根据id删除缓存
     *
     * @param id
     * @return
     */
    @Override
    public int remove(Long id) {
        TransactionVo vo = transanctionCache.asMap().remove(generateKeyById(id));
        return Objects.nonNull(vo) ? 1 : 0;
    }

    /**
     * 查询缓存列表，如果ids为空查所有的，如果不为空根据id列表查。
     *
     * @param ids
     * @return
     */
    @Override
    public List<TransactionVo> getList(List<Long> ids) {
        Map<String, TransactionVo> allCachesMap = null;
        if (CollectionUtils.isEmpty(ids)) {
            allCachesMap = transanctionCache.asMap();
        } else {
            List<String> keys = new ArrayList<>();
            ids.stream().forEach(id -> {
                keys.add(generateKeyById(id));
            });
            allCachesMap = transanctionCache.getAllPresent(keys);
        }

        if (CollectionUtils.isEmpty(allCachesMap)) {
            if (!CollectionUtils.isEmpty(ids)) {
                log.warn("getList result empty.ids:{}", ids.toArray());
            } else {
                log.warn("getList result empty.ids is empty!");
            }

            return new ArrayList<>();
        }
        return allCachesMap.values().stream().toList();
    }

    private String generateKeyById(Long id) {
        return CACHE_KEY_PRE + id;
    }
}
