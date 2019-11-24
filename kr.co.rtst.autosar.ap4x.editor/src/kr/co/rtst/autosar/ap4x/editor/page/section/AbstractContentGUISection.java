package kr.co.rtst.autosar.ap4x.editor.page.section;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.sphinx.emf.editors.forms.sections.AbstractViewerFormSection;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperationDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public abstract class AbstractContentGUISection extends AbstractViewerFormSection implements IAPTransactionalOperationDelegator{
	
	public static final int CLIENT_CONTENT_COLUMN = 7;
	
	private final String sectionTypeName;
	
	private AtomicInteger transactionalOpDelegatorState;
	
	/**
	 * 이 섹션을 위한 실제 타입의 이름을 설정한다. 이 이름들은 모두 IAPModelManager의 하위 객체들에 상수로 정의되어 있다.
	 * @param formPage
	 * @param sectionInput
	 * @param style
	 * @param sectionTypeName
	 */
	public AbstractContentGUISection(AbstractAPEditorPage formPage, int style, String sectionTypeName) {
		super(formPage, formPage.getAPFormEditor().getEditorInputObject(), style);
		this.sectionTypeName = sectionTypeName;
//		detailObject = getAPEditorPage().getAPFormEditorInput().getDetailObject();
		System.out.println("AbstractContentGUISection INPUT:"+sectionInput);
//		System.out.println("AbstractContentGUISection INPUT Detail:"+getAPEditorPage().getAPFormEditorInput().getDetailObject());
		transactionalOpDelegatorState = new AtomicInteger(OP_STATE_READY);
	}
	
	public abstract void initUIContents();
	
	abstract protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent);
	
	protected void setDirty(){
		getAPEditorPage().getAPFormEditor().setDirty();
	}
	
//	protected AbstractAPEditorPage getAbstractAPEditorPage() {
//		return (AbstractAPEditorPage)getFormPage();
//	}
	
	public String getSectionTypeName() {
		return this.sectionTypeName;
	}
	
	@Override
	final protected void createSectionClientContent(IManagedForm managedForm, SectionPart sectionPart, Composite sectionClient) {
		Composite bodyComp = managedForm.getToolkit().createComposite(sectionClient);
		bodyComp.setLayout(APUILayoutFactory.getGridLayoutForSectionContent(CLIENT_CONTENT_COLUMN, false));
		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.horizontalSpan = getNumberOfColumns();
		gData.verticalAlignment = SWT.TOP;
		bodyComp.setLayoutData(gData);
		
		createSectionClientContentDetail(managedForm, sectionPart, bodyComp);
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
		initUIContents();
	}
	
	protected SectionPart createSectionPart(Composite parent, FormToolkit toolkit)
    {
//        style = style == 0 ? getDefaultSectionStyle() : style;
//        return new SectionPart(parent, toolkit, Section.TWISTIE|Section.TITLE_BAR|Section.NO_TITLE_FOCUS_BOX|Section.DESCRIPTION|Section.COMPACT|Section.SHORT_TITLE_BAR|Section.FOCUS_TITLE|Section.CLIENT_INDENT/*ExpandableComposite.TITLE_BAR | Section.DESCRIPTION | ExpandableComposite.TWISTIE*/);
		SectionPart part = new SectionPart(parent, toolkit, /*Section.TWISTIE|*/Section.SHORT_TITLE_BAR|Section.DESCRIPTION|Section.TITLE_BAR);
//		GridData gData = new GridData();
//		gData.verticalAlignment = SWT.TOP;
//		part.getSection().setLayoutData(gData);
		return part;
//		return new SectionPart(parent, toolkit, /*Section.TWISTIE|*/Section.SHORT_TITLE_BAR|Section.DESCRIPTION|Section.TITLE_BAR);
    }
	
	@Override
	protected ToolBarManager createSectionToolbar(Section section, FormToolkit toolkit) {
		// TODO Auto-generated method stub
		return super.createSectionToolbar(section, toolkit);
	}
	
	@Override
	protected void fillSectionToolBarActions(ToolBarManager toolBarManager) {
		// TODO Auto-generated method stub
		super.fillSectionToolBarActions(toolBarManager);
	}
	
	@Override
	public int getState() {
		return transactionalOpDelegatorState.get();
	}
	
	protected IAPProject getAPProject() {
		if(getInputDetail() instanceof GARObject) {
			final GARObject detailObject = (GARObject)getInputDetail();
			System.out.println("RES:"+detailObject.eResource().getURI().devicePath().substring("/resource/".length()));
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(detailObject.eResource().getURI().devicePath().substring("/resource/".length())));
			
			return APProjectManager.getInstance().getAPProject(file.getProject());
		}
		return null;
	}

	public void doTransactionalOperation(IAPTransactionalOperation op) {
		transactionalOpDelegatorState.set(OP_STATE_BUSY);
		if(getInputDetail() instanceof GARObject) {
			final GARObject detailObject = (GARObject)getInputDetail();
//			System.out.println("RES:"+detailObject.eResource().getURI().devicePath().substring("/resource/".length()));
//			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(detailObject.eResource().getURI().devicePath().substring("/resource/".length())));
			
			IAPProject apProject = getAPProject();
			org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
					apProject.getProject(), 
					apProject.getAutosarReleaseDescriptor());
			if (domain != null) {
				domain.getCommandStack().execute(new RecordingCommand(domain) {
					@Override
					protected void doExecute() {
						try {
							op.doProcess(detailObject);
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
	
}
