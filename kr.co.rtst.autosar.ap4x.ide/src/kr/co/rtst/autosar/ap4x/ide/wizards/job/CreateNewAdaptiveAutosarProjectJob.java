package kr.co.rtst.autosar.ap4x.ide.wizards.job;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.workspace.jobs.CreateNewAutosarProjectJob;
import org.artop.aal.workspace.preferences.IAutosarWorkspacePreferences;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sphinx.emf.metamodel.IMetaModelDescriptor;
import org.eclipse.sphinx.emf.metamodel.MetaModelDescriptorRegistry;
import org.eclipse.sphinx.emf.scoping.IResourceScope;
import org.eclipse.sphinx.emf.scoping.IResourceScopeProvider;
import org.eclipse.sphinx.emf.scoping.ResourceScopeProviderRegistry;
import org.eclipse.sphinx.emf.util.EcorePlatformUtil;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;
import org.eclipse.sphinx.emf.workspace.loading.ModelLoadManager;
import org.eclipse.sphinx.platform.util.StatusUtil;

import autosar40.autosartoplevelstructure.AUTOSAR;
import autosar40.autosartoplevelstructure.impl.AUTOSARImpl;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GReferrable;
import kr.co.rtst.autosar.ap4x.core.AP4xCoreActivator;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.ide.AP4xIDEActivator;
import kr.co.rtst.autosar.ap4x.ide.wizards.AdaptiveAutosarProjectCreationInfo;

public class CreateNewAdaptiveAutosarProjectJob extends CreateNewAutosarProjectJob implements IAPMargeAndSaveJobObserver {
	
	private final AdaptiveAutosarProjectCreationInfo projectInfo;
	
//    protected IMetaModelDescriptor metaModelDescriptor;
    protected EPackage rootObjectEPackage;
    protected EClassifier rootObjectEClassifier;

