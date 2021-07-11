import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Revision {

    @Test
    public void verifyThatListIsNotEmpty(){
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.remove(Integer.valueOf(1));
        numbers.add(1);
        Assertions.assertFalse(numbers.isEmpty());
    }

    @Test
    public void verifyThatMethodThrowsRunTimeException(){
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
               throw new RuntimeException(); //test pass
            }
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {2,4,6,8,10})
    void verifyThatNumbersAreEven(int number){

        assertTrue(number % 2 == 0);

    }
}
