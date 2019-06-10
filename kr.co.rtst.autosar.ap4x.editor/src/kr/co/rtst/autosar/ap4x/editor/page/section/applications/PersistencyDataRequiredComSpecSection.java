package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.comspec.PersistencyDataRequiredComSpec;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * PersistencyDataRequiredComSpec
 * @author thkim
 *
 */
public class PersistencyDataRequiredComSpecSection extends AbstractContentGUISection implements SelectionListener{

	private Text textDataElement;
	private Button buttonAddDataElement;
	private Button buttonEraseDataElement;
	
	private Text textInitValue;
	private Button buttonAddInitValue;
	private Button buttonEraseInitValue;
	
	public PersistencyDataRequiredComSpecSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Persistency data required com spec");
		sectionPart.getSection().setDescription("Persistency data required com spec desc");
		
		textDataElement = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Data element*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textInitValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Init value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddInitValue = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseInitValue = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
	}
	
	@Override
	public void initUIContents() {
		PersistencyDataRequiredComSpec input = getCastedInputDetail();
		if(input != null) {
			setDataElementText(input);
			
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private PersistencyDataRequiredComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof PersistencyDataRequiredComSpec) {
			return (PersistencyDataRequiredComSpec)getInputDetail();
		}
		return null;
	}
	
	private void setDataElementText(PersistencyDataRequiredComSpec model) {
		if(model.getDataElement() != null) {
			textDataElement.setText(model.getDataElement().getShortName());
		}else {
			textDataElement.setText("");
		}
	}

}
