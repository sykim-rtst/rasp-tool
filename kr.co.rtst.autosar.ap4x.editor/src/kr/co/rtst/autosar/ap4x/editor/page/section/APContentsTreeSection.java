package kr.co.rtst.autosar.ap4x.editor.page.section;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.ui.provider.TransactionalAdapterFactoryContentProvider;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.sphinx.emf.editors.forms.sections.GenericContentsTreeSection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.IAPModelManager;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;

public class APContentsTreeSection extends GenericContentsTreeSection {
	
	public APContentsTreeSection(AbstractAPEditorPage formPage, Object sectionInput) {
		this(formPage, sectionInput, SWT.NONE);
	}

	public APContentsTreeSection(AbstractAPEditorPage formPage, Object sectionInput, int style) {
		super(formPage, sectionInput, style);
		setTitle(((Referrable)sectionInput).getShortName() + " " + EditorText.EDIT_PAGE_TREE_SEC_TITLE_POST_MESSAGE);
		setDescription(EditorText.EDIT_PAGE_TREE_SEC_DESCRIPTION);
	}
	
	protected ARObject getInputDetail() {
		return getAPEditorPage().getAPFormEditorInput().getDetailObject();
	}
	
	protected AbstractAPEditorPage getAPEditorPage() {
		return (AbstractAPEditorPage)getFormPage();
	}
	
	@Override
	public void createContent(IManagedForm managedForm, Composite parent) {
		// TODO Auto-generated method stub
		super.createContent(managedForm, parent);
		TreeViewer viewer = (TreeViewer)getViewer();
//		((GridData)viewer.getTree().getLayoutData()).minimumWidth = 200;
//		((GridData)viewer.getTree().getLayoutData()).widthHint = 300;
//		GridData gData = new GridData(GridData.FILL_VERTICAL);
		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.widthHint = 300;
		viewer.getTree().setLayoutData(gData);
		
//		viewer.setContentProvider(new AdaptiveAutosarModelContentProvider());
//		viewer.setLabelProvider(new AdaptiveAutosarModelDecoratingLabelProvider());
		
//		viewer.getFilters();
		System.out.println("---FILTER1:"+viewer.getFilters().length);
		ViewerFilter[] oldFilters = viewer.getFilters();
		ViewerFilter[] newFilters = new ViewerFilter[oldFilters.length+1];
		for (int i = 0; i < oldFilters.length; i++) {
			newFilters[i] = oldFilters[i];
		}
		newFilters[newFilters.length-1] = new APModelFilter();
		viewer.setFilters(newFilters);
		System.out.println("---FILTER2:"+viewer.getFilters().length);
		System.out.println("---CP:"+viewer.getContentProvider());
		System.out.println("---LP:"+viewer.getLabelProvider());
	}
	
	@Override
	public void refreshSection() {
		// TODO Auto-generated method stub
		super.refreshSection();
	}
	
//	@Override
//	protected IContentProvider createContentProvider() {
//		return new AdaptiveAutosarModelContentProvider();
//	}
//	
//	@Override
//	protected IBaseLabelProvider createLabelProvider() {
//		return new AdaptiveAutosarModelDecoratingLabelProvider();
//	}
	
	@Override
	protected void createViewerContextMenu() {
		super.createViewerContextMenu();
	}

	@Override
	protected void createSectionClientContent(final IManagedForm managedForm, final SectionPart sectionPart, Composite sectionClient) {
		super.createSectionClientContent(managedForm, sectionPart, sectionClient);
		ColumnViewerToolTipSupport.enableFor((ColumnViewer) getViewer());
	}
	
	protected SectionPart createSectionPart(Composite parent, FormToolkit toolkit)
    {
//        style = style == 0 ? getDefaultSectionStyle() : style;
        return new SectionPart(parent, toolkit, Section.SHORT_TITLE_BAR|Section.DESCRIPTION|Section.TITLE_BAR/*ExpandableComposite.TITLE_BAR | Section.DESCRIPTION | ExpandableComposite.TWISTIE*/);
    }

