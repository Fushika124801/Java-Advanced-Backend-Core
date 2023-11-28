import dao.AccountDaoInfo;
import model.Account;
import model.Currency;
import service.ExchangeService;
import service.impl.ExchangeServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrencyExchanger {

  public static void main(String[] args) {
    AccountDaoInfo accountInfoDAO = new AccountDaoInfo();
    List<Account> allAccounts = accountInfoDAO.findAll();
    ExchangeService exchangeOperationService = new ExchangeServiceImpl();

    ExecutorService executorService1 = Executors.newFixedThreadPool(100);
    ExecutorService executorService2 = Executors.newFixedThreadPool(100);
    ExecutorService executorService3 = Executors.newFixedThreadPool(100);

    for (int i = 0; i < 100; i++) {
      executorService1.execute(
          () -> exchangeOperationService.exchange(allAccounts.get(0), Currency.USD,
              Currency.BYN, BigDecimal.valueOf(10)));
      executorService2.execute(
          () -> exchangeOperationService.exchange(allAccounts.get(1), Currency.BYN,
              Currency.USD, BigDecimal.valueOf(10)));
    }
    executorService1.shutdown();
    executorService2.shutdown();
    executorService3.shutdown();

  }
}