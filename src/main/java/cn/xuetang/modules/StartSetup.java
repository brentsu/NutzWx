package cn.xuetang.modules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import cn.xuetang.modules.sys.bean.*;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.velocity.app.Velocity;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.Daos;
import org.nutz.filepool.NutFilePool;
import org.nutz.ioc.Ioc;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import cn.xuetang.common.task.LoadTask;
import cn.xuetang.service.sys.AppInfoService;

/**
 * 类描述： 创建人：Wizzer 联系方式：www.wizzer.cn 创建时间：2013-11-26 下午2:11:13
 * 
 * @version
 */

public class StartSetup implements Setup {
	private final Log log = Logs.get();

	@Override
	public void destroy(NutConfig config) {
		AppInfoService appServer = Mvcs.getIoc().get(AppInfoService.class);
		try {
			appServer.getSCHEDULER().shutdown();
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void init(NutConfig config) {
		try {
			Ioc ioc = Mvcs.getIoc();
			Dao dao = ioc.get(Dao.class);
			/*dao.drop(Sys_user.class);*/
			if (!dao.exists(Sys_user.class)) {
				Daos.createTablesInPackage(dao, "cn.xuetang.modules", true);
                final Sys_unit defaultUnit = new Sys_unit();
                defaultUnit.setId("0001");
                defaultUnit.setName("系统管理");
                defaultUnit.setLocation(0);
                dao.insert(defaultUnit);

				final Sys_user defaultUser = new Sys_user();
				defaultUser.setLoginname("admin");
				defaultUser.setRealname("admin");
				RandomNumberGenerator rng = new SecureRandomNumberGenerator();
				String salt = rng.nextBytes().toBase64();
				String hashedPasswordBase64 = new Sha256Hash("123", salt, 1024).toBase64();
				defaultUser.setPassword(hashedPasswordBase64);
				defaultUser.setSalt(salt);
				defaultUser.setLoginTime(Times.now());
                defaultUser.setUnitid("0001");
				// dao.insert(defaultUser);
				/**
				 * 全部权限 *:*:* 添加账号 sys:user.add 更新账号 sys:user.update 删除账号
				 * sys:user.delete 浏览账号 sys:user.view
				 * 
				 * 添加角色 sys:role.add 更新角色 sys:role.update 删除角色 sys:role.delete
				 * 浏览角色 sys:role.view
				 * 
				 * 权限管理 sys:permission 权限分类 sys:permissionCategory
				 */
				List<Sys_permission> pers = new ArrayList<Sys_permission>();
				Sys_permissionCategory perCategory = new Sys_permissionCategory();
				perCategory.setStyle("icon-cog");
				perCategory.setLocked(true);
				perCategory.setName("系统管理");
				perCategory.setSyspermissions(pers);
				perCategory.setListIndex(1);
				Sys_permission allPerm = new Sys_permission();
				allPerm.setDescription("全部权限");
				allPerm.setLocked(true);
				allPerm.setShow(false);
				allPerm.setName("*:*:*");
				allPerm.setSyspermissionCategory(perCategory);
				pers.add(allPerm);

				Sys_permission per = new Sys_permission();
				per.setDescription("机构管理");
				per.setLocked(true);
				per.setShow(true);
				per.setName("nutzwx:sys.unit");
				per.setUrl("sys/unit");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

                per = new Sys_permission();
                per.setDescription("机构列表");
                per.setLocked(true);
                per.setShow(false);
                per.setName("nutzwx:sys.unit.list");
                per.setUrl("sys/unit/list");
                per.setSyspermissionCategory(perCategory);
                pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/user");
				per.setStyle("fa fa-users");
				per.setShow(true);
				per.setDescription("用户管理");
				per.setLocked(true);
				per.setName("nutzwx:sys.user");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/role");
				per.setStyle("fa fa-user");
				per.setShow(true);
				per.setDescription("角色管理");
				per.setLocked(true);
				per.setName("nutzwx:sys.role");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/res");
				per.setStyle("fa fa-user");
				per.setShow(true);
				per.setDescription("资源管理");
				per.setLocked(true);
				per.setName("nutzwx:sys.res");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/config");
				per.setStyle("fa fa-user");
				per.setShow(true);
				per.setDescription("参数配置");
				per.setLocked(true);
				per.setName("nutzwx:sys.config");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/dict");
				per.setStyle("fa fa-user");
				per.setShow(true);
				per.setDescription("数字字典");
				per.setLocked(true);
				per.setName("nutzwx:sys.dict");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/task");
				per.setStyle("fa fa-user");
				per.setShow(true);
				per.setDescription("定时任务");
				per.setLocked(true);
				per.setName("nutzwx:sys.task");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/safe");
				per.setShow(true);
				per.setDescription("访问控制");
				per.setLocked(true);
				per.setName("nutzwx:sys.safe");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				per = new Sys_permission();
				per.setUrl("sys/user/log");
				per.setShow(true);
				per.setDescription("登陆日志");
				per.setLocked(true);
				per.setName("nutzwx:sys.log");
				per.setSyspermissionCategory(perCategory);
				pers.add(per);

				dao.insertWith(perCategory, null);

				Sys_role role = new Sys_role();
				role.setName("超级权限");
				role.setDescript("拥有超级管理员的权限");
				List<Sys_permission> syspermissions = new ArrayList<Sys_permission>();
				syspermissions.add(allPerm);
				role.setSyspermissions(syspermissions);
				List<Sys_user> users = new ArrayList<Sys_user>();
				users.add(defaultUser);
				role.setUsers(users);
				dao.insertWith(role, "users");
				dao.insertRelation(role, "syspermissions");
				//
				wxManager(dao);

				//
				article(dao);
				//
				memberCenter(dao);
				//
				app(dao);
				//
				FileSqlManager fm = new FileSqlManager("init_mysql.sql");
				List<Sql> sqlList = fm.createCombo(fm.keys());
				dao.execute(sqlList.toArray(new Sql[sqlList.size()]));
			}
			AppInfoService appServer = ioc.get(AppInfoService.class);
			
			velocityInit(config.getAppRoot());
			appServer.setAPP_BASE_PATH(Strings.sNull(config.getAppRoot()));// 项目路径
			appServer.setAPP_BASE_NAME(Strings.sNull(config.getServletContext().getContextPath()));// 部署名
			appServer.InitSysConfig();// 初始化系统参数
			appServer.InitDataDict();// 初始化数据字典
			appServer.InitAppInfo();// 初始化app接口信息
			appServer.setAPP_NAME(Strings.sNull(appServer.getSYS_CONFIG().get("app_name")));// 项目名称
			appServer.setFILE_POOL(new NutFilePool("~/tmp/myfiles", 10));// 创建一个文件夹用于下载
			// 初始化Quartz任务
			appServer.setSCHEDULER(StdSchedulerFactory.getDefaultScheduler());
			new Thread(config.getIoc().get(LoadTask.class)).start();// 定时任务
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void app(Dao dao) {
		List<Sys_permission> pers = new ArrayList<Sys_permission>();
		Sys_permissionCategory perCategory = new Sys_permissionCategory();
		perCategory.setListIndex(2);
		perCategory.setLocked(true);
		perCategory.setName("应用管理");
        perCategory.setStyle("icon-list-alt");
		perCategory.setSyspermissions(pers);

		Sys_permission allPerm = new Sys_permission();
		allPerm.setUrl("app/project");
		allPerm.setShow(true);
		allPerm.setDescription("项目管理");
		allPerm.setLocked(true);
		allPerm.setName("nutzwx:app.project");
		allPerm.setSyspermissionCategory(perCategory);
		pers.add(allPerm);

		Sys_permission per = new Sys_permission();
		per.setUrl("app/info");
		per.setShow(true);
		per.setDescription("接口管理");
		per.setLocked(true);
		per.setName("nutzwx:app.api");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);
		dao.insertWith(perCategory, null);
	}

	private void memberCenter(Dao dao) {

		List<Sys_permission> pers = new ArrayList<Sys_permission>();
		Sys_permissionCategory perCategory = new Sys_permissionCategory();
		perCategory.setListIndex(3);
		perCategory.setLocked(true);
		perCategory.setName("会员中心");
        perCategory.setStyle("icon-edit");
        perCategory.setSyspermissions(pers);

		Sys_permission allPerm = new Sys_permission();
		allPerm.setUrl("user/info");
		allPerm.setShow(true);
		allPerm.setDescription("会员资料");
		allPerm.setLocked(true);
		allPerm.setName("nutzwx:member.info");
		allPerm.setSyspermissionCategory(perCategory);
		pers.add(allPerm);

		Sys_permission per = new Sys_permission();
		per.setUrl("user/score");
		per.setShow(true);
		per.setDescription("会员积分");
		per.setLocked(true);
		per.setName("nutzwx:member.score");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);

		per = new Sys_permission();
		per.setUrl("user/connwx");
		per.setShow(true);
		per.setDescription("微信帐号");
		per.setLocked(true);
		per.setName("nutzwx:member.account");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);

		dao.insertWith(perCategory, null);
	}

	private void article(Dao dao) {
		List<Sys_permission> pers = new ArrayList<Sys_permission>();
		Sys_permissionCategory perCategory = new Sys_permissionCategory();
		perCategory.setStyle("icon-text-width");
		perCategory.setListIndex(4);
		perCategory.setLocked(true);
		perCategory.setName("内容管理");
		perCategory.setSyspermissions(pers);

		Sys_permission allPerm = new Sys_permission();
		allPerm.setUrl("wx/content");
		allPerm.setShow(true);
		allPerm.setDescription("文章管理");
		allPerm.setLocked(true);
		allPerm.setName("nutzwx:article.content");
		allPerm.setSyspermissionCategory(perCategory);
		pers.add(allPerm);

		Sys_permission per = new Sys_permission();
		per.setUrl("wx/channel");
		per.setShow(true);
		per.setDescription("栏目管理");
		per.setLocked(true);
		per.setName("nutzwx:article.channel");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);
		dao.insertWith(perCategory, null);

	}

