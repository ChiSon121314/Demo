package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import controller.PvEModeController;
import controller.PvPModeController;
import controller.TankController;
import model.Brick;
import model.LiveObserver;
import model.ScoreObserver;
import model.Tank;
import model.TankBot;
import model.TankModel;

public class TankView extends JFrame implements ScoreObserver, LiveObserver {
	private TankController controller;
	private TankModel model;
	private JButton playButton, tutorialButton, quitButton, play, cancel;
	private JFrame mainMenu, skinFrame, pVEFrame, pVPFrame;
	private PvPModeView pvPView;
	private PvEModeView pvEView;
	private PvEModeController pve;
	private PvPModeController pvp;
	private Brick brick;

	private static final int TANK_SIZE = 30;
	private static final int GUN_WIDTH = 6;
	private static final int GUN_HEIGHT = 6;
	private JRadioButton red, blue, green;
	private JPanel selectPanel, skinPanel, buttonPanel, backgroundPanel;

	public TankView(TankController controller, TankModel model) {
		this.controller = controller;
		this.model = model;
		model.addPlayer(new Tank(210, 550, 1, "Player 1"));
		model.addPlayer(new Tank(410, 550, 2, "Player 2"));

		model.registerScoreObserver(this);
		model.registerLiveObserver(this);
	}

