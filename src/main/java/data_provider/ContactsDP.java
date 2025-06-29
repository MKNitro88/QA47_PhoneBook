package data_provider;

import dto.ContactLombok;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactsDP {

    @DataProvider
    public Iterator<ContactLombok> addNewContactFromFile() {
        List<ContactLombok> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader("src/main/resources/data_provider/contacts_data2 - Sheet1.csv"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] splitArray = line.split(",");
                list.add(ContactLombok.builder()
                        .name(splitArray[0])
                        .lastName(splitArray[1])
                        .phone(splitArray[2])
                        .email(splitArray[3])
                        .address(splitArray[4])
                        .description(splitArray[5])
                        .build());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list.listIterator();
    }
}