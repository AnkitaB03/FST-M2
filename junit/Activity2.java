package activities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Activity2 {
    @Test
    public void notEnoughFundsTest(){
        BankAccount bankAccount = new BankAccount(9);
        Assertions.assertThrows(NotEnoughFundsException.class,() -> bankAccount.withdraw(10));
    }

    @Test
    public void enoughFundsTest(){
        BankAccount bankAccount = new BankAccount(100);
        Assertions.assertDoesNotThrow(() -> bankAccount.withdraw(100));

    }
}
