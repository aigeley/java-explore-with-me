package ru.practicum.ewm.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.element.model.Element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@ToString
@Table(name = "users")
@Getter
@Setter
public class User extends Element {
    public static final String ELEMENT_NAME = "Пользователь";

    @Column(name = "email", length = 512, nullable = false, unique = true)
    private String email;

    @Id
    @GeneratedValue(generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.email, user.email) &&
                Objects.equals(this.id, user.id) &&
                Objects.equals(this.name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, id, name);
    }
}

