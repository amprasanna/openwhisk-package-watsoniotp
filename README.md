# openwhisk-watson-iot-platform

# This is still in experimental mode.

The `/watson/iotgw` package enables you to send events to the IBM Watson IoT Platform on behalf of the attached devices.

The package includes the following action and feed:

| Entity | Type | Parameters | Description |
| --- | --- | --- | --- |
| `/watson/iotgw` | package | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType  | Work with the Watson IoT Platform Gateway |
| `/watson/iotgw/publishEvent` | action | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType, typeId, deviceId, payload | Send events, from a registered gateway, to the Watson IoT Platform, on the behalf of devices |


## Publishing Device Events

The `/watson/iotgw/publishEvent` action publishes events from a registered Watson IoT Platform Gateway, on the behalf of attached devices. The parameters are as follows:  
[//]: <> (
- `text`: The notification message to be shown to the user. For example: `-p text "Hi ,OpenWhisk send a notification"`.

# Working with repository

##### Deploying the Package using `installCatalog.sh`

1. `git clone https://github.com/ibm-watson-iot/openwhisk-package-watsoniotp`
2. `cd openwhisk-package-watsoniotp/packages`
3. `./installCatalog.sh AUTH APIHOST WSK_CLI`
   AUTH is your auth key.  APIHOST is the OpenWhisk hostname.  WSK_CLI is location of the Openwhisk CLI binary.
