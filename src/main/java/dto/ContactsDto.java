package dto;
import lombok.*;

import java.util.Arrays;
import java.util.Objects;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactsDto {
    private ContactDto[] contacts;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactsDto that = (ContactsDto) o;
        return Objects.deepEquals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(contacts);
    }
    public int getContactsCount() {
        return contacts != null ? contacts.length : 0;
    }
    public boolean isContactInList(ContactDto contact) {
        if (contacts == null || contact == null) return false;
        for (ContactDto c : contacts) {
            if (c.equals(contact)) {
                return true;
            }
        }
        return false;
    }
}

