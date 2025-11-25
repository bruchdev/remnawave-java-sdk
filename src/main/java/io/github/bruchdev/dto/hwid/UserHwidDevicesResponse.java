package io.github.bruchdev.dto.hwid;

import java.util.List;

public record UserHwidDevicesResponse(
        int total,
        List<HwidDevice> devices
) {
}
