package dictionarySpring.model.database;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dictionaries")
public class Dictionaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "keys", nullable = false)
    private Word keys;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "\"values\"", nullable = false)
    private Word values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Word getKeys() {
        return keys;
    }

    public void setKeys(Word keys) {
        this.keys = keys;
    }

    public Word getValues() {
        return values;
    }

    public void setValues(Word values) {
        this.values = values;
    }

    public Dictionaries() {
    }

    public Dictionaries(Word keys, Word values) {
        this.keys = keys;
        this.values = values;
    }
}