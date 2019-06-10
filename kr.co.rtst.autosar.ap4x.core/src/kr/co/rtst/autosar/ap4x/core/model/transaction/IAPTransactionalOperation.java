package kr.co.rtst.autosar.ap4x.core.model.transaction;

import gautosar.ggenericstructure.ginfrastructure.GARObject;

@FunctionalInterface
public interface IAPTransactionalOperation {

	/**
	 * 연산을 처리한다.
	 * @return
	 */
	GARObject doProcess(GARObject model) throws Exception;
}
