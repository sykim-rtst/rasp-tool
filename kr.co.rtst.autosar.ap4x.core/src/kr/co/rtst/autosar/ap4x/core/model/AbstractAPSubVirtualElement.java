package kr.co.rtst.autosar.ap4x.core.model;

abstract public class AbstractAPSubVirtualElement extends AbstractAPVirtualElement implements IAPSubVirtualElement {
	
	public AbstractAPSubVirtualElement(IAPProject apProject, IAPVirtualElement parent, String name,
			int index, int depth, String iconPath) {
		super(apProject, parent, parent.getName()/*서브인 경우 카테고리는 부모의 이름과 같다*/, name, index, depth, iconPath);
	}

}
