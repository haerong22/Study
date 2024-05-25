package switchpatternmatching;

public sealed interface AnimalDto {

    static AnimalDto of(Animal animal) {
        return switch (animal) {
            case Dog dog -> DogDto.of(dog);
            case Cat cat -> CatDto.of(cat);
        };
    }

    record DogDto(
            String ownerName
    ) implements AnimalDto {

        public static DogDto of(Dog dog) {
            return new DogDto(dog.getOwnerName());
        }
    }

    record CatDto(
            String name,
            int age
    ) implements AnimalDto {

        public static CatDto of(Cat cat) {
            return new CatDto(cat.getName(), cat.getAge());
        }
    }
}
