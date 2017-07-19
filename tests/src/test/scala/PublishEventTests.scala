

package packages

import common.{TestHelpers, Wsk, WskProps, WskTestHelpers, _}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import spray.json.DefaultJsonProtocol.StringJsonFormat
import spray.json.pimpAny
import scala.language.postfixOps

@RunWith(classOf[JUnitRunner])
class PublishEventTests
    extends TestHelpers
    with WskTestHelpers{
  implicit val wskprops = WskProps()
  val wsk = new Wsk()

  // Retrieving all VCAP credentials, although currently only a few would be used
  val credentials = TestUtils.getVCAPcredentials("iotf-service");

  //val appSecret = credentials.get("appSecret");

  val iotCredentialsIdentifier = credentials.get("iotCredentialsIdentifier");
  val mqttHost = (credentials.get("mqtt_host"))toJson;
  val mqttUPort = (credentials.get("mqtt_u_port")).toJson;
  val mqttSPort = (credentials.get("mqtt_s_port")).toJson;
  val httpHost = (credentials.get("http_host")).toJson;
  val org = (credentials.get("org")).toJson;
  val apiKey = (credentials.get("apiKey")).toJson;
  val apiToken = (credentials.get("apiToken")).toJson;

  //These values, although currently obtained from JSON, would in actual practice be assigned
  //val gatewayTypeId = "gatewayType";
  //val gatewayId = "gatewayId";
  //val gatewayToken = "password";

  val gatewayTypeId = (credentials.get("gatewayType")).toJson;
  val gatewayId = (credentials.get("gatewayId")).toJson;
  val gatewayToken = (credentials.get("gatewayToken")).toJson;

  val typeId = "deviceType".toJson;
  val deviceId = "deviceId".toJson;
  val eventType = "evt".toJson;
  //val payload = ("d" -> ("temp" -> 25));
  val payload = "temp".toJson;


  behavior of "WIoTP Package"

    it should "Publish Event action" in {
           val name = "/watson/iot/publishEvent"
             withActivation(wsk.activation,wsk.action.invoke(name, Map("org" -> org, "domain" -> mqttHost, "gatewayTypeId" -> gatewayTypeId, "gatewayId" -> gatewayId, "gatewayToken" -> gatewayToken, "typeId" -> typeId, "deviceId" -> deviceId, "eventType" -> eventType, "payload" -> payload))){
                 _.response.result.get.toString should include ("body")
             }
    }
}
