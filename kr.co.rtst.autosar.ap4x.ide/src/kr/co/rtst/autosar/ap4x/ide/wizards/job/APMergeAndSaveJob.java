package kr.co.rtst.autosar.ap4x.ide.wizards.job;

import java.util.List;
import java.util.Map;

import org.artop.aal.common.resource.impl.AutosarResourceSetImpl;
import org.artop.aal.gautosar.services.splitting.SplitableMerge;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sphinx.emf.metamodel.IMetaModelDescriptor;
import org.eclipse.sphinx.emf.util.EcoreResourceUtil;
import org.eclipse.sphinx.emf.util.WorkspaceTransactionUtil;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.util.Modules;

import gautosar.ggenericstructure.ginfrastructure.GReferrable;

public class APMergeAndSaveJob extends Job {
	
    public static final String NAME = "AP Marge";
    private URI targetUri;
    private List<GReferrable> autosarModels;
    private EObject mergedModel;
    private TransactionalEditingDomain editingDomain;
    private IMetaModelDescriptor descriptor;
    private IAPMargeAndSaveJobObserver observer;

    public APMergeAndSaveJob(List<GReferrable> autosarModels, URI targetURI, TransactionalEditingDomain editingDomain, IMetaModelDescriptor descriptor, IAPMargeAndSaveJobObserver observer)
    {
        super(NAME);
        this.autosarModels = autosarModels;
        targetUri = targetURI;
        this.editingDomain = editingDomain;
        this.descriptor = descriptor;
        this.observer = observer;
    }

    protected IStatus run(IProgressMonitor monitor)
    {
        return doMergeAndSave(monitor);
    }

    public IStatus doMergeAndSave(IProgressMonitor monitor)
    {
        if(monitor == null)
            monitor = new NullProgressMonitor();
        
        monitor.beginTask(NAME, 2);
        Runnable r = new Runnable() {

            public void run()
            {
                mergedModel = createSplitable().createMergedCopy(autosarModels);
                EcoreUtil.resolveAll(mergedModel);
            }
        };
        
        try {
			WorkspaceTransactionUtil.executeInWriteTransaction(editingDomain, r, NAME);
		} catch (OperationCanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			IStatus istatus = Status.CANCEL_STATUS;
	        monitor.done();
	        observer.notifyJobFinish(Status.CANCEL_STATUS);
	        return istatus;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			IStatus istatus = Status.CANCEL_STATUS;
	        monitor.done();
	        observer.notifyJobFinish(Status.CANCEL_STATUS);
	        return istatus;
		}
        monitor.worked(1);
        Map<String, IMetaModelDescriptor> options = Maps.newHashMap();
        options.put("RESOURCE_VERSION_DESCRIPTOR", descriptor);
        EcoreResourceUtil.saveNewModelResource(new AutosarResourceSetImpl(), targetUri, getContentTypeId(), mergedModel, options);
        monitor.done();
        observer.notifyJobFinish(Status.OK_STATUS);
        return Status.OK_STATUS;
    }

    private SplitableMerge createSplitable()
    {
        return (SplitableMerge)(Guice.createInjector(new Module[] {Modules.EMPTY_MODULE})).getInstance(org.artop.aal.gautosar.services.splitting.SplitableMerge.class);
    }

    private String getContentTypeId()
    {
        return (String)descriptor.getContentTypeIds().get(0);
    }

//    static 
//    {
//        NAME = Messages.MergeAndSaveJob_0;
//    }




}
