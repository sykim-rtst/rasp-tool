package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.List;

import org.eclipse.core.resources.IFile;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

/**
 * 도구에서 관리하는 모델 객체를 관리하기 위한 객체
 * 도구에서 관리하는 모든 최상위 요소를 관리하는 각각의 객체는 이 인터페이스를 구현해야 한다.
 * @author thkim
 *
 */
public interface IAPModelManager {
	
	public static final String SHORT_NAME_SEPARATOR = ".";

	/**
	 * 주어진 가상 엘리먼트의 자식을 반환한다.
	 * @param rootElement
	 * @param virtualElementName
	 * @return
	 */
	List<GARObject> getChildren(IFile arxmlFile, String virtualElementName);
	
	/**
	 * 
	 * @param gaObject
	 * @return
	 */
	List<GARObject> getChildren(GARObject gaObject);

	/**
	 * 
	 * @param gaObject
	 * @return
	 */
	GARObject getParent(GARObject gaObject);
	
	/**
	 * 
	 * @param gaObject
	 * @return
	 */
	default boolean hasChildren(GARObject gaObject) {
		List<GARObject> c = getChildren(gaObject);
		if(c != null && !c.isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 이 모델 관리자에서 관리하는 타입들의 IAPTypeDescriptor 객체 목록을 반환한다. 
	 * 목록을 구성할때 반드시 최하위 타입부터 순서대로 목록에 들어 가도록 한다.
	 * @return
	 */
	List<IAPTypeDescriptor> getAPTypeDescriptors();	

//	/**
//	 * 주어진 객체에서 생성가능한 하위 객체들의 디스크립터 객체목록을 반환한다.
//	 * @param gaObject
//	 * @return
//	 */
//	List<IAPTypeDescriptor> getCreatableAPTypeDescriptors(GARObject gaObject);
	
	/**
	 * 주어진 객체에 대응되는 디스크립터 객체를 반환한다.
	 * @param gaObject
	 * @return
	 */
	default IAPTypeDescriptor findAPTypeDescriptor(GARObject gaObject) {
		for (IAPTypeDescriptor typeDesc : getAPTypeDescriptors()) {
			if(typeDesc.isInstance(gaObject)) {
				return typeDesc;
			}
		}
		return null;
	}
	
	/**
	 * 주어진 엘리먼트가 이 모델매니저가 관리하는 최상위 모델의 자식이면서 현재 도구를 통해 관리되어야 하는 요소인지 검사한다.
	 * 실제로 자식이라 하더라도 본 도구에서 관리하지 않는 요소에 대해서는 null을 반환한다.
	 * 이 메소드의 결과가 true인 요소들은 도구에서 해당 요소의 편집기를 제공하고 있다.
	 * 
	 * @param gaObject 검사대상 요소
	 * @return 
	 */
	default boolean isNavigatableSubElement(GARObject gaObject) {
		return findAPTypeDescriptor(gaObject) != null;
	}
	
	/**
	 * 주어진 요소에 대한 가장 근접한 타입명을 반환한다.
	 * 주어진 요소는 반드시 isNavigatableSubElement()결과가 true인 요소인 경우에 정상적인 값을 반환하며, isNavigatableSubElement()결과가 false인경우 null을 반환한다.
	 * 이 메소드는 편집기의 Detail 영역을 식별하기 위해 사용된다.
	 * @param gaObject
	 * @return
	 */
	default String getTypeName(GARObject gaObject) {
		IAPTypeDescriptor typeDesc = findAPTypeDescriptor(gaObject);
		if(typeDesc != null) {
			return typeDesc.getTypeName();
		}
		return null;
	}
}
