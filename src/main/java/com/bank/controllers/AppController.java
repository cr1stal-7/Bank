package com.bank.controllers;

import com.bank.models.Account;
import com.bank.models.PaymentHistory;
import com.bank.models.TransactionHistory;
import com.bank.models.User;
import com.bank.repository.AccountRepository;
import com.bank.repository.PaymentHistoryRepository;
import com.bank.repository.TransactHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private TransactHistoryRepository transactHistoryRepository;

    User user;

    @GetMapping("/dashboard")
    public ModelAndView getDashboard(HttpSession session){
        ModelAndView getDashboardPage = new ModelAndView("dashboard");

        //Извлечение объекта пользователя из сессии и приведение его к типу User
        user = (User)session.getAttribute("user");

        //Получение списка счетов пользователя по его идентификатору
        List<Account> getUserAccounts = accountRepository.getUserAccountsById(user.getUser_id());

        //Получение баланса
        BigDecimal totalAccountsBalance = accountRepository.getTotalBalance(user.getUser_id());

        //Обновляется список счетов пользователя (полученный из переменной getUserAccounts)
        getDashboardPage.addObject("userAccounts", getUserAccounts);

        //Добавляется общий баланс всех счетов пользователя
        getDashboardPage.addObject("totalBalance", totalAccountsBalance);

        return getDashboardPage;
    }

    //Метод для получения истории платежей
    @GetMapping("/payment_history")
    public ModelAndView getPaymentHistory(HttpSession session){
        //Установить представление
        ModelAndView getPaymentHistoryPage = new ModelAndView("paymentHistory");

        //Получить текущего пользователя
        user = (User) session.getAttribute("user");

        //Получить историю платежей пользователя
        List<PaymentHistory> userPaymentHistory = paymentHistoryRepository.getPaymentRecordsById(user.getUser_id());

        //Добавить историю платежей в модель представления
        getPaymentHistoryPage.addObject("payment_history", userPaymentHistory);

        return getPaymentHistoryPage;
    }

    //Определение метода для получения истории транзакций пользователя
    @GetMapping("/transact_history")
    public ModelAndView getTransactHistory(HttpSession session){
        //Создание объекта ModelAndView для возврата страницы с историей транзакций
        ModelAndView getTransactHistoryPage = new ModelAndView("transactHistory");

        //Получение информации о текущем пользователе из сессии
        user = (User) session.getAttribute("user");

        //Получение списка транзакций пользователя из репозитория
        List<TransactionHistory> userTransactHistory = transactHistoryRepository.getTransactionRecordsById(user.getUser_id());

        //Добавление списка транзакций в модель для передачи на страницу
        getTransactHistoryPage.addObject("transact_history", userTransactHistory);

        //Возврат страницы с историей транзакций
        return getTransactHistoryPage;
    }
}
