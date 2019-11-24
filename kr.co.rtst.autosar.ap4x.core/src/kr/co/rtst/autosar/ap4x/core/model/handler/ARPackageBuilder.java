package kr.co.rtst.autosar.ap4x.core.model.handler;

import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.factories.IGAutosarFactoryService;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;

public class ARPackageBuilder {
	
	public static final String LV1_SYSTEM 						= "System";
	public static final String LV1_APPLICATIONS		 			= "Applications";
	public static final String LV1_SERVICE						= "Services";
	
	public static final String LV2_MODE_DECLARATION_GROUPS		= "ModeDeclarationGroups";
	public static final String LV2_MODE_DEPENDENCIES			= "ModeDependencies";
	public static final String LV2_PROCESSES					= "Processes";
	public static final String LV2_STARTUP_CONFIGS				= "StartupConfigs";
	public static final String LV2_EXECUTABLES					= "Executables";
	public static final String LV2_SERVICE_INSTANCE_SD_SERVER_CONFIG		= "sdServerConfig";
	public static final String LV2_SERVICE_INSTANCE_SD_CLIENT_CONFIG	= "sdClientConfig";
	
	public static final String LV2_ETHERNETCLUSTER			 	= "EthernetCluster";
	

	public static GARPackage getGARPackege(final IAPProject apProject, final String lv1PackageName) {
		GARPackage pkg = null;
		final GAUTOSAR root = (GAUTOSAR)(apProject.getRootObject());
		
		for (GARPackage p : root.gGetArPackages()) {
			if(p.gGetShortName().equalsIgnoreCase(lv1PackageName)) {
				pkg = p;
				break;
			}
		}
		if(pkg == null) { // 없으므로 생성한다.
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage lv1Pkg = autosarFactory.createGARPackage();
			lv1Pkg.gSetShortName(lv1PackageName);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						root.gGetArPackages().add(lv1Pkg);
					}
				});
			}
			pkg = lv1Pkg;
		} 
		return pkg;
	}
	
	public static GARPackage getGARPackege(final IAPProject apProject, final String lv1PackageName, final String lv2PackageName) {
		GARPackage pkg = null;
		final GAUTOSAR root = (GAUTOSAR)(apProject.getRootObject());
		
		for (GARPackage p : root.gGetArPackages()) {
			if(p.gGetShortName().equalsIgnoreCase(lv1PackageName)) {
				pkg = p;
				for (GARPackage pp: p.gGetSubPackages()) {
					if(pp.gGetShortName().equalsIgnoreCase(lv2PackageName)) {
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
			GARPackage lv1Pkg = autosarFactory.createGARPackage();
			lv1Pkg.gSetShortName(lv1PackageName);
			GARPackage lv2Pkg = autosarFactory.createGARPackage();
			lv2Pkg.gSetShortName(lv2PackageName);
			lv1Pkg.gGetSubPackages().add(lv2Pkg);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						root.gGetArPackages().add(lv1Pkg);
					}
				});
			}
			
			pkg = lv2Pkg;
			
		}else if(pkg.gGetShortName().equalsIgnoreCase(lv1PackageName)) {
			IGAutosarFactoryService autosarFactory = getAutosarFactoryService(apProject);
			GARPackage lv1Pkg = pkg;
			GARPackage lv2Pkg = autosarFactory.createGARPackage();
			lv2Pkg.gSetShortName(lv2PackageName);
			
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						lv1Pkg.gGetSubPackages().add(lv2Pkg);
					}
				});
			}
			
			return lv2Pkg;
		}
		return pkg;
	}
	
	private static IGAutosarFactoryService getAutosarFactoryService(final IAPProject apProject) {
		if(apProject != null) {
        	return (IGAutosarFactoryService)(new DefaultMetaModelServiceProvider()).getService(apProject.getAutosarReleaseDescriptor(), org.artop.aal.gautosar.services.factories.IGAutosarFactoryService.class);
		}
		return null;
    }
	
}
