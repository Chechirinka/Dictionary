package dictionarySpring.model.database;

import dictionarySpring.configuration.DictionaryType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "word", nullable = false, length = 39)
    private String word;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lan_id", nullable = false)
    private Language lan;

    @OneToMany(mappedBy = "keys")
    private Set<Dictionaries> dictionaries = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public Set<Dictionaries> getDictionaries() {
        return dictionaries;
    }

    public void setDictionaries(Set<Dictionaries> dictionaries) {
        this.dictionaries = dictionaries;
    }

    public Word(String word) {
        this.word = word;
    }

    public Word() {
    }
}