package com.bank.service;

import com.bank.entity.qo.TransactionAddQo;
import com.bank.entity.qo.TransactionListQo;
import com.bank.entity.qo.TransactionUpdateQo;
import com.bank.entity.vo.PageInfo;
import com.bank.entity.vo.TransactionVo;
import com.bank.exception.BusinessException;

public interface TransactionService {

    /**
     * 创建交易
     *
     * @param transactionAddQo
     * @return
     */
    public int addTransaction(TransactionAddQo transactionAddQo) throws BusinessException;

    /**
     * 修改交易
     *
     * @param transactionUpdateQo
     * @return
     */
    public int updateTransaction(TransactionUpdateQo transactionUpdateQo) throws BusinessException;

    /**
     * 删除交易
     *
     * @param id
     * @return
     */
    public int deleteTransaction(Long id) throws BusinessException;

    /**
     * 根据id交易查询
     *
     * @param
     * @return
     */

    public TransactionVo queryTransactionById(Long id);

    /**
     * 分页查询交易列表
     *
     * @param
     * @return
     */
    public PageInfo<TransactionVo> queryTransactionList(TransactionListQo transactionListQo);


}
