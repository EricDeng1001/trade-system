package ai.techfin.tradesystem.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotExistException extends AbstractThrowableProblem {

    private static final long serialVersionUID = -3646560486186551292L;

    public ResourceNotExistException() {
        super(ErrorConstants.RESOURCE_NOT_EXIST_TYPE, "The requested resource does not exist", Status.NOT_FOUND);
    }
}
