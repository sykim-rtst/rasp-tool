package kr.co.rtst.autosar.ap4x.core.model.desc;

import java.util.List;

import gautosar.ggenericstructure.ginfrastructure.GARObject;

public interface IAPTypeDescriptor {
	
	public String getVirtualElementName();

	public String getTypeName();

	public Class<? extends GARObject> getTypeClazz();
	
	public boolean isChildable();

	public boolean isExistDetailView();

	public boolean isEditableDetailView();
	
	public List<IAPTypeDescriptor> getChildrenAPTypeDeacriptors();
	
	default boolean isInstance(GARObject element) {
		return getTypeClazz().isInstance(element);
	}
	
	default void addChildAPTypeDescriptor(IAPTypeDescriptor child) {
		if(!getChildrenAPTypeDeacriptors().contains(child)) {
			getChildrenAPTypeDeacriptors().add(child);
		}
	}
}
