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
//import scala.language.postfixOps

@RunWith(classOf[JUnitRunner])
class PublishEventTests
    extends TestHelpers
    with WskTestHelpers{
  implicit val wskprops = WskProps()
  val wsk = new Wsk()

  // Retrieving all VCAP credentials, although currently only a few would be used
  val credentials = TestUtils.getVCAPcredentials("iotf-service");
  val iotCredentialsIdentifier = credentials.get("iotCredentialsIdentifier");
  val mqttHost = "messaging.internetofthings.ibmcloud.com".toJson;//(credentials.get("mqtt_host"))toJson;
  val mqttUPort = (credentials.get("mqtt_u_port")).toJson;
  val mqttSPort = (credentials.get("mqtt_s_port")).toJson;
  val httpHost = (credentials.get("http_host")).toJson;
  val org = (credentials.get("org")).toJson;
  val apiKey = (credentials.get("apiKey")).toJson;
  val apiToken = (credentials.get("apiToken")).toJson;

  //These values, although currently obtained from JSON, would in actual practice be assigned
  val gatewayTypeId = "gatewayType".toJson;
  val gatewayId = "gatewayId".toJson;
  val gatewayToken = "gatewayToken".toJson;

  val typeId = "deviceType".toJson;
  val deviceId = "deviceId".toJson;
  val eventType = "evt".toJson;
  //val payload = ("d" -> ("temp" -> 25));
  val payload = "{'temp':25}".toJson;


  behavior of "WIoTP Package"

    it should "Publish Event action" in {
           val name = "/watson/iot.gateway/publishEvent"
             withActivation(wsk.activation,wsk.action.invoke(name, Map(
               "org" -> org,
               "domain" -> mqttHost,
               "gatewayTypeId" -> gatewayTypeId,
               "gatewayId" -> gatewayId,
               "gatewayToken" -> gatewayToken,
               "typeId" -> typeId,
               "deviceId" -> deviceId,
               "eventType" -> eventType,
               "payload" -> payload))){
                 _.response.result.get.toString should include ("statusMessage")
             }
    }
}
