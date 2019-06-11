package kr.co.rtst.autosar.ap4x.core.model;

public class APTopVitualElement extends AbstractAPVirtualElement {

	public APTopVitualElement(IAPProject apProject, String name, int index, String iconPath) {
		super(apProject, null, name/*TOP인 경우 이름과 카테고리가 같음*/, name, index, 0, iconPath);
	}
	
	@Override
	public String toString() {
		return "APTopVitualElement["+getName()+"]";
	}
	
}