package kr.co.rtst.autosar.ap4x.core.model.handler;

import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.factories.IGAutosarFactoryService;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.deployment.machine.EnterExitTimeout;
import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.adaptiveplatform.deployment.machine.MachineFactory;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMapping;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMappingSet;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroup;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroupPrototype;
import autosar40.commonstructure.modedeclaration.ModedeclarationFactory;
import autosar40.swcomponent.components.ComponentsFactory;
import autosar40.swcomponent.components.SymbolProps;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperationDelegator;

/**
 * 초기화가 작업을 해주는 유틸리티 메소드를 제공한다.
 * @author thkim
 *
 */
public class APModelInitializer {
	
//	public static final String SYSTEM_PACKAGE = "System";
//	public static final String APPLICATION_PACKAGE = "Applications";
//	public static final String MODEDECLARATIONGROUP_PACKAGE = "ModeDeclarationGroups";
	
	
//	public static void initializeDeployment_EventGroup(final IAPProject apProject, final SomeipServiceInterfaceDeployment deployment) {
//		org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
//				apProject.getProject(), 
//				apProject.getAutosarReleaseDescriptor());
//		if (domain != null) {
//			domain.getCommandStack().execute(new RecordingCommand(domain) {
//				@Override
//				protected void doExecute() {
//					deployment.getEventGroups().add(e)
//				}
//			});
//		}
//	}
	
