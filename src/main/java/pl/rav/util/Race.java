package pl.rav.util;

public enum Race {
    HUMANS ("Human"),
    MONSTERS ("Monster"),
    ;

    private String name;

    Race (String name) {
        this.name = name;
    }

    public String get () {
        return name;
    }

}
