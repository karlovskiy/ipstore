package ipstore.entity.capacity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 3.0, 8/20/13
 */
@Entity
@Table(name = "CAPACITY_TYPE")
public class CapacityType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName = "ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @NotEmpty
    @Column(name = "NAME", nullable = false, unique = true, length = 128)
    private String name;

    @OneToMany(mappedBy = "type", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Set<CapacityNumber> numbers = new HashSet<CapacityNumber>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CapacityNumber> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<CapacityNumber> ranges) {
        this.numbers = ranges;
    }
}