	private void wxManager(Dao dao) {
		List<Sys_permission> pers = new ArrayList<Sys_permission>();
		Sys_permissionCategory perCategory = new Sys_permissionCategory();
		perCategory.setStyle("icon-desktop");
		perCategory.setListIndex(5);
		perCategory.setLocked(true);
		perCategory.setName("微信管理");
		perCategory.setSyspermissions(pers);

		Sys_permission allPerm = new Sys_permission();
		allPerm.setUrl("wx/txt");
		allPerm.setShow(true);
		allPerm.setDescription("微信回复");
		allPerm.setLocked(true);
		allPerm.setName("nutzwx:wx.txt");
		allPerm.setSyspermissionCategory(perCategory);
		pers.add(allPerm);

		Sys_permission per = new Sys_permission();
		per.setShow(true);
		per.setDescription("客服群发");
		per.setLocked(true);
		per.setName("nutzwx:wx.push.group");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);

		per = new Sys_permission();
		per.setUrl("wx/push");
		per.setShow(true);
		per.setDescription("高级群发");
		per.setLocked(true);
		per.setName("nutzwx:wx.push.supper");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);

		per = new Sys_permission();
		per.setUrl("wx/image");
		per.setShow(true);
		per.setDescription("微信相册");
		per.setLocked(true);
		per.setName("nutzwx:wx.image");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);

		per = new Sys_permission();
		per.setUrl("wx/video");
		per.setShow(true);
		per.setDescription("微信视频");
		per.setLocked(true);
		per.setName("nutzwx:wx.video");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);

		per = new Sys_permission();
		per.setShow(true);
		per.setDescription("微信菜单");
		per.setLocked(true);
		per.setName("nutzwx:wx.menu");
		per.setSyspermissionCategory(perCategory);
		pers.add(per);
		dao.insertWith(perCategory, null);
	}

	private void velocityInit(String appPath) throws IOException {
		log.info("Veloctiy引擎初始化...");
		Properties p = new Properties();
		p.setProperty("resource.loader", "file,classloader");
		p.setProperty("file.resource.loader.path", appPath);
		p.setProperty("file", "org.apache.velocity.tools.view.WebappResourceLoader");
		p.setProperty("classloader.resource.loader.class", "cn.xuetang.mvc.VelocityResourceLoader");
		p.setProperty("classloader.resource.loader.path", appPath);
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.setProperty("velocimacro.library.autoreload", "false");
		p.setProperty("classloader.resource.loader.root", appPath);
		p.setProperty("velocimarco.library.autoreload", "true");
		p.setProperty("runtime.log.error.stacktrace", "false");
		p.setProperty("runtime.log.warn.stacktrace", "false");
		p.setProperty("runtime.log.info.stacktrace", "false");
		p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		p.setProperty("runtime.log.logsystem.log4j.category", "velocity_log");
		Velocity.init(p);
		log.info("Veloctiy引擎初始化完成。");
	}
}
