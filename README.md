# openwhisk-watson-iot-platform

# This is still in experimental mode.

The `/watson/iotgw` package enables you to send events to the IBM Watson IoT Platform on the behalf of attached devices.

The package includes the following action:

| Entity | Type | Parameters | Description |
| --- | --- | --- | --- |
| `/watson/iotgw` | package | orgId, domain, gatewayTypeId, gatewayId, gatewayToken, eventType, key, cert  | Work with the Watson IoT Platform Gateway |
| `/watson/iotgw/publishEvent` | action | orgId, domain, gatewayTypeId, gatewayId, gatewayToken, key, cert, eventType, typeId, deviceId, payload | Send events, from a registered gateway, to the Watson IoT Platform, on the behalf of devices |


## Publishing Device Events

The `/watson/iotgw/publishEvent` action publishes events from a registered Watson IoT Platform Gateway, on the behalf of attached devices. The parameters are as follows:  

- `orgId`: The organization id to which the registered gateway belongs to. For example: `-p orgId "uguhsp"`.  

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
  wsk action invoke /myNamespace/myGateway/publishEvent --blocking --result  -p url https://example.com -p text "this is my message"  -p sound soundFileName -p deviceIds "[\"T1\",\"T2\"]"
  ```

  ```
  wsk action invoke /myNamespace/myGateway/publishEvent -i --result --blocking -p orgId ORG_ID -p eventType value -p payload '{"test":"etsd"}' -p typeId myDeviceType -p deviceId 00aabbccde03_0001 -p gatewayTypeId myGatewayType -p gatewayId 00aabbccde03 -p gatewayToken "ZZZZZZ"
  ```
  {: pre}
  ```json
    {
      "statusCode": 200,
      "statusMessage": "OK"
    }
  ```


# Working with repository

##### Deploying the Package using `installCatalog.sh`

1. `git clone https://github.com/ibm-watson-iot/openwhisk-package-watsoniotp`
2. `cd openwhisk-package-watsoniotp/packages`
3. `./installCatalog.sh AUTH APIHOST WSK_CLI`
   AUTH is your auth key.  APIHOST is the OpenWhisk hostname.  WSK_CLI is location of the Openwhisk CLI binary.
