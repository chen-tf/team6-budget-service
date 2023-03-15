package otp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AuthenticationServiceTest {

    @Test
    public void is_valid_test() {
        AuthenticationService target = new AuthenticationService();

        boolean actual = target.isValid("joey", "91000000");

        assertTrue(actual);
    }
}
