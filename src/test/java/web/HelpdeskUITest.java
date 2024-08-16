package web;

import elements.MainMenu;
import io.qameta.allure.Step;
import models.Ticket;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class HelpdeskUITest {

    private WebDriver driver;
    private Ticket ticket;

    @BeforeClass
    public void setup() throws IOException {
        loadProperties();
        setupDriver();
    }

    @Step("Загрузить конфигурацилнные файлы")
    private void loadProperties() throws IOException {
        // Читаем конфигурационные файлы в System.properties
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
    }

    @Step("Создать экземпляр драйвера")
    private void setupDriver() {
        // Создание экземпляра драйвера
        driver = new ChromeDriver();
        // Устанавливаем размер окна браузера, как максимально возможный
        driver.manage().window().maximize();
        // Установим время ожидания для поиска элементов
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Установить созданный драйвер для поиска в веб-страницах
        AbstractPage.setDriver(driver);
    }

    @Test
    public void createTicketTest() {
        // todo: шаги тест-кейса
        driver.navigate().to(System.getProperty("site.url"));
        MainMenu mainMenu = new MainMenu(driver);
        mainMenu.clickOnNewTicketButton();
        ticket = buildNewTicket();
        CreateTicketPage createTicketPage = new CreateTicketPage();
        createTicketPage.createTicket(ticket);
        ViewPage viewPage = new ViewPage();
        viewPage.saveId(ticket);
        viewPage.checkTicket(ticket);

        mainMenu.clickOnLogInButton();
        LoginPage loginPage = new LoginPage();
        loginPage.login(System.getProperty("user"), System.getProperty("password"));
        mainMenu.setInputSearch(ticket.getTitle()).clickOnGoButton();
        TicketsPage ticketsPage = new TicketsPage();
        ticketsPage.openTicket(ticket);
        TicketPage ticketPage = new TicketPage();
        ticketPage.checkTicket(ticket);

    }

    private Ticket buildNewTicket() {
        Ticket ticket = new Ticket();
        // todo: заполнить поля тикета
        ticket.setQueue(2);
        ticket.setTitle("Тест интерфейса " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh mm")));
        ticket.setDescription("Описание Тест Интерфейса");
        ticket.setPriority(3);
        ticket.setDue_date(LocalDateTime.now());
        ticket.setSubmitter_email("user@test.ui");
        return ticket;
    }

    @AfterTest
    public void close() {
        if (driver != null) {
            // Закрываем одно текущее окно браузера
            driver.close();
            // Закрываем все открытые окна браузера, завершаем работу браузера, освобождаем ресурсы
            driver.quit();
        }
    }
}
