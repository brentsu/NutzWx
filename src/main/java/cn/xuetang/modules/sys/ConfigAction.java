package cn.xuetang.modules.sys;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import cn.xuetang.common.action.BaseAction;
import cn.xuetang.common.filter.GlobalsFilter;
import cn.xuetang.common.filter.UserLoginFilter;
import cn.xuetang.common.util.SyncUtil;
import cn.xuetang.modules.sys.bean.Sys_config;
import cn.xuetang.service.AppInfoService;

/**
 * @author Wizzer
 * @time 2013-12-30 11:20:35
 * 
 */
@IocBean
@At("/private/sys/config")
@Filters({ @By(type = GlobalsFilter.class), @By(type = UserLoginFilter.class) })
public class ConfigAction extends BaseAction {
	@Inject
	protected Dao dao;

	@Inject
	private AppInfoService appInfoService;

	@At
	@Ok("vm:template.private.sys.config")
	public void sys_config() {

	}

	@At
	@Ok("vm:template.private.sys.configAdd")
	public void toadd() {

	}

	@At
	@Ok("raw")
	public boolean add(@Param("..") Sys_config sys_config) {
		if (daoCtl.add(dao, sys_config)) {
			appInfoService.InitSysConfig();
			SyncUtil.sendMsg("sysconfig");
			return true;
		} else
			return false;
	}

	@At
	@Ok("vm:template.private.sys.configUpdate")
	public Sys_config toupdate(@Param("id") int id, HttpServletRequest req) {
		return daoCtl.detailById(dao, Sys_config.class, id);
	}

	@At
	public boolean update(@Param("..") Sys_config sys_config) {
		if (daoCtl.update(dao, sys_config)) {
			appInfoService.InitSysConfig();
			SyncUtil.sendMsg("sysconfig");
			return true;
		} else
			return false;
	}

	@At
	public boolean delete(@Param("id") int id) {
		if (daoCtl.deleteById(dao, Sys_config.class, id)) {
			appInfoService.InitSysConfig();
			SyncUtil.sendMsg("sysconfig");
			return true;
		} else
			return false;
	}

	@At
	public boolean deleteIds(@Param("ids") String ids) {
		String[] id = StringUtils.split(ids, ",");
		if (daoCtl.deleteByIds(dao, Sys_config.class, id)) {
			appInfoService.InitSysConfig();
			SyncUtil.sendMsg("sysconfig");
			return true;
		} else
			return false;
	}

	@At
	@Ok("raw")
	public String list(@Param("page") int curPage, @Param("rows") int pageSize) {
		Criteria cri = Cnd.cri();
		cri.where().and("1", "=", "1");
		cri.getOrderBy().desc("id");
		return daoCtl.listPageJson(dao, Sys_config.class, curPage, pageSize, cri);
	}

}