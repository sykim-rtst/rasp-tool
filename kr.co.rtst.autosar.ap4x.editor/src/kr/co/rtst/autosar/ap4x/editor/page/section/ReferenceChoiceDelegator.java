package kr.co.rtst.autosar.ap4x.editor.page.section;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.adaptiveplatform.applicationdesign.portinterface.Field;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.adaptiveplatform.deployment.machine.ServiceDiscoveryConfiguration;
import autosar40.adaptiveplatform.deployment.securecommunication.SecureComProps;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceDiscovery;
import autosar40.adaptiveplatform.systemdesign.MachineDesign;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroup;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroupPrototype;
import autosar40.swcomponent.components.PortGroup;
import autosar40.swcomponent.datatype.dataprototypes.AutosarDataPrototype;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.datatype.datatypes.ApplicationDataType;
import autosar40.swcomponent.datatype.datatypes.AutosarDataType;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.handler.ARPackageBuilder;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.dialog.GARObjectMultiSelectionDialog;
import kr.co.rtst.autosar.ap4x.editor.dialog.GARObjectSingleSelectionDialog;

/**
 * 
 * @author thkim
 *
 */
public class ReferenceChoiceDelegator {

	public static AutosarDataType choiceSingleAutosarDataType(Shell shell, IAPProject apProject) {
		if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
			List<ImplementationDataType> list1 = APModelManagerProvider.apINSTANCE.getImplementationDataTypesModelManager().getAllImplementationDataType((GAUTOSAR)apProject.getRootObject());
			List<ApplicationDataType> list2 = APModelManagerProvider.apINSTANCE.getApplicationDataTypesModelManager().getAllApplicationDataType((GAUTOSAR)apProject.getRootObject());
			List<GARObject> list = new ArrayList<>();
			list.addAll(list1);
			list.addAll(list2);
			GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Type");
			if(d.open() == IDialogConstants.OK_ID) {
				if(d.getSelectedObject() instanceof AutosarDataType) {
					return (AutosarDataType)d.getSelectedObject();
				}
			}
		}
		return null;
	}
	
//	public static NetworkEndpoint choiceSingleNetworkEndpoint(Shell shell, IAPProject apProject) {
////		EthernettopologyFactory
////		List<MachineDesign> machineDesigns = APModelManagerProvider.apINSTANCE.getMachineDesignsModelManager().getAllMachineDesign((GAUTOSAR)apProject.getRootObject());
////		List<GARObject> list = new ArrayList<>();
////		for (MachineDesign md : machineDesigns) {
////			for(CommunicationConnector cc: md.getCommunicationConnectors()) {
////				if(cc instanceof EthernetCommunicationConnector) {
////					list.addAll(((EthernetCommunicationConnector) cc).getNetworkEndpoints());
////				}
////			}
////		}
////		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "NetworkEndpoint");
////		if(d.open() == IDialogConstants.OK_ID) {
////			if(d.getSelectedObject() instanceof NetworkEndpoint) {
////				return (NetworkEndpoint)d.getSelectedObject();
////			}
////		}
////		return null;
//		
//		List<NetworkEndpoint> ethernetClusterList = APModelManagerProvider.apINSTANCE.getAllNetworkEndpoint((GAUTOSAR)apProject.getRootObject());
//		List<GARObject> list = new ArrayList<>();
//		list.addAll(ethernetClusterList);
//		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "NetworkEndpoint");
//		if(d.open() == IDialogConstants.OK_ID) {
//			if(d.getSelectedObject() instanceof NetworkEndpoint) {
//				return (NetworkEndpoint)d.getSelectedObject();
//			}
//		}
//		return null;
//	}
	
