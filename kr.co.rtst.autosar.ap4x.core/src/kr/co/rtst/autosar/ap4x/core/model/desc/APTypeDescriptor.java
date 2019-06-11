package kr.co.rtst.autosar.ap4x.core.model.desc;

import java.util.ArrayList;
import java.util.List;

import gautosar.ggenericstructure.ginfrastructure.GARObject;

/**
 * @author thkim
 *
 */
public class APTypeDescriptor implements IAPTypeDescriptor {

	private List<IAPTypeDescriptor> childrenAPTypeDeacriptors;
	/**
	 * 가장 근접한 가상 요소의 이름
	 */
	private final String virtualElementName;
	private final String typeName; // 편집기 페이지와 매핑될 키값(타입의 이름)
	private final Class<? extends GARObject> typeClazz;
	private final boolean existDetailView; // 상세항목 정보를 보여주는 뷰가 존재하는가? (상세 편집기 페이지를 의미한다.)
	private final boolean editableDetailView; // 상세 항목 정보 뷰의 편집이 가능한가? (보여주기만 하고 전체내용을 편집할 수 없는 경우도 있음)
	
	public APTypeDescriptor(String virtualElementName, String typeName, Class<? extends GARObject> typeClazz, boolean existDetailView, boolean editableDetailView) {
		super();
		this.virtualElementName = virtualElementName;
		this.typeName = typeName;
		this.typeClazz = typeClazz;
		this.existDetailView = existDetailView;
		this.editableDetailView = editableDetailView;
		this.childrenAPTypeDeacriptors = new ArrayList<>();
	}

	public String getVirtualElementName() {
		return virtualElementName;
	}

	public String getTypeName() {
		return typeName;
	}
	
	public Class<? extends GARObject> getTypeClazz() {
		return typeClazz;
	}
	
	public boolean isChildable() {
		return !childrenAPTypeDeacriptors.isEmpty();
	}

	public boolean isExistDetailView() {
		return existDetailView;
	}

	public boolean isEditableDetailView() {
		return editableDetailView;
	}

	public List<IAPTypeDescriptor> getChildrenAPTypeDeacriptors() {
		return childrenAPTypeDeacriptors;
	}
}
