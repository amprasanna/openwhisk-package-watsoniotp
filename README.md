# openwhisk-watson-iot-platform #

# This is still in experimental mode. #

The `/watson/iotgw` package enables a Watson IoT Platform registered gateway to send events to the IBM Watson IoT Platform, on the behalf of devices attached to the Gateway.

The package includes the following action:

| Entity | Type | Parameters | Description |
| --- | --- | --- | --- |
| `/watson/iotgw` | package | org, domain, gatewayTypeId, gatewayId, gatewayToken, key, cert  | Work with the Watson IoT Platform Gateway |
| `/watson/iotgw/publishEvent` | action | org, domain, gatewayTypeId, gatewayId, gatewayToken, key, cert, eventType, typeId, deviceId, payload | Send events, from a registered gateway, to the Watson IoT Platform, on the behalf of devices |

## Creating a Watson IoT Platform Gateway package binding ##

While creating a WIoTP Gateway package binding, you must specify the following parameters,

-  `org`: WIoTP organization Id.
-  `gatewayTypeId`: GatewayTypeId of the registered Gateway.
-  `gatewayId`: Gateway Id of the registered Gateway.
-  `gatewayToken`: Auth Token of the registered Gateway.

The following is an example of creating a package binding.

1. Login to the Bluemix console](https://console.ng.bluemix.net/).

2. Create the [Internet of Things Platform  Service](https://console.bluemix.net/docs/services/IoT/index.html) in the Bluemix and [note the `API Key` and the `API Token`] application](https://console.bluemix.net/docs/services/IoT/platform_authorization.html#connecting-applications).

  Be sure to remember the `API Key` and the `API Token`. This is needed to create GatewayType and to register a Gateway.

3. [Create a gateway type](https://console.bluemix.net/docs/services/IoT/gateways/dashboard.html) (say myGWType) [in the Watson IoT organization and register an instance of the gateway](https://console.bluemix.net/docs/services/IoT/gateways/dashboard.html) (say myGWId).  

  Be sure to remember the `Gateway Token` for the registered gateway.

4. Create a package binding with the `/watson/iotgw`.

  ```
  wsk package bind /watson/iotgw myGW -p org myorg -p gatewayTypeId myGWType -p gatewayId myGWId -p gatewayToken myGWToken
  ```

5. Verify that the package binding exists.

  ```
  wsk package list
  ```
  ```
  packages
  /myNamespace/myGW private binding
  ```


## Publishing Device Events ##

The `/watson/iotgw/publishEvent` action publishes events from a registered Watson IoT Platform Gateway, on the behalf of attached devices. The parameters are as follows:  

- `org`: The organization id to which the registered gateway belongs to. For example: `-p org "uguhsp"`.  

- `domain`: The domain to which the registered gateway belongs to. This parameter is optional and by default points to `messaging.internetofthings.ibmcloud.com`. For example: `-p domain "messaging.internetofthings.ibmcloud.com"`.  

- `gatewayTypeId`: The gateway type id of the registered gateway. For example: `-p gatewayTypeId "myGatewayType"`.  

- `gatewayId`: The gateway id of the registered gateway. This needs to be unique within an organization for a given gateway type. For example: `-p gatewayId "00aabbccde03"`.  

- `gatewayToken`: The token (password) used by the registered gateway to connect to Watson IoT Platform. For example: `-p gatewayToken "ZZZZZZ"`.  

- `key`: The private key of the registered gateway, used for communicating using client certificate exchange in TLS. This parameter is optional. For example: `-p key "XXXXXXXXXXXXXXX"`.  

- `cert`: The public certificate of the registered gateway, used for communicating using client certificate exchange in TLS. This parameter is optional. For example: `-p cert "YYYYYYYYYYYYYY"`.  

- `eventType`: The event type to which the registered gateway would publish events to, on the behalf of the attached devices to. For example: `-p eventType "evt"`.  

- `typeId`: The device type of the device which is attached to the registered gateway, on the behalf of which, the registered gateway is publishing events. For example: `-p typeId "myDeviceType"`.  

- `deviceId`: The device id of the device which is attached to the registered gateway, on the behalf of which, the registered gateway is publishing events. This needs to be unique within an organization for a given gateway type. For example: `-p deviceId "00aabbccde03_0001"`.

- `payload`: This the payload that the registered gateway publishes on the behalf of the device. For example: `-p payload "{'d':{'temp':38}}""`.  

Here is an example of publishing events from the *iotgw* package.

- Publish a device event by using the `publishEvent` action in the package binding that you created previously. Be sure to replace `/myNamespace/myGateway` with your package name.

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


## Working with repository ##

##### Deploying the Package using `installCatalog.sh`

1. `git clone https://github.com/ibm-watson-iot/openwhisk-package-watsoniotp`
2. `cd openwhisk-package-watsoniotp/packages`
3. `./installCatalog.sh AUTH APIHOST WSK_CLI`
   AUTH is your auth key.  APIHOST is the OpenWhisk hostname.  WSK_CLI is location of the Openwhisk CLI binary.
