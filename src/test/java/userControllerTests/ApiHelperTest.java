package userControllerTests;

import common.TestHelpers;
import io.github.bruchdev.dto.api.ResponseObject;
import io.github.bruchdev.dto.user.UserResponse;
import io.github.bruchdev.exception.RemnawaveErrorResponse;
import io.github.bruchdev.exception.RemnawaveException;
import io.github.bruchdev.helpers.ApiHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public class ApiHelperTest {
    private final UUID expectedUUID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    private final OffsetDateTime expectedOffsetDateTime = OffsetDateTime.parse("2025-11-19T11:30:32.634Z");
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldParseUserResponse() throws IOException {
        var responseBodyToParse = Files.readString(Paths.get("src/test/resources/mock-responses/user-payload.json"));
        var parsedUser = ApiHelper.parseResponseBody(responseBodyToParse, UserResponse.class);

        var expectedUser = TestHelpers.getDefaultUserResponse();

        Assertions.assertEquals(expectedUser, parsedUser);
    }

    @Test
    public void shouldParseValidationError() throws IOException {
        var responseBodyToParse = Files.readString(Paths.get("src/test/resources/mock-responses/validation-error-payload.json"));
        var parsedError = mapper.readValue(responseBodyToParse, RemnawaveErrorResponse.class);

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

    @Test
    public void shouldParse2UsersIntoList() throws Exception {
        var usersResponseBody = Files.readString(Paths.get("src/test/resources/mock-responses/find-users-by-email-payload.json"));

        var typeRef = new TypeReference<ResponseObject<List<UserResponse>>>() {
        };
        var parsedUsers = mapper.readValue(usersResponseBody, typeRef).payload();

        var expectedUsers = List.of(TestHelpers.getDefaultUserResponse(),
                TestHelpers.getDefaultUserResponse());

        Assertions.assertEquals(parsedUsers.getFirst(), expectedUsers.getFirst());
        Assertions.assertEquals(parsedUsers.getLast(), expectedUsers.getLast());
    }
}
