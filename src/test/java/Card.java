import lombok.Data;

@Data
public class Card {
    private String cardNumber;
    private String month;
    private String year;
    private String owner;
    private String cvc;

    public String getNumber() {
        return cardNumber;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getOwner() {
        return owner;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getCvc() {
        return cvc;
    }
}
