package kr.co.rtst.autosar.ap4x.editor.dialog;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class GARObjectMultiFilteredList extends FilteredTree{

	private static final long FILTER_DELAY = 400;

	private FormToolkit fToolkit;
	private TreeViewer treeViewer;
	

	/**
	 * Constructor that creates a tree with preset style bits and a CachedContainerCheckedTreeViewer for the tree.
	 *
	 * @param parent parent composite
	 * @param toolkit optional toolkit to create UI elements with, required if the tree is being created in a form editor
	 */
	public GARObjectMultiFilteredList(Composite parent, FormToolkit toolkit) {
		this(parent, toolkit, SWT.NONE);
	}

	/**
	 * Constructor that creates a tree with preset style bits and a CachedContainerCheckedTreeViewer for the tree.
	 *
	 * @param parent parent composite
	 * @param toolkit optional toolkit to create UI elements with, required if the tree is being created in a form editor
	 * @param treeStyle
	 */
	public GARObjectMultiFilteredList(Composite parent, FormToolkit toolkit, int treeStyle) {
		this(parent, toolkit, treeStyle, new PatternFilter());
	}

	/**
	 * Constructor that creates a tree with preset style bits and a CachedContainerCheckedTreeViewer for the tree.
	 *
	 * @param parent parent composite
	 * @param toolkit optional toolkit to create UI elements with, required if the tree is being created in a form editor
	 * @param treeStyle
	 * @param filter pattern filter to use in the filter control
	 */
	public GARObjectMultiFilteredList(Composite parent, FormToolkit toolkit, int treeStyle, PatternFilter filter) {
		super(parent, treeStyle, filter, true);
		fToolkit = toolkit;
	}
	
//	@Override
//	public void setInitialText(String text) {
//		super.setInitialText(IDEText.DIALOG_FILTER_INITIAL_TEXT);
//	}

	@Override
	protected TreeViewer doCreateTreeViewer(Composite actParent, int style) {
		int treeStyle = style | /*SWT.CHECK |*/ SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER;
		Tree tree = null;
		if (fToolkit != null) {
			tree = fToolkit.createTree(actParent, treeStyle);
		} else {
			tree = new Tree(actParent, treeStyle);
		}

		treeViewer = new TreeViewer(tree);
		return treeViewer;
	}

//	/*
//	 * Overridden to hook a listener on the job and set the deferred content
//	 * provider to synchronous mode before a filter is done.
//	 *
//	 */
//	@Override
//	protected WorkbenchJob doCreateRefreshJob() {
//		WorkbenchJob filterJob = super.doCreateRefreshJob();
//		filterJob.addJobChangeListener(new JobChangeAdapter() {
//			@Override
//			public void done(IJobChangeEvent event) {
//				if (event.getResult().isOK()) {
//					getDisplay().asyncExec(new Runnable() {
//						@Override
//						public void run() {
//							if (checkboxViewer.getTree().isDisposed())
//								return;
//							checkboxViewer.restoreLeafCheckState();
//						}
//					});
//				}
//			}
//		});
//		return filterJob;
//	}

	@Override
	protected Text doCreateFilterText(Composite actParent) {
		// Overridden so the text gets create using the toolkit if we have one
		Text parentText = super.doCreateFilterText(actParent);
		if (fToolkit != null) {
			int style = parentText.getStyle();
			parentText.dispose();
			return fToolkit.createText(actParent, null, style);
		}
		return parentText;
	}

	@Override
	protected String getFilterString() {
		String filterString = super.getFilterString();
		if (!filterText.getText().equals(initialText)
				&& filterString.indexOf("*") != 0 //$NON-NLS-1$
				&& filterString.indexOf("?") != 0 //$NON-NLS-1$
				&& filterString.indexOf(".") != 0) {//$NON-NLS-1$
			filterString = "*" + filterString; //$NON-NLS-1$
		}
		return filterString;
	}

	/**
	 * Clears the filter
	 */
	public void clearFilter() {
		getPatternFilter().setPattern(null);
		setFilterText(getInitialText());
		textChanged();
	}

	/**
	 * @return The checkbox treeviewer
	 */
	public TreeViewer getTreeViewer() {
		return treeViewer;
	}

	@Override
	protected long getRefreshJobDelay() {
		return FILTER_DELAY;
	}
}
