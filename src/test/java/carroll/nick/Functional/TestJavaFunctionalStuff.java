package carroll.nick.Functional;

import carroll.nick.Functional.model.animal.*;
import carroll.nick.Functional.model.shape.Cube;
import carroll.nick.Functional.model.shape.Cylinder;
import carroll.nick.Functional.model.shape.Pyramid;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestJavaFunctionalStuff {

    // functions
    @Test
    void testSomeFunctions(){
        //1 input - function
        Function<Integer, String > turnIntegerIntoString = String::valueOf;
        Function<String,String > addAZeroToString = a -> a + "0";
        Function<String,Integer> turnItBack = Integer::valueOf;
        assertThat(turnIntegerIntoString.andThen(addAZeroToString).andThen(turnItBack).apply(10)).isEqualTo(100);

        //2 input - bifunction
        BiFunction<String, Integer, Double> addStringAndIntAsDouble= (s,i) -> Double.valueOf (s + i);
        assertThat(addStringAndIntAsDouble.apply("20", 5)).isEqualTo(205d);
    }

    // anonymous functional interface impl
    @Test
    void testFunctionalInterface(){
        String bigSound = "OQAIUWOIASKDJO";
        Loud loud = () -> bigSound;
        assertThat(loud.makeBigSound()).isEqualTo(bigSound);
    }

    // UnaryOperator this is just an extension of function where in and out are the same type
    @Test
    void testSomeUnaryOperators(){
        UnaryOperator<Animal> flipSpecies = animal -> switch (animal) {
            case Dog d -> new Cat(d.getName());
            case Cat c -> new Dog(c.getName());
            case null, default -> new Bird("Mort");
        };

        assertAll(
                () -> assertThat(flipSpecies.apply(new Dog("Jim"))).isInstanceOf(Cat.class).extracting(Animal::getName).isEqualTo("Jim"),
                () -> assertThat(flipSpecies.apply(new Cat("Carl"))).isInstanceOf(Dog.class),
                () -> assertThat(flipSpecies.apply(null)).isInstanceOf(Bird.class)
        );

        UnaryOperator<Double> getSquareRoot = Math::sqrt;

        List<Double> doubleList = DoubleStream.of(10,20,30,40,50,60,70,80,90,100).boxed().toList();
        doubleList.forEach(in -> assertThat(getSquareRoot.apply(in)).isEqualTo(Math.sqrt(in)));
    }

    // BiOperator this is just an extension of bifunction where all arguments are same type
    @Test
    void testSomeBiOperators(){
        BinaryOperator<String> getFullName = (a,b) -> a + " " + b;
        assertThat(getFullName.apply("John", "Smith")).isEqualTo("John Smith");

        Cube cube = new Cube(5d,5d,5d);
        assertThat(calculate(cube, c -> c.height() * c.depth() * c.height())).isEqualTo(125d);

        Pyramid pyramid = new Pyramid(10d,10d,3d);
        assertThat(calculate(pyramid, p -> (1d/3d) * p.height() * p.baseWidth() * p.baseLength())).isEqualTo(100d);

        Cylinder cylinder = new Cylinder(10d,10d);
        assertThat(calculate(cylinder, cy -> Math.PI * cy.height() * cy.radius() * cy.radius())).isBetween(3141.59, 3141.60);
    }

    private <T> double calculate(T input, Function<T, Double> function) {
        return function.apply(input);
    }


    //supplier
    @Test
    void testSomeSuppliers(){
        Supplier<Cat> catSupplier = () -> {
            Cat bigCat = new Cat("Lenny the lion");
            bigCat.setSound("roar");
            return bigCat;
        };

        assertAll (
                () -> assertThat(getNewCat("carlos", catSupplier).getSound()).isEqualTo("meow"),
                () -> assertThat(getNewCat("jim", catSupplier).getSound()).isEqualTo("meow"),
                () -> assertThat(getNewCat("burt", catSupplier).getSound()).isEqualTo("meow"),
                () -> assertThat(getNewCat("frank", catSupplier).getSound()).isEqualTo("meow"),
                () -> assertThat(getNewCat(null, catSupplier).getSound()).isEqualTo("roar")
        );

    }

    public Cat getNewCat(String name, Supplier<Cat> supplier){
        return null == name ? supplier.get() : new Cat(name);
    }

    //consumer
    @Test
    void testSomeConsumers(){
        int size = 20;
        ArrayList<String> collectionOfStrings = new ArrayList<>();
        IntStream.range(0,size).forEach(a->collectionOfStrings.add(String.valueOf(a)));
        assertThat(collectionOfStrings).containsAll(IntStream.range(0,size).boxed().map(String::valueOf).toList());

        List<Animal> animals = new LinkedList<>();
        for(int i = 0; i< 10; i++){
            animals.add( new Dog("Dug"));
        }
        doSomethingWithString(animals, a -> a.setSound(a.getSound().toUpperCase()));
        assertThat(animals).allMatch(animal -> "WOOF".equals(animal.getSound()));
    }

    private void doSomethingWithString(List<Animal> listOfItems , Consumer<Animal> consumer){
        listOfItems.forEach(consumer);
    }


}
