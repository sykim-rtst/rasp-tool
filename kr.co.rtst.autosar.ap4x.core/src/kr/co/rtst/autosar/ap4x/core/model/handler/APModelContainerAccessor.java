package kr.co.rtst.autosar.ap4x.core.model.handler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.ApplicationstructureFactory;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.Executable;
import autosar40.adaptiveplatform.deployment.machine.MachineFactory;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMapping;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMappingSet;
import autosar40.adaptiveplatform.deployment.process.ProcessFactory;
import autosar40.adaptiveplatform.deployment.process.StartupConfig;
import autosar40.adaptiveplatform.deployment.process.StartupConfigSet;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCluster;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetClusterConditional;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetPhysicalChannel;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import autosar40.system.fibex.fibexcore.coretopology.PhysicalChannel;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;

public class APModelContainerAccessor {

	public static APModelContainerAccessor eINSTANCE = new APModelContainerAccessor();
	
	/**
	 * ProcessToMachineMappingSet 이 존재한다면 찾고 없으면 생성한다.
	 * @param apProject
	 * @return
	 */
	public ProcessToMachineMappingSet getProcessToMachineMappingSet(IAPProject apProject) {
		ProcessToMachineMappingSet set = null;
		
		GARPackage setPkg = ARPackageBuilder.getGARPackege(apProject, ARPackageBuilder.LV1_SYSTEM);
		for (GPackageableElement element : setPkg.gGetElements()) {
			if(element instanceof ProcessToMachineMappingSet) {
				set = (ProcessToMachineMappingSet)element;
				break;
			}
		}
		if(set == null) {
			ProcessToMachineMappingSet newSet = MachineFactory.eINSTANCE.createProcessToMachineMappingSet();
			
			set = newSet;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						setPkg.gGetElements().add(newSet);
					}
				});
			}
		}
		
		return set;
	}
	
	/**
	 * StartupConfigSet 이 존재한다면 찾고 없으면 생성한다.
	 * @param apProject
	 * @return
	 */
	public StartupConfigSet getStartupConfigSet(IAPProject apProject) {
		StartupConfigSet set = null;
		
		GARPackage pkg = ARPackageBuilder.getGARPackege(apProject, ARPackageBuilder.LV1_APPLICATIONS, ARPackageBuilder.LV2_STARTUP_CONFIGS);
		for (GPackageableElement element : pkg.gGetElements()) {
			if(element instanceof StartupConfigSet) {
				set = (StartupConfigSet)element;
				break;
			}
		}
		if(set == null) {
			StartupConfigSet newSet = ProcessFactory.eINSTANCE.createStartupConfigSet();
			
			set = newSet;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						pkg.gGetElements().add(newSet);
					}
				});
			}
		}
		
		return set;
	}
	
	public List<NetworkEndpoint> getAllNetworkEndpoint(GAUTOSAR rootElement) {
		final List<NetworkEndpoint> result = new ArrayList<>();
		
		List<EthernetCluster> ecList = getAllEthernetCluster(rootElement);
		for (EthernetCluster ec : ecList) {
			for (EthernetClusterConditional ecc : ec.getEthernetClusterVariants()) {
				for (PhysicalChannel pc : ecc.getPhysicalChannels()) {
					if(pc instanceof EthernetPhysicalChannel) {
						result.addAll(((EthernetPhysicalChannel) pc).getNetworkEndpoints());
					}
				}
			}
		}
		
		
		return result;
	}
	
	public List<EthernetCluster> getAllEthernetCluster(GAUTOSAR rootElement) {
		final List<EthernetCluster> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getEthernetCluster(p));
		});
		
		return result;
	}
	
	public List<EthernetCluster> getEthernetCluster(GARPackage arPackage){
		List<EthernetCluster> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof EthernetCluster) {
				result.add((EthernetCluster)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getEthernetCluster(p));
			});
		}
		
		return result;
	}
	
//	StartupConfig config = ProcessFactory.eINSTANCE.createStartupConfig();
//	Executable exe = ApplicationstructureFactory.eINSTANCE.createExecutable();
//	autosar40.adaptiveplatform.deployment.process.Process process = ProcessFactory.eINSTANCE.createProcess();
//	ProcessToMachineMapping mapping = MachineFactory.eINSTANCE.createProcessToMachineMapping();
	
}
