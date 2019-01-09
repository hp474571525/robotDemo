package robot.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import robot.utils.EncryptUtil;
import robot.utils.HttpUtil;

/**
 * 机器外呼demo
 * @author sq
 * @time 2018-06-29 05:08
 *
 */
public class RobotController {
	
	// 商户号2778425
	static String cid = "8793008";
	// 秘钥
	static String secretKey = "634a3583084e873ccf31d58622707219";
	//机器人远程地址
	static String robotUri = "https://api.iapply.cn";
	
	/**
	 * 查询方案
	 */
	public static void queryPlans() {
		// 时间戳
		String now = String.valueOf(System.currentTimeMillis());
		//签名
		String sign = EncryptUtil.MD5(now + secretKey);
		
		StringBuilder sb = new StringBuilder();
		sb.append("ts="+ now).append("&sign=" + sign).append("&cid="+cid);
		
		String results = HttpUtil.sendPost(robotUri + "/api/open/plans", sb.toString());
		System.out.println(results);
	}
	
	/**
	 * 查询网关端口
	 */
	public static void queryPorts() {
		// 时间戳
		String now = String.valueOf(System.currentTimeMillis());
		//签名
		String sign = EncryptUtil.MD5(now + secretKey);
		
		StringBuilder sb = new StringBuilder();
		sb.append("ts="+ now).append("&sign=" + sign).append("&cid="+cid).append("&type=0");
		
		String results = HttpUtil.sendPost(robotUri + "/api/open/ports", sb.toString());
		System.out.println(results);
	}
	
	/**
	 * 发起任务
	 * @param times
	 * @param phones
	 */
	public static void newTask(String times, String planId, String portId, List<String> phones) {
		// 时间戳
		String now = String.valueOf(System.currentTimeMillis());
		//签名
		String sign = EncryptUtil.MD5(now + secretKey);
		
		StringBuilder sb = new StringBuilder();
		sb.append("ts="+ now).append("&sign=" + sign).append("&cid="+cid).append("&phoneSave=0");
		sb.append("&planId=" + planId).append("&ports=" + portId).append("&period1=" + times);
		
		List<Map<String, String>> phoneList = new ArrayList<>();
		
		Map<String, String> map = new HashMap<>();
		for (String phone : phones) {
			map.put("param", UUID.randomUUID().toString().substring(20));
			map.put("phone", phone);
			phoneList.add(map);
		}
		sb.append("&phoneJson=" + JSON.toJSONString(phoneList));
		
		String results = HttpUtil.sendPost(robotUri + "/api/open/addTask", sb.toString());
		System.out.println(results);
	}
	
	
	public static void main(String[] args) {
//		queryPlans();//方案查询 
//		queryPorts();//端口查询
		
		String times = "10:30~11:30";
		String planId = "17163";
		String portId = "4141";
		
		Map<Integer, String> params = new HashMap<>();
		params.put(1, "请问您是敬某某吗");
		params.put(2, "您的欠款金额400.35元，已经逾期3天");
		params.put(3, "请问您认识敬某某吗");
		params.put(14, "麻烦转告敬某某");
		params.put(15, "他本期欠款金额400.35元，请存在他中国银行银行尾数434的银行卡中回电代扣");
		params.put(16, "您的联系方式是敬某某作为紧急联系人留在这里的");
		
		params.put(0, "2");
		params.put(-1, "敬某某");
		
//		newTask2(times, planId, portId, phone, params);//新建任务
		
		List<String> phones = new ArrayList<>();
		phones.add("18581549770");
//		newTask(times, planId, portId, phones);
		
		
		updateTask(68687);
	}
	
	public static void newTask2(String times, String planId, String portId, String phone, Map<Integer, String> maps) {
		// 时间戳
		String now = String.valueOf(System.currentTimeMillis());
		//签名
		String sign = EncryptUtil.MD5(now + secretKey);
		
		StringBuilder sb = new StringBuilder();
		sb.append("ts="+ now).append("&sign=" + sign).append("&cid="+cid).append("&phoneSave=1");
		sb.append("&planId=" + planId).append("&ports=" + portId).append("&period1=" + times);
		
		Map<String, String> map = new HashMap<>();
		map.put("phone", phone);
		Iterator<Integer> iterator = maps.keySet().iterator();
		while(iterator.hasNext()) {
			Integer key = iterator.next();
			String value = maps.get(key);
			if(key == 0) {
				map.put("param", value);
			}else if(key == -1) {
				map.put("name", value);
			}else {
				map.put("param" + key, value);
			}
		}
		List<Map<String, String>> phoneList = new ArrayList<>();
		phoneList.add(map);
		sb.append("&phoneJson=" + JSON.toJSONString(phoneList));
		System.out.println(sb.toString());
		String results = HttpUtil.sendPost(robotUri + "/api/open/addTask", sb.toString());
		System.out.println(results);
	}
	
	public static void updateTask(Integer taskId) {
		
		String period1 = "16:30~17:30";
		
		// 时间戳
		String now = String.valueOf(System.currentTimeMillis());
		//签名
		String sign = EncryptUtil.MD5(now + secretKey);
		
		StringBuilder sb = new StringBuilder();
		sb.append("ts="+ now).append("&sign=" + sign).append("&cid="+cid);
		
		sb.append("&taskId=" + taskId).append("&period1=" + period1);
		
		System.out.println(sb.toString());
		String results = HttpUtil.sendPost(robotUri + "/api/open/updateTask", sb.toString());
		System.out.println(results);
	}
	
}