	public CreateNewAdaptiveAutosarProjectJob(String name, IProject project, URI location,
			AutosarReleaseDescriptor autosarRelease, AdaptiveAutosarProjectCreationInfo projectInfo) {
		super(name, project, location, autosarRelease);
		this.rootObjectEPackage = autosarRelease.getRootEPackage();
		this.rootObjectEClassifier = autosarRelease.getRootEPackage().getEClassifier("AUTOSAR");
		this.projectInfo = projectInfo;
	}
	
	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		try
        {
            SubMonitor progress = SubMonitor.convert(monitor, getName(), 100);
            if(progress.isCanceled())
                throw new OperationCanceledException();
            createNewProject(progress.newChild(70));
            addNatures(progress.newChild(15));
            
            IAPProject apProject = APProjectManager.getInstance().getAPProject(newProject);
//            if(projectInfo.isPredefinedArxml()) {
//            	createPredefinedArxml(apProject, monitor);
//            }else {
//            	createEmptyPredefinedArxml(apProject, monitor);
//            }
            
            GAUTOSAR autosar = createProjectArxml(apProject, apProject.getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME), monitor);
            if(projectInfo.getImportArxmlList() != null && !projectInfo.getImportArxmlList().isEmpty()) {
            	// TODO 병합
//            	margeUserDefinedARXMLtoProjectARXML(apProject.getProject().getFile(IAdaptiveAutosarProject.USER_DEFINED_ARXML_NAME));
            	
            	// 임시 폴더 생성
            	IFolder tempFolder = apProject.getProject().getFolder(IAPProject.PROJECT_TEMP_FOLDER);
            	if(!tempFolder.exists()) {
            		tempFolder.create(true, true, monitor);
            	}
            	try {
	            	
            		List<GReferrable> arPkes = new ArrayList<>();
            		gatheringMargedARPackage(apProject.getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME), arPkes, monitor);
            		
            		if(projectInfo.isPredefinedArxml()) {
            			tempFolder.getFile(IAPProject.USER_DEFINED_ARXML_NAME).create(AP4xCoreActivator.getDefault().readEmbeddedFileToStream(IAPProject.PROJECT_PREDEFINED_ARXML_TEMPLATE), true, monitor);
            			gatheringMargedARPackage(tempFolder.getFile(IAPProject.USER_DEFINED_ARXML_NAME), arPkes, monitor);
            		}
            		
	            	File[] files = projectInfo.getImportArxmlList().toArray(new File[0]);
	            	System.out.println("처리될 파일개수:"+files.length);
	            	for (int i = 0; i < files.length; i++) {
						System.out.println("처리될 파일["+i+"]:"+files[i].getName());
					}
            		for (int i = 0; i < files.length; i++) {
//            			System.out.println("EXIST B:"+tempFolder.getFile(files[i].getName()).exists());
            			tempFolder.getFile(files[i].getName()).create(Files.newInputStream(files[i].toPath()), true, monitor);
            			System.out.println("EXIST A:"+tempFolder.getFile(files[i].getName()).exists());
            			if(tempFolder.getFile(files[i].getName()).exists()) {
            				System.out.println(tempFolder.getFile("NAME:"+files[i].getName()).getName()+", SIZE:"+tempFolder.getFile(files[i].getName()).getContents().available());
            			}
            			
            			gatheringMargedARPackage(tempFolder.getFile(files[i].getName()), arPkes, monitor);
//            			margeUserDefinedARXMLtoProjectARXML(apProject, autosar, tempFolder.getFile(files[i].getName())/*, files[i]*/, monitor);
            			
//            			tempFolder.getFile(files[i].getName()).delete(true, monitor);
					}
            		
            		margeARPackages(apProject.getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME), arPkes, monitor);
            	} finally {
//            		if(tempFolder.exists()) {
//            			tempFolder.delete(true, monitor);
//            			apProject.getProject().getFolder(IAdaptiveAutosarProject.PROJECT_TEMP_FOLDER).delete(true, monitor);
//                	}
            	}
            }else if(projectInfo.isPredefinedArxml()) {
            	IFolder tempFolder = apProject.getProject().getFolder(IAPProject.PROJECT_TEMP_FOLDER);
            	if(!tempFolder.exists()) {
            		tempFolder.create(true, true, monitor);
            	}
            	try {
	            	List<GReferrable> arPkes = new ArrayList<>();
	        		gatheringMargedARPackage(apProject.getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME), arPkes, monitor);
	        		
	        		if(projectInfo.isPredefinedArxml()) {
	        			tempFolder.getFile(IAPProject.USER_DEFINED_ARXML_NAME).create(AP4xCoreActivator.getDefault().readEmbeddedFileToStream(IAPProject.PROJECT_PREDEFINED_ARXML_TEMPLATE), true, monitor);
	        			gatheringMargedARPackage(tempFolder.getFile(IAPProject.USER_DEFINED_ARXML_NAME), arPkes, monitor);
	        		}
	        		
	        		margeARPackages(apProject.getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME), arPkes, monitor);
            	}finally {
            		
            	}
            }
            
