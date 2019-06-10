package kr.co.rtst.autosar.ap4x.ide.views.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.ide.AP4xIDEActivator;
import kr.co.rtst.autosar.ap4x.ide.consts.IDEText;

/**
 * 패키지 아래 추가되어야 하는 요소들을 추가하기 위한 대화창
 * 패키지 선택 또는 생성과 ShortName을 설정할 수 있도록한다.
 * @author thkim
 *
 */
public class CreatePackagableElementDialog extends TitleAreaDialog {
	
	private String createElementName;
	
	private FormToolkit toolkit;
	private ARPackageFilteredTree arPackageFilteredTree;
	private Text textPackageName;
	private Text textShortName;
	
	private GAUTOSAR packageContainer;
	
	private GARPackage selectedPackage;
	private boolean dirtyPackageName; // 트리에서 선택한 패키지인경우 바로 사용할 수 있으므로 false로 설정하며, 사용자가 임의로 수정한 경우 true로 설정하여 외부에서 실제 패키지가 존재하는지를 검사하여 처리해야 한다. 
	private String packageName;
	private String shortName;
	
	/**
	 * 
	 * @param parentShell
	 * @param toolkit 없으면 null을 전달해도 무방함
	 * @param packageContainer
	 * @param title
	 */
	public CreatePackagableElementDialog(Shell parentShell, FormToolkit toolkit, GAUTOSAR packageContainer, String createElementName) {
		super(parentShell);
		this.createElementName = createElementName;
		this.packageContainer = packageContainer;
		this.toolkit = toolkit;
	}
	
	public boolean isDirtyPackageName() {
		return dirtyPackageName;
	}
	
	public GARPackage getSelectedPackage() {
		return selectedPackage;
	}
	
	public String getPackageName() {
		return packageName;
	}

	public String getShortName() {
		return shortName;
	}

