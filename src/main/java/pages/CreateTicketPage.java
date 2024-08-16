package pages;

import io.qameta.allure.Step;
import models.Ticket;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/** Страница создания тикета */
public class CreateTicketPage extends HelpdeskBasePage {

    // todo: добавить элементам локтаоры через @FindBy
    @FindBy(xpath = "//select[@name='queue']")
    private WebElement selectQueue;

    @FindBy(xpath = "//input[@name='title']")
    private WebElement inputProblem;

    // todo: добавить остальные поля формы
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitTicketButton;

    @FindBy (xpath = "//textarea")
    private WebElement inputDescription;

    @FindBy(css = "select[name=priority]")
    private WebElement selectPriority;

    @FindBy(css = "input[name=due_date]")
    private WebElement InputDueDate;

    @FindBy(css = "input[type=file]")
    private WebElement attachmentButton;

    @FindBy(css = "input[name=submitter_email]")
    private WebElement inputEmail;

    // todo: проинициализировать элементы
    public CreateTicketPage() {
        PageFactory.initElements(driver,this);
    }
    @Step("Создать тикет")
    public CreateTicketPage createTicket(Ticket ticket) {
        // todo: заполнить остальные поля формы
        setSelectQueue(ticket.getQueue());
        setInputProblem(ticket.getTitle());
        setInputDescription(ticket.getDescription());
        setSelectPriority(ticket.getPriority());
        setInputDueDate(ticket.getDue_date());
        setInputEmail(ticket.getSubmitter_email());
        clickOnSubmitButton();
        return this;
    }

    @Step("Ввести имя проблемы: {text}")
    public void setInputProblem(String text) {inputProblem.sendKeys(text);}

    @Step("Нажать на кнопку создания тикета")
    public void clickOnSubmitButton() {submitTicketButton.click();}

    @Step("Задать очередь {queue}")
    public void setSelectQueue(Integer queue){
        new Select(selectQueue).selectByValue(String.valueOf(queue));
    }

    @Step("Ввести описание проблемы: {text}")
    public void setInputDescription(String text){inputDescription.sendKeys(text);}

    @Step("Задать приоритет {priority}")
    public void setSelectPriority(Integer priority){
        new Select(selectPriority).selectByValue(String.valueOf(priority));
    }

    @Step("Задать дату окончания задачи {date}")
    public void setInputDueDate(String date){InputDueDate.sendKeys(date);}

    @Step("Ввести e-mail {email}")
    public void setInputEmail(String email){inputEmail.sendKeys(email);}
}
