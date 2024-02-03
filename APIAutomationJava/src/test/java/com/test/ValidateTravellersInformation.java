package com.test;
// designed for practice purpose
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class ValidateTravellersInformation {
	@Test
	public void f() {

		given().when().get("http://restapi.adequateshop.com/api/Traveler").then().statusCode(200).header("Content-Type",
				"application/xml; charset=utf-8");
	}

	@Test
	public void f1() {
		Response res = given().when().get("http://restapi.adequateshop.com/api/Traveler");
		System.out.println(res.asString());

		XmlPath xmlpath = new XmlPath(res.asString());
		System.out.println("Page No Details: " + xmlpath.get("TravelerinformationResponse.page").toString());

		List<String> travellers = xmlpath.getList("TravelerinformationResponse.travelers.Travelerinformation.name");
		System.out.println("Name Details:");
		for (String name : travellers) {
			System.out.println(name);
		}
	}

	@Test
	public void singleFileUpload() {

		File file = new File("c:\\Test1.txt");
		given().multiPart("MyFile", file).contentType("multipart/form-data").when().post("").then().statusCode(200)
				.body("fileName", equalTo("Test1.txt")).log().all();
	}

	@Test
	public void multiFileUpload() {

		File file = new File("c:\\Test1.txt");
		File file1 = new File("c:\\Test2.txt");

		given().multiPart("MyFile", file).multiPart("MyFile", file1).contentType("multipart/form-data").when().post("")
				.then().statusCode(200).body("[0].fileName", equalTo("Test1.txt"))
				.body("[0].fileName", equalTo("Test2.txt"));
	}
}
