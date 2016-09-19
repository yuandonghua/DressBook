package cn.dressbook.ui.SnowCommon.common;

public class MenuItem {
	private String menuName;
	private String displayName;
	
	public MenuItem(){
		
	}
	public MenuItem(String menuName, String display){
		this.menuName=menuName;
		this.displayName=display;
	}

	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
