import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGeneration {
    private DataGeneration () {}

    public static Faker faker = new Faker(new Locale("ru"));

    public static String name () {
        return faker.name().fullName();
    }
    public static String approvedCardNumber() {
        return "4444444444444441";
    }
    public static String declinedCardNumber() {
        return "4444444444444442";
    }

    public static String validMonth() {
        String[] monthSet = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int validMonth = (int) (Math.random() * monthSet.length);
        String month = monthSet[validMonth];
        return month;
    }
    public static String validYear() {
      int year = faker.number().numberBetween(20,25);
      return Integer.toString(year);
    }

    public static String setEarlyYear() {
        LocalDate date = LocalDate.now().minusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        String year = date.format(formatter);
        return year;
    }
    public static String validCvc(){
        int cvc = faker.number().numberBetween(100,199);
        return Integer.toString(cvc);
    }
    public static String shortCvc() {
        int shortCvc = faker.number().numberBetween(10,99);
        return Integer.toString(shortCvc);
    }

}
