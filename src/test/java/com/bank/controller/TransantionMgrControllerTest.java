package com.bank.controller;

import com.bank.BankTransactionApplication;
import com.bank.entity.vo.TransactionVo;
import com.bank.service.TransactionService;
import jakarta.annotation.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankTransactionApplication.class)
@AutoConfigureMockMvc
public class TransantionMgrControllerTest {

    private static final String BASE_URL = "http://localhost:8088";

    private static final Long TEST_TRANSACTION_ID = 1l;

    private static final String ADD_TRANSACTION_JSON = "{   \n" +
            "    \"id\":1,\n" +
            "\t\"type\": \"personal\",\n" +
            "    \"amount\": 10.1,\n" +
            "    \"createTime\":\"2025-05-01 07:00:00\",\n" +
            "    \"desc\":\"test1\"\n" +
            "}";

    private static final String UPDATE_TRANSACTION_JSON = "{   \n" +
            "    \"id\":1,\n" +
            "\t\"type\": \"company\",\n" +
            "    \"amount\": 12.1,\n" +
            "    \"createTime\":\"2025-05-01 08:00:00\",\n" +
            "    \"desc\":\"test1\"\n" +
            "}";
    private static final String LIST_TRANSACTION_JSON = "{\n" +
            "    \"pageNum\":1,\n" +
            "    \"pageSize\":3\n" +
            "}";

    @Resource
    private TransactionService transactionServices;

    @Autowired
    private MockMvc mockMvc;


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
     * 测试增加交易信息
     *
     * @return
     */
    @Test
    public void createTransaction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/transaction/add")
                .content(ADD_TRANSACTION_JSON.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 测试修改交易信息
     *
     * @return
     */
    @Test
    public void updateTransaction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + "/transaction/update")
                .content(UPDATE_TRANSACTION_JSON.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 测试删除交易信息
     *
     * @return
     */
    @Test
    public void deleteTransaction() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL + "/transaction/delete?id=1")
                .content("") //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * 测试分页查询交易信息列表
     *
     * @return
     */
    @Test
    public void queryTransactionPageList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_URL + "/transaction/page/list")
                .content(LIST_TRANSACTION_JSON.getBytes()) //传json参数
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
