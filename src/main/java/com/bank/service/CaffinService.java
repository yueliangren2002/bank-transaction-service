package com.bank.service;

import com.bank.entity.vo.TransactionVo;

import java.util.List;
import java.util.Optional;

public interface CaffinService {
    /**
     * id、交易信息存缓存
     *
     * @param id
     * @param value
     */
    public void put(Long id, TransactionVo value);

    /**
     * 根据id查询缓存
     *
     * @param id
     * @return
     */
    public Optional<TransactionVo> get(Long id);

    /**
     * 根据id删除缓存
     *
     * @param id
     * @return
     */

    public int remove(Long id);

    /**
     * 查询缓存列表，如果ids为空查所有的，如果不为空根据id列表查。
     *
     * @param ids
     * @return
     */
    public List<TransactionVo> getList(List<Long> ids);
}
