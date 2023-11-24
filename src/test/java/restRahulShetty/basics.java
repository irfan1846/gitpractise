package restRahulShetty;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;

import files.payload;
import files.reusableMethods;

public class basics {
	public static void main(String[] args) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		
		//add place
		String response=given().log().all().queryParam("key","qsclick123").header("Content-Type","application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		
		
		JsonPath  js=new JsonPath(response);
		String placeid=js.getString("place_id");
		 
		
		System.out.println(placeid);
		
		
		//update place
		String newAdress="Summer Walk Africa";
		
		
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\n"
				+ "\"place_id\":\""+placeid+"\",\n"
				+ "\"address\":\""+newAdress+"\",\n"
				+ "\"key\":\"qaclick123\"\n"
				+ "}").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated")); 
		
		
		String getPlaceResponse=given().log().all().queryParam("key","qaclick123")
		.queryParam("place_id",placeid).when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		//JsonPath js1=new JsonPath(getPlaceResponse);
		
		JsonPath js1=reusableMethods.rawToJson(getPlaceResponse);
		String actualAdress =js1.getString("address");	
		
		System.out.println(actualAdress);
		
		Assert.assertEquals(actualAdress,newAdress);
		
		System.out.print("hello world");
		
		 
			
		
	}

}
