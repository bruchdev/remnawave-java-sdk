package io.github.bruchdev.dto.hwid;

import java.util.List;

public record GetUserHwidDevicesResponse(
        int total,
        List<HwidDeviceResponse> devices
) {
}
