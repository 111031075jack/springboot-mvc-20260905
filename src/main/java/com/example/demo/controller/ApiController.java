package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 宣告 ApiController 是一個 Controller, Rest 表示支援的路徑風格
@RequestMapping("/api") // 資源分組, 相當於 @WebServlet("/api")
public class ApiController {
	
	/**
	 * 1. Welcome
	 * 路徑: /welcome, /home
	 * 
	 * 網址: http://localhost:8080/api/welocme
	 * 網址: http://localhost:8080/api/home
	 * */
	
	@GetMapping(value = {"/welcome", "/home"}, produces = "text/plain;charset=utf-8")
	public String welcome() {
		return "Welcome 歡迎光臨 !" + new Date();
	}
	
	/**
	 * 2. QueryString (? 網址帶參數)
	 * 路徑: /hello?name=John
	 * 路徑: /hello?name=John&age=20
	 * 路徑: /hello?name=Mary
	 * 
	 * 限制: name 為必要參數, age 為可選參數(初始值=18)
	 * 	
	 * 
	 * */
	
	@GetMapping(value = {"/hello"})
	public String hello(@RequestParam(value = "name", required = true) String username,
						@RequestParam(value = "age", required = false, defaultValue = "18") Integer userAge) {
		
		String result = "Hello 姓名: %s, 年齡: %d ".formatted(username, userAge);
		return result; 
	}
	
	/**
	 * 3. 上述 2 的精簡配置寫法
	 * 方法參數與請求參數同名
	 * 
	 * 路徑: /hi?name=John
	 * 路徑: /hi?name=John&age=20
	 * 路徑: /hi?name=Mary
	 * 
	 * */
	@GetMapping("/hi")
	public String hi(@RequestParam String name,
					@RequestParam(required = false, defaultValue = "18")Integer age) {
		
		String result = "Hi 姓名: %s, 年齡: %d ".formatted(name, age);
		return result; 
	}
	
	/** 
	 * 4. Lab 練習 I
	 * 路徑: /bmi?h=170&w=60
	 * 網址: http://localhost:8080/api/bmi?h=170&w=60
	 * 判斷: bmi <= 18 顯示過輕, bmi > 23 顯示過重
	 * 執行結果: 身高:170cm 體重:60kg bmi=20.76(正常)
	*/
	@GetMapping("/bmi")
	public String bmi(@RequestParam Double h, @RequestParam Double w) {
		
		// 老師寫法
		Double bmi = w / Math.pow(h/100, 2);
		String diagnosis = (bmi <= 18) ? "過輕" : (bmi > 23) ? "過重" : "正常";
		String result = "身高: %.1fcm 體重:%.2fkg BMI:%.2f (%s)".formatted(h, w, bmi, diagnosis);
		return result;
	}	
		
		

// 我的寫法
//		Double bmi = w/(h/100*h/100);
//		
//		String result = " ";
//		if(bmi <= 18) {
//			 result = "過輕";
//		} else if (bmi > 23) {
//			 result = "過重";
//		} else {
//			 result = "正常";
//		}
//		
//		String output = "身高: %s 體重: %s bmi:%.2f 判斷:%s".formatted(h, w, bmi, result);
//			
//		return output;
//		
//	}
	
	
	/**
	 * 5. 同名多筆資料
	 * 路徑: /average/ages?age=17&age=21&age=20
	 * 
	 * */
	@GetMapping("/average/ages")
	public String averageOfAge(@RequestParam(name = "age") List<Integer> ages) {
		
		double avg = ages.stream()
					//.mapToInt(Integer::valueOf)
					.mapToInt(age -> Integer.valueOf(age))
					.average()
					.orElse(0);
		
		String result = "年齡:%s 平均: %.1f".formatted(ages, avg);
		return result;
	}

	/**
	 * 6. Lab 練習: 得到多筆 score 資料
	 * 路徑: "/average/scores?score=80&score=100&score=50&score=70&score=30"
	 * 印出分數與平均, 總分
	 * */
	@GetMapping("/average/scores")
	public String averageOfScore(@RequestParam(name = "score") List<Integer> scores) {
		
		double avg = scores.stream()
							.mapToInt(Integer::valueOf)
							.average()
							.orElse(0);
		int sum = scores.stream().mapToInt(Integer::valueOf).sum();
		
		
		String result = "分數:%s 平均: %.1f 總分: %d".formatted(scores, avg, sum);
		return result;
	}
	
	
	

}