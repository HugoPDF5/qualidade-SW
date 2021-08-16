package view.decorator;


/**
 * @see model.Person#INACTIVE
 */
public class Inactive extends Status {
	public String setTextToTheInactiveOrActiveButton(String enrollmentStatus,
			ShowStudentDecorator showStudentDecorator) {
		showStudentDecorator.getDeactivateOrActivateButton().setText("Ativar matrícula");
		enrollmentStatus = "ativada";
		return enrollmentStatus;
	}
}