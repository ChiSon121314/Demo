package controller;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import model.Tank;
import model.TankBot;
import model.TankModel;
import view.PvEModeView;
import view.PvPModeView;
import view.TankView;

public class TankController implements ControllerInterface {
	private TankModel model = new TankModel();
	private TankView view;
	private PvEModeView pve;
	private PvPModeView pvp;

	public TankController(TankModel model) {
		super();
		this.model = model;
		this.view = new TankView(this, model);
		view.createMainMenu();
	}

	public void playPvE() {
		view.createSkinMenu();
	}
	public void playPvP() {
		view.startGamePvP();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


}
