package com.bank.service;

import com.bank.entity.vo.TransactionVo;

import java.util.List;
import java.util.Optional;

public interface CaffinService {
    public void put(Long id, TransactionVo value);

    public Optional<TransactionVo> get(Long id);

    public int remove(Long id);

    public List<TransactionVo> getList(List<Long> ids);
}
