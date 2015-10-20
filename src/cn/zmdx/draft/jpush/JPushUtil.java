package cn.zmdx.draft.jpush;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil{

	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(
            String alert, String sound, Map<String, String> map,
            String... alias) {
        return PushPayload
            .newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.alias(alias))
            .setNotification(
	            Notification
                    .newBuilder()
                    .addPlatformNotification(
                        IosNotification.newBuilder()
                            .setAlert(alert)// alert
                            .setSound(sound)// sound
                            .addExtras(map).build())
                    .build())
            .build();
    }
	
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(
            String alert, Map<String, String> map,
            String... alias) {
        return PushPayload
            .newBuilder()
            .setPlatform(Platform.ios())
            .setAudience(Audience.alias(alias))
            .setNotification(
	            Notification
                    .newBuilder()
                    .addPlatformNotification(
                        IosNotification.newBuilder()
                            .setAlert(alert)// alert
                            .setSound("default")// sound
                            .addExtras(map).build())
                    .build())
            .build();
    }
	public static PushPayload sendNotificationAll(String alert) {
        return PushPayload
            .newBuilder()
            .setPlatform(Platform.ios())
            .setNotification(
	            Notification
                    .newBuilder()
                    .addPlatformNotification(
                        IosNotification.newBuilder()
                            .setAlert(alert)// alert
                            .setSound("default")// sound
                            .addExtras(new HashMap<String,String>() ).build())
                    .build())
            .build();
    }
	
	public static PushPayload alertAll(String alert,Map<String,String> extras) {
		return new Builder()
            .setPlatform(Platform.all())
            .setAudience(Audience.all())
            .setNotification(
	            Notification
                    .newBuilder()
                    .addPlatformNotification(
                        IosNotification.newBuilder()
                            .setAlert(alert)// alert
                            .setSound("default")// sound
                            .addExtras(extras).build())
                    .build()).build();
    }
}
