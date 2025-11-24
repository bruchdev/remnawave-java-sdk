package commonTests;

import controllersTests.BaseControllerTest;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.RemnawaveErrorResponse;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.helpers.ResponseParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParserTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final ResponseParser parser = new ResponseParser(mapper);

    @Test
    public void shouldParseUserResponse() throws IOException {
        var responseBodyToParse = Files.readString(Paths.get("src/test/resources/mock-responses/user-response-body.json"));
        var parsedUser = parser.asSingle(responseBodyToParse, UserResponse.class);
        var expectedUser = BaseControllerTest.getDefaultUserResponse();
        Assertions.assertEquals(expectedUser, parsedUser);
    }

    @Test
    public void shouldParse2UsersIntoList() throws Exception {
        var usersResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/2-users-response-body.json"));
        var parsedUsers = parser.asList(usersResponseBody, UserResponse.class);

        var expectedUsers = List.of(BaseControllerTest.getDefaultUserResponse(),
                BaseControllerTest.getDefaultUserResponse());

        Assertions.assertEquals(expectedUsers.getFirst(), parsedUsers.getFirst());
        Assertions.assertEquals(expectedUsers.getLast(), parsedUsers.getLast());
    }

    @Test
    public void shouldParseValidationError() throws IOException {
        var responseBodyToParse = Files.readString(Paths.get("src/test/resources/mock-responses/validation-error-response-body.json"));
        var parsedError = parser.asError(responseBodyToParse);

        var expectedError = new RemnawaveErrorResponse(400, "Validation failed",
                List.of(
                        new RemnawaveException.FieldError(
                                "datetime",
                                "invalid_string",
                                "Invalid date format",
                                List.of("expireAt")
                        ),
                        new RemnawaveException.FieldError(
                                "uuid",
                                "invalid_string",
                                "Invalid uuid",
                                List.of("uuid")
                        )));
        Assertions.assertEquals(expectedError, parsedError);
    }
}
