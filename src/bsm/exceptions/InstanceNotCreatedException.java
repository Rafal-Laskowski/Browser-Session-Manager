package bsm.exceptions;

public class InstanceNotCreatedException extends RuntimeException {
	private static final long serialVersionUID = -9104979948810397669L;

	public InstanceNotCreatedException(Object object) {
		super(String.format("Instance was not created for class %s", object.getClass().getName()));
	}
}
