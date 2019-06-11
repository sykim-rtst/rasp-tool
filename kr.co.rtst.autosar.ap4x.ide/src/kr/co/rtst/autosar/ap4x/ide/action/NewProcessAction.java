package kr.co.rtst.autosar.ap4x.ide.action;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.ApplicationstructureFactory;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.Executable;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.RootSwComponentPrototype;
import autosar40.adaptiveplatform.executionmanifest.ExecutionmanifestFactory;
import autosar40.adaptiveplatform.executionmanifest.Process;
import autosar40.adaptiveplatform.executionmanifest.StartupConfigSet;
import autosar40.adaptiveplatform.executionmanifest.StateDependentStartupConfig;
import autosar40.adaptiveplatform.machinemanifest.Machine;
import autosar40.adaptiveplatform.machinemanifest.MachinemanifestFactory;
import autosar40.adaptiveplatform.machinemanifest.ProcessToMachineMapping;
import autosar40.adaptiveplatform.machinemanifest.ProcessToMachineMappingSet;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroup;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroupPrototype;
import autosar40.commonstructure.modedeclaration.ModedeclarationFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelContainerAccessor;
import kr.co.rtst.autosar.ap4x.core.model.handler.ARPackageBuilder;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;

public class NewProcessAction extends AbstractAPAction{

//	public static final String PROCESSTOMACHINEMAPPINGSET_CATEGORY_PACKAGE = "System";
	
	/**
	 * 
	 * @param text
	 * @param ownerObject Machine
	 */
	NewProcessAction(String text, GARObject ownerObject) {
		super(text, ownerObject);
	}
	
	@Override
	public void run() {
		Machine machine = (Machine)ownerObject;
		
//		GARPackage setPkg = BuiltInPackage.getGARPackege(getAPProject(), BuiltInPackage.LV1_SYSTEM);
		GARPackage exePkg = ARPackageBuilder.getGARPackege(getAPProject(), ARPackageBuilder.LV1_APPLICATIONS, ARPackageBuilder.LV2_EXECUTABLES);
		GARPackage processPkg = ARPackageBuilder.getGARPackege(getAPProject(), ARPackageBuilder.LV1_SYSTEM, ARPackageBuilder.LV2_PROCESSES);
//		GARPackage configPkg = BuiltInPackage.getGARPackege(getAPProject(), BuiltInPackage.LV1_APPLICATIONS, BuiltInPackage.LV2_STARTUP_CONFIGS);
//		GARPackage dependPkg = BuiltInPackage.getGARPackege(getAPProject(), BuiltInPackage.LV1_APPLICATIONS, BuiltInPackage.LV2_MODE_DEPENDENCIES);
		GARPackage pkgModeDeclarationGroup = ARPackageBuilder.getGARPackege(getAPProject(), ARPackageBuilder.LV1_APPLICATIONS, ARPackageBuilder.LV2_MODE_DECLARATION_GROUPS);
		
		// ProcessToMachineMappingSet 이 존재한다면 찾고 없으면 생성한다.
		ProcessToMachineMappingSet processToMachineMappingSet = APModelContainerAccessor.eINSTANCE.getProcessToMachineMappingSet(getAPProject());
		// StartupConfigSet 이 존재한다면 찾고 없으면 생성한다.
		StartupConfigSet startupConfigSet = APModelContainerAccessor.eINSTANCE.getStartupConfigSet(getAPProject());
		
		// 필요한 모델 생성
		StateDependentStartupConfig mdStartupConfig = ExecutionmanifestFactory.eINSTANCE.createStateDependentStartupConfig();//.createModeDependentStartupConfig();
		autosar40.adaptiveplatform.executionmanifest.StartupConfig startupConfig = ExecutionmanifestFactory.eINSTANCE.createStartupConfig();
		mdStartupConfig.setStartupConfig(startupConfig);
		
		ModeDeclarationGroupPrototype mdgp = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroupPrototype();
		mdgp.setShortName(MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP_PROTOTYPE__SHORT_NAME_POST_FIX);
		ModeDeclarationGroup mdg = ModedeclarationFactory.eINSTANCE.createModeDeclarationGroup();
		mdg.setShortName(MachineModelManager.MACHINE_MODE_MACHINE__MODE_DECLARATION_GROUP__SHORT_NAME_POST_FIX);
		mdgp.setType(mdg);
		
		Executable exe = ApplicationstructureFactory.eINSTANCE.createExecutable();
		RootSwComponentPrototype rootSw = ApplicationstructureFactory.eINSTANCE.createRootSwComponentPrototype();
		exe.setRootSwComponentPrototype(rootSw);
		
		Process process = ExecutionmanifestFactory.eINSTANCE.createProcess();
		process.setExecutable(exe);
		process.setProcessStateMachine(mdgp);//setApplicationModeMachine(mdgp);
		process.getStateDependentStartupConfigs().add(mdStartupConfig);
		
		ProcessToMachineMapping mapping = MachinemanifestFactory.eINSTANCE.createProcessToMachineMapping();
		mapping.setMachine(machine);
		mapping.setProcess(process);
		
		System.out.println("::::"+processToMachineMappingSet.getProcessToMachineMappings().size());
		for (ProcessToMachineMapping m : processToMachineMappingSet.getProcessToMachineMappings()) {
			System.out.println(":::::::"+m.getProcess().hashCode());
		}
		
		doTransactionalOperation(new IAPTransactionalOperation() {
			
			@Override
			public GARObject doProcess(GARObject model) throws Exception {
				processToMachineMappingSet.getProcessToMachineMappings().add(mapping);
				startupConfigSet.getStartupConfigs().add(startupConfig);
				
				pkgModeDeclarationGroup.gGetElements().add(mdg);
				processPkg.gGetElements().add(process);
				exePkg.gGetElements().add(exe);
				return model;
			}
		});
	}
	
}
