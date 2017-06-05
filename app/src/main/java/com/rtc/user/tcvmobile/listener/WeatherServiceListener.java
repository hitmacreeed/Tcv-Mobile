
package com.rtc.user.tcvmobile.listener;

import com.rtc.user.tcvmobile.data.Channel;

public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
