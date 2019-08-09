package kr.co.rtst.autosar.ap4x.ide.action;


import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;

import autosar40.adaptiveplatform.machinemanifest.Machine;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ProvidedSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.RequiredSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipServiceDiscovery;
//import autosar40.adaptiveplatform.deployment.machine.Machine;
//import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceDiscovery;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCommunicationConnector;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;

public interface IAPActionContainer {

	default void populateAPActions(IAPProject apProject, IContributionManager menuManager, IStructuredSelection selection) {
//		if()GARObject ownerObject
		if(selection != null && selection.size() == 1 && selection.getFirstElement() instanceof GARObject) {
			GARObject garObject = (GARObject)selection.getFirstElement();
			
			if(garObject instanceof EthernetCommunicationConnector) {
				EthernetCommunicationConnector owner = (EthernetCommunicationConnector)garObject;
				menuManager.add(new Separator());
				try {
					menuManager.add(new ElementModifyActionWrapper(apProject, "EthernetCommunicationConnector", new NewNetworkEndpointAction("Network endpoint", owner, NewNetworkEndpointAction.EXECUTE_TYPE_FOR_NETWORK_ENDPOINT)));
				} catch (NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
				
				IAction action = null;
				try {
					menuManager.add(action = new ElementModifyActionWrapper(apProject, "EthernetCommunicationConnector", new NewNetworkEndpointAction("Unicast network endpoint", owner, NewNetworkEndpointAction.EXECUTE_TYPE_FOR_UNICAST_NETWORK_ENDPOINT)));
					if(owner.getUnicastNetworkEndpoint() != null) {
						action.setEnabled(false);
					}
				} catch (NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}else if(garObject instanceof SomeipServiceDiscovery) {
				SomeipServiceDiscovery owner = (SomeipServiceDiscovery)garObject;
				
				menuManager.add(new Separator());
				try {
					menuManager.add(new ElementModifyActionWrapper(apProject, "SomeipServiceDiscovery", new NewNetworkEndpointAction("Multicast sd ip address", owner, NewNetworkEndpointAction.EXECUTE_TYPE_FOR_MULTICAST_SECURE)));
				} catch (NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}else if(garObject instanceof Machine) {
				Machine owner = (Machine)garObject;
				
				
				menuManager.add(new Separator());
				try {
					menuManager.add(new ElementModifyActionWrapper(apProject, "Machine", new NewProcessAction("Process", owner)));
				} catch (NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}else if(garObject instanceof ProvidedSomeipServiceInstance)
			{
				ProvidedSomeipServiceInstance owner = (ProvidedSomeipServiceInstance)garObject;
				try {
					menuManager.add(new ElementModifyActionWrapper(apProject, "ProvidedSomeipServiceInstance", new NewServiceInstanceConfig("Service Instance Config", owner)));
				}catch(NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}else if(garObject instanceof RequiredSomeipServiceInstance)
			{
				RequiredSomeipServiceInstance owner = (RequiredSomeipServiceInstance)garObject;
				try {
					menuManager.add(new ElementModifyActionWrapper(apProject, "RequiredSomeipServiceInstance", new NewProcessAction("Sd Config", owner)));
				}catch(NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
	}
	
}
