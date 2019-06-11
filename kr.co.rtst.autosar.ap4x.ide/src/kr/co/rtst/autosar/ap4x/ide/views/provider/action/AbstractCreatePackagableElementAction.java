package kr.co.rtst.autosar.ap4x.ide.views.provider.action;

import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.factories.IGAutosarFactoryService;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;
import org.eclipse.ui.IViewPart;

import autosar40.adaptiveplatform.machinemanifest.Machine;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelInitializer;
import kr.co.rtst.autosar.ap4x.core.model.observer.APModelChangeActionHandler;
import kr.co.rtst.autosar.ap4x.ide.AP4xIDEActivator;
import kr.co.rtst.autosar.ap4x.ide.views.AdaptiveAUTOSARNavigator;
import kr.co.rtst.autosar.ap4x.ide.views.dialog.CreatePackagableElementDialog;

abstract public class AbstractCreatePackagableElementAction extends Action {

	protected final IAPVirtualElement apVirtualElement;
	
	public AbstractCreatePackagableElementAction(IAPVirtualElement apVirtualElement, String title) {
		super();
		this.apVirtualElement = apVirtualElement;
		setText(title);
	}
	
	abstract protected GPackageableElement createGPackageableElement();
	
	abstract protected String getCreateGPackageableElementName();
	
	protected IGAutosarFactoryService getAutosarFactoryService() {
		System.out.println("getAutosarFactoryService===>"+apVirtualElement);
		if(apVirtualElement != null) {
        	return (IGAutosarFactoryService)(new DefaultMetaModelServiceProvider()).getService(apVirtualElement.getApProject().getAutosarReleaseDescriptor(), org.artop.aal.gautosar.services.factories.IGAutosarFactoryService.class);
		}
		return null;
    }
	
	@Override
	public void run() {
		final CreatePackagableElementDialog dialog = new CreatePackagableElementDialog(AP4xIDEActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell(), null, ((GAUTOSAR)apVirtualElement.getApProject().getRootObject()), getCreateGPackageableElementName());
		if(dialog.open() == IDialogConstants.OK_ID) {
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
    		apVirtualElement.getApProject().getProject(), 
    		apVirtualElement.getApProject().getAutosarReleaseDescriptor());
			if (domain != null) {
				
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						
						GARPackage garPackage = null;
						if(!dialog.isDirtyPackageName()) {
							garPackage = dialog.getSelectedPackage();
						}else {
							EList<GARPackage> packageList = ((GAUTOSAR)apVirtualElement.getApProject().getRootObject()).gGetArPackages();
							String[] packageTokens = dialog.getPackageName().split("\\.");
							int checkIndex = 0;
							boolean found = false;
							while(checkIndex<packageTokens.length) {
								for (GARPackage pkg : packageList) {
									if(packageTokens[checkIndex].equals(pkg.gGetShortName())) {
										garPackage = pkg; // 더 찾은 최신 패키지
										found = true;
										break; // 다음 토큰을 검사해야 한다.
									}
								}
								if(found) {
									found = false;
									packageList = garPackage.gGetSubPackages();
									if(packageList == null || packageList.isEmpty()) {
										checkIndex++;
										break; // 여기서 빠져나갔다면 현재 checkIndex 부터 존재하지 않는 패키지이다.
									}
									checkIndex++;
								}else {
									break;
								}
							}
							
							if(garPackage == null) {
								System.out.println("CHECK INDEX(guess=0):"+checkIndex);
								// 하나도 없다.
								IGAutosarFactoryService autosarFactory = getAutosarFactoryService();
								GARPackage rootPackage = autosarFactory.createGARPackage();
								rootPackage.gSetShortName(packageTokens[0]);
								GARPackage parentPackage = rootPackage;
								for (int i = 1; i < packageTokens.length; i++) {
									garPackage = autosarFactory.createGARPackage();
									garPackage.gSetShortName(packageTokens[i]);
									parentPackage.gGetSubPackages().add(garPackage);
									parentPackage = garPackage;
								}
								((GAUTOSAR)apVirtualElement.getApProject().getRootObject()).gGetArPackages().add(rootPackage);
							}else {
								System.out.println("CHECK INDEX:"+checkIndex);
								// garPackage 아래에 남은 토큰에 대한 패키지를 만들어야 한다.
								IGAutosarFactoryService autosarFactory = getAutosarFactoryService();
								GARPackage parentPackage = garPackage;
								for (int i = checkIndex; i < packageTokens.length; i++) {
									garPackage = autosarFactory.createGARPackage();
									garPackage.gSetShortName(packageTokens[i]);
									parentPackage.gGetSubPackages().add(garPackage);
									parentPackage = garPackage;
								}
							}
						}
						
						// 대화창에서 선택한 패키지이거나 또는 입력한 패키지 명으로 생성된 패키지
						if(garPackage != null) {
							
							GPackageableElement gPackageableElement = createGPackageableElement();
							if(gPackageableElement != null) {
								gPackageableElement.gSetShortName(dialog.getShortName());
						    	garPackage.gGetElements().add(gPackageableElement);
							}
							
							if(gPackageableElement instanceof Machine) {
								APModelInitializer.initializeMachine_ModeDeclarationGroupPrototype(apVirtualElement.getApProject(), (Machine)gPackageableElement);
								APModelInitializer.initializeMachine_EnterExitTimeout(apVirtualElement.getApProject(), (Machine)gPackageableElement);
								APModelInitializer.initializeMachine_Process(apVirtualElement.getApProject(), (Machine)gPackageableElement);
							}
						}
					}
				});
				
				APModelChangeActionHandler.apINSTANCE.notifyPostModelChange(apVirtualElement.getApProject());
			}
		}
	}
	
}
