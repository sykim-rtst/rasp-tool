package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipEventDeployment;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipEventGroup;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipFieldDeployment;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipMethodDeployment;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public class InterfaceDeploymentModelManager extends AbstractAPModelManager {
	
	//#################################################################################################
	// Services-Interfaces의 하위에 오는 타입들
	//#################################################################################################
//	public static final String TYPE_NAME_SERVICE_INTERFACE_DEPLOYMENT 	= "ServiceInterfaceDeployment";
	public static final String TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT	= "SomeipServiceInterfaceDeployment";

	public static final String TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT 				= "SomeipEventDeployment";
	public static final String TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT 				= "SomeipMethodDeployment";
	public static final String TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT 				= "SomeipFieldDeployment";
	
	//#################################################################################################
	// Services-Deployments의 하위에 오는 타입들
	//#################################################################################################
	// 없음
	
	InterfaceDeploymentModelManager() {
	}
	
	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT, TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT, SomeipServiceInterfaceDeployment.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT, TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT, SomeipEventDeployment.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT, TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT, SomeipMethodDeployment.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT, TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT, SomeipFieldDeployment.class, true, true);
		
		apTypeDescriptors.add(TD_TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT);
		
		apTypeDescriptors.add(TD_TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT);
		apTypeDescriptors.add(TD_TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT);
		apTypeDescriptors.add(TD_TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT);
		
		TD_TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT.addChildAPTypeDescriptor(TD_TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT);
		TD_TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT.addChildAPTypeDescriptor(TD_TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT);
		TD_TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT.addChildAPTypeDescriptor(TD_TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT);
		
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
				case IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT:{
					return new ArrayList<>(getAllSomeipServiceInterfaceDeployment(root));
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
				case TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT:{
					SomeipServiceInterfaceDeployment model = (SomeipServiceInterfaceDeployment)element;
					children.addAll(model.getEventDeployments());
					children.addAll(model.getMethodDeployments());
					children.addAll(model.getFieldDeployments());
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + children.size());
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
//		// Event
//		if(element instanceof VariableDataPrototype) {
//			VariableDataPrototype model = (VariableDataPrototype)element;
//			// TODO
//		}else if(element instanceof Field) {
//			Field model = (Field)element;
//			return model.getServiceInterface();
//		}else if(element instanceof ClientServerOperation) {
//			ClientServerOperation model = (ClientServerOperation)element;
//			// TODO
//		}else if(element instanceof ApplicationError) {
//			ApplicationError model = (ApplicationError)element;
//			// TODO
//		}
//		
//		else if(element instanceof ArgumentDataPrototype) {
//			ArgumentDataPrototype model = (ArgumentDataPrototype)element;
//			// TODO
//		}
		return null;
	}
	
//	/**
//	 * 
//	 * @param element
//	 * @return
//	 */
//	public boolean hasChildren(GARObject element) {
////		if(element instanceof ServiceInterface) {
////			ServiceInterface model = (ServiceInterface)element;
////			return !(model.getEvents().isEmpty()) || !(model.getFields().isEmpty()) || !(model.getMethods().isEmpty()) || !(model.getPossibleErrors().isEmpty());
////		}
////		// -> Method
////		else if(element instanceof ClientServerOperation) {
////			ClientServerOperation model = (ClientServerOperation)element;
////			return !model.getArguments().isEmpty();
////		}
////		// -> PossibleError
////		else if(element instanceof ApplicationError) {
////			// TODO 
////			return false;
////		}
//		return false;
//	}
	
	public List<SomeipServiceInterfaceDeployment> getAllSomeipServiceInterfaceDeployment(GAUTOSAR rootElement) {
		final List<SomeipServiceInterfaceDeployment> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getSomeipServiceInterfaceDeployment(p));
		});
		
		return result;
	}
	
	public List<SomeipServiceInterfaceDeployment> getSomeipServiceInterfaceDeployment(GARPackage arPackage){
		List<SomeipServiceInterfaceDeployment> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof SomeipServiceInterfaceDeployment) {
				result.add((SomeipServiceInterfaceDeployment)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getSomeipServiceInterfaceDeployment(p));
			});
		}
		
		return result;
	}
	
}