	public static void initializeMachine_FunctionGroup(final IAPProject apProject, final Machine machine, final ModeDeclarationGroupPrototype mdgp) {
		GARPackage pkg = null;
		final GAUTOSAR root = ((GAUTOSAR)apProject.getRootObject());
		ModeDeclarationGroup mdg = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroup();
		
		mdg.setShortName(machine.getShortName()+MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP__SHORT_NAME_POST_FIX);
		
//		ModeDeclarationGroupPrototype mdgp = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroupPrototype();
//		mdgp.setShortName(machine.getShortName()+MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP_PROTOTYPE__SHORT_NAME_POST_FIX);
//		machine.getMachineModeMachines().add(mdgp);
		
		for (GARPackage p : root.gGetArPackages()) {
			if(p.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_SYSTEM)) {
				pkg = p;
				for (GARPackage pp: p.gGetSubPackages()) {
					if(pp.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS)) {
						pkg = pp;
						break;
					}
				}
				break;
			}
		}
		if(pkg == null) {
			// 모두 생성되어야 함
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = autosarFactory.createGARPackage();
			systemPackage.gSetShortName(ARPackageBuilder.LV1_SYSTEM);
			GARPackage modelPackage = autosarFactory.createGARPackage();
			modelPackage.gSetShortName(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
			systemPackage.gGetSubPackages().add(modelPackage);
			modelPackage.gGetElements().add(mdg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						mdgp.setType(mdg);
						root.gGetArPackages().add(systemPackage);
					}
				});
			}
					
		}else if(pkg.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_SYSTEM)) {
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = pkg;
			GARPackage modelPackage = autosarFactory.createGARPackage();
			modelPackage.gSetShortName(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
			modelPackage.gGetElements().add(mdg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						mdgp.setType(mdg);
						systemPackage.gGetSubPackages().add(modelPackage);
					}
				});
			}
		}else {
			GARPackage modelPackage = pkg;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						mdgp.setType(mdg);
						modelPackage.gGetElements().add(mdg);
					}
				});
			}
			
		}
	}
	
	public static void initializeMachine_ModeDeclarationGroupPrototype(final IAPProject apProject, final Machine machine) {
		GARPackage pkg = null;
		final GAUTOSAR root = ((GAUTOSAR)apProject.getRootObject());
		ModeDeclarationGroup mdg = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroup();
		
//		ModeDeclaration md = ModedeclarationFactory.eINSTANCE.createModeDeclaration();
//		mdg.getModeDeclarations()

		mdg.setShortName(machine.getShortName()+MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP__SHORT_NAME_POST_FIX);
		
		ModeDeclarationGroupPrototype mdgp = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroupPrototype();
		mdgp.setShortName(machine.getShortName()+MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP_PROTOTYPE__SHORT_NAME_POST_FIX);
		mdgp.setType(mdg);
//		machine.getMachineModeMachines().add(mdgp);
		
		for (GARPackage p : root.gGetArPackages()) {
			if(p.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_SYSTEM)) {
				pkg = p;
				for (GARPackage pp: p.gGetSubPackages()) {
					if(pp.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS)) {
						pkg = pp;
						break;
					}
				}
				break;
			}
		}
		if(pkg == null) {
			// 모두 생성되어야 함
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = autosarFactory.createGARPackage();
			systemPackage.gSetShortName(ARPackageBuilder.LV1_SYSTEM);
			GARPackage modelPackage = autosarFactory.createGARPackage();
			modelPackage.gSetShortName(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
			systemPackage.gGetSubPackages().add(modelPackage);
			modelPackage.gGetElements().add(mdg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						machine.getMachineModeMachines().add(mdgp);
						root.gGetArPackages().add(systemPackage);
					}
				});
			}
					
		}else if(pkg.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_SYSTEM)) {
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = pkg;
			GARPackage modelPackage = autosarFactory.createGARPackage();
			modelPackage.gSetShortName(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
			modelPackage.gGetElements().add(mdg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						machine.getMachineModeMachines().add(mdgp);
						systemPackage.gGetSubPackages().add(modelPackage);
					}
				});
			}
		}else {
			GARPackage modelPackage = pkg;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						machine.getMachineModeMachines().add(mdgp);
						modelPackage.gGetElements().add(mdg);
					}
				});
			}
			
		}
	}
	
	public static void initializeMachine_EnterExitTimeout(final IAPProject apProject, final Machine machine) {
		org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
				apProject.getProject(), 
				apProject.getAutosarReleaseDescriptor());
		if (domain != null) {
			domain.getCommandStack().execute(new RecordingCommand(domain) {
				@Override
				protected void doExecute() {
					EnterExitTimeout enterExitTimeout = MachineFactory.eINSTANCE.createEnterExitTimeout();
					machine.setDefaultApplicationTimeout(enterExitTimeout);
				}
			});
		}
	}
	
	public static void initializeMachine_ProcessModeDeclarationGroup(final IAPProject apProject, final Machine machine) {
		GARPackage pkg = null;
		final GAUTOSAR root = ((GAUTOSAR)apProject.getRootObject());
		ModeDeclarationGroup mdg = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroup();
		
//		ModeDeclaration md = ModedeclarationFactory.eINSTANCE.createModeDeclaration();
//		mdg.getModeDeclarations()

		mdg.setShortName(machine.getShortName()+MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP__SHORT_NAME_POST_FIX);
		
		ModeDeclarationGroupPrototype mdgp = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroupPrototype();
		mdgp.setShortName(machine.getShortName()+MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP_PROTOTYPE__SHORT_NAME_POST_FIX);
		mdgp.setType(mdg);
//		machine.getMachineModeMachines().add(mdgp);
		
		for (GARPackage p : root.gGetArPackages()) {
			if(p.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_APPLICATIONS)) {
				pkg = p;
				for (GARPackage pp: p.gGetSubPackages()) {
					if(pp.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS)) {
						pkg = pp;
						break;
					}
				}
				break;
			}
		}
		if(pkg == null) {
			// 모두 생성되어야 함
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = autosarFactory.createGARPackage();
			systemPackage.gSetShortName(ARPackageBuilder.LV1_APPLICATIONS);
			GARPackage modelPackage = autosarFactory.createGARPackage();
			modelPackage.gSetShortName(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
			systemPackage.gGetSubPackages().add(modelPackage);
			modelPackage.gGetElements().add(mdg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						machine.getMachineModeMachines().add(mdgp);
						root.gGetArPackages().add(systemPackage);
					}
				});
			}
					
		}else if(pkg.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_APPLICATIONS)) {
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = pkg;
			GARPackage modelPackage = autosarFactory.createGARPackage();
			modelPackage.gSetShortName(ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
			modelPackage.gGetElements().add(mdg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						machine.getMachineModeMachines().add(mdgp);
						systemPackage.gGetSubPackages().add(modelPackage);
					}
				});
			}
		}else {
			GARPackage modelPackage = pkg;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						machine.getMachineModeMachines().add(mdgp);
						modelPackage.gGetElements().add(mdg);
					}
				});
			}
			
		}
	}
	
	public static void initializeMachine_Process(final IAPProject apProject, final Machine machine) {
		GARPackage pkg = null;
		final GAUTOSAR root = ((GAUTOSAR)apProject.getRootObject());
		ProcessToMachineMappingSet processToMachineMappingSet = MachineFactory.eINSTANCE.createProcessToMachineMappingSet();
		ProcessToMachineMapping processToMachineMapping = MachineFactory.eINSTANCE.createProcessToMachineMapping();
//		processToMachineMapping.setShortName(machine.getShortName()+"_"+);
		
//		processToMachineMappingSet.getProcessToMachineMappings().get(0).get
		
		for (GARPackage p : root.gGetArPackages()) {
			if(p.gGetShortName().equalsIgnoreCase(ARPackageBuilder.LV1_SYSTEM)) {
				pkg = p;
				break;
			}
		}
		if(pkg == null) {
			// 모두 생성되어야 함
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage systemPackage = autosarFactory.createGARPackage();
			systemPackage.gSetShortName(ARPackageBuilder.LV1_SYSTEM);
			
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						systemPackage.gGetElements().add(processToMachineMappingSet);
						root.gGetArPackages().add(systemPackage);
					}
				});
			}
					
		}else {
			GARPackage systemPackage = pkg;
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						systemPackage.gGetElements().add(processToMachineMappingSet);
					}
				});
			}
			
		}
	}
	
	private static IGAutosarFactoryService getAutosarFactoryService(final IAPProject apProject) {
		if(apProject != null) {
        	return (IGAutosarFactoryService)(new DefaultMetaModelServiceProvider()).getService(apProject.getAutosarReleaseDescriptor(), org.artop.aal.gautosar.services.factories.IGAutosarFactoryService.class);
		}
		return null;
    }

	public static SymbolProps initializeNamespace(IAPTransactionalOperationDelegator delegator, ServiceInterface owner) {
		if(owner.getNamespaces().isEmpty()) {
			delegator.doTransactionalOperation(new IAPTransactionalOperation() {
				@Override
				public GARObject doProcess(GARObject model) throws Exception {
					owner.getNamespaces().add(ComponentsFactory.eINSTANCE.createSymbolProps());
					return model;
				}
			});
		}
		return owner.getNamespaces().get(0);
	}

}
