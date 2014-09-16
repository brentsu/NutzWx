package cn.xuetang.modules.sys;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import cn.xuetang.common.util.SyncUtil;
import cn.xuetang.modules.sys.bean.Sys_config;
import cn.xuetang.service.sys.AppInfoService;
import cn.xuetang.service.sys.SysConfigService;

/**
 * @author Wizzer
 * @time 2013-12-30 11:20:35
 * 
 */
@IocBean
@At("/private/sys/config")
public class ConfigAction {
	@Inject
	protected SysConfigService sysConfigService;

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
		if (sysConfigService.insert(sys_config)) {
			appInfoService.InitSysConfig();
			String urls = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_url"));
			String key = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_key"));
			SyncUtil.sendMsg("sysconfig",urls,key);
			return true;
		} else
			return false;
	}

	@At
	@Ok("vm:template.private.sys.configUpdate")
	public Sys_config toupdate(@Param("id") int id) {
		return sysConfigService.fetch(id);
	}

	@At
	public boolean update(@Param("..") Sys_config sys_config) {
		if (sysConfigService.update(sys_config)) {
			appInfoService.InitSysConfig();
			String urls = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_url"));
			String key = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_key"));
			SyncUtil.sendMsg("sysconfig",urls,key);
			return true;
		} else
			return false;
	}

	@At
	public boolean delete(@Param("id") int id) {
		if (sysConfigService.delete(id) > 0) {
			appInfoService.InitSysConfig();
			String urls = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_url"));
			String key = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_key"));
			SyncUtil.sendMsg("sysconfig",urls,key);
			return true;
		} else
			return false;
	}

	@At
	public boolean deleteIds(@Param("ids") String[] ids) {
		if (sysConfigService.deleteByIds(ids)) {
			appInfoService.InitSysConfig();
			String urls = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_url"));
			String key = Strings.sNull(appInfoService.getSYS_CONFIG().get("sync_key"));
			SyncUtil.sendMsg("sysconfig",urls,key);
			return true;
		} else
			return false;
	}

	@At
	@Ok("raw")
	public String list(String contion) {
		return sysConfigService.listForDataTable(contion);
	}

}