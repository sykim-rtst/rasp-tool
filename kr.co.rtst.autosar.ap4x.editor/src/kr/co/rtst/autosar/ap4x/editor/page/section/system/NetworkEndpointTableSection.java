package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyFactory;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.IpAddressKeepEnum;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.Ipv4AddressSourceEnum;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.Ipv4Configuration;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.Ipv6AddressSourceEnum;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.Ipv6Configuration;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineDesignsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.core.util.ValueUtil;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.dialog.DnsServerIPv4Dialog;
import kr.co.rtst.autosar.ap4x.editor.dialog.DnsServerIPv6Dialog;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class NetworkEndpointTableSection extends AbstractContentGUISection implements SelectionListener {
	
	
	private TableViewer tableIpv4Configuration;
	private Button buttonAddIpv4Configuration;
	private Button buttonRemoveIpv4Configuration;
	
	private TableViewer tableIpv6Configuration;
	private Button buttonAddIpv6Configuration;
	private Button buttonRemoveIpv6Configuration;
	
	public NetworkEndpointTableSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineDesignsModelManager.TYPE_NAME_NETWORK_ENDPOINT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initUIContents() {
		NetworkEndpoint input = getCastedInputDetail();
		if(input != null) {
			tableIpv4Configuration.setInput(input.getNetworkEndpointAddress());
			tableIpv6Configuration.setInput(input.getNetworkEndpointAddress());
		}
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		sectionPart.getSection().setText("Network endpoint");
		sectionPart.getSection().setDescription("Network endpoint desc");
		
		CTabFolder tabFolder = new CTabFolder(parent, SWT.TOP);
		tabFolder.setLayout(APUILayoutFactory.getGridLayoutForSectionContent(1, false));
		GridData gData = new GridData(GridData.FILL_BOTH);
		tabFolder.setLayoutData(gData);
		
		Composite compv4 = managedForm.getToolkit().createComposite(tabFolder);
		compv4.setLayout(APUILayoutFactory.getGridLayoutForSectionContent(CLIENT_CONTENT_COLUMN, false));
		gData = new GridData(GridData.FILL_BOTH);
		compv4.setLayoutData(gData);
		
		tableIpv4Configuration = new TableViewer(APSectionUIFactory.createTable(
				compv4, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				IPV4_TABLE_COLUMN, 
				IPV4_TABLE_COLUMN_WIDTH, 
				IPV4_TABLE_COLUMN_ALIGN, 
				IPV4_TABLE_COLUMN_LISTENER, 
				IPV4_TABLE_COLUMN_RESIZABLE, 
				null));
		tableIpv4Configuration.setLabelProvider(new IPv4ModelLabelProvider());
		tableIpv4Configuration.setContentProvider(new IPv4ModelContentProvider());
		
		Composite compIPv4Button = new Composite(compv4, SWT.NONE);
		compIPv4Button.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compIPv4Button.setLayoutData(gData);
		buttonAddIpv4Configuration = APSectionUIFactory.createButtonImage(compIPv4Button, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveIpv4Configuration = APSectionUIFactory.createButtonImage(compIPv4Button, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		hookIPv4Listeners();
		
		///////////////////////
		Composite compv6 = managedForm.getToolkit().createComposite(tabFolder);
		compv6.setLayout(APUILayoutFactory.getGridLayoutForSectionContent(CLIENT_CONTENT_COLUMN, false));
		gData = new GridData(GridData.FILL_BOTH);
		compv6.setLayoutData(gData);
		
		tableIpv6Configuration = new TableViewer(APSectionUIFactory.createTable(
				compv6, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableIpv6Configuration.setLabelProvider(new ModelLabelProvider());
		tableIpv6Configuration.setContentProvider(new ModelContentProvider());
		
		Composite compIPv6Button = new Composite(compv6, SWT.NONE);
		compIPv6Button.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compIPv6Button.setLayoutData(gData);
		buttonAddIpv6Configuration = APSectionUIFactory.createButtonImage(compIPv6Button, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveIpv6Configuration = APSectionUIFactory.createButtonImage(compIPv6Button, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		hookIPv6Listeners();
		
		CTabItem itemv4 = new CTabItem(tabFolder, SWT.NONE);
		itemv4.setControl(compv4);
		itemv4.setText("IPv4 Configuration");
		
		CTabItem itemv6 = new CTabItem(tabFolder, SWT.NONE);
		itemv6.setControl(compv6);
		itemv6.setText("IPv6 Configuration");
		
		tabFolder.setSelection(0);
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddIpv4Configuration)) {
			final NetworkEndpoint input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						Ipv4Configuration ipv4 = EthernettopologyFactory.eINSTANCE.createIpv4Configuration();
						input.getNetworkEndpointAddress().add(ipv4);
						return model;
					}
				});
				
				tableIpv4Configuration.refresh();
			}
		} else if(e.getSource().equals(buttonRemoveIpv4Configuration)) {
			final NetworkEndpoint input = getCastedInputDetail();
			if(input != null) {
				if(!tableIpv4Configuration.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getNetworkEndpointAddress().removeAll(tableIpv4Configuration.getStructuredSelection().toList());
							return model;
						}
					});
					
					tableIpv4Configuration.refresh();
				}
			}
		} else if(e.getSource().equals(buttonAddIpv6Configuration)) {
			final NetworkEndpoint input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						Ipv6Configuration ipv6 = EthernettopologyFactory.eINSTANCE.createIpv6Configuration();
						input.getNetworkEndpointAddress().add(ipv6);
						return model;
					}
				});
				
				tableIpv6Configuration.refresh();
			}
		} else if(e.getSource().equals(buttonRemoveIpv6Configuration)) {
			final NetworkEndpoint input = getCastedInputDetail();
			if(input != null) {
				if(!tableIpv6Configuration.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getNetworkEndpointAddress().removeAll(tableIpv6Configuration.getStructuredSelection().toList());
							return model;
						}
					});
					
					tableIpv6Configuration.refresh();
				}
			}
		}		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private NetworkEndpoint getCastedInputDetail() {
		if(getInputDetail() instanceof NetworkEndpoint) {
			return (NetworkEndpoint)getInputDetail();
		}
		return null;
	}
	
	public static final String[] IPV4_TABLE_COLUMN				 = {
			"",
			"Assignment priority",
			"Default gateway",
			"DNS server address",
			"IP address keep behavior",
			"IPv4 address",
			"IPv4 address source",
			"Network mask",
			"TTL",
	}; 
	public static final int[] IPV4_TABLE_COLUMN_WIDTH				 = {
			0, 100, 100, 100, 100, 100, 100, 100, 100 
	}; 
	public static final int[] IPV4_TABLE_COLUMN_ALIGN			 = {
			SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT
	}; 
	public static final boolean[] IPV4_TABLE_COLUMN_LISTENER			 = {
			false, false, false, false, false, false, false, false, false
	}; 
	public static final boolean[] IPV4_TABLE_COLUMN_RESIZABLE			 = {
			false, true, true, true, true, true, true, true, true
	}; 
	
	public static final String[] IPV4_IP_ADDRESS_KEEP_ENUM_VALUES = {"forget", "storePersistently"};
	public static final String[] IPV4_IPV4_ADDRESS_SOURCE_ENUM_VALUES = {"autoip", "autoip_doip", "dhcpv4", "fixed"};

	protected void hookIPv4Listeners() {
		tableIpv4Configuration.setColumnProperties(IPV4_TABLE_COLUMN);
		tableIpv4Configuration.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(tableIpv4Configuration.getTable()),
				new TextCellEditor(tableIpv4Configuration.getTable()),
				new DialogCellEditor(tableIpv4Configuration.getTable()) {
					
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {
						DnsServerIPv4Dialog d = new DnsServerIPv4Dialog(cellEditorWindow.getShell(), ((Ipv4Configuration)(tableIpv4Configuration.getStructuredSelection().getFirstElement())));
						if(d.open() == IDialogConstants.OK_ID) {
							return d.getDnsList(); 
						}
						return null;
					}
				},
				new ComboBoxCellEditor(tableIpv4Configuration.getTable(), IPV4_IP_ADDRESS_KEEP_ENUM_VALUES),
				new TextCellEditor(tableIpv4Configuration.getTable()),
				new ComboBoxCellEditor(tableIpv4Configuration.getTable(), IPV4_IPV4_ADDRESS_SOURCE_ENUM_VALUES),
				new TextCellEditor(tableIpv4Configuration.getTable()),
				new TextCellEditor(tableIpv4Configuration.getTable()),
		});
		tableIpv4Configuration.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(final Object element, final String property, final Object value) {
				final Ipv4Configuration model = (Ipv4Configuration)((TableItem)element).getData();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject arg) throws Exception {
						if(IPV4_TABLE_COLUMN[1].equals(property)) {
							model.setAssignmentPriority(Long.parseLong((String)value));
						}else if(IPV4_TABLE_COLUMN[2].equals(property)) {
							model.setDefaultGateway(createInet4Address((String)value));
						}else if(IPV4_TABLE_COLUMN[3].equals(property)) {
							if(value instanceof List) {
								model.getDnsServerAddress().clear();
								for (String string : ((List<String>)value)) {
									model.getDnsServerAddress().add(createInet4Address(string));
								}
							}
						}else if(IPV4_TABLE_COLUMN[4].equals(property)) {
							if( ((Integer)value) == 0 ) {
								model.setIpAddressKeepBehavior(IpAddressKeepEnum.FORGET);
							}else {
								model.setIpAddressKeepBehavior(IpAddressKeepEnum.STORE_PERSISTENTLY);
							}
						}else if(IPV4_TABLE_COLUMN[5].equals(property)) {
							model.setIpv4Address(createInet4Address((String)value));
						}else if(IPV4_TABLE_COLUMN[6].equals(property)) {
							if( ((Integer)value) == 0 ) {
								model.setIpv4AddressSource(Ipv4AddressSourceEnum.AUTO_IP);
							}else if( ((Integer)value) == 1 ) {
								model.setIpv4AddressSource(Ipv4AddressSourceEnum.AUTO_IP_DOIP);
							}else if( ((Integer)value) == 2 ) {
								model.setIpv4AddressSource(Ipv4AddressSourceEnum.DHCPV4);
							}else if( ((Integer)value) == 3 ) {
								model.setIpv4AddressSource(Ipv4AddressSourceEnum.FIXED);
							}
						}else if(IPV4_TABLE_COLUMN[7].equals(property)) {
							model.setNetworkMask(createInet4Address((String)value));
						}else if(IPV4_TABLE_COLUMN[8].equals(property)) {
							model.setTtl(Long.parseLong((String)value));
						}
						return model;
					}
				});
				tableIpv4Configuration.refresh();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				Ipv4Configuration model = (Ipv4Configuration)element;
				if(IPV4_TABLE_COLUMN[1].equals(property)) {
					return model.getAssignmentPriority().toString();
				}else if(IPV4_TABLE_COLUMN[2].equals(property)) {
					return model.getDefaultGateway()!=null?model.getDefaultGateway().getHostAddress():"";
				}else if(IPV4_TABLE_COLUMN[3].equals(property)) {
					return getDnsIPv4AddressSetString(model);
				}if(IPV4_TABLE_COLUMN[4].equals(property)) {
					return IpAddressKeepEnum.STORE_PERSISTENTLY.equals(model.getIpAddressKeepBehavior())?1:0;
				}else if(IPV4_TABLE_COLUMN[5].equals(property)) {
					return model.getIpv4Address()!=null?model.getIpv4Address().getHostAddress():"";
				}if(IPV4_TABLE_COLUMN[6].equals(property)) {
					if(Ipv4AddressSourceEnum.AUTO_IP_DOIP.equals(model.getIpv4AddressSource())) {
						return 1;
					}else if(Ipv4AddressSourceEnum.DHCPV4.equals(model.getIpv4AddressSource())) {
						return 2;
					}else if(Ipv4AddressSourceEnum.FIXED.equals(model.getIpv4AddressSource())) {
						return 3;
					}else {
						return 0;
					}
				}else if(IPV4_TABLE_COLUMN[7].equals(property)) {
					return model.getNetworkMask()!=null?model.getNetworkMask().getHostAddress():"";
				}else if(IPV4_TABLE_COLUMN[8].equals(property)) {
					return model.getTtl().toString();
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if(property.trim().length()>0) {
					return true;
				}
				return false;
			}
		});
		
	}

	private class IPv4ModelContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).stream().filter(e->{ return (e instanceof Ipv4Configuration); }).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class IPv4ModelLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof Ipv4Configuration) {
				Ipv4Configuration e = (Ipv4Configuration)element;
				switch(columnIndex) {
				case 1: return String.valueOf(e.getAssignmentPriority());
				case 2: return e.getDefaultGateway()!=null?e.getDefaultGateway().getHostAddress():"";
				case 3: return e.getDnsServerAddress().isEmpty()?"":getDnsIPv4AddressSetString(e);
				case 4: {
						if(IpAddressKeepEnum.FORGET.equals(e.getIpAddressKeepBehavior())) {
							return IPV4_IP_ADDRESS_KEEP_ENUM_VALUES[0];
						} else if(IpAddressKeepEnum.STORE_PERSISTENTLY.equals(e.getIpAddressKeepBehavior())) {
							return IPV4_IP_ADDRESS_KEEP_ENUM_VALUES[1];
						}
						return "";
					}
				case 5: return e.getIpv4Address()!=null?e.getIpv4Address().getHostAddress():"";
				case 6: {
						if(Ipv4AddressSourceEnum.AUTO_IP.equals(e.getIpv4AddressSource())) {
							return IPV4_IPV4_ADDRESS_SOURCE_ENUM_VALUES[0];
						} else if(Ipv4AddressSourceEnum.AUTO_IP_DOIP.equals(e.getIpv4AddressSource())) {
							return IPV4_IPV4_ADDRESS_SOURCE_ENUM_VALUES[1];
						} else if(Ipv4AddressSourceEnum.DHCPV4.equals(e.getIpv4AddressSource())) {
							return IPV4_IPV4_ADDRESS_SOURCE_ENUM_VALUES[2]; //"dhcpv4";
						} else if(Ipv4AddressSourceEnum.FIXED.equals(e.getIpv4AddressSource())) {
							return IPV4_IPV4_ADDRESS_SOURCE_ENUM_VALUES[3]; //"fixed";
						}
						return "";
					}
				case 7: return e.getNetworkMask()!=null?e.getNetworkMask().getHostAddress():"";
				case 8: return String.valueOf(e.getTtl());
				}
			}
			return "";
		}
	}
	
	private Inet4Address createInet4Address(String address) {
		String[] tokens = address.split("\\.");
		if(tokens.length == 4) {
			byte[] addr = new byte[4];
			try {
				for (int i = 0; i < addr.length; i++) {
					addr[i] = (byte)Integer.parseInt(tokens[i]);
				}
				return (Inet4Address)InetAddress.getByAddress(addr);
			}catch(Exception e) {
				
			}
		}
		return null;
	}
	
	private String getDnsIPv4AddressSetString(Ipv4Configuration conf) {
		StringBuilder buf = new StringBuilder();
		if(!conf.getDnsServerAddress().isEmpty()) {
			buf.append(conf.getDnsServerAddress().get(0).getHostAddress());
			if(conf.getDnsServerAddress().size()>1) {
				for (int i = 1; i < conf.getDnsServerAddress().size(); i++) {
					buf.append(", ").append(conf.getDnsServerAddress().get(i).getHostAddress());
				}
			}
		}
		return buf.toString();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String[] TABLE_COLUMN				 = {
			"",
			"Assignment priority",
			"Default router",
			"DNS server address",
			"IP address keep behavior",
			"IPv6 address",
			"IPv6 address source",
			"IP address prefix length",
			"Hop count",
			"Enable anycast",
	}; 
	public static final int[] TABLE_COLUMN_WIDTH				 = {
			0, 100, 100, 100, 100, 100, 100, 100, 100, 100 
	}; 
	public static final int[] TABLE_COLUMN_ALIGN			 = {
			SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT, SWT.LEFT
	}; 
	public static final boolean[] TABLE_COLUMN_LISTENER			 = {
			false, false, false, false, false, false, false, false, false, false
	}; 
	public static final boolean[] TABLE_COLUMN_RESIZABLE			 = {
			false, true, true, true, true, true, true, true, true, true
	}; 
	
	public static final String[] IP_ADDRESS_KEEP_ENUM_VALUES = {"forget", "storePersistently"};
	public static final String[] IPV6_ADDRESS_SOURCE_ENUM_VALUES = {"dhcpv6", "fixed", "linkLocal", "linkLocal_doip", "routerAdvertisement"};

	protected void hookIPv6Listeners() {
//		private TableViewer tableIpv6Configuration;
//		private Button buttonAddIpv6Configuration;
//		private Button buttonRemoveIpv6Configuration;
		tableIpv6Configuration.setColumnProperties(TABLE_COLUMN);
		tableIpv6Configuration.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(tableIpv6Configuration.getTable()),
				new TextCellEditor(tableIpv6Configuration.getTable()),
				new DialogCellEditor(tableIpv6Configuration.getTable()) {
					
					@Override
					protected Object openDialogBox(Control cellEditorWindow) {
						DnsServerIPv6Dialog d = new DnsServerIPv6Dialog(cellEditorWindow.getShell(), ((Ipv6Configuration)(tableIpv6Configuration.getStructuredSelection().getFirstElement())));
						if(d.open() == IDialogConstants.OK_ID) {
							return d.getDnsList(); 
						}
						return null;
					}
				},
				new ComboBoxCellEditor(tableIpv6Configuration.getTable(), IP_ADDRESS_KEEP_ENUM_VALUES),
				new TextCellEditor(tableIpv6Configuration.getTable()),
				new ComboBoxCellEditor(tableIpv6Configuration.getTable(), IPV6_ADDRESS_SOURCE_ENUM_VALUES),
				new TextCellEditor(tableIpv6Configuration.getTable()),
				new TextCellEditor(tableIpv6Configuration.getTable()),
				new ComboBoxCellEditor(tableIpv6Configuration.getTable(), APSectionUIFactory.COMBO_BOOLEAN_VALUE),
		});
		tableIpv6Configuration.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(final Object element, final String property, final Object value) {
				final Ipv6Configuration model = (Ipv6Configuration)((TableItem)element).getData();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject arg) throws Exception {
						if(TABLE_COLUMN[1].equals(property)) {
							model.setAssignmentPriority(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[2].equals(property)) {
							model.setDefaultRouter(createInet6Address((String)value));
						}else if(TABLE_COLUMN[3].equals(property)) {
							if(value instanceof List) {
								model.getDnsServerAddress().clear();
								for (String string : ((List<String>)value)) {
									model.getDnsServerAddress().add(createInet6Address(string));
								}
							}
						}else if(TABLE_COLUMN[4].equals(property)) {
							if( ((Integer)value) == 0 ) {
								model.setIpAddressKeepBehavior(IpAddressKeepEnum.FORGET);
							}else {
								model.setIpAddressKeepBehavior(IpAddressKeepEnum.STORE_PERSISTENTLY);
							}
						}else if(TABLE_COLUMN[5].equals(property)) {
							model.setIpv6Address(createInet6Address((String)value));
						}else if(TABLE_COLUMN[6].equals(property)) {
							if( ((Integer)value) == 0 ) {
								model.setIpv6AddressSource(Ipv6AddressSourceEnum.DHCPV6);
							}else if( ((Integer)value) == 1 ) {
								model.setIpv6AddressSource(Ipv6AddressSourceEnum.FIXED);
							}else if( ((Integer)value) == 2 ) {
								model.setIpv6AddressSource(Ipv6AddressSourceEnum.LINK_LOCAL);
							}else if( ((Integer)value) == 3 ) {
								model.setIpv6AddressSource(Ipv6AddressSourceEnum.LINK_LOCAL_DOIP);
							}else if( ((Integer)value) == 4 ) {
								model.setIpv6AddressSource(Ipv6AddressSourceEnum.ROUTER_ADVERTISEMENT);
							}
						}else if(TABLE_COLUMN[7].equals(property)) {
							model.setIpAddressPrefixLength(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[8].equals(property)) {
							model.setHopCount(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[9].equals(property)) {
							if( ((Integer)value) == 0 ) {
								model.setEnableAnycast(true);
							}else {
								model.setEnableAnycast(false);
							}
						}
						return model;
					}
				});
				tableIpv6Configuration.refresh();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				Ipv6Configuration model = (Ipv6Configuration)element;
				if(TABLE_COLUMN[1].equals(property)) {
					return model.getAssignmentPriority().toString();
				}else if(TABLE_COLUMN[2].equals(property)) {
					return model.getDefaultRouter()!=null?model.getDefaultRouter().getHostAddress():"";
				}else if(TABLE_COLUMN[3].equals(property)) {
					return getDnsIPv6AddressSetString(model);
				}if(TABLE_COLUMN[4].equals(property)) {
					return IpAddressKeepEnum.STORE_PERSISTENTLY.equals(model.getIpAddressKeepBehavior())?1:0;
				}else if(TABLE_COLUMN[5].equals(property)) {
					return model.getIpv6Address()!=null?model.getIpv6Address().getHostAddress():"";
				}if(TABLE_COLUMN[6].equals(property)) {
					if(Ipv6AddressSourceEnum.FIXED.equals(model.getIpv6AddressSource())) {
						return 1;
					}else if(Ipv6AddressSourceEnum.LINK_LOCAL.equals(model.getIpv6AddressSource())) {
						return 2;
					}else if(Ipv6AddressSourceEnum.LINK_LOCAL_DOIP.equals(model.getIpv6AddressSource())) {
						return 3;
					}else if(Ipv6AddressSourceEnum.ROUTER_ADVERTISEMENT.equals(model.getIpv6AddressSource())) {
						return 4;
					}else {
						return 0;
					}
				}else if(TABLE_COLUMN[7].equals(property)) {
					return model.getIpAddressPrefixLength().toString();
				}else if(TABLE_COLUMN[8].equals(property)) {
					return model.getHopCount().toString();
				}if(TABLE_COLUMN[9].equals(property)) {
					return model.isSetEnableAnycast()?0:1;
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if(property.trim().length()>0) {
					return true;
				}
				return false;
			}
		});
		
	}

	private class ModelContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).stream().filter(e->{ return (e instanceof Ipv6Configuration); }).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class ModelLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof Ipv6Configuration) {
				Ipv6Configuration e = (Ipv6Configuration)element;
				switch(columnIndex) {
				case 1: return String.valueOf(e.getAssignmentPriority());
				case 2: return e.getDefaultRouter()!=null?e.getDefaultRouter().getHostAddress():"";
				case 3: return e.getDnsServerAddress().isEmpty()?"":getDnsIPv6AddressSetString(e);
				case 4: {
						if(IpAddressKeepEnum.FORGET.equals(e.getIpAddressKeepBehavior())) {
							return IP_ADDRESS_KEEP_ENUM_VALUES[0];
						} else if(IpAddressKeepEnum.STORE_PERSISTENTLY.equals(e.getIpAddressKeepBehavior())) {
							return IP_ADDRESS_KEEP_ENUM_VALUES[1];
						}
						return "";
					}
				case 5: return e.getIpv6Address()!=null?e.getIpv6Address().getHostAddress():"";
				case 6: {
						if(Ipv6AddressSourceEnum.DHCPV6.equals(e.getIpv6AddressSource())) {
							return IPV6_ADDRESS_SOURCE_ENUM_VALUES[0];
						} else if(Ipv6AddressSourceEnum.FIXED.equals(e.getIpv6AddressSource())) {
							return IPV6_ADDRESS_SOURCE_ENUM_VALUES[1];
						} else if(Ipv6AddressSourceEnum.LINK_LOCAL.equals(e.getIpv6AddressSource())) {
							return IPV6_ADDRESS_SOURCE_ENUM_VALUES[2];
						} else if(Ipv6AddressSourceEnum.LINK_LOCAL_DOIP.equals(e.getIpv6AddressSource())) {
							return IPV6_ADDRESS_SOURCE_ENUM_VALUES[3];
						} else if(Ipv6AddressSourceEnum.ROUTER_ADVERTISEMENT.equals(e.getIpv6AddressSource())) {
							return IPV6_ADDRESS_SOURCE_ENUM_VALUES[3];
						}
						return "";
					}
				case 7: return e.getIpAddressPrefixLength().toString();
				case 8: return e.getHopCount().toString();
				case 9: return e.isSetEnableAnycast()?APSectionUIFactory.COMBO_BOOLEAN_VALUE[APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE]:APSectionUIFactory.COMBO_BOOLEAN_VALUE[APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE];
				}
			}
			return "";
		}
	}
	
	/**
	 * 2바이트 단위 16진수를 :으로 구분하여 8개를 나열한다.
	 * FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF:FFFF
	 * @param address
	 * @return
	 */
	private Inet6Address createInet6Address(String address) {
		String[] tokens = address.split(":");
		if(tokens.length == 8) {
			byte[] addr = new byte[16];
			try {
				String fillAddr = address.replaceAll(":", "");
				int addrIndex = 0;
				for (int i = 0; i < addr.length; i++) {
					addr[i] = ValueUtil.parseHex2Byte(fillAddr.substring(addrIndex, addrIndex+2));
					addrIndex += 2;
				}
				return (Inet6Address)InetAddress.getByAddress(addr);
			}catch(Exception e) {
				
			}
		}
		return null;
	}
	
	private String getDnsIPv6AddressSetString(Ipv6Configuration conf) {
		StringBuilder buf = new StringBuilder();
		if(!conf.getDnsServerAddress().isEmpty()) {
			buf.append(conf.getDnsServerAddress().get(0).getHostAddress());
			if(conf.getDnsServerAddress().size()>1) {
				for (int i = 1; i < conf.getDnsServerAddress().size(); i++) {
					buf.append(", ").append(conf.getDnsServerAddress().get(i).getHostAddress());
				}
			}
		}
		return buf.toString();
	}

}
