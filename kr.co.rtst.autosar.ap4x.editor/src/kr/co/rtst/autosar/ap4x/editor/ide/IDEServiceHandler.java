package kr.co.rtst.autosar.ap4x.editor.ide;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMapping;
import autosar40.adaptiveplatform.deployment.machine.ServiceDiscoveryConfiguration;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceDiscovery;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.adaptiveplatform.systemdesign.MachineDesign;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.swcomponent.datatype.datatypes.ApplicationDataType;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCommunicationConnector;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import autosar40.system.fibex.fibexcore.coretopology.CommunicationConnector;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.APFormEditorInput;
import kr.co.rtst.autosar.ap4x.ide.externalservice.IARObjectEventListener;

public class IDEServiceHandler implements IARObjectEventListener {
	
	@Override
	public Object handle(int event, ARObject inputObject) {
		if(event == IARObjectEventListener.EVENT_DOUBLECLICK_ON_NAVIGATOR) {
			
			// 편집대상 최상위 엘리먼트를 더블클릭했는지 검사
			if(inputObject instanceof AdaptiveApplicationSwComponentType ||
					inputObject instanceof ServiceInterface ||
					inputObject instanceof SomeipServiceInterfaceDeployment ||
					inputObject instanceof MachineDesign ||
					inputObject instanceof Machine ||
					inputObject instanceof ImplementationDataType ||
					inputObject instanceof ApplicationDataType) {
				
				openEditor(inputObject, inputObject);
//				try {
//					org.eclipse.emf.common.util.URI uri = null;
//		            if(!inputObject.eIsProxy()) {
//		                uri = EcoreUtil.getURI(inputObject);
//		                System.out.println("URI====>"+uri);
//		                if(uri != null) {
//		                	IWorkbenchPage page = AP4xEditorActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
//							if(page != null) {
//								page.openEditor(new APFormEditorInput(uri, inputObject), APFormEditor.ID);
//							}
//		                }
//		            }
//				} catch (PartInitException e) {
//					e.printStackTrace();
//				}
			} else {
				// TODO 편집대상 엘리먼트의 자식인 경우에만 반응하도록 검사(편집기가 열린 후 해당 항목이 선택된 상태가 되도록 한다.)
				if(APModelManagerProvider.apINSTANCE.getSwComponentsModelManager().isNavigatableSubElement(inputObject)) {
					// 부모를 찾아야 함
					System.out.println("Parent::"+inputObject.eContainer().getClass());
					EObject parent = inputObject.eContainer();
					while(parent != null) {
						if(parent instanceof AdaptiveApplicationSwComponentType) {
							openEditor((ARObject)parent, inputObject);
							break;
						} else {
							parent = parent.eContainer();
						}
					}
				} else if(APModelManagerProvider.apINSTANCE.getInterfacesModelManager().isNavigatableSubElement(inputObject)) {
					EObject parent = inputObject.eContainer();
					while(parent != null) {
						if(parent instanceof ServiceInterface) {
							openEditor((ARObject)parent, inputObject);
							break;
						} else {
							parent = parent.eContainer();
						}
					}
				} else if(APModelManagerProvider.apINSTANCE.getInterfaceDeploymentModelManager().isNavigatableSubElement(inputObject)) {
					EObject parent = inputObject.eContainer();
					while(parent != null) {
						if(parent instanceof SomeipServiceInterfaceDeployment) {
							openEditor((ARObject)parent, inputObject);
							break;
						} else {
							parent = parent.eContainer();
						}
					}
				} else if(APModelManagerProvider.apINSTANCE.getMachineDesignsModelManager().isNavigatableSubElement(inputObject)) {
					if(!(inputObject instanceof NetworkEndpoint)) {
						EObject parent = inputObject.eContainer();
						while(parent != null) {
							if(parent instanceof MachineDesign) {
								openEditor((ARObject)parent, inputObject);
								break;
							} else {
								parent = parent.eContainer();
							}
						}
					} else {
						// 부모를 찾아야 한다.
						EObject parent = inputObject;
						while(parent.eContainer() != null) {
							parent = parent.eContainer();
						}
						
						System.out.println("NetworkEndpoint 최상위 부모:"+(parent.getClass().getName()));
//						List<NetworkEndpoint> netrowkEndpointList = APModelManagerProvider.apINSTANCE.getAllNetworkEndpoint((GAUTOSAR)parent);
						
						List<MachineDesign> mdList = APModelManagerProvider.apINSTANCE.getMachineDesignsModelManager().getAllMachineDesign((GAUTOSAR)parent);
						for (MachineDesign md : mdList) {
							for (CommunicationConnector cc : md.getCommunicationConnectors()) {
								if(cc instanceof EthernetCommunicationConnector) {
									if(inputObject.equals(((EthernetCommunicationConnector) cc).getUnicastNetworkEndpoint())) {
										openEditor((ARObject)md, inputObject);
										return null;
									}else if(((EthernetCommunicationConnector) cc).getNetworkEndpoints().contains(inputObject)) {
										openEditor((ARObject)md, inputObject);
										return null;
									}
								}
							}
							for (ServiceDiscoveryConfiguration sc : md.getServiceDiscoverConfigs()) {
								if(sc instanceof SomeipServiceDiscovery) {
									if( inputObject.equals(((SomeipServiceDiscovery) sc).getMulticastSdIpAddress())) {
										openEditor((ARObject)md, inputObject);
										return null;
									}
								}
							}
						}
					}
				} else if(APModelManagerProvider.apINSTANCE.getImplementationDataTypesModelManager().isNavigatableSubElement(inputObject)) {
					EObject parent = inputObject.eContainer();
					while(parent != null) {
						if(parent instanceof ImplementationDataType) {
							openEditor((ARObject)parent, inputObject);
							break;
						} else {
							parent = parent.eContainer();
						}
					}
				} else if(APModelManagerProvider.apINSTANCE.getApplicationDataTypesModelManager().isNavigatableSubElement(inputObject)) {
					EObject parent = inputObject.eContainer();
					while(parent != null) {
						if(parent instanceof ApplicationDataType) {
							openEditor((ARObject)parent, inputObject);
							break;
						} else {
							parent = parent.eContainer();
						}
					}
				} else if(APModelManagerProvider.apINSTANCE.getMachinesModelManager().isNavigatableSubElement(inputObject)) {
					if(!(inputObject instanceof ProcessToMachineMapping)) {
						EObject parent = inputObject.eContainer();
						while(parent != null) {
							if(parent instanceof Machine) {
								openEditor((ARObject)parent, inputObject);
								break;
							} else {
								parent = parent.eContainer();
							}
						}
					}else {
						System.out.println("Process....");
						openEditor(((ProcessToMachineMapping)inputObject).getMachine(), inputObject);
					}
				}
			}
		}
		return null;
	}
	
	private void openEditor(ARObject editTopObject, ARObject inputObject) {
		try {
			org.eclipse.emf.common.util.URI uri = null;
            if(!editTopObject.eIsProxy()) {
                uri = EcoreUtil.getURI(editTopObject);
                System.out.println("URI====>"+uri);
                if(uri != null) {
                	IWorkbenchPage page = AP4xEditorActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
					if(page != null) {
						page.openEditor(new APFormEditorInput(uri, inputObject), APFormEditor.ID);
					}
                }
            }
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

}
