package cn.xuetang.modules.sys.bean;

import java.util.ArrayList;
import java.util.List;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Index;
import org.nutz.dao.entity.annotation.Many;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Readonly;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.TableIndexes;

/**
 * @author 科技㊣²º¹³ 2014年4月19日 上午8:54:23 http://www.rekoe.com QQ:5382211
 */
@Table("sys_permission_category")
@TableIndexes({ @Index(name = "permission_listIndex", fields = { "listIndex" }, unique = true) })
public class Sys_permissionCategory {

	@Name
	@Prev(els = { @EL("uuid()") })
	private String id;

	@Column("parent")
	private String parentId;

	//@One(target = Sys_permissionCategory.class, field = "parentId")
	@Readonly
	private Sys_permissionCategory parent;

	@Readonly
	private List<Sys_permissionCategory> children = new ArrayList<Sys_permissionCategory>();

	@Column
	private String name;

	@Many(target = Sys_permission.class, field = "permissionCategoryId")
	private List<Sys_permission> syspermissions;

	@Column("is_locked")
	@ColDefine(type = ColType.BOOLEAN)
	private boolean locked;

	@Column("page_style")
	private String style;

	@Column("action_url")
	private String url;

	@Column("tree_path")
	@ColDefine(type = ColType.TEXT)
	private String treePath;

	@Column("list_index")
	private int listIndex;

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Sys_permissionCategory getParent() {
		return parent;
	}

	public void setParent(Sys_permissionCategory parent) {
		this.parent = parent;
	}

	public List<Sys_permissionCategory> getChildren() {
		return children;
	}

	public void setChildren(List<Sys_permissionCategory> children) {
		this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getListIndex() {
		return listIndex;
	}

	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Sys_permission> getSyspermissions() {
		return syspermissions;
	}

	public void setSyspermissions(List<Sys_permission> syspermissions) {
		this.syspermissions = syspermissions;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getTreePath() {
		return treePath;
	}
}
