#!/bin/bash
# author : Jeffrey Dare, Amit M Mangalvedkar
#
# use the command line interface to install standard actions deployed
# automatically
#
# To run this command
# ./installCatalog.sh  <AUTH> <APIHOST> <IBM_CLOUD_CLI>
# IBM_CLOUD_CLI="$OPENWHISK_HOME/bin/bx"

set -e
set -x

if [ $# -eq 0 ]
then
echo "Usage: ./installCatalog.sh <authkey> <apihost> <pathtobxcli>"
exit
fi

AUTH="$1"
APIHOST="$2"
IBM_CLOUD_CLI="$3"
PACKAGE_NAME="iot-gateway"

# If the auth key file exists, read the key in the file. Otherwise, take the
# first argument as the key itself.
if [ -f "$AUTH" ]; then
    AUTH=`cat $AUTH`
fi

PACKAGE_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

export WSK_CONFIG_FILE= # override local property file to avoid namespace clashes

# watson IoT platform actions

echo Installing Watson IoT platform package.

$IBM_CLOUD_CLI -i --apihost "$APIHOST"  package update --auth "$AUTH"  --shared yes ${PACKAGE_NAME} \
-a description "This is the package for Watson IoT Platform Gateway." \
-a parameters '[{"name":"org", "required":true, "bindTime":true, "description":"Organization ID"},
{"name":"gatewayTypeId", "required":true, "bindTime":true,"description":"Gateway Type ID"},
{"name":"gatewayId", "required":true, "bindTime":true,"description":"Gateway ID"},
{"name":"gatewayToken", "required":true, "bindTime":true, "type":"password", "description":"Token for the Gateway Registered in the platform"}]' \
-a prettyName "Watson IoT Platform" \
-p bluemixServiceName "iotf-service"

$IBM_CLOUD_CLI -i --apihost "$APIHOST" action update --auth "$AUTH" "${PACKAGE_NAME}/publishEvent" "$PACKAGE_HOME/actions/publishEvent.js" \
-a description 'Send event to the Watson IoT Plaform as a IoT Gateway' \
-a parameters '[ {"name":"org", "required":true, "bindTime":true, "description":"Organization ID"},
{"name":"gatewayToken", "required":true, "type":"password", "description":"Token for the Gateway Registered in the platform"},
{"name":"domain", "required":false,  "description":"Domain of the messaging server"},
{"name":"gatewayTypeId", "required":true, "description":"Gateway Type ID"},
{"name":"gatewayId", "required":true, "description":"Gateway ID"},
{"name":"typeId", "required":true, "description":"Type ID of the device attached"},
{"name":"deviceId", "required":true, "description":"Device ID of the device attached"},
{"name":"eventType", "required":true, "description":"Type of event published"},
{"name":"payload", "required":true, "description":"Payload of the device event"}]' \
-a sampleInput '{"domain" : "messaging.internetofthings.ibmcloud.com", "deviceId":"xxxx01", "typeId":"xxxx", "eventType" : "status", "payload": {"temp" : 4 }}' \
-a sampleOutput '{ "statusCode": 200, "statusMessage": "OK" }'
