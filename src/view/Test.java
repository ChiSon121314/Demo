package view;

import controller.TankController;
import model.TankModel;

public class Test {
	public static void main(String[] args) {
		TankModel model = new TankModel();
		TankController controller = new TankController(model);
	}

}
