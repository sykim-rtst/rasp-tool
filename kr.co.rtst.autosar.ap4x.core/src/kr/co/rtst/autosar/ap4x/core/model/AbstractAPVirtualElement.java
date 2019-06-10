package kr.co.rtst.autosar.ap4x.core.model;

abstract public class AbstractAPVirtualElement implements IAPVirtualElement{
	
	private final IAPProject apProject;
	private final String categoryName;
	private final String name;
	private final String iconPath;
	private final int index;
	private final int depth;
	private final IAPVirtualElement parent;
	private IAPVirtualElement[] children;
	
	public AbstractAPVirtualElement(IAPProject apProject, IAPVirtualElement parent, String categoryName, String name, int index, int depth, String iconPath) {
		super();
		this.apProject = apProject;
		this.categoryName = categoryName;
		this.name = name;
		this.index = index;
		this.depth = depth;
		this.parent = parent;
		this.children = new IAPVirtualElement[0];
		this.iconPath = iconPath;
	}
	
	@Override
	public int getIndex() {
		return this.index;
	}
	
	@Override
	public int getDepth() {
		return this.depth;
	}
	
	@Override
	public IAPProject getApProject() {
		return this.apProject;
	}
	
	@Override
	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getIconPath() {
		return this.iconPath;
	}
	
	@Override
	public IAPVirtualElement getParent() {
		return parent;
	}

	@Override
	public IAPVirtualElement[] getChildren() {
		return children;
	}

	@Override
	public void setChildren(IAPVirtualElement[] children) {
		if(children != null) {
			this.children = children;
		}else {
			this.children = new IAPVirtualElement[0];
		}
	}

}
