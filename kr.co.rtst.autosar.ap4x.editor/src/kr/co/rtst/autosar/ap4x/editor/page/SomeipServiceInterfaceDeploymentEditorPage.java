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
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfaceDeploymentModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipEventDeploymentSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipFieldDeploymentSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipMethodDeploymentSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.services.SomeipServiceInterfaceDeploymentSection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class SomeipServiceInterfaceDeploymentEditorPage extends AbstractAPEditorPage {
	
	public SomeipServiceInterfaceDeploymentEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_SERVICE_INTERFACE_DEPLOYMENT);
	}
	
	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getInterfaceDeploymentModelManager().getAPTypeDescriptors()) {
			controlProviderMap.put(typeDesc.getTypeName(), createDetail(parent, managedForm, typeDesc.getTypeName()));
		}
		return controlProviderMap;
	}
	
	private Control createDetail(Composite parent, IManagedForm managedForm, String typeName) {
		Composite control = managedForm.getToolkit().createComposite(parent);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		switch(typeName) {
		case InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT:
			createDetailSomeipServiceInterfaceDeployment(control, managedForm);
			break;
		case InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT:
			createDetailSomeipEventDeployment(control, managedForm);
			break;
		case InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT:
			createDetailSomeipMethodDeployment(control, managedForm);
			break;
		case InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT:
			createDetailSomeipFieldDeployment(control, managedForm);
			break;
		}
		return control;
	}
	
	private void createDetailSomeipServiceInterfaceDeployment(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipServiceInterfaceDeploymentSection sectionGUI = new SomeipServiceInterfaceDeploymentSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailSomeipEventDeployment(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipEventDeploymentSection sectionGUI = new SomeipEventDeploymentSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailSomeipMethodDeployment(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));

		SomeipMethodDeploymentSection sectionGUI = new SomeipMethodDeploymentSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailSomeipFieldDeployment(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));

		SomeipFieldDeploymentSection sectionGUI = new SomeipFieldDeploymentSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
}
