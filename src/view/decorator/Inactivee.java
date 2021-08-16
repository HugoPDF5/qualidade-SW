package view.decorator;


/**
 * @see model.Person#INACTIVE
 */
public class Inactivee extends Statuss {
	public String setTextToTheDeactiveOrActiveButton(String statusState, ShowTeacherDecorator showTeacherDecorator) {
		showTeacherDecorator.getDisableTeacherBtn().setText("Ativar professor");
		statusState = "ativado";
		return statusState;
	}
}