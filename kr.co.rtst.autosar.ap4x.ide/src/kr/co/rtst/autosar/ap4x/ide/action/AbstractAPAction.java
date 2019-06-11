package kr.co.rtst.autosar.ap4x.ide.action;

import java.util.concurrent.atomic.AtomicInteger;

import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.factories.IGAutosarFactoryService;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jface.action.Action;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

import autosar40.commonstructure.modedeclaration.ModeDeclarationGroup;
import autosar40.commonstructure.modedeclaration.ModeDeclarationGroupPrototype;
import autosar40.commonstructure.modedeclaration.ModedeclarationFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperationDelegator;

abstract public class AbstractAPAction extends Action implements IAPTransactionalOperationDelegator{

	protected AtomicInteger transactionalOpDelegatorState;
	protected GARObject ownerObject;
	
	public AbstractAPAction(String text, GARObject ownerObject) {
		setText(text);
		this.ownerObject = ownerObject;
		transactionalOpDelegatorState = new AtomicInteger(OP_STATE_READY);
	}
	
	@Override
	public void doTransactionalOperation(IAPTransactionalOperation op) {
		transactionalOpDelegatorState.set(OP_STATE_BUSY);
		if(ownerObject instanceof GARObject) {
			
			IAPProject apProject = getAPProject();
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						try {
							op.doProcess(ownerObject);
							transactionalOpDelegatorState.set(OP_STATE_DONE_SUCCESS);
						} catch (Exception e) {
							transactionalOpDelegatorState.set(OP_STATE_DONE_FAILED);
							e.printStackTrace();
						}
					}
				});
			}
			
		}
	}

	@Override
	public int getState() {
		return transactionalOpDelegatorState.get();
	}
	
	protected IAPProject getAPProject() {
		if(ownerObject instanceof GARObject) {
//			System.out.println("RES:"+ownerObject.eResource().getURI().devicePath().substring("/resource/".length()));
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(ownerObject.eResource().getURI().devicePath().substring("/resource/".length())));
			
			return APProjectManager.getInstance().getAPProject(file.getProject());
		}
		return null;
	}
	
	
//	protected GARPackage getGARPackege(String catetoryPkg, String subPkg) {
//		GARPackage pkg = null;
//		final IAPProject apProject = getAPProject();
//		final GAUTOSAR root = (GAUTOSAR)(getAPProject().getRootObject());
//		
//		for (GARPackage p : root.gGetArPackages()) {
//			if(p.gGetShortName().equalsIgnoreCase(catetoryPkg)) {
//				pkg = p;
//				for (GARPackage pp: p.gGetSubPackages()) {
//					if(pp.gGetShortName().equalsIgnoreCase(subPkg)) {
//						pkg = pp;
//						break;
//					}
//				}
//				break;
//			}
//		}
//		if(pkg == null) {
//			// 모두 생성되어야 함
//			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
//			GARPackage systemPackage = autosarFactory.createGARPackage();
//			systemPackage.gSetShortName(catetoryPkg);
//			GARPackage modelPackage = autosarFactory.createGARPackage();
//			modelPackage.gSetShortName(subPkg);
//			systemPackage.gGetSubPackages().add(modelPackage);
//			
//			doTransactionalOperation(new IAPTransactionalOperation() {
//				
//				@Override
//				public GARObject doProcess(GARObject model) throws Exception {
//					// TODO Auto-generated method stub
//					root.gGetArPackages().add(systemPackage);
//					return model;
//				}
//			});
//			
//			return modelPackage;
//			
//		}else if(pkg.gGetShortName().equalsIgnoreCase(catetoryPkg)) {
//			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
//			GARPackage systemPackage = pkg;
//			GARPackage modelPackage = autosarFactory.createGARPackage();
//			modelPackage.gSetShortName(subPkg);
//			
//			doTransactionalOperation(new IAPTransactionalOperation() {
//				
//				@Override
//				public GARObject doProcess(GARObject model) throws Exception {
//					// TODO Auto-generated method stub
//					systemPackage.gGetSubPackages().add(modelPackage);
//					return model;
//				}
//			});
//			
//			return modelPackage;
//		}else {
//			return pkg;
//		}
//	}
//	
//	private static IGAutosarFactoryService getAutosarFactoryService(final IAPProject apProject) {
//		if(apProject != null) {
//        	return (IGAutosarFactoryService)(new DefaultMetaModelServiceProvider()).getService(apProject.getAutosarReleaseDescriptor(), org.artop.aal.gautosar.services.factories.IGAutosarFactoryService.class);
//		}
//		return null;
//    }
	
}