//	public static List<GARObject> choiceMultiNetworkEndpoint(Shell shell, IAPProject apProject) {
//		List<NetworkEndpoint> ethernetClusterList = APModelManagerProvider.apINSTANCE.getAllNetworkEndpoint((GAUTOSAR)apProject.getRootObject());
//		List<GARObject> list = new ArrayList<>();
//		list.addAll(ethernetClusterList);
//		GARObjectMultiSelectionDialog d = new GARObjectMultiSelectionDialog(shell, null, list, "NetworkEndpoint");
//		if(d.open() == IDialogConstants.OK_ID) {
//			if(d.getSelectedObjects() != null) {
//				return d.getSelectedObjects();
//			}
//		}
//		return null;
//	}
	
	public static List<GARObject> choiceMultiMachineMode(Shell shell, IAPProject apProject, Machine machine) {
//		List<ModeDeclaration> modelList = APModelManagerProvider.apINSTANCE.getAllNetworkEndpoint((GAUTOSAR)apProject.getRootObject());
		
		List<GARObject> list = new ArrayList<>();
		for (ModeDeclarationGroupPrototype modeDeclarationGroupPrototype : machine.getMachineModeMachines()) {
			list.addAll(modeDeclarationGroupPrototype.getType().getModeDeclarations());
		}
		GARObjectMultiSelectionDialog d = new GARObjectMultiSelectionDialog(shell, null, list, "Machine mode");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObjects() != null) {
				return d.getSelectedObjects();
			}
		}
		return null;
	}
	
	public static List<GARObject> choiceMultiFunctionGroupMode(Shell shell, IAPProject apProject, Machine machine) {
//		List<ModeDeclaration> modelList = APModelManagerProvider.apINSTANCE.getAllNetworkEndpoint((GAUTOSAR)apProject.getRootObject());
		
		List<GARObject> list = new ArrayList<>();
		for (ModeDeclarationGroupPrototype modeDeclarationGroupPrototype : machine.getFunctionGroups()) {
			list.addAll(modeDeclarationGroupPrototype.getType().getModeDeclarations());
		}
		GARObjectMultiSelectionDialog d = new GARObjectMultiSelectionDialog(shell, null, list, "Function group mode");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObjects() != null) {
				return d.getSelectedObjects();
			}
		}
		return null;
	}
	
	public static List<GARObject> choiceMultiExecutionDependency(Shell shell, IAPProject apProject) {
//		List<ModeDeclaration> modelList = APModelManagerProvider.apINSTANCE.getAllNetworkEndpoint((GAUTOSAR)apProject.getRootObject());
		
		List<GARObject> list = new ArrayList<>();
		for (GARObject e : ARPackageBuilder.getGARPackege(apProject, ARPackageBuilder.LV1_APPLICATIONS, ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS).gGetElements()) {
			if(e instanceof ModeDeclarationGroup) {
				list.addAll(((ModeDeclarationGroup)e).getModeDeclarations());
			}
		}
		
//		List<GARObjectWrapper> list = new ArrayList<>();
//		ProcessToMachineMappingSet processToMachineMappingSet = APModelRepository.eINSTANCE.getProcessToMachineMappingSet(apProject);
//		for (ProcessToMachineMapping mapping: processToMachineMappingSet.getProcessToMachineMappings()) {
//			for(ModeDeclaration mode: mapping.getProcess().getApplicationModeMachine().getType().getModeDeclarations()) {
//				list.add(new GARObjectWrapper(mode, mapping.getProcess().getShortName()));
//			}
//		}
		
//		GARObjectWrapperMultiSelectionDialog d = new GARObjectWrapperMultiSelectionDialog(shell, null, list, "Execution dependency");
		GARObjectMultiSelectionDialog d = new GARObjectMultiSelectionDialog(shell, null, list, "Execution dependency");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObjects() != null) {
				return d.getSelectedObjects();
			}
		}
		return null;
	}
	
	public static SecureComProps choiceSingleSecureComProps(Shell shell, IAPProject apProject) {
//		EthernettopologyFactory
		List<MachineDesign> machineDesigns = APModelManagerProvider.apINSTANCE.getMachineDesignsModelManager().getAllMachineDesign((GAUTOSAR)apProject.getRootObject());
		List<GARObject> list = new ArrayList<>();
		for (MachineDesign md : machineDesigns) {
			for(ServiceDiscoveryConfiguration sc: md.getServiceDiscoverConfigs()) {
				if(sc instanceof SomeipServiceDiscovery) {
					list.addAll(((SomeipServiceDiscovery) sc).getUnicastSecureComProps());
				}
			}
		}
		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "SecureComProps");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObject() instanceof SecureComProps) {
				return (SecureComProps)d.getSelectedObject();
			}
		}
		return null;
	}
	
	public static AdaptiveApplicationSwComponentType choiceSingleSwComponentType(Shell shell, IAPProject apProject) {
//		EthernettopologyFactory
		List<AdaptiveApplicationSwComponentType> modelList = APModelManagerProvider.apINSTANCE.getSwComponentsModelManager().getAllAdaptiveApplicationSwComponentType((GAUTOSAR)apProject.getRootObject());
		List<GARObject> list = new ArrayList<>();
		list.addAll(modelList);
		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "SwComponentType");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObject() instanceof AdaptiveApplicationSwComponentType) {
				return (AdaptiveApplicationSwComponentType)d.getSelectedObject();
			}
		}
		return null;
	}
	
	public static ServiceInterface choiceSinglePortInterface(Shell shell, IAPProject apProject) {
//		EthernettopologyFactory
		List<ServiceInterface> serviceInterfaces = APModelManagerProvider.apINSTANCE.getInterfacesModelManager().getAllServiceInterface((GAUTOSAR)apProject.getRootObject());
		List<GARObject> list = new ArrayList<>();
		list.addAll(serviceInterfaces);
		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "PortInterface");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObject() instanceof ServiceInterface) {
				return (ServiceInterface)d.getSelectedObject();
			}
		}
		return null;
	}
	
	public static VariableDataPrototype choiceSingleEvent(Shell shell, IAPProject apProject, ServiceInterface relatedInterface) {
		if(relatedInterface != null) {
			List<GARObject> list = new ArrayList<>();
			list.addAll(relatedInterface.getEvents());
			GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Event");
			if(d.open() == IDialogConstants.OK_ID) {
				if(d.getSelectedObject() instanceof VariableDataPrototype) {
					return (VariableDataPrototype)d.getSelectedObject();
				}
			}
		}else {
			MessageDialog.openError(shell, EditorText.DIALOG_ERROR_TITLE, EditorText.DIALOG_ERROR_MESSAGE_INTERFACE_NOT_FOUND);
		}
		return null;
	}
	
	public static Field choiceSingleField(Shell shell, IAPProject apProject, ServiceInterface relatedInterface) {
//		List<ServiceInterface> serviceInterfaces = APModelManagerProvider.apINSTANCE.getInterfacesModelManager().getAllServiceInterface((GAUTOSAR)apProject.getRootObject());
//		List<GARObject> list = new ArrayList<>();
//		for (ServiceInterface si : serviceInterfaces) {
//			list.addAll(si.getFields());
//		}
		if(relatedInterface != null) {
			List<GARObject> list = new ArrayList<>();
			list.addAll(relatedInterface.getFields());
			GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Field");
			if(d.open() == IDialogConstants.OK_ID) {
				if(d.getSelectedObject() instanceof Field) {
					return (Field)d.getSelectedObject();
				}
			}
		}else {
			MessageDialog.openError(shell, EditorText.DIALOG_ERROR_TITLE, EditorText.DIALOG_ERROR_MESSAGE_INTERFACE_NOT_FOUND);
		}
		return null;
	}
	
	public static ClientServerOperation choiceSingleMethod(Shell shell, IAPProject apProject, ServiceInterface relatedInterface) {
////		EthernettopologyFactory
//		List<ServiceInterface> serviceInterfaces = APModelManagerProvider.apINSTANCE.getInterfacesModelManager().getAllServiceInterface((GAUTOSAR)apProject.getRootObject());
//		List<GARObject> list = new ArrayList<>();
//		for (ServiceInterface si : serviceInterfaces) {
//			list.addAll(si.getMethods());
//		}
		if(relatedInterface != null) {
			List<GARObject> list = new ArrayList<>();
			list.addAll(relatedInterface.getMethods());
			GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Opeartion");
			if(d.open() == IDialogConstants.OK_ID) {
				if(d.getSelectedObject() instanceof ClientServerOperation) {
					return (ClientServerOperation)d.getSelectedObject();
				}
			}
		}else {
			MessageDialog.openError(shell, EditorText.DIALOG_ERROR_TITLE, EditorText.DIALOG_ERROR_MESSAGE_INTERFACE_NOT_FOUND);
		}
		return null;
	}
	
	public static PortGroup choiceSinglePortGroup(Shell shell, IAPProject apProject) {
//		EthernettopologyFactory
		List<AdaptiveApplicationSwComponentType> swList = APModelManagerProvider.apINSTANCE.getSwComponentsModelManager().getAllAdaptiveApplicationSwComponentType((GAUTOSAR)apProject.getRootObject());
		List<GARObject> list = new ArrayList<>();
		for (AdaptiveApplicationSwComponentType si : swList) {
			list.addAll(si.getPortGroups());
		}
		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Port Group");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObject() instanceof PortGroup) {
				return (PortGroup)d.getSelectedObject();
			}
		}
		return null;
	}
	
	public static AutosarDataPrototype choiceSingleDataElement(Shell shell, IAPProject apProject, ServiceInterface relatedInterface) {
		if(relatedInterface != null) {
			List<GARObject> list = new ArrayList<>();
			list.addAll( relatedInterface.getEvents() );
			list.addAll( relatedInterface.getFields() );
			
			GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Data Element");
			if(d.open() == IDialogConstants.OK_ID) {
				if(d.getSelectedObject() instanceof AutosarDataPrototype) {
					return (AutosarDataPrototype)d.getSelectedObject();
				}
			}
		}else {
			MessageDialog.openError(shell, EditorText.DIALOG_ERROR_TITLE, EditorText.DIALOG_ERROR_MESSAGE_INTERFACE_NOT_FOUND);
		}
		return null;
	}
	
	public static MachineDesign choiceSingleMachineDesign(Shell shell, IAPProject apProject) {
		List<MachineDesign> mdList = APModelManagerProvider.apINSTANCE.getMachineDesignsModelManager().getAllMachineDesign((GAUTOSAR)apProject.getRootObject());
		List<GARObject> list = new ArrayList<>();
		list.addAll(mdList);
		
		GARObjectSingleSelectionDialog d = new GARObjectSingleSelectionDialog(shell, null, list, "Machine Design");
		if(d.open() == IDialogConstants.OK_ID) {
			if(d.getSelectedObject() instanceof MachineDesign) {
				return (MachineDesign)d.getSelectedObject();
			}
		}
		
		return null;
	}
	
}
