package cn.xuetang.service.sys;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import cn.xuetang.modules.sys.bean.Sys_role;
import cn.xuetang.modules.sys.bean.Sys_permission;
import cn.xuetang.service.BaseService;

@IocBean(fields = { "dao" })
public class SysRoleService extends BaseService<Sys_role> {

	public SysRoleService() {
	}

	public SysRoleService(Dao dao) {
		super(dao);
	}

	public List<String> getPermissionNameList(Sys_role role) {
		dao().fetchLinks(role, "syspermissions");
		List<String> permissionNameList = new ArrayList<String>();
		if (!Lang.isEmpty(role.getSyspermissions())) {
			for (Sys_permission syspermission : role.getSyspermissions()) {
				permissionNameList.add(syspermission.getName());
			}
		}
		return permissionNameList;
	}
}
