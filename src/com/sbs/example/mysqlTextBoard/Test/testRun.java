package com.sbs.example.mysqlTextBoard.Test;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.DisqusApiDataListThread;
import com.sbs.java.ssg.util.util;

public class testRun {
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class TestDataType1 {
		public int age;
		public String name;
		public int height;
	}


	public void run() {
		testJackson5();

	}
	private void testJackson5() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\", \"height\":178},{\"age\":23, \"name\":\"홍길순\", \"height\":168},{\"age\":24, \"name\":\"임꺽정\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<TestDataType1> rs = null;

		try {
			rs = ob.readValue(jsonString, new TypeReference<List<TestDataType1>>() {
			});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(0).height + rs.get(1).height);
	}
	

	private void testJackson4() {
		String jsonString = "[{\"age\":22, \"name\":\"홍길동\"},{\"age\":23, \"name\":\"홍길순\"},{\"age\":24, \"name\":\"임꺽정\"}]";

		ObjectMapper ob = new ObjectMapper();
		List<Map<String, Object>> rs = null;

		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(1));
	}

	private void testJackson3() {
		String jsonString = "[1,2,3]";

		ObjectMapper ob = new ObjectMapper();
		List<Integer> rs = null;

		try {
			rs = ob.readValue(jsonString, List.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs.get(2));
	}

	private void Jacksontest2() {
		String jsonString = "1";

		ObjectMapper ob = new ObjectMapper();
		Integer rs = null;

		try {
			rs = ob.readValue(jsonString, Integer.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
	}

	private void Jacksontest() {
		String jsonString = "{\"age\":22, \"name\":\"홍길동\"}";

		ObjectMapper ob = new ObjectMapper();
		Map rs = null;
		try {
			rs = ob.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return;
		}

		System.out.println(rs);
	}

	private void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		// https://disqus.com/api/3.0/forums/listThreads.json?api_key=YeC422bcRwWC8Irb4PeWcnz31UVSEs0uMZcX7iWa84GIqzW6bC14dbnVViQqedkV&forum=nsw-blog&thread:ident=info_3.html

		String rs = util.callApi(url, "api_key=YeC422bcRwWC8Irb4PeWcnz31UVSEs0uMZcX7iWa84GIqzW6bC14dbnVViQqedkV",
				"forum=nsw-blog", "thread:ident=info_3.html");
		System.out.println(url);
	}
	private void testApi2() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		Map<String, Object> rs = util.callApiResponseToMap(url, "api_key=" + Container.config.getDisqusApiKey(),
				"forum=my-ssg", "thread:ident=article_detail_2.html");
		List<Map<String, Object>> response = (List<Map<String, Object>>) rs.get("response");
		Map<String, Object> thread = response.get(0);
		System.out.println((int) thread.get("likes"));
	}

	private void testApi3() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";
		DisqusApiDataListThread rs = (DisqusApiDataListThread) util.callApiResponseTo(DisqusApiDataListThread.class,
				url, "api_key=" + Container.config.getDisqusApiKey(), "forum=my-ssg",
				"thread:ident=article_detail_2.html");
		System.out.println(rs.response.get(0).likes + rs.response.get(0).posts);
	}
}
