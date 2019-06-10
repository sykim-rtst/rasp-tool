package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.adaptiveplatform.deployment.adaptivemoduleimplementation.BuildTypeEnum;
import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMapping;
import autosar40.adaptiveplatform.deployment.process.ExecutionDependency;
import autosar40.adaptiveplatform.deployment.process.ProcessFactory;
import autosar40.adaptiveplatform.deployment.process.SchedulingPolicyKindEnum;
import autosar40.adaptiveplatform.deployment.process.instancerefs.InstancerefsFactory;
import autosar40.adaptiveplatform.deployment.process.instancerefs.ModeInMachineInstanceRef;
import autosar40.adaptiveplatform.deployment.process.instancerefs.ModeInProcessInstanceRef;
import autosar40.commonstructure.modedeclaration.ModeDeclaration;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class ProcessSection extends ShortNameContentGUISection implements SelectionListener {
	
	private Text textRootSwComponentPrototype;
	private Button buttonAddRootSwComponentPrototype;
	private Button buttonEraseRootSwComponentPrototype;
	
	private Combo comboBuildType;
	
	private ListViewer listExecutionDependency;
	private Button buttonAddExecutionDependency;
	private Button buttonEraseExecutionDependency;
	
	private ListViewer listFunctionGroupMode;
	private Button buttonAddFunctionGroupMode;
	private Button buttonEraseFunctionGroupMode;
	
	private ListViewer listMachineMode;
	private Button buttonAddMachineMode;
	private Button buttonEraseMachineMode;
	
	private Combo comboSchedulingPolicy;
	
	private Text textSchedulingPriority;
	
	public ProcessSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineModelManager.TYPE_NAME_PROCESS_TO_MACHINE_MAPPING);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Process");
		sectionPart.getSection().setDescription("Process desc");
		
		textRootSwComponentPrototype = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Root sw component prototype*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddRootSwComponentPrototype = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseRootSwComponentPrototype = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		comboBuildType = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Build type*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "Debug", "Release");
		
