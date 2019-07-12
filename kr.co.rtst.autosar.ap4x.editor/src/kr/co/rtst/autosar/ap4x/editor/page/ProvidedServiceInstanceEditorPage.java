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
import kr.co.rtst.autosar.ap4x.core.model.manager.ProvidedServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.ProvidedServiceInstanceSection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class ProvidedServiceInstanceEditorPage extends AbstractAPEditorPage{

	public ProvidedServiceInstanceEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_PROVIDED_SERVICE_INSTANCE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		// TODO Auto-generated method stub
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getProvidedServiceInstanceModelManager().getAPTypeDescriptors()) {
			controlProviderMap.put(typeDesc.getTypeName(), createDetail(parent, managedForm, typeDesc.getTypeName()));
		}
		return controlProviderMap;
	}
	
	private Control createDetail(Composite parent, IManagedForm managedForm, String typeName) {
		Composite control = managedForm.getToolkit().createComposite(parent);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		switch(typeName) {
		case ProvidedServiceInstanceModelManager.TYPE_NAME_PROVIDED_SERVICE_INSTANCE:
			createDetailProvidedServiceInstance(control, managedForm);
			break;
//		case InterfacesModelManager.TYPE_NAME_VARIABLE_DATA_PROTOTYPE:
//			createDetailVariableDataPrototype(control, managedForm);
//			break;
//		case InterfacesModelManager.TYPE_NAME_FIELD:
//			createDetailField(control, managedForm);
//			break;
//		case InterfacesModelManager.TYPE_NAME_CLIENT_SERVER_OPERATION:
//			createClientServerOperation(control, managedForm);
//			break;
//		case InterfacesModelManager.TYPE_NAME_APPLICATION_ERROR:
//			createDetailApplcationError(control, managedForm);
//			break;
//		case InterfacesModelManager.TYPE_NAME_SECVICE_INTERFACE:
//			createDetailServiceInterface(control, managedForm);
//			break;
		}
		return control;
	}
	
	private void createDetailProvidedServiceInstance(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ProvidedServiceInstanceSection sectionGUI = new ProvidedServiceInstanceSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
}
