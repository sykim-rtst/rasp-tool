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
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipProvidedEventGroupSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipSdServerServiceInstanceConfigSection;
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
			case ProvidedServiceInstanceModelManager.TYPE_NAME_SD_SERVER_SERVICE_INSTANCE_CONFIG:
				createDetailSdServerServiceInstanceConfig(control, managedForm);
				break;
			case ProvidedServiceInstanceModelManager.TYPE_NAME_EVENT_GROUP:
				createDetailProvidedEventGroup(control, managedForm);
				break;
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
	
	private void createDetailSdServerServiceInstanceConfig(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipSdServerServiceInstanceConfigSection sectionGUI = new SomeipSdServerServiceInstanceConfigSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailProvidedEventGroup(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipProvidedEventGroupSection sectionGUI = new SomeipProvidedEventGroupSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
}
