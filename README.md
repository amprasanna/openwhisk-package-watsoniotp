# openwhisk-watson-iot-platform #

# Important: The IBM Watson IoT Platform Gateway package is currently offered as an experimental service. Experimental services might not be stable and can change in ways that are not compatible with earlier versions. #

The `/watson/iot-gateway` package enables a Watson IoT Platform registered gateway to send events to Watson IoT Platform on behalf of devices that are that are in the resource group that is associated with the gateway.

The package includes the following entities:

| Entity | Type | Parameters | Description |
| --- | --- | --- | --- |
| `/watson/iot-gateway` | package | org, domain, gatewayTypeId, gatewayId, gatewayToken, key, cert  | Work with Watson IoT Platform Gateway |
| `/watson/iot-gateway/publishEvent` | action | org, domain, gatewayTypeId, gatewayId, gatewayToken, key, cert, eventType, typeId, deviceId, payload | Send events from a registered gateway to Watson IoT Platform on behalf of its associated devices  |

## Creating a Watson IoT Platform Gateway package binding ##

To create a Watson IoT Platform Gateway package binding, you must specify the following parameters:

-  `org`: The organization identifier.
-  `gatewayTypeId`: The gateway type identifier of the registered gateway.
-  `gatewayId`: The gateway identifier of the registered gateway.
-  `gatewayToken`: The authorization token of the registered gateway.

Complete the following steps to create a package binding:

1. [Login to the Bluemix console](https://console.ng.bluemix.net/).

2. Create the [Internet of Things Platform  Service](https://console.bluemix.net/docs/services/IoT/index.html) in Bluemix and [note the `API Key` and the `API Token`] (https://console.bluemix.net/docs/services/IoT/platform_authorization.html#connecting-applications).

  **Note:** Make a note of the `API Key` and the `API Token`. This information is required to create a gateway type and to register a gateway.

3. [Create a gateway type](https://console.bluemix.net/docs/services/IoT/gateways/dashboard.html), for example, *myGWType* in your Watson IoT organization and [register an instance of the gateway](https://console.bluemix.net/docs/services/IoT/gateways/dashboard.html), for example, *myGWId*.  

  **Note:** Make a note of the `Gateway Token` for the registered gateway.

4. Create a package binding with the `/watson/iot-gateway` by using the following example command:

  ```
  wsk package bind /watson/iot-gateway myGW -p org myorg -p gatewayTypeId myGWType -p gatewayId myGWId -p gatewayToken myGWToken
  ```

5. Verify that the package binding exists by using the following command:

  ```
  wsk package list
  ```
  ```
  packages
  /myNamespace/myGW private binding
  ```


## Publishing Device Events ##

The `/watson/iot-gateway/publishEvent` action publishes events from a registered Watson IoT Platform gateway, on behalf of its associated devices. The following parameters are used:  

- `org`: The identifier of the organization to which the registered gateway belongs, for example: `-p org "uguhsp"`.  

- `domain`: The domain to which the registered gateway belongs. This parameter is optional and by default points to `messaging.internetofthings.ibmcloud.com`. For example: `-p domain "messaging.internetofthings.ibmcloud.com"`.  

- `gatewayTypeId`: The gateway type identifier of the registered gateway, for example: `-p gatewayTypeId "myGatewayType"`.  

- `gatewayId`: The gateway identifier of the registered gateway. The identifier must be unique within an organization for a given gateway type, for example: `-p gatewayId "00aabbccde03"`.  

- `gatewayToken`: The token (password) that is used by the registered gateway to connect to Watson IoT Platform, for example: `-p gatewayToken "ZZZZZZ"`.  

- `key`: The private key of the registered gateway. The key is used for communicating by using client certificate exchange in TLS. This parameter is optional, for example: `-p key "XXXXXXXXXXXXXXX"`.  

- `cert`: The public certificate of the registered gateway. The public certificate is used for communicating by using client certificate exchange in TLS. This parameter is optional, for example: `-p cert "YYYYYYYYYYYYYY"`.  

- `eventType`: The event type which the registered gateway publishes events to on the behalf of its associated devices, for example: `-p eventType "evt"`.  

- `typeId`: The device type of the device that is associated with the registered gateway. The registered gateway publishes events on behalf of the associated device, for example: `-p typeId "myDeviceType"`.  

- `deviceId`: The device identifier of the device that is associated with the registered gateway. The registered gateway publishes events on behalf of the associated device. The device identifier must be unique within an organization for a given gateway type, for example: `-p deviceId "00aabbccde03_0001"`.

- `payload`: The payload that the registered gateway publishes on the behalf of the device, for example: `-p payload "{'d':{'temp':38}}""`.  

The following example shows how to publish events from the *iot-gateway* package:

- Publish a device event by using the `publishEvent` action in the package binding that you created. You must replace `/myNamespace/myGateway` with your package name.

  ```
  wsk action invoke /myNamespace/myGateway/publishEvent -i --result --blocking -p org ORG_ID -p eventType value -p payload '{"test":"etsd"}' -p typeId myDeviceType -p deviceId 00aabbccde03_0001 -p gatewayTypeId myGatewayType -p gatewayId 00aabbccde03 -p gatewayToken "ZZZZZZ"
  ```
  {: pre}
  ```json
    {
      "statusCode": 200,
      "statusMessage": "OK"
    }
  ```


## Working with the repository ##

##### Deploying the package by using `installCatalog.sh`

1. `git clone https://github.com/ibm-watson-iot/openwhisk-package-watsoniotp`
2. `cd openwhisk-package-watsoniotp/packages`
3. `./installCatalog.sh AUTH APIHOST WSK_CLI`
   where AUTH is your authorization key, APIHOST is the OpenWhisk hostname, and WSK_CLI is the location of the Openwhisk CLI binary.
