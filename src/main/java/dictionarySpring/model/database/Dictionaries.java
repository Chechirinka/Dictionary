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
    private Words keys;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "\"values\"", nullable = false)
    private Words values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Words getKeys() {
        return keys;
    }

    public void setKeys(Words keys) {
        this.keys = keys;
    }

    public Words getValues() {
        return values;
    }

    public void setValues(Words values) {
        this.values = values;
    }

    public Dictionaries() {
    }

    public Dictionaries(Words keys, Words values) {
        this.keys = keys;
        this.values = values;
    }
}