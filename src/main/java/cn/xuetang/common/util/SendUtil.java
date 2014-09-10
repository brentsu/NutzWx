package cn.xuetang.common.util;

import java.util.ArrayList;
import java.util.List;

import org.nutz.http.Request;
import org.nutz.http.Sender;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import cn.xuetang.modules.user.bean.User_score;

public class SendUtil extends Thread {

	private final static Log log = Logs.get();
	private String type;
	private String url;
	private String key;

	public SendUtil(String t, String k, String u) {
		this.type = t;
		this.key = k;
		this.url = u;
	}

	public void run() {
		try {
			UrlUtil.getOneHtml(url + "?key=" + key + "&type=" + type, "UTF-8");
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void main(String[] args) {
		List<User_score> list = new ArrayList<>();
		list.add(new User_score(1, 1, 1, 1));
		list.add(new User_score(2, 2, 2, 2));
		Request request = Request.post("http://192.168.1.13:8080/nutz_wx/user/question");
		request.setData(Json.toJson(list,JsonFormat.compact()));
		Sender.create(request).setTimeout(3000).send();
	}
}
