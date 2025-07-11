package carroll.nick.Functional.model.animal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cat extends Animal {
    public Cat(String name){
        super(name, "meow");
    }
}
