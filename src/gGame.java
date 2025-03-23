import javax.swing.*;

public class gGame {
    private static final int MAX_count = 10;
    private static final int MAX_number = 100;

    public static void main(String[] args) {
        boolean gameOver = false;
        int Score = 0;

        while (!gameOver) {
            int comNumber = (int) (Math.random() * MAX_number + 1);
            int userAnswer = -1;
            int count = 1;
            while (userAnswer != comNumber && count <= MAX_count) {
                //Lấy giá trị thông tin từ người dùng qua hộp thoại
                String input = User(Score);
                //Dong hộp thoại
                if (input == null) {
                    gameOver = true;
                    break;
                }

                //Kiểm tra người dùng có nhập đúng không
                try {
                    userAnswer = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập lại!");
                    continue;
                }
                //Hiện thị kết quả và gợi ý
                JOptionPane.showMessageDialog(null, "" + Answer(userAnswer, comNumber, count));
                count++;

            }
            if (count > 10) {
                JOptionPane.showMessageDialog(null, "Đã hết số lượt thử");
            }
            int reponse = JOptionPane.showConfirmDialog(null, "Bạn muốn chơi tiếp không?", "Tiếp tục", JOptionPane.YES_NO_OPTION);
            if (JOptionPane.NO_OPTION == reponse) {
                gameOver = true;
            }
        }
        JOptionPane.showMessageDialog(null, "Cảm ơn bạn đã chơi!");
    }

    public static String Answer(int userAnswer, int comNumber, int count) {
        String gan = "Số gần đúng!";
        if (userAnswer < 0 || userAnswer > MAX_number) {
            return "Không hợp lệ!";
        }
        if (userAnswer == comNumber) {
            return String.format("Chính xác! Số đúng là: %d\nLần đoán thứ %d", userAnswer, count);
        }
        String feedBack = (userAnswer > comNumber) ? "Số bạn cao hơn số cần dự đoán. Lần đoán thứ " + count : "Số bạn thấp hơn số cần dự đoán. Lần đoán thứ " + count;
        if (Math.abs(userAnswer - comNumber) <= 5) {
            feedBack = gan + feedBack;
        }
        return feedBack;
    }

    public static String User(int Score) {
        return JOptionPane.showInputDialog(null, "Số điểm hiện tại: " + Score + "\nNhập số từ trong khoảng 1-100", "Trò chơi đoán số", JOptionPane.QUESTION_MESSAGE);
    }
}

