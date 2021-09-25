import org.example.mapper.ModelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MapperTest {

    @Test
    void mapper_test() {
        UserEntity userEntity = new UserEntity(1L, "kim", "1234");
        ModelMapper modelMapper = new ModelMapper();

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        Assertions.assertEquals(1L, userDto.getId());
        Assertions.assertEquals("kim", userDto.getUsername());
        System.out.println("userDto = " + userDto);
    }
}
