# openwhisk-watson-iot-platform

# This is still in experimental mode.

The `/whisk.system/watsoniotp` package enables you to send events to the IBM Watson IoT Platform on behalf of the attached devices.

The package includes the following action and feed:

| Entity | Type | Parameters | Description |
| --- | --- | --- | --- |
| `/whisk.system/watsoniotp` | package | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType  | Work with the Watson IoT Platform Gateway |
| `/whisk.system/watsoniotp/publishEvent` | action | org, domain, gatewayTypeId, gatewayId, gatewayToken, eventType, typeId, deviceId, payload, docId | Send events to the Watson IoT Platform |



# Working with repository

##### Deploying the Package using `installCatalog.sh`

1. `git clone https://github.com/openwhisk/openwhisk-package-pushnotifications`
2. `cd openwhisk-package-pushnotifications/packages`
3. `./installCatalog.sh AUTH APIHOST WSK_CLI`
   AUTH is your auth key.  APIHOST is the OpenWhisk hostname.  WSK_CLI is location of the Openwhisk CLI binary.
