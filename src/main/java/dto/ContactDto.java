package dto;

import lombok.*;

import java.util.Objects;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    private String id; //optional - required for update
    private String name; // required
    private String lastName; // required
    private String email; // optional
    private String phone; // digits 10 -15 optional
    private String address; // required - optional for update
    private String description; // optional

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactDto contact = (ContactDto) o;
        return
                Objects.equals(name, contact.name)
                        && Objects.equals(lastName, contact.lastName)
                        && Objects.equals(email, contact.email)
                        && Objects.equals(phone, contact.phone)
                        && Objects.equals(address, contact.address);}


    @Override public int hashCode() {
        return Objects.hash(name, lastName, email, phone, address);
    }
}
