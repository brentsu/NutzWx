package cn.xuetang.modules.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;

import com.octo.captcha.service.image.ImageCaptchaService;

@IocBean
public class CaptchaModule {

	@Inject
	private ImageCaptchaService imageCaptchaService;

	@At
	@Ok("raw:image/jpeg")
	@Filters
	public Object captcha(HttpSession session, HttpServletRequest req) {
		return imageCaptchaService.getChallengeForID(session.getId(), req.getLocale());
	}
}
