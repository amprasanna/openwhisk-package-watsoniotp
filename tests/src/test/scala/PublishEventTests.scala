/** author : Amit M Mangalvedkar
 *
 * Code to test Watson IoT Gateway Package for OpenWhisk
 * This code publishes an event on the behalf of a device attached to a registered Gateway
 */

 package packages

 import common.{TestHelpers, Wsk, WskProps, WskTestHelpers, TestUtils}
 import org.junit.runner.RunWith
 import org.scalatest.junit.JUnitRunner
 import spray.json.DefaultJsonProtocol.StringJsonFormat
 import spray.json.pimpAny
 import org.apache.http.client.methods.HttpPost
 import org.apache.http.impl.client.HttpClientBuilder;
 import com.google.gson.Gson
 import org.apache.http.entity.StringEntity
 import java.util.Base64
 import java.nio.charset.StandardCharsets

 @RunWith(classOf[JUnitRunner])
 class PublishEventTests
     extends TestHelpers
     with WskTestHelpers{
   implicit val wskprops = WskProps()
   val wsk = new Wsk()


   // Retrieving all VCAP credentials, although currently only a few would be used
   val credentials = TestUtils.getVCAPcredentials("iotf-service");
   val iotCredentialsIdentifier = credentials.get("iotCredentialsIdentifier");
   val mqttHost = credentials.get("mqtt_host");
   val mqttUPort = credentials.get("mqtt_u_port");
   val mqttSPort = credentials.get("mqtt_s_port");
   val httpHost = credentials.get("http_host");
   val org = credentials.get("org");
   val apiKey = credentials.get("apiKey");
   val apiToken = credentials.get("apiToken");


   //These values, although currently obtained from JSON, would in actual practice be assigned
   val gatewayTypeId = "myGatewayType";
   val gatewayId = "gatewayId";
   val gatewayToken = "gatewayToken";


   //Common code for all ReST calls
   val client = HttpClientBuilder.create().build();
   val base64encoded = "Basic " + Base64.getEncoder.encodeToString((apiKey + ":" + apiToken).getBytes(StandardCharsets.UTF_8))


   //GatewayType and GatewayId constants being used to temporarily create for testing


   //GatewayType creation code
   val gatewayTypeReST = "https://".concat(httpHost).concat("/api/v0002/device/types/");
   case class GatewayPayload(id: String, classId: String);
   val owgwtype = new GatewayPayload(gatewayTypeId,"Gateway");
   val owgwTypeAsJson = new Gson().toJson(owgwtype);
   var post = new HttpPost(gatewayTypeReST);
   post.setHeader("Authorization",base64encoded);
   post.setHeader("Content-type","application/json");
   post.setEntity(new StringEntity(owgwTypeAsJson))
   var response = client.execute(post)



   //Gateway instance creation code
   val gatewayReST = "https://".concat(httpHost).concat("/api/v0002/device/types/").concat(gatewayTypeId).concat("/devices/");
   println("Gateway Instance = " + gatewayReST)
   case class GatewayInstancePayload(deviceId: String, authToken: String);
   val owgw = new GatewayInstancePayload(gatewayId,gatewayToken);
   val owgwAsJson = new Gson().toJson(owgw);
   post = new HttpPost(gatewayReST);
   post.setHeader("Authorization",base64encoded);
   post.setHeader("Content-type","application/json");
   post.setEntity(new StringEntity(owgwAsJson))
   response = client.execute(post)




   val typeId = "deviceType";
   val deviceId = "deviceId";
   val eventType = "evt";
   //val payload = ("d" -> ("temp" -> 25));
   val payload = "{'temp':25}";

   behavior of "WIoTP Package"

     it should "Publish Event action" in {
            val name = "/guest/iot.gateway/publishEvent"
              withActivation(wsk.activation,wsk.action.invoke(name, Map(
                "org" -> org.toJson,
                //"domain" -> mqttHost.toJson,
                "gatewayTypeId" -> gatewayTypeId.toJson,
                "gatewayId" -> gatewayId.toJson,
                "gatewayToken" -> gatewayToken.toJson,
                "typeId" -> typeId.toJson,
                "deviceId" -> deviceId.toJson,
                "eventType" -> eventType.toJson,
                "payload" -> payload.toJson))){
                  _.response.result.get.toString should include ("statusMessage")
                }
       }
   }
