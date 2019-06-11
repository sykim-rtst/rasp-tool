package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public class ImplementationDataTypesModelManager extends AbstractAPModelManager {
	//#################################################################################################
	// Types-ImplementationDataTypes의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_IMPLEMENTATION_DATA_TYPE = "ImplementationDataType";
	
	ImplementationDataTypesModelManager() {
	}
	
	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_IMPLEMENTATION_DATA_TYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_TYPE_IMPL, TYPE_NAME_IMPLEMENTATION_DATA_TYPE, ImplementationDataType.class, true, true);
		
		apTypeDescriptors.add(TD_TYPE_NAME_IMPLEMENTATION_DATA_TYPE);
		
		return apTypeDescriptors;
	}
	
	/**
	 * 주어진 가상 엘리먼트의 자식을 반환한다.
	 * @param rootElement
	 * @param virtualElementName
	 * @return
	 */
	public List<GARObject> getChildren(IFile arxmlFile, String virtualElementName){
		GAUTOSAR root = load(arxmlFile);
		if(root != null) {
			switch(virtualElementName) {
				case IAPVirtualElement.VE_NAME_TYPE_IMPL:{
					return new ArrayList<>(getAllImplementationDataType(root));
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	public List<GARObject> getChildren(GARObject element){
		return null;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public GARObject getParent(GARObject element) {
		return null;
	}
	
	public List<ImplementationDataType> getAllImplementationDataType(GAUTOSAR rootElement/*ARPackage rootPackage*/) {
		final List<ImplementationDataType> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getImplementationDataType(p));
		});
		
		return result;
	}
	
	public List<ImplementationDataType> getImplementationDataType(GARPackage arPackage){
		List<ImplementationDataType> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof ImplementationDataType) {
				result.add((ImplementationDataType)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getImplementationDataType(p));
			});
		}
		
		return result;
	}
	
}
