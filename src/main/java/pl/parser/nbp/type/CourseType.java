package pl.parser.nbp.type;

public enum CourseType {
    SELLING("kurs_sprzedazy"),
    BUYING("kurs_kupna");

    private final String name;

    private CourseType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
