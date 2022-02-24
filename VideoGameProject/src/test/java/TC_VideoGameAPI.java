import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.*;

public class TC_VideoGameAPI {
	
  @Test(priority=1)
  public void test_GetAllVideoGames() {
	  given().header("Accept", "application/json").
	  when().get("http://localhost:8080/app/videogames").
	  then().assertThat().statusCode(200).contentType(ContentType.JSON);
  }
  
  @Test(priority=2)
  public void test_AddNewVideoGame() {
	  
	  Map<String, Object>  data = new HashMap<>();
	  data.put("id", "100");
	  data.put("name", "Spider-Man");
	  data.put("releaseDate", "2019-09-20T08:55:58.510Z");
	  data.put("reviewScore", "5");
	  data.put("category", "Adventure");
	  data.put("rating", "Universal");
	  
	  Response res = given().header("Accept", "application/json").contentType(ContentType.JSON)
	  	.body(data)
	  .when()
	  	.post("http://localhost:8080/app/videogames")
	  .then()
	  	.statusCode(200)
	  	.log().body()
	  	.extract().response();
	  
	  String jsonString = res.asPrettyString();
	  Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
  }
  
  @Test(priority=3)
  public void  test_getVideoGame() {
	  given().header("Accept", "application/json").contentType(ContentType.JSON)
	  .when()
	  	.get("http://localhost:8080/app/videogames/100")
	  .then()
	  	.statusCode(200)
	  	.log().body()
	  	.body("name", equalTo("Spider-Man"));
  }
  
  @Test(priority=4)
  public void test_updateVideoGame() {
	  
	  Map<String, Object>  data = new HashMap<>();
	  data.put("id", "100");
	  data.put("name", "Pacman");
	  data.put("releaseDate", "2019-09-20T08:55:58.510Z");
	  data.put("reviewScore", "4");
	  data.put("category", "Adventure");
	  data.put("rating", "Universal");
	  
	  given()
	  	.header("Accept", "application/json")
	  	.contentType(ContentType.JSON)
	  	.body(data)
	  .when()
	  	.put("http://localhost:8080/app/videogames/100")
	  .then()
	  	.statusCode(200)
	  	.log().body()
	  	.body("name", equalTo("Pacman"));
	  
  }
  
  @Test(priority=5)
  public void test_deleteVideoGame() {
	  Response res = given().header("Accept", "application/json").contentType(ContentType.JSON)
	  .when()
	  	.delete("http://localhost:8080/app/videogames/100")
	  .then()
	  	.statusCode(200)
	  	.log().body()
	  	.extract().response();
	  
	  String jsonString = res.asPrettyString();
	  Assert.assertEquals(jsonString.contains("Record Deleted Successfully"), true);
  }
  
  
}
