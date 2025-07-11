package carroll.nick.Functional.model.animal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bird extends Animal {
    public Bird(String name){
        super(name,"tweet");
    }
}
