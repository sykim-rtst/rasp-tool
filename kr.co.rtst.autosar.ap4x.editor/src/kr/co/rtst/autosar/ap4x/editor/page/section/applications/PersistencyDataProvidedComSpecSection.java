package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.comspec.PersistencyDataProvidedComSpec;
import autosar40.adaptiveplatform.applicationdesign.comspec.PersistencyRedundancyEnum;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * PersistencyDataProvidedComSpec
 * @author thkim
 *
 */
public class PersistencyDataProvidedComSpecSection extends AbstractContentGUISection implements SelectionListener{

	private Text textDataElement;
	private Button buttonAddDataElement;
	private Button buttonEraseDataElement;
	
	private Text textInitValue;
	private Button buttonAddInitValue;
	private Button buttonEraseInitValue;
	
	private Combo comboRedundancy;
	
	public PersistencyDataProvidedComSpecSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Persistency data provided com spec");
		sectionPart.getSection().setDescription("Persistency data provided com spec desc");
		
		textDataElement = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Data element*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textInitValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Init value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddInitValue = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseInitValue = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		comboRedundancy = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Redundancy: ", CLIENT_CONTENT_COLUMN-1, 0, this, "None", "Redundant");
	}
	
	@Override
	public void initUIContents() {
		PersistencyDataProvidedComSpec input = getCastedInputDetail();
		if(input != null) {
			setDataElementText(input);
			
//			if(input.getRedundancy() != null) {
//				if(input.getRedundancy().equals(PersistencyRedundancyEnum.NONE)) {
//					comboRedundancy.select(0);
//				} else if(input.getRedundancy().equals(PersistencyRedundancyEnum.REDUNDANT)) {
//					comboRedundancy.select(1);
//				}
//			}
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboRedundancy)) {
			final PersistencyDataProvidedComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
//						switch(comboRedundancy.getSelectionIndex()) {
//						case 0:input.setRedundancy(PersistencyRedundancyEnum.NONE);break;
//						case 1:input.setRedundancy(PersistencyRedundancyEnum.REDUNDANT);break;
//						}
						return model;
					}
				});
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	private PersistencyDataProvidedComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof PersistencyDataProvidedComSpec) {
			return (PersistencyDataProvidedComSpec)getInputDetail();
		}
		return null;
	}
	
	private void setDataElementText(PersistencyDataProvidedComSpec model) {
		if(model.getDataElement() != null) {
			textDataElement.setText(model.getDataElement().getShortName());
		}else {
			textDataElement.setText("");
		}
	}

}
