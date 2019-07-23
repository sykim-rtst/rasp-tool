package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ProvidedSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ServiceinstancedeploymentFactory;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipEventProps;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipMethodProps;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.ServiceEventDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.ServiceInterfaceDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.ServiceMethodDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipEventDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipMethodDeployment;
import autosar40.swcomponent.datatype.units.UnitGroup;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.ProvidedServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
//import kr.co.rtst.autosar.ap4x.editor.page.section.system.SomeipServiceDiscoverySection.SecureComPropsContentProvider;
//import kr.co.rtst.autosar.ap4x.editor.page.section.system.SomeipServiceDiscoverySection.SecureComPropsLabelProvider;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class ProvidedServiceInstanceSection extends ShortNameContentGUISection implements SelectionListener{
	
	private Text textServiceInterface;
	private Button buttonChooseInterface;
	private Button buttonEraseInterface;
	
	private Text textServiceInstanceId;
	
	private ListViewer listEventProps;
	private ListViewer listMethodResponseProps;
	private Text textLoadBalancingPriority;
	private Text textLoadBalancingWeight;
	
	
	public ProvidedServiceInstanceSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, ProvidedServiceInstanceModelManager.TYPE_NAME_PROVIDED_SERVICE_INSTANCE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Provided Service Instance type");
		sectionPart.getSection().setDescription("Provided Service Instance desc");
		
		textServiceInterface = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service interface: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonChooseInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textServiceInstanceId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service instance ID: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, false);
		
		listEventProps = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Event props: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1);
		listEventProps.setContentProvider(new ProvidedServiceInstanceContentProvider());
		listEventProps.setLabelProvider(new SomeipEventPropsLabelProvider());
		
		listMethodResponseProps = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Method response\r\nprops: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1);
		listMethodResponseProps.setContentProvider(new ProvidedServiceInstanceContentProvider());
		listMethodResponseProps.setLabelProvider(new SomeipMethodPropsLabelProvider());
		
		//hookTextListener();
		
	}
	
//	private void hookTextListener()
//	{
//		
//	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final ProvidedSomeipServiceInstance input = getCastedInputObject();
		final ServiceInterfaceDeployment serviceInterface = input.getServiceInterface();
		
		if(input != null) {
			
			listEventProps.setInput(input.getEventProps());
			listEventProps.refresh();
			
			listMethodResponseProps.setInput(input.getMethodResponseProps());
			listMethodResponseProps.refresh();
			
			if(serviceInterface != null) {
				textServiceInterface.setText(serviceInterface.gGetShortName());
			}
		}
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonChooseInterface)) { //ServiceInterface 선택버튼
			final ProvidedSomeipServiceInstance input = getCastedInputObject();
			if(input != null)
			{
				IAPProject apProject = getAPProject();
				
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR)
				{
					final ServiceInterfaceDeployment selectedType = ReferenceChoiceDelegator.choiceSingleServiceInterfaceDeployment(buttonChooseInterface.getShell(), apProject);
					
					if(selectedType != null)
					{
						EList<ServiceEventDeployment> serviceEventDeployments = selectedType.getEventDeployments();
						
						EList<SomeipEventProps> someipEventProps = new BasicEList<SomeipEventProps>();
						
						SomeipEventProps singleEventProps;
						for (ServiceEventDeployment ed: serviceEventDeployments) {
							singleEventProps = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipEventProps();
							singleEventProps.setEvent((SomeipEventDeployment)ed);
							someipEventProps.add(singleEventProps);
						}
	
						EList<ServiceMethodDeployment> methodDeployments = selectedType.getMethodDeployments();
						EList<SomeipMethodProps> someipMethodProps = new BasicEList<SomeipMethodProps>();
						
						SomeipMethodProps singleMethodProps;
						for(ServiceMethodDeployment md: methodDeployments)
						{
							singleMethodProps = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipMethodProps();
							singleMethodProps.setMethod((SomeipMethodDeployment)md);
							someipMethodProps.add(singleMethodProps);
						}
						
						
						doTransactionalOperation(new IAPTransactionalOperation() {
							
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.getEventProps().clear();
								input.getEventProps().addAll(someipEventProps);
								
								input.getMethodResponseProps().clear();
								input.getMethodResponseProps().addAll(someipMethodProps);
								
								input.setServiceInterface(selectedType);
								
								return null;
							}
						});
						
						
						textServiceInterface.setText(selectedType.getShortName());
						listEventProps.refresh();
						listMethodResponseProps.refresh();
						
					}
				}
			}
		}else if(e.getSource().equals(buttonEraseInterface)) { //Service insterface 선택 취소 버튼
			final ProvidedSomeipServiceInstance input = getCastedInputObject();
			if(input != null)
			{
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setServiceInterface(null);
						input.getEventProps().clear();
						input.getMethodResponseProps().clear();
						
						return null;
					}
				});
				textServiceInterface.setText("");
				listEventProps.refresh();
				listMethodResponseProps.refresh();
			}
		}
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private ProvidedSomeipServiceInstance getCastedInputObject()
	{
		return (ProvidedSomeipServiceInstance)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}
	
	private class ProvidedServiceInstanceContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class SomeipEventPropsLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public SomeipEventPropsLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof SomeipEventProps)
			{
				return ((SomeipEventProps)element).getEvent().getShortName();
			}else
			{
				return "";
			}
		}
		
		@Override
		public Image getImage(Object element) {
			Image img = apModelLabelProvider.getImage(element);
			if(img == null) {
				img = AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
			}
			return img;
		}
	}
	
	private class SomeipMethodPropsLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public SomeipMethodPropsLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof SomeipMethodProps)
			{
				return ((SomeipMethodProps)element).getMethod().getShortName();
			}else
			{
				return "";
			}
		}
		
		@Override
		public Image getImage(Object element) {
			Image img = apModelLabelProvider.getImage(element);
			if(img == null) {
				img = AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
			}
			return img;
		}
	}
}


