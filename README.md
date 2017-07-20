# openwhisk-watson-iot-platform

# This is still in experimental mode.

<<<<<<< HEAD
The `/watson/iotgw` package enables you to send events to the IBM Watson IoT Platform on behalf of the attached devices.
=======
The `/watson/iot` package enables you to send events to the IBM Watson IoT Platform on behalf of the attached devices.
>>>>>>> 71e4c1d458e7ee7bc31611637d1343f9ab42e4c3

The package includes the following action and feed:

| Entity | Type | Parameters | Description |
| --- | --- | --- | --- |
<<<<<<< HEAD
| `/watson/iotgw` | package | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType  | Work with the Watson IoT Platform Gateway |
| `/watson/iotgw/publishEvent` | action | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType, typeId, deviceId, payload | Send events, from a registered gateway, to the Watson IoT Platform, on the behalf of devices |
=======
| `/watson/iot` | package | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType  | Work with the Watson IoT Platform Gateway |
| `/watson/iot/publishEvent` | action | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType, typeId, deviceId, payload, docId | Send events to the Watson IoT Platform |
>>>>>>> 71e4c1d458e7ee7bc31611637d1343f9ab42e4c3



## Publishing Device Events

The `/watson/iotgw/publishEvent` action publishes events from a registered Watson IoT Platform Gateway, on the behalf of attached devices. The parameters are as follows:

- `text`: The notification message to be shown to the user. For example: `-p text "Hi ,OpenWhisk send a notification"`.
- `url`: An optional URL that can be sent along with the alert. For example: `-p url "https:\\www.w3.ibm.com"`.
- `deviceIds` The list of specified devices. For example: `-p deviceIds "[\"deviceID1\"]"`.
- `platforms` Send notification to the devices of the specified platforms. 'A' for apple (iOS) devices and 'G' for google (Android) devices. For example `-p platforms ["A"]`.
- `userIds` - Send notification to the devices of the specified users. For example: `-p userIds "[\"testUser\"]"`
- `tagNames` Send notification to the devices that have subscribed to any of these tags. For example `-p tagNames "[\"tag1\"]" `.

- `gcmCollapseKey`: This parameter identifies a group of messages
- `gcmCategory` - The category identifier to be used for the interactive push notifications.
- `gcmIcon` - Specify the name of the icon to be displayed for the notification. Make sure the icon is already packaged with the client application.
- `gcmDelayWhileIdle`: When this parameter is set to true, it indicates that the message will not be sent until the device becomes active.
- `gcmSync`: Device group messaging makes it possible for every app instance in a group to reflect the latest messaging state.
- `gcmVisibility`: private/public - Visibility of this notification, which affects how and when the notifications are revealed on a secure locked screen.
- `gcmPayload`: Custom JSON payload that will be sent as part of the notification message. For example: `-p gcmPayload "{\"hi\":\"hello\"}"`
- `gcmPriority`: Sets the priority of the message.
- `gcmSound`: The sound file (on device) that will be attempted to play when the notification arrives on the device.
- `gcmTimeToLive`: This parameter specifies how long (in seconds) the message will be kept in GCM storage if the device is offline.
- `gcmStyleType`: Specifies the type of expandable notifications. The possible values are `bigtext_notification`, `picture_notification`, `inbox_notification`.
- `gcmStyleTitle`: Specifies the title of the notification. The title is displayed when the notification is expanded. Title must be specified for all three expandable notification.
- `gcmStyleUrl`: An URL from which the picture has to be obtained for the notification. Must be specified for `picture_notification`.
- `gcmStyleText`: The big text that needs to be displayed on expanding a `bigtext_notification`. Must be specified for `bigtext_notification`.
- `gcmStyleLines`: An array of strings that is to be displayed in inbox style for `inbox_notification`. Must be specified for `inbox_notification`.
- `gcmLightsLedArgb` - The color of the led. The hardware will do its best approximation.
- `gcmLightsLedOnMs` - The number of milliseconds for the LED to be on while it's flashing. The hardware will do its best approximation.
- `gcmLightsLedOffMs` - The number of milliseconds for the LED to be off while it's flashing. The hardware will do its best approximation.

- `apnsBadge`: The number to display as the badge of the application icon.
- `apnsCategory`: The category identifier to be used for the interactive push notifications.
- `apnsIosActionKey`: The title for the Action key .
- `apnsPayload`: Custom JSON payload that will be sent as part of the notification message.
- `apnsType`: ['DEFAULT', 'MIXED', 'SILENT'].
- `apnsSound`: The name of the sound file in the application bundle. The sound of this file is played as an alert.
- `apnsTitleLocKey` - The key to a title string in the Localizable.strings file for the current localization. The key string can be formatted with %@ and %n$@ specifiers to take the variables specified in the `titleLocArgs` array.
- `apnsLocKey` - A key to an alert-message string in a Localizable.strings file for the current localization (which is set by the user’s language preference). The key string can be formatted with %@ and %n$@ specifiers to take the variables specified in the locArgs array.
- `apnsLaunchImage` - The filename of an image file in the app bundle, with or without the filename extension. The image is used as the launch image when users tap the action button or move the action slider.
- `pnsTitleLocArgs` - Variable string values to appear in place of the format specifiers in `title-loc-key`.
- `apnsLocArgs` - Variable string values to appear in place of the format specifiers in `locKey`.
- `apnstitle` - The title of Rich Push notifications (Supported only on iOS 10 and above).
- `apnsSubtitle` - The subtitle of the Rich Notifications. (Supported only on iOS 10 and above).
- `apnsAttachmentUrl` - The link to the iOS notifications media (video, audio, GIF, images - Supported only on iOS 10 and above).

- `fireFoxTitle`: Specifies the title to be set for the WebPush Notification.
- `fireFoxIconUrl`: The URL of the icon to be set for the WebPush Notification.
- `fireFoxTimeToLive`: This parameter specifies how long (in seconds) the message should be kept in GCM storage if the device is offline.
- `fireFoxPayload`: Custom JSON payload that will be sent as part of the notification message.

- `chromeTitle`: Specifies the title to be set for the WebPush Notification.
- `chromeIconUrl`: The URL of the icon to be set for the WebPush Notification.
- `chromeTimeToLive`: This parameter specifies how long (in seconds) the message should be kept in GCM storage if the device is offline.
- `chromePayload`: Custom JSON payload that will be sent as part of the notification message.

- `safariTitle` - Specifies the title to be set for the Safari Push Notifications.
- `safariUrlArgs` - The URL arguments that need to be used with this notification. This has to provided in the form of a JSON Array.
- `safariAction` - The label of the action button.

- `chromeAppExtTitle`: Specifies the title to be set for the WebPush Notification.
- `chromeAppExtCollapseKey`: This parameter identifies a group of messages.
- `chromeAppExtDelayWhileIdle`: When this parameter is set to true, it indicates that the message should not be sent until the device becomes active.
- `chromeAppExtIconUrl`: The URL of the icon to be set for the WebPush Notification.
- `chromeAppExtTimeToLive`: This parameter specifies how long (in seconds) the message should be kept in GCM storage if the device is offline.
- `chromeAppExtPayload`: Custom JSON payload that will be sent as part of the notification message.

# Working with repository

##### Deploying the Package using `installCatalog.sh`

1. `git clone https://github.com/ibm-watson-iot/openwhisk-package-watsoniotp`
2. `cd openwhisk-package-watsoniotp/packages`
3. `./installCatalog.sh AUTH APIHOST WSK_CLI`
   AUTH is your auth key.  APIHOST is the OpenWhisk hostname.  WSK_CLI is location of the Openwhisk CLI binary.
