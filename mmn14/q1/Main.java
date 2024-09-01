import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

public class Main {
    private static Random random = new Random();

    /**
     * Answer to branch b (סעיף ב)
     */
    public static <T extends Comparable<T>> SortedGroup<T> reduce(SortedGroup<T> sGroup, T x) {
        SortedGroup<T> reducedGroup = new SortedGroup<>();
        for (T element : sGroup) {
            if (element.compareTo(x) > 0) {
                reducedGroup.add(element);
            }
        }
        return reducedGroup;
    }

    /**
     * Answer to branch c (סעיף ג)
     */
    public static void main(String[] args) {
        // constructing list of students
        ArrayList<Student> students = new ArrayList<>();
        IntStream.range(0,20)
                .forEach(i -> students.add(new Student(
                        "Yoni"+(random.nextInt(90) + 10),
                        random.nextInt(900000) + 100000,
                        random.nextInt(100) + 1)));
        System.out.println("Original students list:");
        students.forEach(System.out::println);
        System.out.println();

        // constructing sorted list from the one above
        // testing SortedGroup's constructor, "iterator" and "add" methods
        SortedGroup<Student> sortedStudents = new SortedGroup<>();
        students.forEach(sortedStudents::add);
        System.out.println("Sorted students list:");
        sortedStudents.forEach(System.out::println);
        System.out.println();

        // constructing reduced sorted list from the one above
        // testing "reduce" function
        SortedGroup<Student> reducedStudents = reduce(sortedStudents, new Student("",0,60));
        System.out.println("Reduced students list:");
        reducedStudents.forEach(System.out::println);
        System.out.println();

        // constructing another sorted list to test "remove" method
        SortedGroup<Student> anotherSorted = new SortedGroup<>();
        anotherSorted.add(new Student("one", 111, 99));
        IntStream.range(0,3).forEach(i -> anotherSorted.add(new Student("two", 222, 60)));
        anotherSorted.add(new Student("tre", 333, 80));
        System.out.println("5 students list with 3 identical:");
        anotherSorted.forEach(System.out::println);
        System.out.println(anotherSorted.remove(new Student("two", 222, 60)) + " students have been deleted");
        System.out.println("I've removed student 222, now there should only be 2 students in the list:");
        anotherSorted.forEach(System.out::println);
        System.out.println();

        // NOTE: no need to explicitly test the "iterator" method, as for
        // it is already called implicitly within the forEach loop and the "reduce" function
    }
}
