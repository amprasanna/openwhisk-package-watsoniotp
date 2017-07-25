var request = require('request');
var util = require('util');
/**
* @param {Object} params
* Details of the Gateway
* @param {String} [params.org] The Organization ID
* @param {String} [params.domain] Domain name of the messaging host
* @param {String} [params.gatewayTypeId] Gateway Type ID
* @param {String} [params.gatewayId] Gateway ID
* @param {String} [params.gatewayToken] Gateway Token
*
* Details of the end device
* @param {String} [params.typeId] Device Type Id
* @param {String} [params.deviceId] Device Id
* @param {String} [params.eventType] Event Type

* @param {String} [params.payload] Payload of the Event
*
* @return {Promise}
*/
function main(params){

   let errorMsg = paramsCheck(params);
   if(errorMsg) {
       return Promise.reject(errorMsg);
   }
   // Optional param. If not defined, default messaging server will be used
   if(!params.domain) {
       params.domain = "messaging.internetofthings.ibmcloud.com";
   }

   return new Promise((resolve, reject) => {
     let uri = util.format(
   "https://%s.%s/api/v0002/device/types/%s/devices/%s/events/%s",
   params.org, params.domain,params.typeId, params.deviceId ,
   params.eventType
   );

   let postObj = {
       headers: {
         'content-type' : 'application/json',
         'authorization' : 'Basic ' + new Buffer('g/'+params.org+'/'+ params.gatewayTypeId+'/'+ params.gatewayId + ':' + params.gatewayToken).toString('base64')
       },
       url: uri,
       json: params.payload
     };

   request.post(postObj, function(error, response, body){
         if(!error){
           if(response.statusCode === 200)
           {
             resolve({statusCode:response.statusCode,statusMessage:response.statusMessage })
           }else{
             reject(response)
           }
         }else{
             reject(error.message);
         }
       });

   });
 }

/**
*  Function to check if all the params are passed properly
*/
function paramsCheck(params) {
   if (!params.org) {
       return ('No Organization ID provided');
   }
   else if (!params.typeId) {
       return 'No Device type Id provided';
   }
   else if (!params.deviceId) {
       return 'No Device Id provided';
   }
   else if (!params.eventType) {
       return 'No Event Type provided';
   }
   else if (!params.gatewayTypeId) {
       return 'No Gateway Type Id provided';
   }
   else if (!params.gatewayId) {
       return 'No Gateway Id provided';
   }
   else if(!params.gatewayToken){
       return 'No Gateway Token provided';
   }
   else if (params.payload === undefined) {
       return 'No Payload provided';
   }
   else {
       return undefined;
   }
}
