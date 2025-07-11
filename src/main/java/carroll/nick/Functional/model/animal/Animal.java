package carroll.nick.Functional.model.animal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Animal implements Loud {
    private String name;
    private String sound;
    public Animal(String name, String sound){
        this.sound = sound;
        this.name = name;
    }
    @Override
    public String makeBigSound() {
        return sound.toUpperCase();
    }
}