	@Override
	protected void fillSectionToolBarActions(ToolBarManager toolBarManager) {
		super.fillSectionToolBarActions(toolBarManager);
//		toolBarManager.add(new AppearanceAction((StructuredViewer) getViewer(), (AutosarFormEditor) formPage.getEditor()));
	}
	
//	@Override
//	public IContentProvider getContentProvider() {
//		return super.getContentProvider();
////		return new AdaptiveAutosarModelEditorContentProvider();
//	}
//	
//	@Override
//	public IBaseLabelProvider getLabelProvider() {
//		return super.getLabelProvider();
////		return new AdaptiveAutosarModelEditorDecoratingLabelProvider();
//	}
//	
//	@Override
//	protected IContentProvider createContentProvider() {
//		return new AdaptiveAutosarModelEditorContentProvider();
////		return super.createContentProvider();
//	}
//	
//	@Override
//	protected IBaseLabelProvider createLabelProvider() {
//		return new AdaptiveAutosarModelEditorDecoratingLabelProvider();
////		return super.createLabelProvider();
//	}
	
	@Override
	protected IContentProvider createContentProvider() {
//		AdapterFactory adapterFactory = getCustomAdapterFactory();
//        if(adapterFactory != null)
//        {
//            org.eclipse.emf.edit.domain.EditingDomain editingDomain = formPage.getTransactionalFormEditor().getEditingDomain();
//            if(editingDomain instanceof TransactionalEditingDomain)
//                return new TransactionalAdapterFactoryContentProvider((TransactionalEditingDomain)editingDomain, adapterFactory);
//        }
//        return formPage.getContentProvider();
        
        AdapterFactory adapterFactory = getAPEditorPage().getTransactionalFormEditor().getAdapterFactory();
        if(adapterFactory != null)
        {
            org.eclipse.emf.edit.domain.EditingDomain editingDomain = getAPEditorPage().getTransactionalFormEditor().getEditingDomain();
            if(editingDomain instanceof TransactionalEditingDomain) {
                return new TransactionalAdapterFactoryContentProvider((TransactionalEditingDomain)editingDomain, adapterFactory) {
                	@Override
                	public Object[] getChildren(Object parentElement) {
                		if(parentElement instanceof GARObject) {
                			IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)parentElement);
//                			System.out.println("--------------IAPModelManager--------------getChildren::parentElement:"+parentElement+", modelManager:"+modelManager);
                			if(modelManager != null) {
//                				System.out.println("--------------=============--------------getChildren::parentElement:"+parentElement+", modelManager:"+modelManager);
                				return modelManager.getChildren((GARObject)parentElement).toArray();
                			}
                		}
                		return super.getChildren(parentElement);
                	}
                	
                	@Override
                	public boolean hasChildren(Object element) {
                		// TODO Auto-generated method stub
//                		return super.hasChildren(element);
                		if(element instanceof GARObject) {
                			IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)element);
                			if(modelManager != null) {
                				System.out.println("hasChildren::element:"+element+", modelManager:"+modelManager);
                				return modelManager.hasChildren((GARObject)element);
                			}
                		}
                		return false;
                	}
                };
            }
        }
        return null;
//        
//		TransactionalAdapterFactoryContentProvider cp = (TransactionalAdapterFactoryContentProvider)super.createContentProvider();
//		System.out.println(":::::::::::::::::::::::"+cp);
//		System.out.println(":::::::::::::::::::::::"+cp.getAdapterFactory());
//		System.out.println(":::::::::::::::::::::::"+((ComposedAdapterFactory)cp.getAdapterFactory()).getRootAdapterFactory());
//		
//		return cp;
	}
	
	private class APModelFilter extends ViewerFilter{
		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
//			if(getAPparentElement)
			if(element instanceof GARObject) {
				IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)getAPEditorPage().getAPFormEditor().getEditorInputObject());
				return modelManager.isNavigatableSubElement((GARObject)element);
//				System.out.println("--------------IAPModelManager--------------getChildren::parentElement:"+parentElement+", modelManager:"+modelManager);
//				if(modelManager != null) {
//					System.out.println("--------------=============--------------getChildren::parentElement:"+parentElement+", modelManager:"+modelManager);
//					return modelManager.getChildren((GARObject)parentElement).toArray();
			}
			return false;
		}
	}
	
}
