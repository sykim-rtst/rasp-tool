package kr.co.rtst.autosar.ap4x.core.model;

public class APSubVirtualElement extends AbstractAPSubVirtualElement {
	
	public APSubVirtualElement(IAPVirtualElement parent, String name, int index, String iconPath) {
		super(parent.getApProject(), parent, name, index, 1, iconPath);
	}
	
	@Override
	public String toString() {
		return "APSubVitualElement["+getName()+"]";
	}

}
