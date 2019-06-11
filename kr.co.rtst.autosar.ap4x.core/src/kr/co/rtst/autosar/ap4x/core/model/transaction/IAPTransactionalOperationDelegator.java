package kr.co.rtst.autosar.ap4x.core.model.transaction;

public interface IAPTransactionalOperationDelegator {

	public static final int OP_STATE_READY = 10;
	public static final int OP_STATE_BUSY = 20;
	public static final int OP_STATE_DONE_SUCCESS = 100;
	public static final int OP_STATE_DONE_FAILED = 200;
	
	void doTransactionalOperation(IAPTransactionalOperation op);
	
	int getState();
}
