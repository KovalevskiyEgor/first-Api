package com.example.finalProjectAlishev;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FinalProjectAlishevApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjectAlishevApplication.class, args);

		RestTemplate restTemplate =new RestTemplate();

		Map<String,String> jsonToSend = new HashMap<>();

		jsonToSend.put("name","test name");
		jsonToSend.put("job","test job");

		HttpEntity<Map<String,String>> request= new HttpEntity<>(jsonToSend);

		String url="https://reqres.in/api/users/";
		String response =restTemplate.postForObject(url,request, String.class);

		System.out.println(response);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
