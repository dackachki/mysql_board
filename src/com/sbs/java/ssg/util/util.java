package com.sbs.java.ssg.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class util {
	public static String getNowDateStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		return format.format(time);
	}

	// 파일에 내용쓰기
	public static void writeFileContents(String filePath, StringBuilder sb) {
		writeFileContents(filePath, sb + "");
	}

	// 첫 문자 소문자화
	public static String lcfirst(String str) {
		String newStr = "";
		newStr += str.charAt(0);
		newStr = newStr.toLowerCase();

		return newStr + str.substring(1);
	}

	// 파일이 존재하는지
	public static boolean isFileExists(String filePath) {
		File f = new File(filePath);
		if (f.isFile()) {
			return true;
		}

		return false;
	}

	// 파일내용 읽어오기
	public static String getFileContents(String filePath) {
		String rs = null;
		try {
			// 바이트 단위로 파일읽기
			FileInputStream fileStream = null; // 파일 스트림

			fileStream = new FileInputStream(filePath);// 파일 스트림 생성
			// 버퍼 선언
			byte[] readBuffer = new byte[fileStream.available()];
			while (fileStream.read(readBuffer) != -1) {
			}

			rs = new String(readBuffer);

			fileStream.close(); // 스트림 닫기
		} catch (Exception e) {
			e.getStackTrace();
		}

		return rs;
	}

	// 파일 쓰기
	public static void writeFileContents(String filePath, String contents) {

		BufferedOutputStream bs = null;
		try {
			bs = new BufferedOutputStream(new FileOutputStream(filePath));
			bs.write(contents.getBytes()); // Byte형으로만 넣을 수 있음
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void mkdirs(String path) {
		File dir = new File(path);

		if (dir.exists() == false) {
			dir.mkdirs();
		}
	}
	public static boolean copy(String sourcePath, String destPath) {
		Path source = Paths.get(sourcePath);
        Path target = Paths.get(destPath);

        if (!Files.exists(target.getParent())) {
            try {
				Files.createDirectories(target.getParent());
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        }

        try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			return true;
		}
        
        return true;
	}public static String callApi(String urlStr, String... args) {
		// URL 구성 시작
		StringBuilder queryString = new StringBuilder();

		for (String param : args) {
			if (queryString.length() == 0) {
				queryString.append("?");
			} else {
				queryString.append("&");
			}

			queryString.append(param);
		}

		urlStr += queryString.toString();
		// URL 구성 끝

		// 연결생성 시작
		HttpURLConnection con = null;

		try {
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000); // 최대통신시간 제한
			con.setReadTimeout(5000); // 최대데이터읽기시간 제한
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (ProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		// 연결생성 끝

		// 연결을 통해서 데이터 가져오기 시작
		StringBuffer content = null;
		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			String inputLine;
			content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 연결을 통해서 데이터 가져오기 끝
		System.out.println(content);
		return content.toString();
	}

	public static Map<String, Object> callApiResponseToMap(String urlStr, String... args) {
		String jsonString = callApi(urlStr, args);

		ObjectMapper mapper = new ObjectMapper();

		try {
			return (Map<String, Object>) mapper.readValue(jsonString, Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Object callApiResponseTo(Class cls, String urlStr, String... args) {
		String jsonString = callApi(urlStr, args);

		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(jsonString, cls);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}
}