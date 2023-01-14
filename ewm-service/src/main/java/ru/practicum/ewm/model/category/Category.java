package ru.practicum.ewm.model.category;

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
@Table(name = "categories")
@Getter
@Setter
public class Category extends Element {
    public static final String ELEMENT_NAME = "Категория";

    @Id
    @GeneratedValue(generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(this.id, category.id) &&
                Objects.equals(this.name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

