import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class gamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20; //Ô hiển thị
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; //Tổng ô
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS]; //tọa độ x thân rắn
    final int y[] = new int[GAME_UNITS]; // tọa độ y thân rán
    int bodyParts = 6; //độ dài của rắn
    int appleEaten; // so luong tao an
    int appleX; // toa do tao
    int appleY; // toa do tao
    char direction = 'R'; //Hướng di chuyển
    boolean running = false;
    Timer timer; // bộ đếm thời gian đẻ kiểm soát tốc độ cập nhật game
    Random random;

    gamePanel() {
        random = new Random(); // sd để đặt vị trí của táo
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT)); // Kích thước ưu tiển
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter() {
            //Đặt panel có thể nhận focus và thêm bộ lắng nghe sự kiện bàn phím.
            //Tuy nhiên, KeyAdapter được khởi tạo mà không có nội dung, nên không có hành động nào được xử lý.
        });
        startGame();
    }

    public void startGame() {
        newApple();//đặt vị trí táo
        running = true;
        timer = new Timer(DELAY, this); //khởi động timer với độ trễ xác định, liên kết với ActionListener để cập nhật trò chơi theo chu kỳ
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Đảm bảo panel đc vẽ đúng cách
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {

            //Vẽ lưới
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            //Vẽ táo
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Vẽ rắn
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //Vẽ một hình chữ nhật đầy màu tại tọa độ (x[i], y[i]) với kích thước UNIT_SIZE x UNIT_SIZE.
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }

                //Hiện điểm số
                g.setColor(Color.red);
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                FontMetrics metrics = g.getFontMetrics(g.getFont());
                g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten)) / 2, g.getFont().getSize());

            }
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        //Đặt vị trí ngẫu nhiên của táo
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;

    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    public void checkApple() {
        //Đầu rắn x[0] y[0] chạm táo
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            appleEaten++;
            newApple();
        }

    }

    public void checkCollisons() {
        //check head collisons with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        //check head touches left boder
        if (x[0] < 0) running = false;
        //check head touches right boder
        if (x[0] >= SCREEN_WIDTH) running = false;
        //check head touches top boder
        if (y[0] < 0) running = false;
        //check head touches bottom boder
        if (y[0] >= SCREEN_HEIGHT) running = false;

        if (!running) timer.stop();
    }

    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        FontMetrics metrics1 = g.getFontMetrics(g.getFont());
        g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + appleEaten)) / 2, g.getFont().getSize());
        //Game over text
        g.setColor(Color.red);
        g.setFont(new Font("TimesRoman", Font.BOLD, 75));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisons();
        }
        repaint(); // vẽ lại màn hình
    }

    public class MyKeyAdapter extends KeyAdapter { //Xử lý sự kiện bàn phím
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') direction = 'L'; // ngăn chặn đổi quay dau
                }
                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') direction = 'R';
                }
                case KeyEvent.VK_UP -> {
                    if (direction != 'D') direction = 'U';
                }
                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') direction = 'D';
                }
            }
        }
    }
}
