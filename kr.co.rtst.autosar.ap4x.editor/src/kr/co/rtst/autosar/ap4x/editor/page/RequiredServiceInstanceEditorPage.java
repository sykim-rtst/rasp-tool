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
import kr.co.rtst.autosar.ap4x.core.model.manager.RequiredServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.RequiredServiceInstanceSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipRequiredEventGroupSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipSdClientServiceInstanceConfigSection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class RequiredServiceInstanceEditorPage extends AbstractAPEditorPage{

	public RequiredServiceInstanceEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_REQUIRED_SERVICE_INSTANCE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		// TODO Auto-generated method stub
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getRequiredServiceInstanceModelManager().getAPTypeDescriptors()) {
			controlProviderMap.put(typeDesc.getTypeName(), createDetail(parent, managedForm, typeDesc.getTypeName()));
		}
		return controlProviderMap;
	}
	
	private Control createDetail(Composite parent, IManagedForm managedForm, String typeName) {
		Composite control = managedForm.getToolkit().createComposite(parent);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		switch(typeName) {
		case RequiredServiceInstanceModelManager.TYPE_NAME_REQUIRED_SERVICE_INSTANCE:
			createDetailRequiredServiceInstance(control, managedForm);
			break;
		case RequiredServiceInstanceModelManager.TYPE_NAME_SERVICE_INSTANCE_CLIENT_CONFIG:
			createDetailSdClientServiceInstanceConfig(control, managedForm);
			break;
		case RequiredServiceInstanceModelManager.TYPE_NAME_EVENT_GROUP:
			createDetailRequiredEventGroup(control, managedForm);
			break;
		}
		return control;
	}
	
	private void createDetailRequiredServiceInstance(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		RequiredServiceInstanceSection sectionGUI = new RequiredServiceInstanceSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailSdClientServiceInstanceConfig(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipSdClientServiceInstanceConfigSection sectionGUI = new SomeipSdClientServiceInstanceConfigSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailRequiredEventGroup(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipRequiredEventGroupSection sectionGUI = new SomeipRequiredEventGroupSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
}
