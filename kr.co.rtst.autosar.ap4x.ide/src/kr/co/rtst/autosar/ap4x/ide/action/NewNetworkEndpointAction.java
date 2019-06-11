package kr.co.rtst.autosar.ap4x.ide.action;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipServiceDiscovery;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCluster;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetClusterConditional;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCommunicationConnector;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetPhysicalChannel;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyFactory;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.handler.ARPackageBuilder;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;

public class NewNetworkEndpointAction extends AbstractAPAction{

	public static final int EXECUTE_TYPE_FOR_NETWORK_ENDPOINT = 1;
	public static final int EXECUTE_TYPE_FOR_UNICAST_NETWORK_ENDPOINT = 2;
	public static final int EXECUTE_TYPE_FOR_MULTICAST_SECURE = 3;
	
//	public static final String ETHERNETCLUSTER_CATEGORY_PACKAGE = "System";
//	public static final String ETHERNETCLUSTER_PACKAGE = "EthernetCluster";
	
	private int executeType;
	/**
	 * 
	 * @param text
	 * @param ownerObject
	 */
	NewNetworkEndpointAction(String text, GARObject ownerObject, int executeType) {
		super(text, ownerObject);
		this.executeType = executeType;
	}
	
	@Override
	public void run() {
		
//		GARPackage pkg = getGARPackege(ETHERNETCLUSTER_CATEGORY_PACKAGE, ETHERNETCLUSTER_PACKAGE);
		GARPackage pkg = ARPackageBuilder.getGARPackege(getAPProject(), ARPackageBuilder.LV1_SYSTEM, ARPackageBuilder.LV2_ETHERNETCLUSTER);
		if(pkg != null) {
			EthernetCluster ethernetCluster = null;
			for (GPackageableElement element : pkg.gGetElements()) {
				if(element instanceof EthernetCluster) {
					ethernetCluster = (EthernetCluster)element;
				}
			}
			if(ethernetCluster == null) {
				ethernetCluster = EthernettopologyFactory.eINSTANCE.createEthernetCluster();
				
				EthernetClusterConditional ecc = EthernettopologyFactory.eINSTANCE.createEthernetClusterConditional();
				EthernetPhysicalChannel epc = EthernettopologyFactory.eINSTANCE.createEthernetPhysicalChannel();
				NetworkEndpoint networkEndpoint = EthernettopologyFactory.eINSTANCE.createNetworkEndpoint();
				
				epc.getNetworkEndpoints().add(networkEndpoint);
				ecc.getPhysicalChannels().add(epc);
				ethernetCluster.getEthernetClusterVariants().add(ecc);
				
				final EthernetCluster ec = ethernetCluster;
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						pkg.gGetElements().add(ec);
						return model;
					}
				});
			}
			if(ethernetCluster != null){
				final EthernetCluster ec = ethernetCluster;
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						NetworkEndpoint networkEndpoint = EthernettopologyFactory.eINSTANCE.createNetworkEndpoint();
						((EthernetPhysicalChannel)ec.getEthernetClusterVariants().get(0).getPhysicalChannels().get(0)).getNetworkEndpoints().add(networkEndpoint);
						
						switch(executeType) {
						case EXECUTE_TYPE_FOR_NETWORK_ENDPOINT:
							if(ownerObject instanceof EthernetCommunicationConnector) {
								((EthernetCommunicationConnector) ownerObject).getNetworkEndpoints().add(networkEndpoint);
							}
							break;
						case EXECUTE_TYPE_FOR_UNICAST_NETWORK_ENDPOINT:
							if(ownerObject instanceof EthernetCommunicationConnector) {
								((EthernetCommunicationConnector) ownerObject).setUnicastNetworkEndpoint(networkEndpoint);
							}
							break;
						case EXECUTE_TYPE_FOR_MULTICAST_SECURE:
							if(ownerObject instanceof SomeipServiceDiscovery) {
								((SomeipServiceDiscovery) ownerObject).setMulticastSdIpAddress(networkEndpoint);
							}
							break;
						}
						return null;
					}
				});
			}
		}
		
	}
	
}
