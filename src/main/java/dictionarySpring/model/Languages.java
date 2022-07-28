package dictionarySpring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "languages")
public class Languages {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int name;
    private int pattern;

    public Languages(int name, int pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public Languages() {}

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

}
