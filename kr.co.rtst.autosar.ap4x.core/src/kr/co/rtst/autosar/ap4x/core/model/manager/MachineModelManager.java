package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.adaptiveplatform.machinemanifest.Machine;
import autosar40.adaptiveplatform.machinemanifest.ProcessToMachineMapping;
import autosar40.adaptiveplatform.machinemanifest.ProcessToMachineMappingSet;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroupPrototype;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelContainerAccessor;

/**
 * Machine 에 관련된 모델들을 관리해주는 객체이다.
 * @author thkim
 *
 */
public class MachineModelManager extends AbstractAPModelManager {
	public static final String MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP__SHORT_NAME_POST_FIX 				= "_ModeDeclarationGroup";
	public static final String MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP_PROTOTYPE__SHORT_NAME_POST_FIX 		= "_Mode";
	
	//#################################################################################################
	// System-Machines의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE = "ModeDeclarationGroupPrototype";
	public static final String TYPE_NAME_PROCESS_TO_MACHINE_MAPPING = "ProcessToMachineMapping";

	public static final String TYPE_NAME_MACHINE = "Machine";
	
	MachineModelManager() {
	}
	
	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE, TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE, ModeDeclarationGroupPrototype.class, true, true);
//		IAPTypeDescriptor TD_TYPE_NAME_PROCESS_TO_MACHINE_MAPPING_SET = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE, TYPE_NAME_PROCESS_TO_MACHINE_MAPPING_SET, ProcessToMachineMappingSet.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_PROCESS_TO_MACHINE_MAPPING = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE, TYPE_NAME_PROCESS_TO_MACHINE_MAPPING, ProcessToMachineMapping.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_MACHINE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE, TYPE_NAME_MACHINE, Machine.class, true, true);
		
		apTypeDescriptors.add(TD_TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE);
		apTypeDescriptors.add(TD_TYPE_NAME_PROCESS_TO_MACHINE_MAPPING);
		apTypeDescriptors.add(TD_TYPE_NAME_MACHINE);
		
		TD_TYPE_NAME_MACHINE.addChildAPTypeDescriptor(TD_TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE);
		TD_TYPE_NAME_MACHINE.addChildAPTypeDescriptor(TD_TYPE_NAME_PROCESS_TO_MACHINE_MAPPING);
		
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
				case IAPVirtualElement.VE_NAME_MACHINE:{
					return new ArrayList<>(getAllMachine(root));
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
		List<GARObject> children = new ArrayList<>();
		IAPTypeDescriptor typeDesc = findAPTypeDescriptor(element);
		if(typeDesc != null && typeDesc.isChildable()) {
			switch(typeDesc.getTypeName()) {
				case TYPE_NAME_MACHINE:{
					Machine model = (Machine)element;
					children.addAll(model.getFunctionGroups());
					ProcessToMachineMappingSet processToMachineMappingSet = APModelContainerAccessor.eINSTANCE.getProcessToMachineMappingSet(APProjectManager.getInstance().getAPProject(element));
					children.addAll(processToMachineMappingSet.getProcessToMachineMappings());
//					for(GARObject garObject: model.getFunctionGroups()) {
//						if(isNavigatableSubElement(garObject)){
//							chindren.add(garObject);
//						}
//					}
//					for(GARObject garObject: model.getProcessors()) {
//						if(isNavigatableSubElement(garObject)){
//							chindren.add(garObject);
//						}
//					}
//					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size());
				}
				break;
			}
		}
		return children;
	}

	/**
	 * 
	 * @param element
	 * @return
	 */
	public GARObject getParent(GARObject element) {
		return null;
	}
	
//	/**
//	 * 
//	 * @param element
//	 * @return
//	 */
//	public boolean hasChildren(GARObject element) {
//		return false;
//	}

	public List<Machine> getAllMachine(GAUTOSAR rootElement) {
		final List<Machine> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getMachine(p));
		});
		
		return result;
	}
	
	public List<Machine> getMachine(GARPackage arPackage){
		List<Machine> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof Machine) {
				result.add((Machine)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getMachine(p));
			});
		}
		
		return result;
	}
	
}
