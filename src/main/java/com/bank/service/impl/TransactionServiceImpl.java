package com.bank.service.impl;

import com.bank.entity.qo.TransactionAddQo;
import com.bank.entity.qo.TransactionListQo;
import com.bank.entity.qo.TransactionUpdateQo;
import com.bank.entity.vo.PageInfo;
import com.bank.entity.vo.TransactionVo;
import com.bank.exception.BusinessException;
import com.bank.service.CaffinService;
import com.bank.service.TransactionService;
import com.bank.util.IdGenerator;
import com.bank.util.PageUtils;
import com.bank.util.SnowflakeIdGenerator;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {


    @Resource
    private CaffinService caffinService;

    @Resource
    private SnowflakeIdGenerator snowflakeIdGenerator;


    @Override
    public int addTransaction(TransactionAddQo transactionAddQo) throws BusinessException {
        //生成交易id
        if (Objects.isNull(transactionAddQo.getId())) {
            transactionAddQo.setId(snowflakeIdGenerator.nextId());
        }
        //判断是否重复添加
        if (Objects.nonNull(queryTransactionById(transactionAddQo.getId()))) {
            log.error("Add transaction repeatedly!id:{}.",transactionAddQo.getId());
            throw new BusinessException("Add transaction repeatedly!");
        }

        TransactionVo transactionVo = new TransactionVo();
        BeanUtils.copyProperties(transactionAddQo, transactionVo);
        caffinService.put(transactionAddQo.getId(), transactionVo);
        return 0;
    }

    @Override
    public int updateTransaction(TransactionUpdateQo transactionUpdateQo) throws BusinessException {
        TransactionVo existTransaction = queryTransactionById(transactionUpdateQo.getId());
        if (Objects.isNull(existTransaction)) {
            log.error("Update transaction error!id:{} is not exist!", transactionUpdateQo.getId());
            throw new BusinessException("Update transaction error!id:" + transactionUpdateQo.getId() + " is not exist");
        }
        //先清除原交易缓存
        deleteTransaction(transactionUpdateQo.getId());
        //再新增交易缓存
        TransactionAddQo transactionAddQo = new TransactionAddQo();
        BeanUtils.copyProperties(transactionUpdateQo, transactionAddQo);
        return addTransaction(transactionAddQo);
    }

    @Override
    public int deleteTransaction(Long id) throws BusinessException {
        TransactionVo transactionVo = queryTransactionById(id);
        if (Objects.isNull(transactionVo)) {
            log.error("Delete transaction error!id:{} is not exist!", id);
            throw new BusinessException("Delete transaction error!id:" + id + " is not exist");
        }
        return caffinService.remove(id);
    }

    @Override
    public PageInfo<TransactionVo> queryTransactionList(TransactionListQo transactionListQo) {
        List<TransactionVo> transactionVoList = caffinService.getList(transactionListQo.getIds());
        //当前页码参数校验
        if (Objects.isNull(transactionListQo.getPageNum()) || transactionListQo.getPageNum() <= 0) {
            transactionListQo.setPageNum(PageUtils.DEAULT_PAGE_NUM);
        }
        //分页大小参数校验
        if (Objects.isNull(transactionListQo.getPageSize()) || transactionListQo.getPageSize() <= 0) {
            transactionListQo.setPageSize(PageUtils.DEAULT_PAGE_SIZE);
        }
        return PageUtils.list2PageInfo(transactionVoList, transactionListQo.getPageNum(),
                transactionListQo.getPageSize(), transactionVoList.size());
    }

    @Override
    public TransactionVo queryTransactionById(Long id) {
        Optional<TransactionVo> optional = caffinService.get(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;

    }
}
