package kh.khtn.speedtotext.model;

/**
 * Created by 11200 on 4/13/2018.
 */

public class DictionaryItem {
    private String name;
    private int numberCounter;

    public DictionaryItem(String name, int numberCounter) {
        this.name = name;
        this.numberCounter = numberCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberCounter() {
        return numberCounter;
    }

    public void setNumberCounter(int numberCounter) {
        this.numberCounter = numberCounter;
    }
}
