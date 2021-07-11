import contacts.ContactManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

/*
  If you would prefer that JUnit Jupiter execute all test methods on the same test instance,
  simply annotate your test class with @TestInstance(Lifecycle.PER_CLASS). When using this mode,
  a new test instance will be created once per test class.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    ContactManager contactManager;


    @BeforeAll
    public static void initAll() {
        System.out.println("Init all");
    }

    @BeforeEach
    public void initContactManager() {
        System.out.println("Each time");
        contactManager = new ContactManager();
    }

    @Test
    public void shouldCreateContact() {
        contactManager.addContact("Islam", "Alaa", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
    }

    @Test
    @DisplayName("Should not create contact when first name is null")
    public void shouldThrowRunTimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                contactManager.addContact(null, "Alaa", "0123456789");
            }
        });
    }

    @Test
    @DisplayName("Should not create contact when second name is null")
    public void shouldThrowRunTimeExceptionWhenSecondNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                contactManager.addContact("Islam", null, "0123456789");
            }
        });
    }

    @Test
    @DisplayName("Should not create contact when phone number is null")
    public void shouldThrowRunTimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                contactManager.addContact("Islam", "Alaa", null);
            }
        });
    }

    @Test
    @EnabledOnOs(value = OS.MAC, disabledReason = "Enabled on MAC only.")
    public void osCheck(){
        System.out.println("I am on MAC");
    }



    @Nested
    class RepeatedTests{
        @RepeatedTest(value = 5, name =  "This is {currentRepetition} of {totalRepetitions}")
        public void repeatTest(){
            System.out.println("Repeating");
        }
    }


    @Nested
    class ParameterizedTests{
        @ParameterizedTest
        @ValueSource(strings = {"0123456789","0123456789", "0123456789"})
        public void checkMultiplePhoneNumbers(String phoneNumber){
            contactManager.addContact("Islam", "Alaa", phoneNumber);
        }

        @ParameterizedTest
        @MethodSource("phoneNumbers")
        @Disabled
        public void checkMultiplePhoneNumbersMethodSource(String phoneNumber){
            contactManager.addContact("Islam", "Alaa", phoneNumber);
        }

        public List<String> phoneNumbers(){
            return Arrays.asList("0123456789", "0123456789", "0123456789");
        }

        @ParameterizedTest
        @CsvSource("0123456789, 0123456789, 0123456789")
        public void checkMultiplePhoneNumbersCsvSource(String phoneNumber){
            contactManager.addContact("Islam", "Alaa", phoneNumber);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void checkMultiplePhoneNumbersCsvFileSource(String phoneNumber){
            contactManager.addContact("Islam", "Alaa", phoneNumber);
        }

    }


}