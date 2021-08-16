package view.decorator;


/**
 * @see model.Person#ACTIVE
 */
public class Active extends Status {
	public String setTextToTheInactiveOrActiveButton(String enrollmentStatus,
			ShowStudentDecorator showStudentDecorator) {
		showStudentDecorator.getDeactivateOrActivateButton().setText("Desativar matrícula");
		enrollmentStatus = "desativada";
		return enrollmentStatus;
	}
}