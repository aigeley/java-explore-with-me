package ru.practicum.ewm.model.category;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.element.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category implements Identifiable {
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

        //сущности с пустым id не равны
        if (id == null || o == null || getClass() != o.getClass()) {
            return false;
        }

        Category other = (Category) o;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        //константное значение, что бы hash не изменялся после присвоения id
        return 13;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

