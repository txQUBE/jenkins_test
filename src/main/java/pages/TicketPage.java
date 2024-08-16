package pages;

import io.qameta.allure.Step;
import models.Dictionaries;
import models.Ticket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/** Страница отдельного тикета (авторизированный пользователь) */
public class TicketPage extends HelpdeskBasePage {

    /* Верстка страницы может измениться, поэтому для таблиц вместо индексов строк и столбцов лучше использовать
       более универсальные локаторы, например поиск по тексту + parent, following-sibling и другие.

       Текст тоже может измениться, но в этом случае элемент не будет найден и тест упадет,
       а ошибку можно будет легко локализовать и исправить.
       В случае изменений ячеек таблицы, локатор будет продолжать работать, но будет указывать на другой элемент,
       поведение теста при этом изменится непредсказуемым образом и ошибку будет сложно найти. */
    private WebElement dueDate = driver.findElement(By.xpath("//th[text()='Due Date']/following-sibling::td[1]"));

    public TicketPage(){}

    // todo: проинициализировать элементы через driver.findElement
    private WebElement title = driver.findElement(By.xpath("//h3"));
    private WebElement queue = driver.findElement(By.xpath("//th[@colspan='4']"));
    private WebElement email = driver.findElement(By.xpath("//th[text()='Submitter E-Mail']/following-sibling::*"));
    private WebElement priority = driver.findElement(By.xpath("//td[@class]"));
    private WebElement description = driver.findElement(By.xpath("//td[@id='ticket-description']/p"));

    @Step("Проверить значение полей на странице тикета")
    public void checkTicket(Ticket ticket) {
        // todo: добавить реализацию метода
//        Assert.assertTrue(dueDate.getText().contains(ticket.getDue_date()));
        Assert.assertTrue(title.getText().contains(ticket.getTitle()));
        String captionText = queue.getText();
        captionText = captionText.substring(captionText.indexOf("Queue: ") + 7, captionText.indexOf("Edit | Delete | Hold"));
        Assert.assertTrue(captionText.contains(Dictionaries.getQueue(ticket.getQueue())));
        Assert.assertTrue(priority.getText().contains(Dictionaries.getPriority(ticket.getPriority())));
        Assert.assertTrue(email.getText().contains(ticket.getSubmitter_email()));
        Assert.assertTrue(description.getText().contains(ticket.getDescription()));
    }
}
