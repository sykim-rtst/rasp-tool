package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.adaptiveplatform.systemdesign.MachineDesign;
import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelInitializer;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class MachineSection extends ShortNameContentGUISection implements SelectionListener {

//	private Text textPnResetTimer;
	
	private Text textDefaultEnterTimeout;
	private Text textDefaultExitTimeout;
	
//	private ListViewer listModuleInstantiation;
//	private Button buttonAddModuleInstantiation;
//	private Button buttonRemoveModuleInstantiation;
	
	private Text textMachineDesign;
	private Button buttonAddMachineDesign;
	private Button buttonEraseMachineDesign;
	
	public MachineSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineModelManager.TYPE_NAME_MACHINE);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Machine");
		sectionPart.getSection().setDescription("Machine desc");
		
		
		textDefaultEnterTimeout = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get("DefaultEnterTimeout"), "Default enter timeout: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textDefaultExitTimeout = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get("DefaultExitTimeout"), "Default exit timeout: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
//		listModuleInstantiation = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Module Instantiation: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
//		listModuleInstantiation.setContentProvider(new ListModelContentProvider());
//		listModuleInstantiation.setLabelProvider(new ListModelLabelProvider());
//		Composite compButton = new Composite(parent, SWT.NONE);
//		compButton.setLayout(APUIFactory.getGridLayoutDefault(1, false));
//		GridData gData = new GridData(GridData.FILL_VERTICAL);
//		gData.verticalAlignment = SWT.TOP;
//		compButton.setLayoutData(gData);
//		buttonAddModuleInstantiation = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
//		buttonRemoveModuleInstantiation = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		textMachineDesign = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Machine design*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddMachineDesign = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseMachineDesign = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		hookTextListener();
	}
	
	private void hookTextListener() {
		textDefaultEnterTimeout.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						Machine input = getCastedInputDetail();
						if(input != null) {
							try {
								input.getDefaultApplicationTimeout().setEnterTimeoutValue(Double.parseDouble(textDefaultEnterTimeout.getText()));
							}catch(NumberFormatException e) {
								input.getDefaultApplicationTimeout().setEnterTimeoutValue(0d);
							}
						}
						return model;
					}
				});
			}
		});
		
		textDefaultExitTimeout.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						Machine input = getCastedInputDetail();
						if(input != null) {
							try {
								input.getDefaultApplicationTimeout().setExitTimeoutValue(Double.parseDouble(textDefaultExitTimeout.getText()));
							}catch(NumberFormatException e) {
								input.getDefaultApplicationTimeout().setExitTimeoutValue(0d);
							}
						}
						return model;
					}
				});
			}
		});
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		Machine input = getCastedInputDetail();
		if(input != null) {
			setDefaultTimeoutText(input);
			
//			listModuleInstantiation.setInput(input.getMachineModeMachines().get(0).getType().getModeDeclarations());
//			listModuleInstantiation.refresh();
		}
	}
	
	private Machine getCastedInputDetail() {
		if(getInputDetail() instanceof Machine) {
			return (Machine)getInputDetail();
		}
		return null;
	}
	
	@Override
	protected void notifyShortNameChanged() {
		super.notifyShortNameChanged();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddMachineDesign)) {
			final Machine input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final MachineDesign selectedType = ReferenceChoiceDelegator.choiceSingleMachineDesign(buttonAddMachineDesign.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setMachineDesign(selectedType);
								return model;
							}
						});
						
						setMachineDesignText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseMachineDesign)) {
			final Machine input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setMachineDesign(null);
						return model;
					}
				});
				
				setMachineDesignText(input);
			}
		} /*else if(e.getSource().equals(buttonAddModuleInstantiation)) {
			final Machine input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						return model;
					}
				});
				
				listModuleInstantiation.refresh();
			}
		} else if(e.getSource().equals(buttonRemoveModuleInstantiation)) {
			final Machine input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						return model;
					}
				});
				
				listModuleInstantiation.refresh();
			}
		}*/
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private void setMachineDesignText(Machine input) {
		if(input.getMachineDesign() != null) {
			textMachineDesign.setText(input.getMachineDesign().getShortName());
		}else {
			textMachineDesign.setText("");
		}
	}
	
	private void setDefaultTimeoutText(Machine input) {
		if(input.getDefaultApplicationTimeout() == null) {
			APModelInitializer.initializeMachine_EnterExitTimeout(getAPProject(), input);
		}
		textDefaultEnterTimeout.setText(String.valueOf(input.getDefaultApplicationTimeout().getEnterTimeoutValue().longValue()));
		textDefaultExitTimeout.setText(String.valueOf(input.getDefaultApplicationTimeout().getExitTimeoutValue().longValue()));
	}
	
	private class ListModelContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class ListModelLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public ListModelLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			String result =  apModelLabelProvider.getText(element);
			if(result == null || result.trim().length()==0) {
				if(element instanceof Referrable) {
					return ((Referrable) element).getShortName();
				}
			}else {
				return result;
			}
			return "";
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
