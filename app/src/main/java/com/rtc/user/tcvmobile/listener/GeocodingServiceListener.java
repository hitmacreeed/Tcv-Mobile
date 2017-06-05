
package com.rtc.user.tcvmobile.listener;

import com.rtc.user.tcvmobile.data.LocationResult;

public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
