package kr.co.rtst.autosar.ap4x.editor.dialog;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;

public class GARObjectSingleSelectionDialog extends Dialog {
	
	private String selectElementName;
	private FormToolkit toolkit;
	private GARObjectSingleFilteredList garObjectList;
	
	private List<GARObject> objectList;
	private GARObject selectedObject;

	/**
	 * 
	 * @param parentShell
	 * @param toolkit 없으면 null을 전달해도 무방함
	 * @param packageContainer
	 * @param title
	 */
	public GARObjectSingleSelectionDialog(Shell parentShell, FormToolkit toolkit, List<GARObject> objectList, String selectElementName) {
		super(parentShell);
		this.objectList = objectList;
		this.toolkit = toolkit;
		this.selectElementName = selectElementName;
		this.selectedObject = null;
	}
	
	@Override
	public void create() {
		super.create();
		getShell().setText(String.format(EditorText.DIALOG_WINDOW_TITLE_SELECT_ELEMENT, selectElementName));
//		setMessage(String.format(IDEText.DIALOG_TITLE_NEW_ELEMENT, createElementName));
//		setTitle(String.format(IDEText.DIALOG_MESSAGE_NEW_ELEMENT, createElementName));
		setPageComplete();
	}
	
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(300, 350);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite comp = new Composite((Composite)super.createDialogArea(parent), SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		GridLayout layout = null;
		Composite treeComp = new Composite(comp, SWT.NONE);
		layout = new GridLayout();
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
		
		garObjectList = new GARObjectSingleFilteredList(treeComp, toolkit);
		garObjectList.getTreeViewer().getTree().setLayoutData(gData);
		garObjectList.getTreeViewer().setLabelProvider(new GARPackageLabelProvider());
		garObjectList.getTreeViewer().setContentProvider(new GARPackageContentProvider());
		garObjectList.getTreeViewer().setInput(objectList);
		
		initUIContent();
		
		hookListener();
		
		setPageComplete();
		
		return comp;
	}
	/**
	 * 대화창 UI의 내용을 초기화 한다.
	 */
	private void initUIContent(){
	}
	/**
	 * 대화창 UI들 중 이벤트처리가 필요한 컴포넌트들에 액션 리스너를 등록한다.
	 */
	private void hookListener(){
		garObjectList.getTreeViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				setPageComplete();
			}
		});
		
		garObjectList.getTreeViewer().addDoubleClickListener(new IDoubleClickListener() {
			
			@Override
			public void doubleClick(DoubleClickEvent event) {
				if(garObjectList.getTreeViewer().getStructuredSelection().size() == 1 && garObjectList.getTreeViewer().getStructuredSelection().getFirstElement() instanceof GARObject) {
					selectedObject = (GARObject)garObjectList.getTreeViewer().getStructuredSelection().getFirstElement();
					okPressed();
				}
			}
		});
	}
	
	public GARObject getSelectedObject() {
		return selectedObject;
	}
	
	/**
	 * 대화창에 구성된 값들이 완전한지 검사한다.
	 */
	protected void setPageComplete() {
		// TODO 정규표현식 검사가 요구됨
		Button button = (Button)getButton(IDialogConstants.OK_ID);
		if(button != null && !button.isDisposed()){
			if(garObjectList.getTreeViewer().getStructuredSelection().size() == 1 && garObjectList.getTreeViewer().getStructuredSelection().getFirstElement() instanceof GARObject) {
				selectedObject = (GARObject)garObjectList.getTreeViewer().getStructuredSelection().getFirstElement();
				button.setEnabled(true);
			}else {
				button.setEnabled(false);
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
	
	private class GARPackageContentProvider implements ITreeContentProvider{

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public Object[] getChildren(Object parentElement) {
			return new Object[0];
		}

		@Override
		public Object getParent(Object element) {
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return false;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof List) {
				return ((List) inputElement).toArray();
			}
			return new Object[0];
		}
	}
}
