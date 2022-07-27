package dictionarySpring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "words")
public class Words {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int word;

    public Words(int word, int lan_id) {
        this.word = word;
        this.lan_id = lan_id;
    }

    public Words() {}

    private int lan_id;

    public int getWord() {
        return word;
    }

    public void setWord(int word) {
        this.word = word;
    }

    public int getLan_id() {
        return lan_id;
    }

    public void setLan_id(int lan_id) {
        this.lan_id = lan_id;
    }


}
