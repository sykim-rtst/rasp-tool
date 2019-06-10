package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import autosar40.swcomponent.datatype.datatypes.AutosarDataType;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfacesModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * VariableDataPrototype 타입의 Event 요소의 편집을 위한 섹션
 * @author thkim
 *
 */
public class EventSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textType;
	private Button buttonAddType;
	private Button buttonEraseType;
	
	public EventSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfacesModelManager.TYPE_NAME_VARIABLE_DATA_PROTOTYPE);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Variable data prototype");
		sectionPart.getSection().setDescription("Variable data prototype desc");
		
		textType = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Type*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddType = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseType = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final VariableDataPrototype event = getCastedInputDetail();
		if(event != null) {
			setTypeText(event);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddType)) {
			IAPProject apProject = getAPProject();
			if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
				final AutosarDataType selectedType = ReferenceChoiceDelegator.choiceSingleAutosarDataType(buttonAddType.getShell(), apProject);
				if(selectedType != null) {
					final VariableDataPrototype input = getCastedInputDetail();
					if(input != null) {
						
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setType(selectedType);
								return model;
							}
						});
						
						setTypeText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseType)) {
			final VariableDataPrototype input = getCastedInputDetail();
			if(input != null) {
				
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setType(null);
						return model;
					}
				});
				
				setTypeText(input);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private VariableDataPrototype getCastedInputDetail() {
		if(getInputDetail() instanceof VariableDataPrototype) {
			return (VariableDataPrototype)getInputDetail();
		}
		return null;
	}
	
	private void setTypeText(VariableDataPrototype model) {
		if(model.getType() != null) {
			textType.setText(model.getType().getShortName());
		}else {
			textType.setText("");
		}
	}

}
