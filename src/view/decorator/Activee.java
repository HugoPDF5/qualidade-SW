package view.decorator;


/**
 * @see model.Person#ACTIVE
 */
public class Activee extends Statuss {
	public String setTextToTheDeactiveOrActiveButton(String statusState, ShowTeacherDecorator showTeacherDecorator) {
		showTeacherDecorator.getDisableTeacherBtn().setText("Desativar professor");
		statusState = "desativado";
		return statusState;
	}
}