package cn.xuetang.modules.sys;

import java.util.List;

import javax.servlet.http.HttpSession;

import cn.xuetang.service.sys.SysPermissionCategoryService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.web.Webs;

import cn.xuetang.modules.sys.bean.Sys_user;
import cn.xuetang.modules.sys.bean.Sys_permissionCategory;
import cn.xuetang.service.sys.SysUserService;

/**
 * @author Wizzer.cn
 * @time 2012-9-13 上午10:54:04
 */
@IocBean
@At("/private")
@RequiresAuthentication
public class IndexAction {
	@Inject
	protected SysUserService sysUserService;

	@Inject
	private SysPermissionCategoryService sysPermissionCategoryService;

	@At
	@Ok("raw")
	public boolean reload(@Attr(Webs.ME) Sys_user user, @Param("resid") String resid, HttpSession session) {
		sysUserService.update(Chain.make("loginresid", resid), Cnd.where("userid", "=", user.getUserid()));
		user.setLoginresid(resid);
		return true;
	}

	@At
	@Ok("vm:template.private.index")
	public List<Sys_permissionCategory> index(@Attr(Webs.ME) Sys_user user) {
		return sysPermissionCategoryService.list();
	}

}
