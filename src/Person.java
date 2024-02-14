public class Person {
    private int age;
    private String name;
    public Person(String n, int a){
        age = a;
        name = n;
    }
    public void printDetails(){
        System.out.println(age);
        System.out.println(name);
    }
}
