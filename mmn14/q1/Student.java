/**
 * Answer to branch c (סעיף ג)
 */
public class Student implements Comparable<Student> {
    private final String name;
    private final int id;
    private final int grade;

    public Student(String name, int id, int grade) {
        if (!(grade >= 0 && grade <= 100 && id > 0)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.id = id;
        this.grade = grade;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(id)
                .append(": ")
                .append(name)
                .append(" has got ")
                .append(grade);
        return stringBuilder.toString();
    }

    public int compareTo(Student other) {
        return grade - other.grade;
    }

    public boolean equals(Object other) { return other instanceof Student && compareTo((Student)other) == 0; }
}