//		tableExecutionDependency = new TableViewer(APSectionUIFactory.createLabelTableAsList(parent, ToolTipFactory.get(""), SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL, CLIENT_CONTENT_COLUMN-2, "Execution dependency: "));
		listExecutionDependency = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Execution dependency: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		listExecutionDependency.setContentProvider(new ModelContentProvider());
		listExecutionDependency.setLabelProvider(new ModelLabelProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddExecutionDependency = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonEraseExecutionDependency = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
//		tableFunctionGroupMode = new TableViewer(APSectionUIFactory.createLabelTableAsList(parent, ToolTipFactory.get(""), SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL, CLIENT_CONTENT_COLUMN-2, "Function group mode: "));
		listFunctionGroupMode = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Function group mode: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		listFunctionGroupMode.setContentProvider(new ModelContentProvider());
		listFunctionGroupMode.setLabelProvider(new ModelLabelProvider());
		compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddFunctionGroupMode = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonEraseFunctionGroupMode = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
//		tableMachineMode = new TableViewer(APSectionUIFactory.createLabelTableAsList(parent, ToolTipFactory.get(""), SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL, CLIENT_CONTENT_COLUMN-2, "Machine mode: "));
		listMachineMode = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Machine mode: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		listMachineMode.setContentProvider(new ModelContentProvider());
		listMachineMode.setLabelProvider(new ModelLabelProvider());
		compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddMachineMode = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonEraseMachineMode = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		comboSchedulingPolicy = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Scheduling policy*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "FIFO", "Round robin", "Other");
		
		textSchedulingPriority = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Scheduling priority*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		textSchedulingPriority.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						ProcessToMachineMapping input = getCastedInputDetail();
						if(input != null) {
							try {
								input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().setSchedulingPriority(Integer.parseInt(textSchedulingPriority.getText()));
							}catch(NumberFormatException e) {
								input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().setSchedulingPriority(0);
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
		ProcessToMachineMapping input = getCastedInputDetail();
		if(input != null) {
			setRootSwComponentPrototypeText(input);
			
			if(BuildTypeEnum.BUILD_TYPE_DEBUG.equals(input.getProcess().getExecutable().getBuildType())) {
				comboBuildType.select(0);
			}else if(BuildTypeEnum.BUILD_TYPE_RELEASE.equals(input.getProcess().getExecutable().getBuildType())) {
				comboBuildType.select(1);
			}
			
			listExecutionDependency.setInput(input.getProcess().getModeDependentStartupConfigs().get(0).getExecutionDependencies());
			listExecutionDependency.refresh();
			
			listFunctionGroupMode.setInput(input.getProcess().getModeDependentStartupConfigs().get(0).getFunctionGroupModes());
			listFunctionGroupMode.refresh();
			
			listMachineMode.setInput(input.getProcess().getModeDependentStartupConfigs().get(0).getMachineModes());
			listMachineMode.refresh();
			
			if(SchedulingPolicyKindEnum.SCHEDULING_POLICY_FIFO.equals(input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().getSchedulingPolicy())) {
				comboSchedulingPolicy.select(0);
			} else if(SchedulingPolicyKindEnum.SCHEDULING_POLICY_ROUND_ROBIN.equals(input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().getSchedulingPolicy())) {
				comboSchedulingPolicy.select(1);
			} else if(SchedulingPolicyKindEnum.SCHEDULING_POLICY_OTHER.equals(input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().getSchedulingPolicy())) {
				comboSchedulingPolicy.select(2);
			}
			
			textSchedulingPriority.setText(input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().getSchedulingPriority().toString());
		}
	}
	
	private ProcessToMachineMapping getCastedInputDetail() {
		if(getInputDetail() instanceof ProcessToMachineMapping) {
			return (ProcessToMachineMapping)getInputDetail();
		}
		return null;
	}
	
	private Machine getCastedInputObject() {
		return (Machine)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboBuildType)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboBuildType.getSelectionIndex()) {
						case 0:input.getProcess().getExecutable().setBuildType(BuildTypeEnum.BUILD_TYPE_DEBUG);break;
						case 1:input.getProcess().getExecutable().setBuildType(BuildTypeEnum.BUILD_TYPE_RELEASE);break;
						}
						return model;
					}
				});
			}
		} else if(e.getSource().equals(comboSchedulingPolicy)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboSchedulingPolicy.getSelectionIndex()) {
						case 0:input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().setSchedulingPolicy(SchedulingPolicyKindEnum.SCHEDULING_POLICY_FIFO);break;
						case 1:input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().setSchedulingPolicy(SchedulingPolicyKindEnum.SCHEDULING_POLICY_ROUND_ROBIN);break;
						case 2:input.getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().setSchedulingPolicy(SchedulingPolicyKindEnum.SCHEDULING_POLICY_OTHER);break;
						}
						return model;
					}
				});
			}
		} else if(e.getSource().equals(buttonAddRootSwComponentPrototype)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final AdaptiveApplicationSwComponentType selectedType = ReferenceChoiceDelegator.choiceSingleSwComponentType(buttonAddRootSwComponentPrototype.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.getProcess().getExecutable().getRootSwComponentPrototype().setApplicationType(selectedType);
								return model;
							}
						});
						
						setRootSwComponentPrototypeText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseRootSwComponentPrototype)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.getProcess().getExecutable().getRootSwComponentPrototype().setApplicationType(null);
						return model;
					}
				});
				
				setRootSwComponentPrototypeText(input);
			}
		} else if(e.getSource().equals(buttonAddExecutionDependency)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					// TODO 타입을 선택할 수 있어야 한다.
					final List<GARObject> selectedTypes = ReferenceChoiceDelegator.choiceMultiExecutionDependency(buttonAddExecutionDependency.getShell(), apProject);
					if(selectedTypes != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								for (GARObject garObject : selectedTypes) {
									if(garObject instanceof ModeDeclaration) {
										ModeInProcessInstanceRef ref = InstancerefsFactory.eINSTANCE.createModeInProcessInstanceRef();
										ref.setTargetModeDeclaration((ModeDeclaration)garObject);
										
										ExecutionDependency ed = ProcessFactory.eINSTANCE.createExecutionDependency();
										ed.setApplicationMode(ref);
										
										input.getProcess().getModeDependentStartupConfigs().get(0).getExecutionDependencies().add(ed);
									}
								}
								return model;
							}
						});
						
						listExecutionDependency.refresh();
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseExecutionDependency)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				if(!listExecutionDependency.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getProcess().getModeDependentStartupConfigs().get(0).getExecutionDependencies().removeAll(listExecutionDependency.getStructuredSelection().toList());
							return model;
						}
					});
					
					listExecutionDependency.refresh();
				}
			}
		} else if(e.getSource().equals(buttonAddFunctionGroupMode)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					// TODO 타입을 선택할 수 있어야 한다.
					final List<GARObject> selectedTypes = ReferenceChoiceDelegator.choiceMultiFunctionGroupMode(buttonAddMachineMode.getShell(), apProject, getCastedInputObject());
					if(selectedTypes != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								for (GARObject garObject : selectedTypes) {
									if(garObject instanceof ModeDeclaration) {
										ModeInMachineInstanceRef ref = InstancerefsFactory.eINSTANCE.createModeInMachineInstanceRef();
										ref.setTargetModeDeclaration((ModeDeclaration)garObject);
										input.getProcess().getModeDependentStartupConfigs().get(0).getFunctionGroupModes().add(ref);
									}
								}
								return model;
							}
						});
						
						listFunctionGroupMode.refresh();
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseFunctionGroupMode)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				if(!listFunctionGroupMode.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getProcess().getModeDependentStartupConfigs().get(0).getFunctionGroupModes().removeAll(listFunctionGroupMode.getStructuredSelection().toList());
							return model;
						}
					});
					
					listFunctionGroupMode.refresh();
				}
			}
		} else if(e.getSource().equals(buttonAddMachineMode)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					// TODO 타입을 선택할 수 있어야 한다.
					final List<GARObject> selectedTypes = ReferenceChoiceDelegator.choiceMultiMachineMode(buttonAddMachineMode.getShell(), apProject, getCastedInputObject());
					if(selectedTypes != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								for (GARObject garObject : selectedTypes) {
									if(garObject instanceof ModeDeclaration) {
										ModeInMachineInstanceRef ref = InstancerefsFactory.eINSTANCE.createModeInMachineInstanceRef();
										ref.setTargetModeDeclaration((ModeDeclaration)garObject);
										input.getProcess().getModeDependentStartupConfigs().get(0).getMachineModes().add(ref);
									}
								}
								return model;
							}
						});
						
						listMachineMode.refresh();
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseMachineMode)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				if(!listMachineMode.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getProcess().getModeDependentStartupConfigs().get(0).getMachineModes().removeAll(listMachineMode.getStructuredSelection().toList());
							return model;
						}
					});
					
					listMachineMode.refresh();
				}
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void setRootSwComponentPrototypeText(ProcessToMachineMapping model) {
		if(model.getProcess().getExecutable().getRootSwComponentPrototype().getApplicationType() != null) {
			textRootSwComponentPrototype.setText(model.getProcess().getExecutable().getRootSwComponentPrototype().getApplicationType().getShortName());
		} else {
			textRootSwComponentPrototype.setText("");
		}
	}
	
	@Override
	protected void notifyShortNameChanged() {
		super.notifyShortNameChanged();
		getCastedInputDetail().getProcess().setShortName(getCastedInputDetail().getShortName());
	}
	
	private class ModelContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class ModelLabelProvider extends LabelProvider {
		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			if(element instanceof ModeInMachineInstanceRef) {
				ModeInMachineInstanceRef model = (ModeInMachineInstanceRef)element;
				return model.getTargetModeDeclaration().getShortName();
			}else if(element instanceof ModeInProcessInstanceRef) {
				ModeInProcessInstanceRef model = (ModeInProcessInstanceRef)element;
				return model.getTargetModeDeclaration().getShortName();
			}else if(element instanceof ExecutionDependency) {
				ExecutionDependency model = (ExecutionDependency)element;
//				ProcessToMachineMapping mapping = APChildModelAccessAgent.findProcessToMachineMappingFor(getAPProject(), model.getApplicationMode().getTargetModeDeclaration());
//				if(mapping != null) {
//					return mapping.getShortName()+"."+model.getApplicationMode().getTargetModeDeclaration().getShortName();
//				}else {
//					return model.getApplicationMode().getTargetModeDeclaration().getShortName();
//				}
				return model.getApplicationMode().getTargetModeDeclaration().getShortName();
//				return ((autosar40.adaptiveplatform.deployment.process.Process)(model.eContainer().eContainer())).getShortName()+"."+model.getApplicationMode().getTargetModeDeclaration().getShortName();
			}
			return "";
		}
	}

}
