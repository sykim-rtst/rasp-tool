package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.adaptiveplatform.applicationdesign.adaptiveinternalbehavior.AdaptiveSwcInternalBehavior;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.adaptiveplatform.applicationdesign.comspec.PersistencyDataProvidedComSpec;
import autosar40.adaptiveplatform.applicationdesign.comspec.PersistencyDataRequiredComSpec;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.swcomponent.communication.ClientComSpec;
import autosar40.swcomponent.communication.PPortComSpec;
import autosar40.swcomponent.communication.RPortComSpec;
import autosar40.swcomponent.communication.ReceiverComSpec;
import autosar40.swcomponent.communication.SenderComSpec;
import autosar40.swcomponent.communication.ServerComSpec;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.PortGroup;
import autosar40.swcomponent.components.RPortPrototype;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public class SwComponentsModelManager extends AbstractAPModelManager {
	
	//#################################################################################################
	// Applications-SwComponents의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_RECEIVER_COM_SPEC = "ReceiverComSpec";
	public static final String TYPE_NAME_CLIENT_COM_SPEC = "ClientComSpec";
	public static final String TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC = "PersistencyDataRequiredComSpec";
//	public static final String TYPE_NAME_RPORTCOMSPEC = "RPortComSpec"; // 위 3개의 타입이 이 타입의 서브타입이다. 실제 이 타입이 사용되는 경우는 현재 없다.
	
	public static final String TYPE_NAME_SENDER_COM_SPEC = "SenderComSpec";
	public static final String TYPE_NAME_SERVER_COM_SPEC = "ServerComSpec";
	public static final String TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC = "PersistencyDataProvidedComSpec";
//	public static final String TYPE_NAME_PPORTCOMSPEC = "PPortComSpec"; // 위 3개의 타입이 이 타입의 서브타입이다. 실제 이 타입이 사용되는 경우는 현재 없다.
	
	public static final String TYPE_NAME_PPORT_PROTOTYPE = "PPortPrototype";
	public static final String TYPE_NAME_RPORT_PROTOTYPE = "RPortPrototype";
	public static final String TYPE_NAME_PORT_GROUP = "PortGroup";
	public static final String TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR = "AdaptiveSwcInternalBehavior";
	
	public static final String TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE = "AdaptiveApplicationSwComponentType";
	
	SwComponentsModelManager() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_RECEIVER_COM_SPEC = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_RECEIVER_COM_SPEC, ReceiverComSpec.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_CLIENT_COM_SPEC = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_CLIENT_COM_SPEC, ClientComSpec.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC, PersistencyDataRequiredComSpec.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_SENDER_COM_SPEC = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_SENDER_COM_SPEC, SenderComSpec.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_SERVER_COM_SPEC = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_SERVER_COM_SPEC, ServerComSpec.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC, PersistencyDataProvidedComSpec.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_PPORT_PROTOTYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_PPORT_PROTOTYPE, PPortPrototype.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_RPORT_PROTOTYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_RPORT_PROTOTYPE, RPortPrototype.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_PORT_GROUP = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_PORT_GROUP, PortGroup.class, true, false);
		IAPTypeDescriptor TD_TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR, AdaptiveSwcInternalBehavior.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_APPLICATION_SWC, TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE, AdaptiveApplicationSwComponentType.class, true, true);
		
		apTypeDescriptors.add(TD_TYPE_NAME_RECEIVER_COM_SPEC);
		apTypeDescriptors.add(TD_TYPE_NAME_CLIENT_COM_SPEC);
		apTypeDescriptors.add(TD_TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC);
		
		apTypeDescriptors.add(TD_TYPE_NAME_SENDER_COM_SPEC);
		apTypeDescriptors.add(TD_TYPE_NAME_SERVER_COM_SPEC);
		apTypeDescriptors.add(TD_TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC);
		
		apTypeDescriptors.add(TD_TYPE_NAME_PPORT_PROTOTYPE);
		apTypeDescriptors.add(TD_TYPE_NAME_RPORT_PROTOTYPE);
		apTypeDescriptors.add(TD_TYPE_NAME_PORT_GROUP);
		apTypeDescriptors.add(TD_TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR);
		
		apTypeDescriptors.add(TD_TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE);
		
		TD_TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_PPORT_PROTOTYPE);
		TD_TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_RPORT_PROTOTYPE);
		TD_TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_PORT_GROUP);
		TD_TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR);
		
		TD_TYPE_NAME_PPORT_PROTOTYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_SENDER_COM_SPEC);
		TD_TYPE_NAME_PPORT_PROTOTYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_SERVER_COM_SPEC);
		TD_TYPE_NAME_PPORT_PROTOTYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC);
		
		TD_TYPE_NAME_RPORT_PROTOTYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_RECEIVER_COM_SPEC);
		TD_TYPE_NAME_RPORT_PROTOTYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_CLIENT_COM_SPEC);
		TD_TYPE_NAME_RPORT_PROTOTYPE.addChildAPTypeDescriptor(TD_TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC);
		
		TD_TYPE_NAME_PORT_GROUP.addChildAPTypeDescriptor(TD_TYPE_NAME_PPORT_PROTOTYPE);
		TD_TYPE_NAME_PORT_GROUP.addChildAPTypeDescriptor(TD_TYPE_NAME_RPORT_PROTOTYPE);
		TD_TYPE_NAME_PORT_GROUP.addChildAPTypeDescriptor(TD_TYPE_NAME_PORT_GROUP); // 자기가 자기자신을 부모로 가지는 경우이다. 절대 순환하는 로직을 작성하지 말것
		
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
				case IAPVirtualElement.VE_NAME_APPLICATION_SWC:{
					return new ArrayList<>(getAllAdaptiveApplicationSwComponentType(root));
				}
				case IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM:{
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
				case TYPE_NAME_RPORT_PROTOTYPE:{
					RPortPrototype model = (RPortPrototype)element;
					EList<RPortComSpec> rPortChildren = model.getRequiredComSpecs();
					if(rPortChildren != null && !rPortChildren.isEmpty()) {
						rPortChildren.stream().forEach(e->{
							if(e instanceof ReceiverComSpec) {
								chindren.add(e);
							}
						});
						rPortChildren.stream().forEach(e->{
							if(e instanceof ClientComSpec) {
								chindren.add(e);
							}
						});
						rPortChildren.stream().forEach(e->{
							if(e instanceof PersistencyDataRequiredComSpec) {
								chindren.add(e);
							}
						});
					}
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size() + " / " + rPortChildren.size());
				}
				break;
				case TYPE_NAME_PPORT_PROTOTYPE:{
					PPortPrototype model = (PPortPrototype)element;
					EList<PPortComSpec> pPortChildren = model.getProvidedComSpecs();
					if(pPortChildren != null && !pPortChildren.isEmpty()) {
						pPortChildren.stream().forEach(e->{
							if(e instanceof SenderComSpec) {
								chindren.add(e);
							}
						});
						pPortChildren.stream().forEach(e->{
							if(e instanceof ServerComSpec) {
								chindren.add(e);
							}
						});
						pPortChildren.stream().forEach(e->{
							if(e instanceof PersistencyDataProvidedComSpec) {
								chindren.add(e);
							}
						});
					}
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size() + " / " + pPortChildren.size());
				}
				break;
				case TYPE_NAME_PORT_GROUP:{
					PortGroup model = (PortGroup)element;
					chindren.addAll(model.getOuterPorts());
					chindren.addAll(model.getInnerGroups());
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size());
				}
				break;
				case TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE:{
					AdaptiveApplicationSwComponentType model = (AdaptiveApplicationSwComponentType)element;
					chindren.addAll(model.getPorts());
					chindren.addAll(model.getPortGroups());
					chindren.addAll(model.getInternalBehaviors());
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
	public GARObject getParent(GARObject gaObject) {
//		// ->RPortPrototype->RPortComSpec
//		if(element instanceof TransformationComSpecProps) {
//			TransformationComSpecProps model = (TransformationComSpecProps)element;
////			return model.
//		}
//		// ->PPortPrototype->PPortComSpec
//		else if(element instanceof CompositeNetworkRepresentation) {
//			CompositeNetworkRepresentation model = (CompositeNetworkRepresentation)element;
////			return model.
//		}
//		
//		else if(element instanceof AdaptiveApplicationSwComponentType) {
////			AdaptiveApplicationSwComponentType model = (AdaptiveApplicationSwComponentType)element;
////			return !(model.getPorts().isEmpty()) || !(model.getPortGroups().isEmpty()) || !(model.getInternalBehaviors().isEmpty());
//		}else if(element instanceof PPortPrototype) {
//			PPortPrototype model = (PPortPrototype)element;
//			return model.getSwComponentType();
//		}else if(element instanceof RPortPrototype) {
//			RPortPrototype model = (RPortPrototype)element;
//			return model.getSwComponentType();
//		}else if(element instanceof PortGroup) {
//			PortGroup model = (PortGroup)element;
//			return model.getSwComponentType();
//		}else if(element instanceof AdaptiveSwcInternalBehavior) {
//			AdaptiveSwcInternalBehavior model = (AdaptiveSwcInternalBehavior)element;
//			return model.getAdaptiveApplicationSwComponentType();
//		}
		return null;
	}
	
//	/**
//	 * 
//	 * @param element
//	 * @return
//	 */
//	public boolean hasChildren(GARObject element) {
//		return !getChildren(element).isEmpty();
////		// ->RPortPrototype->RPortComSpec 3가지 타입이 존재함.
////		if(element instanceof /*RPortComSpec*/ClientComSpec) {
////			ClientComSpec model = (ClientComSpec)element;
////			return !model.getTransformationComSpecProps().isEmpty();
////		}else if(element instanceof /*RPortComSpec*/ReceiverComSpec) {
////			ReceiverComSpec model = (ReceiverComSpec)element;
////			return !model.getTransformationComSpecProps().isEmpty();
////		}else if(element instanceof /*RPortComSpec*/PersistencyDataRequiredComSpec) {
////			return false;
////		}
////		
////		// ->PPortPrototype->PPortComSpec 3가지 타입이 존재함.
////		else if(element instanceof /*PPortComSpec*/SenderComSpec) {
////			SenderComSpec model = (SenderComSpec)element;
////			return !model.getCompositeNetworkRepresentations().isEmpty();
////		}else if(element instanceof /*PPortComSpec*/ServerComSpec) {
////			ServerComSpec model = (ServerComSpec)element;
////			return !model.getTransformationComSpecProps().isEmpty();
////		}else if(element instanceof /*PPortComSpec*/PersistencyDataProvidedComSpec) {
////			return false;
////		}
////		
////		else if(element instanceof AdaptiveApplicationSwComponentType) {
////			AdaptiveApplicationSwComponentType model = (AdaptiveApplicationSwComponentType)element;
////			return !(model.getPorts().isEmpty()) || !(model.getPortGroups().isEmpty()) || !(model.getInternalBehaviors().isEmpty());
////		}else if(element instanceof PPortPrototype) {
////			PPortPrototype model = (PPortPrototype)element;
////			return !model.getProvidedComSpecs().isEmpty();
////		}else if(element instanceof RPortPrototype) {
////			RPortPrototype model = (RPortPrototype)element;
////			return !model.getRequiredComSpecs().isEmpty();
////		}else if(element instanceof PortGroup) {
////			return false;
////		}else if(element instanceof InternalBehavior) {
////			return false;
////		}
////		return false;
////		return false;
//	}
	
	/**
	 * 주어진 엘리먼트 아래의 모든 패키지에 대한 AdaptiveApplicationSwComponentType 엘리먼트를 반환한다.
	 * @param rootElement
	 * @return
	 */
	public List<AdaptiveApplicationSwComponentType> getAllAdaptiveApplicationSwComponentType(GAUTOSAR rootElement) {
		final List<AdaptiveApplicationSwComponentType> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getAdaptiveApplicationSwComponentType(p));
		});
		
		return result;
	}
	
	/**
	 * 주어진 패키지 아래의 모든  AdaptiveApplicationSwComponentType 엘리먼트를 반환한다.
	 * @param arPackage
	 * @return
	 */
	public List<AdaptiveApplicationSwComponentType> getAdaptiveApplicationSwComponentType(GARPackage arPackage){
		List<AdaptiveApplicationSwComponentType> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof AdaptiveApplicationSwComponentType) {
				result.add((AdaptiveApplicationSwComponentType)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getAdaptiveApplicationSwComponentType(p));
			});
		}
		
		return result;
	}
	
}
