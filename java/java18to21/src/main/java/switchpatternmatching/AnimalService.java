package switchpatternmatching;

public class AnimalService {

    public AnimalDto getInformation(AnimalType type, long id) {
        Animal animal = new Dog();
        return AnimalDto.of(animal);
    }
}
