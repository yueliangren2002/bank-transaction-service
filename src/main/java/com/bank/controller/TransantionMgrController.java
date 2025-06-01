package com.bank.controller;

import com.bank.entity.qo.TransactionAddQo;
import com.bank.entity.qo.TransactionListQo;
import com.bank.entity.qo.TransactionUpdateQo;
import com.bank.entity.vo.CommonResult;
import com.bank.entity.vo.PageInfo;
import com.bank.entity.vo.TransactionVo;
import com.bank.service.TransactionService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransantionMgrController {

    @Resource
    private TransactionService transactionServices;

    /**
     * 增加交易信息
     *
     * @param transactionAddQo
     * @return
     */
    @PostMapping(value = "/add")
    public CommonResult<Integer> createTransaction(@Valid @RequestBody TransactionAddQo transactionAddQo
    ) {
        try {
            int result = transactionServices.addTransaction(transactionAddQo);
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public CommonResult<Integer> updateTransaction(@Valid @RequestBody TransactionUpdateQo transactionUpdateQo) {
        try {
            int result = transactionServices.updateTransaction(transactionUpdateQo);
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }

    @DeleteMapping(value = "/delete")
    public CommonResult<Integer> deleteTransaction(@PathParam("id") Long id) {
        try {
            int result = transactionServices.deleteTransaction(id);
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }

    @GetMapping(value = "/page/list")
    public CommonResult<PageInfo<TransactionVo>> queryTransactionPageList(@Valid @RequestBody TransactionListQo transactionListQo
    ) {
        try {
            PageInfo<TransactionVo> pageList = transactionServices.queryTransactionList(transactionListQo);
            return CommonResult.success(pageList);
        } catch (Exception e) {
            return CommonResult.failed(e.getMessage());
        }
    }

}
