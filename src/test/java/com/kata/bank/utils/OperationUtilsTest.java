package com.kata.bank.utils;

import com.kata.bank.domain.OperationType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OperationUtilsTest {

    @Test
    public void testGetOperationDeposit() {
        // result
        String result = OperationUtils.getOperation(1);

        // ASSERT
        Assert.assertEquals(result, OperationType.DEPOSIT.getValue());
    }

    @Test
    public void testGetOperationWithdrawal() {
        // result
        String result = OperationUtils.getOperation(2);

        // ASSERT
        Assert.assertEquals(result, OperationType.WITHDRAWAL.getValue());
    }

    @Test
    public void testGetOperationNotFound() {
        // result
        String result = OperationUtils.getOperation(4);

        // ASSERT
        Assert.assertEquals(result, OperationConstants.OPERATION_NOT_FOUND);
    }
}
