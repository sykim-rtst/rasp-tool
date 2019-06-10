package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceDiscovery;
import autosar40.adaptiveplatform.systemdesign.MachineDesign;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCommunicationConnector;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelContainerAccessor;

public class MachineDesignsModelManager extends AbstractAPModelManager {
	//#################################################################################################
	// Machines-MachineDesigns의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR = "EthernetCommunicationConnector";
	public static final String TYPE_NAME_SOMEIP_SERVICE_DISCOVERY = "SomeipServiceDiscovery";
	
	public static final String TYPE_NAME_NETWORK_ENDPOINT					= "NetworkEndpoint";

	public static final String TYPE_NAME_MACHINE_DESIGN = "MachineDesign";
	
	MachineDesignsModelManager() {
	}
	
	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE_DESIGN, TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR, EthernetCommunicationConnector.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_SOMEIP_SERVICE_DISCOVERY = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE_DESIGN, TYPE_NAME_SOMEIP_SERVICE_DISCOVERY, SomeipServiceDiscovery.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_NETWORK_ENDPOINT = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE_DESIGN, TYPE_NAME_NETWORK_ENDPOINT, NetworkEndpoint.class, true, true);
		
		IAPTypeDescriptor TD_TYPE_NAME_MACHINE_DESIGN = new APTypeDescriptor(IAPVirtualElement.VE_NAME_MACHINE_DESIGN, TYPE_NAME_MACHINE_DESIGN, MachineDesign.class, true, true);
		
		apTypeDescriptors.add(TD_TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR);
		apTypeDescriptors.add(TD_TYPE_NAME_SOMEIP_SERVICE_DISCOVERY);
		apTypeDescriptors.add(TD_TYPE_NAME_NETWORK_ENDPOINT);
		apTypeDescriptors.add(TD_TYPE_NAME_MACHINE_DESIGN);
		
		TD_TYPE_NAME_MACHINE_DESIGN.addChildAPTypeDescriptor(TD_TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR);
		TD_TYPE_NAME_MACHINE_DESIGN.addChildAPTypeDescriptor(TD_TYPE_NAME_SOMEIP_SERVICE_DISCOVERY);
		TD_TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR.addChildAPTypeDescriptor(TD_TYPE_NAME_NETWORK_ENDPOINT);
		TD_TYPE_NAME_SOMEIP_SERVICE_DISCOVERY.addChildAPTypeDescriptor(TD_TYPE_NAME_NETWORK_ENDPOINT);
		
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
				case IAPVirtualElement.VE_NAME_MACHINE_DESIGN:{
					return new ArrayList<>(getAllMachineDesign(root));
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
				case TYPE_NAME_MACHINE_DESIGN:{
					MachineDesign model = (MachineDesign)element;
					for(GARObject garObject: model.getCommunicationConnectors()) {
						if(isNavigatableSubElement(garObject)){
							chindren.add(garObject);
						}
					}
					for(GARObject garObject: model.getServiceDiscoverConfigs()) {
						if(isNavigatableSubElement(garObject)){
							chindren.add(garObject);
						}
					}
//					System.out.println(typeDesc.getTypeName()+" 자식요소: " + chindren.size());
				}
				break;
				case TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR:{
					EObject parent = element;
					while(parent.eContainer() != null) {
						parent = parent.eContainer();
					}
					
					System.out.println("TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR 최상위 부모:"+(parent.getClass().getName()));
					List<NetworkEndpoint> netrowkEndpointList = APModelContainerAccessor.eINSTANCE.getAllNetworkEndpoint((GAUTOSAR)parent);
					for (NetworkEndpoint networkEndpoint : netrowkEndpointList) {
						if(((EthernetCommunicationConnector)element).getNetworkEndpoints().contains(networkEndpoint)) {
							chindren.add(networkEndpoint);
						}else if(networkEndpoint.equals(((EthernetCommunicationConnector)element).getUnicastNetworkEndpoint())) {
							chindren.add(networkEndpoint);
						}
					}
				}
				break;
				case TYPE_NAME_SOMEIP_SERVICE_DISCOVERY:{
					EObject parent = element;
					while(parent.eContainer() != null) {
						parent = parent.eContainer();
					}
					
					System.out.println("TYPE_NAME_SOMEIP_SERVICE_DISCOVERY 최상위 부모:"+(parent.getClass().getName()));
					List<NetworkEndpoint> netrowkEndpointList = APModelContainerAccessor.eINSTANCE.getAllNetworkEndpoint((GAUTOSAR)parent);
					for (NetworkEndpoint networkEndpoint : netrowkEndpointList) {
						if(networkEndpoint.equals(((SomeipServiceDiscovery)element).getMulticastSdIpAddress())) {
//						if(((SomeipServiceDiscovery)element).getMulticastSdIpAddress().equals(networkEndpoint)) {
							chindren.add(networkEndpoint);
							break;
						}
					}
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

	public List<MachineDesign> getAllMachineDesign(GAUTOSAR rootElement) {
		final List<MachineDesign> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getMachineDesign(p));
		});
		
		return result;
	}
	
	public List<MachineDesign> getMachineDesign(GARPackage arPackage){
		List<MachineDesign> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof MachineDesign) {
				result.add((MachineDesign)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getMachineDesign(p));
			});
		}
		
		return result;
	}
	
}
