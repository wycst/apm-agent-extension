package com.boco.mis.opentrace.json.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.boco.mis.opentrace.data.server.Database;
import com.boco.mis.opentrace.json.Json;

public class JsonTest {

	public static void main(String[] args) {
		
		   Persion persion = new  Persion();
		   persion.setAge(11);
		   persion.setName("zhangsan");

		   List students = new ArrayList();
		   Persion.Student student = new Persion.Student();
		   student.setName("t1");
		   student.setAge(8);
		   students.add(student);
		   
		   student = new Persion.Student();
		   student.setName("t2");
		   student.setAge(16);
		   students.add(student);
		   
		   persion.setStudents(students);
		   
		   String databaseJSON = JSON.toJSONString(persion);
		   Object d2 = Json.parse(databaseJSON, Persion.class);
		    
			
			
		   String jsonStr = "[{\"children\":[{\"name\":\"a1\"},{\"name\":\"a2\"}],\"name\":\"a\"},{\"children\":[{\"name\":\"b1\"},{\"name\":\"b2\"}],\"name\":\"b\"}]";
		
		    Map testMap = new HashMap();
		    
		    List l = new ArrayList();
		    for(int i = 0; i < 10; i ++) {
		    	testMap = new HashMap();
		    	testMap.put("key_" + i, "value_" + i);
		    	//testMap.put("number", i);
		    	//testMap.put("date", new Date());
		    	l.add(testMap);
		    }
		    jsonStr = JSON.toJSONString(l);
		    System.out.println(" json finish ");
		    int count = 1000;
		    
		    long start = System.currentTimeMillis();
		    Object t = null;
		    for(int j = 0 ; j < count; j ++) {
//		        t = parse(jsonStr);
		    	Json.parse(databaseJSON);
		    }
		    long end = System.currentTimeMillis();
		    System.out.println("====to==== " + (end - start));
		    
		    long start1 = System.currentTimeMillis();
		    for(int j = 0 ; j < count; j ++) {
		    	// t = JSON.parse(jsonStr);
		    	// t = JSON.parse(jsonStr);
		    	JSON.parseObject(databaseJSON, Persion.class);
		    }
		    long end1 = System.currentTimeMillis();
		    System.out.println("========fast" + (end1 - start1));
		    
			
			HashMap target = new HashMap();
			
			List childList = new ArrayList();
			
			HashMap child1 = new HashMap();
			child1.put("name", "c1");
			childList.add(child1);
			
			HashMap child2 = new HashMap();
			child2.put("name", "c2");
			childList.add(child2);
			
			target.put("children", childList);
			target.put("name", "zhangsan");

			List list = new ArrayList();
			list.add(target);
			list.add("asdf");
			
			
//			System.out.println(toJsonString(list));
//			System.out.println(JSONArray.toJSONString(list));
			
			Database database = new Database();
			database.setHost("123");
			database.setUser("dbserver");

//			target.put("database", database);

//			for (int i = 0; i < 1000; i++) {
//				target.put("key_" + i, "value_" + i);
//			}
	//
//			//
			for (int i = 0; i < 1; i++) {
				target.put("keynumber_" + i, i);
			}

			long s3 = System.currentTimeMillis();
			String json3 = null;

			for (int i = 0; i < count; i++) {
				database = new Database();
				database.setHost("123" + i);
				database.setUser("dbserver" + i);
				json3 = JSON.toJSONString(target);
			}

			long e3 = System.currentTimeMillis();
			System.out.println(" fastjson " + (e3 - s3));
			System.out.println(" fastjson " + json3);

			long s1 = System.currentTimeMillis();
			String json2 = null;

			for (int i = 0; i < count; i++) {
				database = new Database();
				database.setHost("123" + i);
				database.setUser("dbserver" + i);
				json2 = Json.toJsonString(target);
				
			}

			long e1 = System.currentTimeMillis();
			System.out.println(" toJsonString " + (e1 - s1));
			System.out.println(" toJsonString " + json2);
		}

}