//            newProject.getFolder(IAdaptiveAutosarProject.)
            
            if(metaModelVersionDescriptor != null && metaModelVersionPreference != null)
                metaModelVersionPreference.setInProject(newProject, metaModelVersionDescriptor);
            progress.worked(15);
            return Status.OK_STATUS;
        }
        catch(OperationCanceledException exception)
        {
            return Status.CANCEL_STATUS;
        }
        catch(Exception ex)
        {
            return StatusUtil.createErrorStatus(AP4xIDEActivator.getDefault(), ex);
        }
	}
	
	private List<GReferrable> collectSelection(GAUTOSAR root/*IStructuredSelection selections*/)
    {
//		AUTOSARImpl
//		GAUTOSAR
//        List<GReferrable> elements = new ArrayList(selections.size());
		List<GReferrable> elements = new ArrayList(root.gGetArPackages().size());
        for(Iterator it = root.gGetArPackages().iterator(); it.hasNext();)
        {
            Object object = it.next();
            if(object instanceof GReferrable)
                elements.add((GReferrable)object);
        }

        return elements;
    }
	
	private IMetaModelDescriptor getAutosarRelease(EObject eObject, TransactionalEditingDomain editingDomain)
    {
        Resource eResource = eObject.eResource();
        if(eResource == null)
            return null;
        org.eclipse.emf.common.util.URI eUri = eResource.getURI();
        if(eUri.isPlatformResource())
        {
            String platformString = eUri.toPlatformString(true);
            IFile file = (IFile)ResourcesPlugin.getWorkspace().getRoot().findMember(platformString);
            if(file != null)
            {
//            	IFile file = ((IAPTopElement) parentElement).getApProject().getProject().getFile(IAdaptiveAutosarProject.USER_DEFINED_ARXML_NAME);
//    			AutosarReleaseDescriptor releaseDescription = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(file.getProject());
//    			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(file.getProject(), releaseDescription);
//    			EObject object = EcorePlatformUtil.loadModelRoot(editingDomain, file);
    			
    			IMetaModelDescriptor descriptor = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(file.getProject());
    			
//                IMetaModelDescriptor descriptor = AutosarProjects.getMetaModelDescriptor(file.getProject());
                return descriptor;
            }
        }
        return null;
    }
	
	private static void wait(Object jobFamily)
    {
        boolean wasInterrupted = false;
        do
            try
            {
                Job.getJobManager().join(jobFamily, null);
                wasInterrupted = false;
            }
            catch(OperationCanceledException e)
            {
                e.printStackTrace();
            }
            catch(InterruptedException _ex)
            {
                wasInterrupted = true;
            }
        while(wasInterrupted);
    }
	
	private void gatheringMargedARPackage(IFile margedFile, List<GReferrable> arPkes, IProgressMonitor monitor){
		System.out.println("----------------- 수집 대상 파일:"+margedFile.getName());
		AutosarReleaseDescriptor releaseDescription = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(margedFile.getProject());
		
		System.out.println("*************TRUE*1:"+margedFile.isAccessible());
		System.out.println("*************FALSE*2:"+ResourceScopeProviderRegistry.INSTANCE.isNotInAnyScope(margedFile));
		IMetaModelDescriptor effectiveMMDescriptor = MetaModelDescriptorRegistry.INSTANCE.getEffectiveDescriptor(margedFile);
		System.out.println("*************TRUE*3:"+releaseDescription.getClass().isInstance(effectiveMMDescriptor));
		System.out.println("*************FALSE*4:"+EcorePlatformUtil.isFileLoaded(margedFile));
		IResourceScopeProvider resourceScopeProvider = ResourceScopeProviderRegistry.INSTANCE.getResourceScopeProvider(effectiveMMDescriptor);
		System.out.println("*************TRUE*5:"+(resourceScopeProvider != null));
		if(resourceScopeProvider != null) {
			System.out.println("*************TRUE*5-1:"+resourceScopeProvider);
			IResourceScope resourceScope = resourceScopeProvider.getScope(margedFile);
			System.out.println("*************TRUE*6:"+(resourceScope != null));
			if(resourceScope != null) {
				System.out.println("*************TRUE*6-1:"+resourceScope);
				TransactionalEditingDomain tEditingDomain = WorkspaceEditingDomainUtil.getMappedEditingDomain(margedFile);
				System.out.println("*************TRUE*7:"+(tEditingDomain != null));
				if(tEditingDomain != null) {
					System.out.println("**************7-1:"+tEditingDomain);
//					Collection filesToLoadInEditingDomain = (Collection)filesToLoad.get(editingDomain);
					System.out.println("**************9:"+margedFile.isAccessible());
					System.out.println("**************10:"+margedFile.isAccessible());
					System.out.println("**************11:"+margedFile.isAccessible());
					System.out.println("**************12:"+margedFile.isAccessible());
				}
			}
		}
		
		ModelLoadManager.INSTANCE.loadFile(margedFile, releaseDescription, true, monitor);
		
		org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(margedFile.getProject(), releaseDescription);
		EObject margeEObject = EcorePlatformUtil.loadModelRoot(editingDomain, margedFile);
	
		System.out.println("MARGE::"+margeEObject.getClass().getName());
		
		
		System.out.println("기존 패키지 수:"+arPkes.size());
        for (int i = 0; i < arPkes.size(); i++) {
			System.out.println("기존 패키지["+i+"]"+arPkes.get(i).gGetShortName());
		}
        EList<ARPackage> pkes = ((AUTOSAR)margeEObject).getArPackages();
        System.out.println("추가 패키지 수:"+pkes.size());
        for (int i = 0; i < pkes.size(); i++) {
			System.out.println("추가 패키지["+i+"]"+pkes.get(i).gGetShortName());
		}
		arPkes.addAll(collectSelection((AUTOSARImpl)margeEObject));
//		System.out.println("전체 패키지수:"+arPkes.size());
//        for (int i = 0; i < arPkes.size(); i++) {
//			System.out.println("전체 패키지["+i+"]:"+arPkes.get(i).gGetShortName());
//		}
        
	}
	
	private void margeARPackages(IFile projectArxmlFile, List<GReferrable> arPkes, IProgressMonitor monitor) {
		AutosarReleaseDescriptor releaseDescription = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(projectArxmlFile.getProject());
		org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(projectArxmlFile.getProject(), releaseDescription);
		APMergeAndSaveJob mergeAndSaveJob = new APMergeAndSaveJob(arPkes, EcorePlatformUtil.createURI(projectArxmlFile.getFullPath()), editingDomain, releaseDescription, this);
        mergeAndSaveJob.setPriority(40);
        AP4xIDEActivator.getDefault().getLog().log(new Status(IStatus.INFO, AP4xIDEActivator.PLUGIN_ID, "프로젝트 생성시 선택된 arxml들의 병합작업 시작 요청."));
        mergeAndSaveJob.schedule();
	}
	
	@Override
	public void notifyJobFinish(IStatus status) {
		AP4xIDEActivator.getDefault().getLog().log(new Status(IStatus.INFO, AP4xIDEActivator.PLUGIN_ID, "프로젝트 생성시 선택된 arxml들의 병합작업 완료 통보 받음."));
		
		IAPProject apProject = APProjectManager.getInstance().getAPProject(newProject);
		IFile projectArxmlFile = apProject.getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME);
		AutosarReleaseDescriptor releaseDescription = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(projectArxmlFile.getProject());
		org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(projectArxmlFile.getProject(), releaseDescription);
		
		try {
			projectArxmlFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
        
        ModelLoadManager.INSTANCE.reloadFile(projectArxmlFile, releaseDescription, true, null);
        EObject object = EcorePlatformUtil.loadModelRoot(editingDomain, projectArxmlFile);
        
        IFolder tempFolder = apProject.getProject().getFolder(IAPProject.PROJECT_TEMP_FOLDER);
    	if(tempFolder.exists()) {
    		try {
				tempFolder.delete(true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
    	}
	}
	
	private GAUTOSAR createProjectArxml(IAPProject apProject, IFile file, IProgressMonitor monitor) {
		GAUTOSAR autosar = (GAUTOSAR)createInitialModel();
    	saveInitialModel(autosar, file, monitor);
    	return autosar;
	}
	
	private EObject createInitialModel()
    {
        return rootObjectEPackage.getEFactoryInstance().create((EClass)rootObjectEClassifier);
    }

	private void saveInitialModel(EObject rootObject, IFile newFile, IProgressMonitor monitor)
    {
        org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(newFile.getProject(), metaModelVersionDescriptor);
        EcorePlatformUtil.saveNewModelResource(editingDomain, newFile.getFullPath(), metaModelVersionDescriptor.getDefaultContentTypeId(), rootObject, false, monitor);
    }
	
	@Override
	protected void addNatures(IProgressMonitor monitor) throws CoreException {
		super.addNatures(monitor);
		IAPProject.addRTSTAdaptiveAutosarNature(newProject, monitor);
	}
	
}
