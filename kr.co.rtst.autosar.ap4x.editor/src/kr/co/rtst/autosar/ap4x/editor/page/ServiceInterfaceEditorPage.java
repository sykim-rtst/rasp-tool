package kr.co.rtst.autosar.ap4x.editor.page;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfacesModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.ErrorSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.ArgumentSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.MethodSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.FieldSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.ServiceInterfaceSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.EventSection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class ServiceInterfaceEditorPage extends AbstractAPEditorPage {
	
	public ServiceInterfaceEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_SERVICE_INTERFACE);
	}
	
	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getInterfacesModelManager().getAPTypeDescriptors()) {
			controlProviderMap.put(typeDesc.getTypeName(), createDetail(parent, managedForm, typeDesc.getTypeName()));
		}
		return controlProviderMap;
	}
	
	private Control createDetail(Composite parent, IManagedForm managedForm, String typeName) {
		Composite control = managedForm.getToolkit().createComposite(parent);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		switch(typeName) {
		case InterfacesModelManager.TYPE_NAME_ARGUMENT_DATA_PROTOTYPE:
			createDetailArgumentDataPrototype(control, managedForm);
			break;
		case InterfacesModelManager.TYPE_NAME_VARIABLE_DATA_PROTOTYPE:
			createDetailVariableDataPrototype(control, managedForm);
			break;
		case InterfacesModelManager.TYPE_NAME_FIELD:
			createDetailField(control, managedForm);
			break;
		case InterfacesModelManager.TYPE_NAME_CLIENT_SERVER_OPERATION:
			createClientServerOperation(control, managedForm);
			break;
		case InterfacesModelManager.TYPE_NAME_APPLICATION_ERROR:
			createDetailApplcationError(control, managedForm);
			break;
		case InterfacesModelManager.TYPE_NAME_SECVICE_INTERFACE:
			createDetailServiceInterface(control, managedForm);
			break;
		}
		return control;
	}
	
	private void createDetailArgumentDataPrototype(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ArgumentSection sectionGUI = new ArgumentSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailVariableDataPrototype(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		EventSection sectionGUI = new EventSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailField(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		FieldSection sectionGUI = new FieldSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createClientServerOperation(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		MethodSection sectionGUI = new MethodSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailApplcationError(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ErrorSection sectionGUI = new ErrorSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailServiceInterface(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ServiceInterfaceSection sectionGUI = new ServiceInterfaceSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
}