	@Override
	public void create() {
		super.create();
		getShell().setText(String.format(IDEText.DIALOG_WINDOW_TITLE_NEW_ELEMENT, createElementName));
		setMessage(String.format(IDEText.DIALOG_TITLE_NEW_ELEMENT, createElementName));
		setTitle(String.format(IDEText.DIALOG_MESSAGE_NEW_ELEMENT, createElementName));
		setPageComplete();
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(400, 450);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite comp = new Composite((Composite)super.createDialogArea(parent), SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		GridLayout layout = null;
		Composite treeComp = new Composite(comp, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		treeComp.setLayout(layout);
		
		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.horizontalSpan = 2;
		treeComp.setLayoutData(gData);
		
		arPackageFilteredTree = new ARPackageFilteredTree(treeComp, toolkit);
		arPackageFilteredTree.getTreeViewer().getTree().setLayoutData(gData);
		arPackageFilteredTree.getTreeViewer().setLabelProvider(new GARPackageLabelProvider());
		arPackageFilteredTree.getTreeViewer().setContentProvider(new GARPackageContentProvider());
		arPackageFilteredTree.getTreeViewer().setInput(packageContainer);
		
		Composite compButton = new Composite(treeComp, SWT.NONE);
		layout = new GridLayout();
		layout.marginBottom = 0;
		layout.marginTop = 20;
		compButton.setLayout(layout);
		gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = GridData.BEGINNING;
		compButton.setLayoutData(gData);
		Button button = new Button(compButton, SWT.PUSH);
		button.setImage(AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/collapseall.gif"));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {arPackageFilteredTree.getTreeViewer().collapseAll();}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		button = new Button(compButton, SWT.PUSH);
		button.setImage(AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/expandall.gif"));
		button.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {arPackageFilteredTree.getTreeViewer().expandAll();}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
		
		Label label = new Label(comp, SWT.NONE);
		label.setText(IDEText.DIALOG_PACKAGE_NAME_TEXT);
		textPackageName = new Text(comp, SWT.BORDER);
		textPackageName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		label = new Label(comp, SWT.NONE);
		label.setText(IDEText.DIALOG_SHORT_NAME_TEXT);
		textShortName = new Text(comp, SWT.BORDER);
		textShortName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		initUIContent();
		
		hookListener();
		
		setPageComplete();
		
		return comp;
	}
	/**
	 * 대화창 UI의 내용을 초기화 한다.
	 */
	private void initUIContent(){
		dirtyPackageName = true; // 초기에는 트리에서 선택된 것이 없으므로 true로 초기화 한다. 실제로는 테스트가 비어있을 경우 대화창을 닫을 수 없으므로 초기화 하지 않아도 문제되지 않는다.
		arPackageFilteredTree.getTreeViewer().expandAll();
	}
	/**
	 * 대화창 UI들 중 이벤트처리가 필요한 컴포넌트들에 액션 리스너를 등록한다.
	 */
	private void hookListener(){
		
//		arPackageFilteredTree.getTreeViewer().addDoubleClickListener(new IDoubleClickListener() {
//			@Override
//			public void doubleClick(DoubleClickEvent event) {
//				if(arPackageFilteredTree.getTreeViewer().getStructuredSelection().size() == 1) {
//					selectedPackage = (GARPackage)arPackageFilteredTree.getTreeViewer().getStructuredSelection().getFirstElement();
//					
//					List<String> pkgNames = new ArrayList<>();
//					GARPackage tempPkg = selectedPackage;
//					
//					pkgNames.add(tempPkg.gGetShortName());
//					while(tempPkg.eContainer() instanceof GARPackage) {
//						tempPkg = (GARPackage)tempPkg.eContainer();
//						pkgNames.add(tempPkg.gGetShortName());
//					}
//					StringBuilder fullName = new StringBuilder();
//					for (int i = pkgNames.size()-1; i >= 0; i--) {
//						fullName.append(pkgNames.get(i));
//						if(i != 0) {
//							fullName.append(".");
//						}
//					}
//					textPackageName.setText(fullName.toString());
//					dirtyPackageName = false;
//				}else {
//					selectedPackage = null;
//				}
//			}
//		});
		
		arPackageFilteredTree.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(arPackageFilteredTree.getTreeViewer().getStructuredSelection().size() == 1) {
					selectedPackage = (GARPackage)event.getStructuredSelection().getFirstElement();
					
					List<String> pkgNames = new ArrayList<>();
					GARPackage tempPkg = selectedPackage;
					
					pkgNames.add(tempPkg.gGetShortName());
					while(tempPkg.eContainer() instanceof GARPackage) {
						tempPkg = (GARPackage)tempPkg.eContainer();
						pkgNames.add(tempPkg.gGetShortName());
					}
					StringBuilder fullName = new StringBuilder();
					for (int i = pkgNames.size()-1; i >= 0; i--) {
						fullName.append(pkgNames.get(i));
						if(i != 0) {
							fullName.append(".");
						}
					}
					textPackageName.setText(fullName.toString());
					dirtyPackageName = false;
				}else {
					selectedPackage = null;
				}
			}
		});
		textPackageName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirtyPackageName = true;
				packageName = textPackageName.getText().trim();
				setPageComplete();
			}
		});
		
		textShortName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				shortName = textShortName.getText().trim();
				setPageComplete();
			}
		});
	}
	
	/**
	 * 대화창에 구성된 값들이 완전한지 검사한다.
	 */
	protected void setPageComplete() {
		// TODO 정규표현식 검사가 요구됨
		Button button = (Button)getButton(IDialogConstants.OK_ID);
		if(button != null && !button.isDisposed()){
			if(packageName == null || packageName.trim().length() == 0) {
				button.setEnabled(false);
			}else if(shortName == null || shortName.trim().length() == 0) {
				button.setEnabled(false);
			}else {
				button.setEnabled(true);
			}
		}
	}
	
	private class GARPackageLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public GARPackageLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			return apModelLabelProvider.getText(element);
		}
		
		@Override
		public Image getImage(Object element) {
			return apModelLabelProvider.getImage(element);
		}
	}
	
	private class GARPackageContentProvider implements ITreeContentProvider{

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof GARPackage) {
				return ((GARPackage) parentElement).gGetSubPackages().toArray();
			}else if(parentElement instanceof GAUTOSAR) {
				return ((GAUTOSAR) parentElement).gGetArPackages().toArray();
			}
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			if(element instanceof GARPackage) {
				return ((GARPackage) element).eContainer();
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			if(element instanceof GARPackage) {
				return !((GARPackage) element).gGetSubPackages().isEmpty();
			}else if(element instanceof GAUTOSAR) {
				return !((GAUTOSAR) element).gGetArPackages().isEmpty();
			}
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}
	}
}
