package kr.co.rtst.autosar.ap4x.editor.page;

import java.util.Map;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.sphinx.emf.editors.forms.layouts.LayoutFactory;
import org.eclipse.sphinx.emf.editors.forms.pages.GenericContentsTreePage;
import org.eclipse.sphinx.emf.editors.forms.sections.IFormSection;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.IAPModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.APFormEditorInput;
import kr.co.rtst.autosar.ap4x.editor.page.section.APContentsTreeSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.layout.APEditorStackLayout;

public abstract class AbstractAPEditorPage extends GenericContentsTreePage {
	
	private Composite detailComposite;
	private StackLayout layout;
	protected Map<String, Control> controlProviderMap;
	
	public AbstractAPEditorPage(APFormEditor formEditor, String title) {
		super(formEditor, title);
	}
	
	@Override
	protected void doCreateFormContent(final IManagedForm managedForm) {
		// Create single columned page layout
		Composite body = managedForm.getForm().getBody();
		body.setLayout(LayoutFactory.createFormBodyGridLayout(false, 2));
		
		managedForm.getToolkit().decorateFormHeading(managedForm.getForm().getForm());
		
		// Create model contents tree section
		APContentsTreeSection sectionTree = new APContentsTreeSection(this, getAPFormEditor().getEditorInputObject()/*pageInput*/);
		sectionTree.createContent(managedForm, body);
		addSection(sectionTree);
		
		contentsTreeSection = sectionTree;
		
//		sectionTree.getViewer().addSelectionChangedListener(new ISelectionChangedListener() {
//			@Override
//			public void selectionChanged(SelectionChangedEvent event) {
//				if(event.getStructuredSelection().size() == 1 && event.getStructuredSelection().getFirstElement() instanceof ARObject) {
//					getAPFormEditorInput().setDetailObject((ARObject)event.getStructuredSelection().getFirstElement());
//					changeDetailControl();
//				}
//			}
//		});
		
//		detailComposite = managedForm.getToolkit().createComposite(body, SWT.NONE);
//		layout = new StackLayout();
//		detailComposite.setLayout(layout);
//		detailComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
//		
//		controlProviderMap = createDetailControls(detailComposite, managedForm);
//		
//		changeDetailControl();
		
		detailComposite = managedForm.getToolkit().createComposite(body);
		layout = new APEditorStackLayout();
		detailComposite.setLayout(layout);
//		detailComposite.setLayout(APUIFactory.getGridLayoutNoMargin(1, false));
		detailComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		controlProviderMap = createDetailControls(detailComposite, managedForm);
		
//		controlProviderMap = APDetailGUISectionFactory.createDetailGUISection(getAPFormEditor().getEditorInputObject(), detailComposite, managedForm, this);
		
		changeDetailControl();
		
//		for (Control contentGUISection : sectionControlsd) {
//			contentGUISection.createContent(managedForm, detailComposite);
//			addSection(contentGUISection);
//		}
		
//		AdaptiveApplicationSWCTypeGUISection sectionGUI = new AdaptiveApplicationSWCTypeGUISection(this, pageInput, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
//		sectionGUI.createContent(managedForm, detailComposite);
//		addSection(sectionGUI);
//		
//		AdaptiveApplicationSWCTypeGUISection sectionGUI2 = new AdaptiveApplicationSWCTypeGUISection(this, pageInput, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
//		sectionGUI2.createContent(managedForm, detailComposite);
//		addSection(sectionGUI2);
		
		init();
	}
	
	protected void init() {
//		((TreeViewer)contentsTreeSection.getViewer()).addDoubleClickListener(new IDoubleClickListener() {
//			
//			@Override
//			public void doubleClick(DoubleClickEvent event) {
//				if(event.getSelection() instanceof IStructuredSelection) {
//					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
//					if(selection.size() == 1 && selection.getFirstElement() instanceof ARObject) {
//						getAPFormEditorInput().setDetailObject((ARObject)selection.getFirstElement());
//						changeDetailControl();
//					}
//				}
//			}
//		});
		
		((TreeViewer)contentsTreeSection.getViewer()).addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(event.getStructuredSelection().size() == 1 && event.getStructuredSelection().getFirstElement() instanceof ARObject) {
					getAPFormEditorInput().setDetailObject((ARObject)event.getStructuredSelection().getFirstElement());
					System.out.println(">>>>> :"+getAPFormEditorInput().getDetailObject());
					changeDetailControl();
				}
//				if(event.getSelection() instanceof IStructuredSelection) {
//					IStructuredSelection selection = (IStructuredSelection)event.getSelection();
//					if(selection.size() == 1 && selection.getFirstElement() instanceof ARObject) {
//						getAPFormEditorInput().setDetailObject((ARObject)selection.getFirstElement());
//						changeDetailControl();
//					}
//				}
			}
		});


		if(getAPFormEditorInput().getDetailObject() != null) {
			((TreeViewer)contentsTreeSection.getViewer()).setSelection(new StructuredSelection(getAPFormEditorInput().getDetailObject()), true);
		}
	}
	
	public APFormEditor getAPFormEditor() {
		return (APFormEditor)getEditor();
	}
	
	public APFormEditorInput getAPFormEditorInput() {
		return (APFormEditorInput)getEditorInput();
	}
	
	protected abstract Map<String, Control> createDetailControls(final Composite parent, final IManagedForm managedForm);
	
	private void changeDetailControl() {
		layout.topControl = lookupDetailControlProvider();
		
		if(layout.topControl != null) {
			detailComposite.layout(true, true);
			
			for (IFormSection section: getSections()) {
				if(section instanceof AbstractContentGUISection) {
					if(((AbstractContentGUISection)section).getSectionTypeName().equals(layout.topControl.getData())){
						((AbstractContentGUISection)section).initUIContents();
						// 여러개의 섹션을 포함하고 있을 수 있으므로 여기서 종료하지 않는다.
					}
				}
			}
		}
		System.out.println("-------changeDetailControl-------");
	}
	
	private Control lookupDetailControlProvider() {
		System.out.println("lookupDetailControlProvider:"+getAPFormEditorInput().getDetailObject());
		if(getAPFormEditorInput().getDetailObject() != null) {
			IAPModelManager modelProvider = APModelManagerProvider.apINSTANCE.lookupModelManager(getAPFormEditorInput().getDetailObject());
			System.out.println("IAPModelManager:"+modelProvider);
			if(modelProvider != null) {
				System.out.println("TypeName:"+modelProvider.getTypeName(getAPFormEditorInput().getDetailObject()));
				return controlProviderMap.get(modelProvider.getTypeName(getAPFormEditorInput().getDetailObject()));
			}
//			System.out.println("lookupDetailControlProvider->"+getAPFormEditorInput().getDetailObject().getClass().getSimpleName()+", V:"+controlProviderMap.get(getAPFormEditorInput().getDetailObject().getClass().getSimpleName()));
//			if(temp == 1) {
//				temp = 2;
//			}else {
//				temp = 1;
//			}
//			return controlProviderMap.get("TEST"+temp);
		}
		return null;
	}
	
//	@Override
//	public IContentProvider getContentProvider() {
//		return super.getContentProvider();
//	}
//
//	@Override
//	public ILabelProvider getLabelProvider() {
//		System.out.println("pageInput::"+pageInput.getClass());
//		
//		return new APColumnLabelProvider((APFormEditor) getEditor());
//	}
	
	public void refreshInputObjectTree() {
		if(contentsTreeSection != null) {
			contentsTreeSection.refreshSection();
			contentsTreeSection.getViewer().refresh();
		}
	}

}
