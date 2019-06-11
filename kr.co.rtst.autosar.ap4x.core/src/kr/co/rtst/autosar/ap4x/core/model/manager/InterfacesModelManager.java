package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.adaptiveplatform.applicationdesign.portinterface.Field;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.portinterface.ApplicationError;
import autosar40.swcomponent.portinterface.ArgumentDataPrototype;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public class InterfacesModelManager extends AbstractAPModelManager {
	
//	public static final String[] TYPE_NAMES_INTERFACES = {
//			TYPE_NAME_ARGUMENT_DATA_PROTOTYPE,
//			
//			TYPE_NAME_VARIABLE_DATA_PROTOTYPE,
//			TYPE_NAME_FIELD,
//			TYPE_NAME_CLIENT_SERVER_OPERATION,
//			TYPE_NAME_APPLICATION_ERROR,
//			
//			TYPE_NAME_SECVICE_INTERFACE,
//	};
//	
//	public static final Class<?>[] TYPE_CLASSES_INTERFACES = new Class[] {
//			ArgumentDataPrototype.class,
//			
//			VariableDataPrototype.class,
//			Field.class,
//			ClientServerOperation.class,
//			ApplicationError.class,
//			
//			ServiceInterface.class,
//	};
	
	//#################################################################################################
	// Services-Interfaces의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_ARGUMENT_DATA_PROTOTYPE = "ArgumentDataPrototype";

	public static final String TYPE_NAME_VARIABLE_DATA_PROTOTYPE = "VariableDataPrototype";
	public static final String TYPE_NAME_FIELD = "Field";
	public static final String TYPE_NAME_CLIENT_SERVER_OPERATION = "ClientServerOperation";
	public static final String TYPE_NAME_APPLICATION_ERROR = "ApplicationError";

	public static final String TYPE_NAME_SECVICE_INTERFACE = "ServiceInterface";
	
	//#################################################################################################
	// Services-Deployments의 하위에 오는 타입들
	//#################################################################################################
	// 없음
	
	InterfacesModelManager() {
	}
	
	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_ARGUMENT_DATA_PROTOTYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, TYPE_NAME_ARGUMENT_DATA_PROTOTYPE, ArgumentDataPrototype.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_VARIABLE_DATA_PROTOTYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, TYPE_NAME_VARIABLE_DATA_PROTOTYPE, VariableDataPrototype.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_FIELD = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, TYPE_NAME_FIELD, Field.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_CLIENT_SERVER_OPERATION = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, TYPE_NAME_CLIENT_SERVER_OPERATION, ClientServerOperation.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_APPLICATION_ERROR = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, TYPE_NAME_APPLICATION_ERROR, ApplicationError.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_SECVICE_INTERFACE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, TYPE_NAME_SECVICE_INTERFACE, ServiceInterface.class, true, true);
		
		apTypeDescriptors.add(TD_TYPE_NAME_ARGUMENT_DATA_PROTOTYPE);
		
		apTypeDescriptors.add(TD_TYPE_NAME_VARIABLE_DATA_PROTOTYPE);
		apTypeDescriptors.add(TD_TYPE_NAME_FIELD);
		apTypeDescriptors.add(TD_TYPE_NAME_CLIENT_SERVER_OPERATION);
		apTypeDescriptors.add(TD_TYPE_NAME_APPLICATION_ERROR);
		
		apTypeDescriptors.add(TD_TYPE_NAME_SECVICE_INTERFACE);
		
		TD_TYPE_NAME_SECVICE_INTERFACE.addChildAPTypeDescriptor(TD_TYPE_NAME_VARIABLE_DATA_PROTOTYPE);
		TD_TYPE_NAME_SECVICE_INTERFACE.addChildAPTypeDescriptor(TD_TYPE_NAME_FIELD);
		TD_TYPE_NAME_SECVICE_INTERFACE.addChildAPTypeDescriptor(TD_TYPE_NAME_CLIENT_SERVER_OPERATION);
		TD_TYPE_NAME_SECVICE_INTERFACE.addChildAPTypeDescriptor(TD_TYPE_NAME_APPLICATION_ERROR);
		
		TD_TYPE_NAME_CLIENT_SERVER_OPERATION.addChildAPTypeDescriptor(TD_TYPE_NAME_ARGUMENT_DATA_PROTOTYPE);
		TD_TYPE_NAME_APPLICATION_ERROR.addChildAPTypeDescriptor(TD_TYPE_NAME_ARGUMENT_DATA_PROTOTYPE);
		
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
				case IAPVirtualElement.VE_NAME_SERVICE_INTERFACE:{
					return new ArrayList<>(getAllServiceInterface(root));
				}
				case IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT:{
					// TODO
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
		List<GARObject> chindren = new ArrayList<>();
		IAPTypeDescriptor typeDesc = findAPTypeDescriptor(element);
		if(typeDesc != null && typeDesc.isChildable()) {
			switch(typeDesc.getTypeName()) {
				case TYPE_NAME_CLIENT_SERVER_OPERATION:{
					ClientServerOperation model = (ClientServerOperation)element;
					chindren.addAll(model.getArguments());
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size());
				}
				break;
//				case TYPE_NAME_APPLICATION_ERROR:{
//					ApplicationError model = (ApplicationError)element;
//					chindren.addAll(model.getErrorContexts());
//					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size());
//				}
//				break;
				case TYPE_NAME_SECVICE_INTERFACE:{
					ServiceInterface model = (ServiceInterface)element;
					chindren.addAll(model.getEvents());
					chindren.addAll(model.getFields());
					chindren.addAll(model.getMethods());
//					chindren.addAll(model.getPossibleErrors());
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size());
				}
				break;
			}
		}
		
		return chindren;
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

	public List<ServiceInterface> getAllServiceInterface(GAUTOSAR rootElement) {
		final List<ServiceInterface> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getServiceInterface(p));
		});
		
		return result;
	}
	
	public List<ServiceInterface> getServiceInterface(GARPackage arPackage){
		List<ServiceInterface> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof ServiceInterface) {
				result.add((ServiceInterface)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getServiceInterface(p));
			});
		}
		
		return result;
	}
	
}
