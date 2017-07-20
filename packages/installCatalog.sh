#!/bin/bash
# author : Jeffrey Dare
#
# use the command line interface to install standard actions deployed
# automatically
#
# To run this command
# ./installCatalog.sh  <AUTH> <APIHOST> <WSK_CLI>
# AUTH and APIHOST are found in $HOME/.wskprops
# WSK_CLI="$OPENWHISK_HOME/bin/wsk"

set -e
set -x

if [ $# -eq 0 ]
then
echo "Usage: ./installCatalog.sh <authkey> <apihost> <pathtowskcli>"
exit
fi

AUTH="$1"
APIHOST="$2"
WSK_CLI="$3"
PACKAGE_NAME="iot.gateway"

# If the auth key file exists, read the key in the file. Otherwise, take the
# first argument as the key itself.
if [ -f "$AUTH" ]; then
    AUTH=`cat $AUTH`
fi

PACKAGE_HOME="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

export WSK_CONFIG_FILE= # override local property file to avoid namespace clashes

# watson IoT platform actions

echo Installing Watson IoT platform package.

$WSK_CLI -i --apihost "$APIHOST"  package update --auth "$AUTH"  --shared yes ${PACKAGE_NAME} \
-a description "This is the package for Watson IoT Platform Gateway." \
-a parameters '[{"name":"org", "required":true, "bindTime":true, "description":"Organization ID"},
{"name":"domain", "required":false, "description":"Domain of the messaging server (Optional)"},
{"name":"gatewayTypeId", "required":true, "description":"Gateway Type ID"},
{"name":"gatewayId", "required":true, "description":"Gateway ID"},
{"name":"gatewayToken", "required":false, "type":"password", "description":"Token for the Gateway Registered in the platform"},
{"name":"cert", "required":false, "description":"Gateway client certificate .pem file (Optional)"},
{"name":"key", "required":false, "description":"Key for the client certrificate (Optional)"}]' \
-a prettyName "Watson IoT Platform" \
-p bluemixServiceName "iotf-service"

$WSK_CLI -i --apihost "$APIHOST" action update --auth "$AUTH" "${PACKAGE_NAME}/publishEvent" "$PACKAGE_HOME/actions/publishEvent.js" \
-a description 'Send event to the Watson IoT Plaform as a IoT Gateway' \
-a parameters '[ {"name":"org", "required":true, "bindTime":true, "description":"Organization ID"},
{"name":"gatewayToken", "required":false, "type":"password", "description":"Token for the Gateway Registered in the platform"},
{"name":"domain", "required":false,  "description":"Domain of the messaging server"},
{"name":"gatewayTypeId", "required":true, "description":"Gateway Type ID"},
{"name":"gatewayId", "required":true, "description":"Gateway ID"},
{"name":"cert", "required":false, "description":"Gateway client certificate .pem file"},
{"name":"key", "required":false, "description":"Key for the client certrificate"},
{"name":"typeId", "required":true, "description":"Type ID of the device attached"},
{"name":"deviceId", "required":true, "description":"Device ID of the device attached"},
{"name":"eventType", "required":true, "description":"Type of event published"},
{"name":"payload", "required":true, "description":"Payload of the device event"}]' \
-a sampleInput '{"deviceId":"xxxx01", "typeId":"xxxx", "payload": {"temp" : 4 }}' \
-a sampleOutput '{ "statusCode": 200, "statusMessage": "OK" }'
