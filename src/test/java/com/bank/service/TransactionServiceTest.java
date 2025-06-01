package com.bank.service;

import com.bank.BankTransactionApplication;
import com.bank.entity.qo.TransactionAddQo;
import com.bank.entity.qo.TransactionListQo;
import com.bank.entity.qo.TransactionUpdateQo;
import com.bank.entity.vo.PageInfo;
import com.bank.entity.vo.TransactionVo;
import com.bank.exception.BusinessException;
import jakarta.annotation.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankTransactionApplication.class)
public class TransactionServiceTest {

    private static final Long TEST_TRANSACTION_ID = 1l;
    @Resource
    private TransactionService transactionServices;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        TransactionVo transactionVo = transactionServices.queryTransactionById(TEST_TRANSACTION_ID);
        if (Objects.nonNull(transactionVo)) {
            transactionServices.deleteTransaction(TEST_TRANSACTION_ID);
        }

    }

    /**
     * 正常添加交易异常
     *
     * @throws BusinessException
     */
    @Test
    public void addTransaction_success() throws BusinessException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createTime = sdf.parse("2025-05-01 01:00:00");
        TransactionAddQo transactionAddQo = TransactionAddQo.builder().id(TEST_TRANSACTION_ID).type("person")
                .createTime(createTime)
                .amount(new BigDecimal(1.0))
                .build();
        transactionServices.addTransaction(transactionAddQo);

    }

    /**
     * 测试重复添加交易异常
     *
     * @throws BusinessException
     */
    @Test
    public void addTransaction_add_same_transaction_exception() throws BusinessException {
        TransactionAddQo transactionAddQo = TransactionAddQo.builder().id(TEST_TRANSACTION_ID).type("person")
                .amount(new BigDecimal(1.0))
                .build();
        transactionServices.addTransaction(transactionAddQo);

        assertThrows(BusinessException.class, () -> {
            transactionServices.addTransaction(transactionAddQo);
        });

    }

    /**
     * 测试更新交易正常流程
     *
     * @throws BusinessException
     */
    @Test
    public void updateTransaction() throws BusinessException {
        //先添加交易信息amount初始化1.0
        TransactionAddQo transactionAddQo = TransactionAddQo.builder().id(TEST_TRANSACTION_ID).type("person")
                .amount(new BigDecimal(1.0))
                .build();
        transactionServices.addTransaction(transactionAddQo);
        //将交易金额amount改成2.0
        TransactionUpdateQo updateTransaction = TransactionUpdateQo.builder().id(TEST_TRANSACTION_ID)
                .amount(new BigDecimal(2.0))
                .build();

        transactionServices.updateTransaction(updateTransaction);

        TransactionVo transactionVo = transactionServices.queryTransactionById(TEST_TRANSACTION_ID);

        assertTrue(Objects.nonNull(transactionVo));
        assertTrue(transactionVo.getId().equals(TEST_TRANSACTION_ID));
        assertTrue(transactionVo.getAmount().equals(new BigDecimal(2.0)));
    }

    /**
     * 测试更新id不存在的交易抛异常
     *
     * @throws BusinessException
     */
    @Test
    public void updateTransaction_id_not_exist() throws BusinessException {

        TransactionUpdateQo updateTransaction = TransactionUpdateQo.builder().id(TEST_TRANSACTION_ID)
                .amount(new BigDecimal(2.0))
                .build();
        //校验更新交易id存在的异常
        assertThrows(BusinessException.class, () -> {
            transactionServices.updateTransaction(updateTransaction);
        });
    }

    /**
     * 测试删除交易正常
     *
     * @throws BusinessException
     * @throws ParseException
     */
    @Test
    public void deleteTransaction_success() throws BusinessException, ParseException {
        //先添加交易信息
        addTransaction_success();
        //校验删除交易信息返回值
        int result = transactionServices.deleteTransaction(TEST_TRANSACTION_ID);
        assertEquals(1, result);
    }

    /**
     * 测试删除交易异常情况，id不存在应该抛异常
     *
     * @throws BusinessException
     * @throws ParseException
     */
    @Test
    public void deleteTransaction_id_not_exist() throws BusinessException, ParseException {
        //先添加交易信息
        addTransaction_success();
        //校验删除交易信息返回值
        assertThrows(BusinessException.class, () -> {
            transactionServices.deleteTransaction(2L);
        });
    }

    /**
     * 测试按id查询交易正常
     *
     * @throws BusinessException
     * @throws ParseException
     */
    @Test
    public void queryTransactionById() throws BusinessException, ParseException {
        //先添加交易信息
        addTransaction_success();
        TransactionVo transactionVo = transactionServices.queryTransactionById(TEST_TRANSACTION_ID);

        assertTrue(Objects.nonNull(transactionVo));
        assertTrue(transactionVo.getId().equals(TEST_TRANSACTION_ID));
    }

    /**
     * 测试分页查询交易列表，正常
     *
     * @throws BusinessException
     * @throws ParseException
     */
    @Test
    public void queryTransactionList() throws BusinessException {
        TransactionAddQo transactionAddQo1 = TransactionAddQo.builder().id(TEST_TRANSACTION_ID).type("person")
                .amount(new BigDecimal(1.0))
                .build();
        transactionServices.addTransaction(transactionAddQo1);
/*
        TransactionAddQo transactionAddQo2 =  TransactionAddQo.builder().id(12L).type("company")
                .amount(new BigDecimal(2.0))
                .build();
        transactionServices.addTransaction(transactionAddQo2);

        TransactionAddQo transactionAddQo3 =  TransactionAddQo.builder().id(13L).type("company")
                .amount(new BigDecimal(3.0))
                .build();
        transactionServices.addTransaction(transactionAddQo3);*/

        TransactionListQo transactionListQo = new TransactionListQo();
        transactionListQo.setPageNum(1);
        transactionListQo.setPageSize(2);
        PageInfo<TransactionVo> pageInfoList = transactionServices.queryTransactionList(transactionListQo);
        //返回结果不能为空
        assertTrue(Objects.nonNull(pageInfoList));
        //总数为1
        assertEquals(pageInfoList.getTotal(), 1);
        //页面大小为2
        assertEquals(pageInfoList.getPageSize(), 2);

        assertTrue(Objects.nonNull(pageInfoList.getList()));
        //返回列表为1
        assertEquals(pageInfoList.getList().size(), 1);

    }
}