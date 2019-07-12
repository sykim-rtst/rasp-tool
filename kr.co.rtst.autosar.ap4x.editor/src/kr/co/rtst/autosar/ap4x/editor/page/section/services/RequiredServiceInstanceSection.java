package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import kr.co.rtst.autosar.ap4x.core.model.manager.ProvidedServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.manager.RequiredServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class RequiredServiceInstanceSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textRequiredServiceInstanceId;
	private Text textServiceInsterface;
	private ListViewer listEventProps;
	private ListViewer listMethodRequestProps;
	private Text textRequireMinorVersion;
	
	public RequiredServiceInstanceSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, RequiredServiceInstanceModelManager.TYPE_NAME_REQUIRED_SERVICE_INSTANCE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Required Service Instance");
		sectionPart.getSection().setDescription("Required Service Instance desc");
		
		textRequiredServiceInstanceId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service interface: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, false);
		textServiceInsterface = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service interface ID: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, false);
		
		listEventProps = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Event props: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		//listEventProps.setContentProvider(new SecureComPropsContentProvider());
		//listEventProps.setLabelProvider(new SecureComPropsLabelProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		
		listMethodRequestProps = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Method response props: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		//listEventProps.setContentProvider(new SecureComPropsContentProvider());
		//listEventProps.setLabelProvider(new SecureComPropsLabelProvider());
		Composite compButton2 = new Composite(parent, SWT.NONE);
		compButton2.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData2 = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData2);
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