	public void createMainMenu() {
		mainMenu = new JFrame();
		mainMenu.setTitle("Tank Game");
		mainMenu.setSize(600, 600);
		mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenu.setResizable(false);
		mainMenu.setLayout(new BorderLayout());

		BackgroundPanel backgroundPanel = new BackgroundPanel("D:/Wordspace/TankGame/src/image/menu.png");
		mainMenu.setContentPane(backgroundPanel);

		// set title
		JPanel title = new JPanel();
		title.setOpaque(false);
		title.setPreferredSize(new Dimension(500, 100));
		JLabel t = new JLabel("TANK GAME");
		t.setFont(new Font(null, Font.BOLD, 70));
		t.setForeground(Color.WHITE);
		title.setBorder(new EmptyBorder(10, 10, 10, 10));
		title.add(t);
		mainMenu.add(title, BorderLayout.NORTH); // set button

		JPanel button = new JPanel();
		button.setOpaque(false);
		button.setLayout(new GridLayout(3, 1, 0, 20));
		playButton = new JButton("Play");
		tutorialButton = new JButton("Tutorial");
		quitButton = new JButton("Quit");

		Font buttonFont = new Font("Arial", Font.BOLD, 30);
		playButton.setFont(buttonFont);
		tutorialButton.setFont(buttonFont);
		quitButton.setFont(buttonFont);

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPlayOption();
			}
		});

		tutorialButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showTutorial();
			}
		});

		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitGame();
			}
		});

		button.add(playButton);
		button.add(tutorialButton);
		button.add(quitButton);

		Dimension buttonSize = new Dimension(180, 60);
		playButton.setPreferredSize(buttonSize);
		tutorialButton.setPreferredSize(buttonSize);
		quitButton.setPreferredSize(buttonSize);
		button.setBorder(new EmptyBorder(200, 80, 60, 80));
		mainMenu.add(button, BorderLayout.CENTER);

		mainMenu.setLocationRelativeTo(null);
		mainMenu.setVisible(true);
	}

	// Xử lý sự kiện
	public void showPlayOption() {
		String[] options = { "Chơi đơn", "PK (Người chơi vs Người chơi)" };

		int choice = JOptionPane.showOptionDialog(this, "Chọn chế độ chơi:", "Chọn chế độ", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		switch (choice) {
		case 0: // Chơi đơn
			controller.playPvE();
			break;
		case 1: // PK (Người chơi vs Người chơi)
			controller.playPvP();
			break;
		default:
			break;
		}
	}

	public void showTutorial() {
		JOptionPane.showMessageDialog(this,
				"Hướng dẫn chơi đơn:\n" + "- Người chơi 1 di chuyển bằng các phím W, A, S, D và bắn bằng phím SPACE.\n"
						+ "- Mục tiêu là đạt được điểm cao nhất!\n"
						+ "- Khi hạ được kẻ địch người chơi sẽ được 1000 điểm tương ứng với mỗi kẻ địch bị tiêu diệt.\n"
						+ "- Kẻ địch sẽ mạnh dần theo số điểm người chơi.\n" + "- Nhấn R để tạo lại màn chơi.\n"
						+ "- Nhấn M để trở lại màn hình chính của trò chơi.\n" + "Hướng dẫn chơi đôi:\n"
						+ "- Người chơi 1 di chuyển bằng các phím W, A, S, D và bắn bằng phím SPACE.\n"
						+ "- Người chơi 2 di chuyển bằng các phím mũi tên và bắn bằng phím ENTER.\n"
						+ "- Mục tiêu là bắn trúng đối thủ để giành chiến thắng.\n"
						+ "- Nhấn R để tạo lại màn chơi khi một trong hai người chơi thua cuộc.\n"
						+ "- Nhấn M để trở lại màn hình chính của trò chơi.\n" + "Chúc bạn chơi vui!",
				"Hướng Dẫn", JOptionPane.INFORMATION_MESSAGE);
	}

	public void quitGame() {
		int response = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát không?", "Xác nhận",
				JOptionPane.YES_NO_OPTION);
		if (response == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public void createSkinMenu() {
		skinFrame = new JFrame("Select your skin");
		skinFrame.setSize(300, 200);
		skinFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		skinFrame.setLayout(new BorderLayout());

		red = new JRadioButton("Red");
		blue = new JRadioButton("Blue");
		green = new JRadioButton("Green");

		play = new JButton("Play");
		cancel = new JButton("Cancel");

		selectPanel = new JPanel();
		skinPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawSkin(g);
			}
		};
		buttonPanel = new JPanel();

		skinPanel.setPreferredSize(new Dimension(300, 150));

		selectPanel.setBorder(BorderFactory.createTitledBorder("Select your skin"));
		skinPanel.setBorder(BorderFactory.createTitledBorder("Skin"));

		selectPanel.add(red);
		selectPanel.add(blue);
		selectPanel.add(green);

		buttonPanel.add(play);
		buttonPanel.add(cancel);
		buttonPanel.setLayout(new FlowLayout());

		// Group the radio buttons
		ButtonGroup group = new ButtonGroup();
		group.add(red);
		group.add(blue);
		group.add(green);

		red.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skinPanel.repaint();
			}
		});

		blue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skinPanel.repaint();
			}
		});

		green.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skinPanel.repaint();
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skinFrame.dispose();
			}
		});

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tankColor = 0;
				if (red.isSelected()) {
					tankColor = 1;
				} else if (blue.isSelected()) {
					tankColor = 2;
				} else if (green.isSelected()) {
					tankColor = 3;
				}
				startGamePvE(tankColor); // Phương thức này cần được triển khai
				skinFrame.dispose();
			}
		});

		skinFrame.add(selectPanel, BorderLayout.NORTH);
		skinFrame.add(skinPanel, BorderLayout.CENTER);
		skinFrame.add(buttonPanel, BorderLayout.SOUTH);

		skinFrame.setLocationRelativeTo(null);
		skinFrame.setVisible(true);
	}

	public void drawSkin(Graphics g) {
		Dimension skinSize = skinPanel.getSize();
		int x = (int) ((skinSize.getWidth() - TANK_SIZE) / 2); // Calculate x coordinate to center the skin
		int y = (int) ((skinSize.getHeight() - TANK_SIZE + 12) / 2); // Calculate y coordinate to center the skin

		Color tankColor = null;
		if (red.isSelected()) {
			tankColor = Color.RED;
			g.drawImage(new ImageIcon("D:/Wordspace/TankGame/src/image/tank1_up.png").getImage(), x, y, null);
		} else if (blue.isSelected()) {
			tankColor = Color.BLUE;
			g.drawImage(new ImageIcon("D:/Wordspace/TankGame/src/image/tank2_up.png").getImage(), x, y, null);
		} else if (green.isSelected()) {
			tankColor = Color.GREEN;
			g.drawImage(new ImageIcon("D:/Wordspace/TankGame/src/image/tank3_up.png").getImage(), x, y, null);
		} else {
			// Default tank color or image if none selected
			tankColor = Color.GRAY;
			g.fillRect(x, y, TANK_SIZE, TANK_SIZE); // Draw a default tank shape
			g.fillRect(x + (TANK_SIZE - GUN_WIDTH) / 2, y - GUN_HEIGHT, GUN_WIDTH, GUN_HEIGHT); // Draw tank gun
		}

//	    g.setColor(tankColor);
//	    g.fillRect(x, y, TANK_SIZE, TANK_SIZE); // Draw tank body
//	    g.fillRect(x + (TANK_SIZE - GUN_WIDTH) / 2, y - GUN_HEIGHT, GUN_WIDTH, GUN_HEIGHT); // Draw tank gun
	}

	public void startGamePvE(int color) {
		// Create player tank and bot tank
		Tank player1 = new Tank(200, 550, color, "Player");
		TankBot bot = new TankBot(200, 200, 4, "Bot", player1);

		// Create the game view and controller
		pvEView = new PvEModeView(player1, bot);
		pve = new PvEModeController(player1, bot, pvEView);

		// Create and configure the game frame
		pVEFrame = new JFrame();
		pVEFrame.setBounds(10, 10, 800, 630);
		pVEFrame.setTitle("PvE Mode");
		pVEFrame.setResizable(false);
		pVEFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pVEFrame.add(pvEView);
		pVEFrame.setVisible(true);
		pVEFrame.addKeyListener(pve);
		pVEFrame.setLocationRelativeTo(null);

		// Hide the main menu
		mainMenu.setVisible(false);
		pVEFrame.setVisible(true);
		pVEFrame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_M) {
					mainMenu.setVisible(true);
					pVEFrame.dispose(); // Close the current game frame
				}
			}
		});
	}

	public void startGamePvP() {
		List<Tank> players = model.getPlayers();
		brick = new Brick();

		Tank player1 = players.get(0);
		Tank player2 = players.get(1);

		pvPView = new PvPModeView(player1, player2, brick);
		pvp = new PvPModeController(player1, player2, pvPView, brick);

		pVPFrame = new JFrame();
		pVPFrame.setBounds(10, 10, 800, 620);
		pVPFrame.setTitle("PvP Mode");
		pVPFrame.setResizable(false);
		pVPFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pVPFrame.add(pvPView);
		pVPFrame.setVisible(true);
		pVPFrame.addKeyListener(pvp);
		pVPFrame.setLocationRelativeTo(null);

		// Ẩn menu
		mainMenu.dispose();
		pVPFrame.setVisible(true);
		pVPFrame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_M) {
					createMainMenu();
					pVPFrame.dispose(); // Đóng cửa sổ hiện tại
				}
			}
		});
	}

	public void updateScore(String playerName, int score) {
		if (pvPView != null) {
			pvPView.updateScore(playerName, score);
		}
	}

	public void updateLives(String playerName, int lives) {
		if (pvPView != null) {
			pvPView.updateLives(playerName, lives);
		}
	}

	class BackgroundPanel extends JPanel {
		private Image backgroundImage;

		public BackgroundPanel(String fileName) {
			try {
				backgroundImage = new ImageIcon(fileName).getImage();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (backgroundImage != null) {
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
			}
		}
	}
}
